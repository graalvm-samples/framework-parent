package com.fushun.framework.dynamic.datasource;

import cn.hutool.core.util.ObjectUtil;
import com.fushun.framework.base.SpringContextUtil;
import com.fushun.framework.exception.DynamicBaseException;
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

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import java.lang.reflect.Method;

@Aspect
@Component
@Order(Ordered.HIGHEST_PRECEDENCE + 3)
@Slf4j
public class DataSourceAspect {

    // 拦截类级别的 @UseDataSource 注解
    @Pointcut("within(@com.fushun.framework.dynamic.datasource.UseDataSource *)")
    public void dataSourceAnnotatedClasses() {}

    // 拦截方法级别的 @UseDataSource 注解（可选）
    @Pointcut("@annotation(com.fushun.framework.dynamic.datasource.UseDataSource)")
    public void dataSourceAnnotatedMethods() {}

    // 合并切点：支持类和方法级别的注解
    @Pointcut("dataSourceAnnotatedClasses() || dataSourceAnnotatedMethods()")
    public void combinedDataSourcePointcut() {}

    @Before("combinedDataSourcePointcut()")
    public void switchDataSource(JoinPoint point) {
        try {
            // 获取目标方法
            MethodSignature methodSignature = (MethodSignature) point.getSignature();

            // 获取目标方法所在类上的 @UseDataSource 注解
            UseDataSource useDataSourceAnnotation = methodSignature.getMethod().getDeclaringClass().getAnnotation(UseDataSource.class);

            // 如果注解不为 null，则可以获取注解的值
            if (useDataSourceAnnotation != null) {
                String dataSourceName = useDataSourceAnnotation.value();
                // 使用 dataSourceName 进行切换数据源逻辑
                DataSourceContextHolder.setDataSource(dataSourceName);
            }
        } catch (Exception err) {
            log.error("", err);
        }
    }

    @Around("combinedDataSourcePointcut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        UseDataSource useDataSource = getUseDataSourceAnnotation(signature);
        // 动态获取事务管理器（假设命名规则为"数据源名称+TransactionManager"）
        String useDataSourceName=useDataSource.value();
        if(ObjectUtil.isEmpty(useDataSourceName)){
            useDataSourceName="yudaoDataSource";
        }
        String tmName = useDataSourceName + "TransactionManager";

        PlatformTransactionManager tm = SpringContextUtil.getAppContext().getBean(tmName, PlatformTransactionManager.class);

        // 使用编程式事务
        TransactionTemplate template = new TransactionTemplate(tm);
        return template.execute(status -> {
            try {
                return joinPoint.proceed();  // 执行原方法
            } catch (RuntimeException e) {
                throw e; // 运行时异常直接抛出
            } catch (Throwable e) {
                throw new DynamicBaseException(e,"TransactionManager exception","TransactionManager exception");  // 异常转换为RuntimeException以触发回滚
            }
        });
    }

    @After("combinedDataSourcePointcut()")
    public void restoreDataSource(JoinPoint point) {
        DataSourceContextHolder.clearDataSource();
    }

    // 辅助方法：获取类或方法上的 @UseDataSource 注解
    private UseDataSource getUseDataSourceAnnotation(MethodSignature signature) {
        Method method = signature.getMethod();
        UseDataSource annotation = method.getAnnotation(UseDataSource.class);
        if (annotation == null) {
            return method.getDeclaringClass().getAnnotation(UseDataSource.class);
        }
        return annotation;
    }
}
