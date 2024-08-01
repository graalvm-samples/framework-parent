package com.fushun.framework.dynamic.datasource;

import java.util.concurrent.ConcurrentHashMap;

public class DataSourceContextHolder {

    private static final ConcurrentHashMap<Long,String> contextHolder = new ConcurrentHashMap<>();

    public static void setDataSource(String dataSourceType) {
        contextHolder.put(Thread.currentThread().threadId(),dataSourceType);
    }

    public static String getDataSource() {
        return contextHolder.get(Thread.currentThread().threadId());
    }

    public static void clearDataSource() {
        contextHolder.remove(Thread.currentThread().threadId());
    }
}

