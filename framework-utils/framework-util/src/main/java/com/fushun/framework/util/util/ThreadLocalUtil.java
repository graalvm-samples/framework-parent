package com.fushun.framework.util.util;

import java.util.concurrent.ConcurrentHashMap;


public class ThreadLocalUtil {

    static final ThreadLocal<ConcurrentHashMap<String,Object>> map=
            new ThreadLocal<ConcurrentHashMap<String,Object>>();

    public static <T> void setData(String key,T data){
        get().put(key,data);
    }

    public static <T> T getData(String key){
        return (T)get().get(key);
    }

    private static ConcurrentHashMap<String,Object> get(){
        ConcurrentHashMap<String,Object> concurrentHashMap=map.get();
        if(concurrentHashMap==null){
            map.set(new ConcurrentHashMap<>());
        }
        return  map.get();
    }


}

