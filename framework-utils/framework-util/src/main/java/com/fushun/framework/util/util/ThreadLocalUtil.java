package com.fushun.framework.util.util;

import java.util.concurrent.ConcurrentHashMap;

public class ThreadLocalUtil {

    static final ConcurrentHashMap<Long,ConcurrentHashMap<String, Object>> map=
            new ConcurrentHashMap<>();


    public static <T> void setData(String key,T data){
        get().put(key,data);
    }

    @SuppressWarnings("unchecked")
    public static <T> T getData(String key){
        ConcurrentHashMap<String,Object> c=get();
        return (T) c.get(key);
    }

    private static ConcurrentHashMap<String,Object> get(){
        long virtualThreadId = Thread.currentThread().threadId();
        ConcurrentHashMap<String,Object> concurrentHashMap=map.get(virtualThreadId);
        if(concurrentHashMap==null){
            map.put(virtualThreadId,new ConcurrentHashMap<>());
        }
        return  map.get(virtualThreadId);
    }

    public static void remove(){
        long virtualThreadId = Thread.currentThread().threadId();
        map.remove(virtualThreadId);
    }

    public static void removeKey(String key){
        get().remove(key);
    }


}
