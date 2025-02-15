package com.fushun.framework.util.util;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ThreadLocalUtil {

    static final ConcurrentHashMap<Long, ConcurrentHashMap<String, TimedValue>> map =
            new ConcurrentHashMap<>();

    // 定时任务的调度器
    private static final ScheduledExecutorService cleanupExecutor = Executors.newScheduledThreadPool(1, Thread.ofVirtual().factory());

    static {
        // 安排清理任务每小时运行一次
        cleanupExecutor.scheduleAtFixedRate(ThreadLocalUtil::cleanup, 1, 1, TimeUnit.HOURS);
    }

    public static <T> void setData(String key, T data) {
        get().put(key, new TimedValue(data));
    }

    @SuppressWarnings("unchecked")
    public static <T> T getData(String key) {
        TimedValue timedValue = get().get(key);
        if (timedValue != null) {
            timedValue.updateLastAccessTime(); // 更新上次访问时间
            return (T) timedValue.value;
        }
        return null;
    }

    private static ConcurrentHashMap<String, TimedValue> get() {
        long virtualThreadId = Thread.currentThread().threadId();
        return map.computeIfAbsent(virtualThreadId, k -> new ConcurrentHashMap<>());
    }

    public static void remove() {
        long virtualThreadId = Thread.currentThread().threadId();
        map.remove(virtualThreadId);
    }

    public static void removeKey(String key) {
        get().remove(key);
    }

    private static void cleanup() {
        long cutoffTime = System.currentTimeMillis() - TimeUnit.HOURS.toMillis(1);
        for (Long threadId : map.keySet()) {
            ConcurrentHashMap<String, TimedValue> dataMap = map.get(threadId);
            if (dataMap != null) {
                dataMap.entrySet().removeIf(entry -> entry.getValue().lastAccessTime < cutoffTime);
            }
        }
    }

    // 包装类，用于存储值和时间戳
    private static class TimedValue {
        private final Object value;
        private volatile long lastAccessTime;

        public TimedValue(Object value) {
            this.value = value;
            this.lastAccessTime = System.currentTimeMillis();
        }

        public void updateLastAccessTime() {
            this.lastAccessTime = System.currentTimeMillis();
        }
    }
}
