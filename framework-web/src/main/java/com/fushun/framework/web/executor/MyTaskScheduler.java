package com.fushun.framework.web.executor;

import cn.hutool.core.util.ObjectUtil;
import com.fushun.framework.util.util.UUIDUtil;
import org.slf4j.MDC;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;
import org.springframework.scheduling.support.ScheduledMethodRunnable;
import com.fushun.framework.web.config.filter.LogCostFilter;
import java.util.concurrent.Executor;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;

public class MyTaskScheduler extends ConcurrentTaskScheduler {

    public MyTaskScheduler() {
    }

    public MyTaskScheduler(ScheduledExecutorService scheduledExecutor) {
        super(scheduledExecutor);
    }

    public MyTaskScheduler(Executor concurrentExecutor, ScheduledExecutorService scheduledExecutor) {
        super(concurrentExecutor, scheduledExecutor);
    }

    // TODO override other methods

    @Override
    public ScheduledFuture<?> schedule(Runnable task, Trigger trigger) {
        return super.schedule(decorateTask(task), trigger);
    }

    private Runnable decorateTask(Runnable task) {
        // not 100% sure about safety of this cast
        return new MyRunnable(task);
    }

    private static class MyRunnable implements Runnable {

        private final Runnable runnable;

        public MyRunnable(Runnable runnable) {
            this.runnable = runnable;
        }

        @Override
        public void run() {
            //每一次调度的时候，都重新生成
            if(ObjectUtil.isEmpty(MDC.get(LogCostFilter.MDC_KEY_REQ_ID))){
                MDC.put(LogCostFilter.MDC_KEY_REQ_ID, UUIDUtil.getUUID().toString());
            }
            try {
                runnable.run();
            } finally {
                MDC.clear();
            }

        }
    }
}
