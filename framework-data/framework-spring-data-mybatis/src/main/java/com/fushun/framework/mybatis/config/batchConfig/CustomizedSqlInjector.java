package com.fushun.framework.mybatis.config.batchConfig;

import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.injector.DefaultSqlInjector;
import com.baomidou.mybatisplus.core.metadata.TableInfo;

import java.util.List;

/**
 * 自定义方法SQL注入器
 */
public class CustomizedSqlInjector extends DefaultSqlInjector {
    /**
     * 如果只需增加方法，保留mybatis plus自带方法，
     * 可以先获取super.getMethodList()，再添加add
     */
    @Override
    public List<AbstractMethod> getMethodList(Class<?> mapperClass, TableInfo tableInfo) {
        List<AbstractMethod> methodList = super.getMethodList(mapperClass, tableInfo);
        methodList.add(new InsertBatchMethod("InsertBatch"));
        methodList.add(new UpdateBatchMethod("UpdateBatch"));
        methodList.add(new MysqlInsertOrUpdateBath("MysqlInsertOrUpdate"));
        return methodList;
    }
}