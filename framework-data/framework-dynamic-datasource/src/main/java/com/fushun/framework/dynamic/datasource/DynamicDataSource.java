package com.fushun.framework.dynamic.datasource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class DynamicDataSource extends AbstractRoutingDataSource {
    @Override
    protected Object determineCurrentLookupKey() {
        // 可以根据自定义逻辑返回当前请求应当使用的数据源标识
        return DataSourceContextHolder.getDataSource();
    }
}
