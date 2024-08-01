package com.fushun.framework.dynamic.datasource;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Order(Ordered.HIGHEST_PRECEDENCE+3)
@Slf4j
public class DataSourceAspect {

    @Before("@annotation(useDataSource)")
    public void switchDataSource(JoinPoint point, UseDataSource useDataSource) {
        DataSourceContextHolder.setDataSource(useDataSource.value());
    }

    @After("@annotation(useDataSource)")
    public void restoreDataSource(JoinPoint point, UseDataSource useDataSource) {
        DataSourceContextHolder.clearDataSource();
    }

    @Pointcut("within(@com.fushun.framework.dynamic.datasource.UseDataSource *)")
    public void dataSourceAnnotatedClasses() {}

    @Before("dataSourceAnnotatedClasses()")
    public void switchDataSource(JoinPoint point) {
        try{
            // 获取目标方法
            MethodSignature methodSignature = (MethodSignature) point.getSignature();

            // 获取目标方法上的 @UseDataSource 注解
            UseDataSource useDataSourceAnnotation = methodSignature.getMethod().getDeclaringClass().getAnnotation(UseDataSource.class);

            // 如果注解不为 null，则可以获取注解的值
            if (useDataSourceAnnotation != null) {
                String dataSourceName = useDataSourceAnnotation.value();
                // 使用 dataSourceName 进行切换数据源逻辑
                DataSourceContextHolder.setDataSource(dataSourceName);
            }
        }catch (Exception err){
            log.error("",err);
        }
    }

    @After("dataSourceAnnotatedClasses()")
    public void restoreDataSource(JoinPoint point) {
        DataSourceContextHolder.clearDataSource();
    }
}
