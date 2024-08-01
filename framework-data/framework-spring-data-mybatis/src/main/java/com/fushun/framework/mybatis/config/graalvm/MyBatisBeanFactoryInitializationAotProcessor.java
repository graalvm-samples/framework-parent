package com.fushun.framework.mybatis.config.graalvm;

import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;
import org.mybatis.spring.mapper.MapperFactoryBean;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aot.hint.MemberCategory;
import org.springframework.aot.hint.RuntimeHints;
import org.springframework.beans.PropertyValue;
import org.springframework.beans.factory.aot.BeanFactoryInitializationAotContribution;
import org.springframework.beans.factory.aot.BeanFactoryInitializationAotProcessor;
import org.springframework.beans.factory.aot.BeanRegistrationExcludeFilter;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.RegisteredBean;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;

@Component
public class MyBatisBeanFactoryInitializationAotProcessor
        implements BeanFactoryInitializationAotProcessor, BeanRegistrationExcludeFilter {

    private static Logger logger= LoggerFactory.getLogger(MyBatisBeanFactoryInitializationAotProcessor.class);

    private final Set<Class<?>> excludeClasses = new HashSet<>();

    MyBatisBeanFactoryInitializationAotProcessor() {
        logger.info("MyBatisBeanFactoryInitializationAotProcessor");
        excludeClasses.add(MapperScannerConfigurer.class);
    }

    @Override public boolean isExcludedFromAotProcessing(RegisteredBean registeredBean) {
        return excludeClasses.contains(registeredBean.getBeanClass());
    }

    @Override
    public BeanFactoryInitializationAotContribution processAheadOfTime(ConfigurableListableBeanFactory beanFactory) {
        logger.info("MyBatisBeanFactoryInitializationAotProcessor processAheadOfTime");
        String[] beanNames = beanFactory.getBeanNamesForType(MapperFactoryBean.class);
        if (beanNames.length == 0) {
            return null;
        }
        return (context, code) -> {
            RuntimeHints hints = context.getRuntimeHints();
            for (String beanName : beanNames) {
                BeanDefinition beanDefinition = beanFactory.getBeanDefinition(beanName.substring(1));
                PropertyValue mapperInterface = beanDefinition.getPropertyValues().getPropertyValue("mapperInterface");
                if (mapperInterface != null && mapperInterface.getValue() != null) {
                    Class<?> mapperInterfaceType = (Class<?>) mapperInterface.getValue();
                    if (mapperInterfaceType != null) {
                        registerReflectionTypeIfNecessary(mapperInterfaceType, hints);
                        hints.proxies().registerJdkProxy(mapperInterfaceType);
                        hints.resources()
                                .registerPattern(mapperInterfaceType.getName().replace('.', '/').concat(".xml"));
                        registerMapperRelationships(mapperInterfaceType, hints);
                    }
                }
            }
        };
    }

    private void registerMapperRelationships(Class<?> mapperInterfaceType, RuntimeHints hints) {
        Method[] methods = ReflectionUtils.getAllDeclaredMethods(mapperInterfaceType);
        for (Method method : methods) {
            if (method.getDeclaringClass() != Object.class) {
                ReflectionUtils.makeAccessible(method);
                registerSqlProviderTypes(method, hints, SelectProvider.class, SelectProvider::value, SelectProvider::type);
                registerSqlProviderTypes(method, hints, InsertProvider.class, InsertProvider::value, InsertProvider::type);
                registerSqlProviderTypes(method, hints, UpdateProvider.class, UpdateProvider::value, UpdateProvider::type);
                registerSqlProviderTypes(method, hints, DeleteProvider.class, DeleteProvider::value, DeleteProvider::type);
                Class<?> returnType = MyBatisMapperTypeUtils.resolveReturnClass(mapperInterfaceType, method);
                registerReflectionTypeIfNecessary(returnType, hints);
                MyBatisMapperTypeUtils.resolveParameterClasses(mapperInterfaceType, method)
                        .forEach(x -> registerReflectionTypeIfNecessary(x, hints));
            }
        }
    }

    @SafeVarargs
    private <T extends Annotation> void registerSqlProviderTypes(
            Method method, RuntimeHints hints, Class<T> annotationType, Function<T, Class<?>>... providerTypeResolvers) {
        for (T annotation : method.getAnnotationsByType(annotationType)) {
            for (Function<T, Class<?>> providerTypeResolver : providerTypeResolvers) {
                registerReflectionTypeIfNecessary(providerTypeResolver.apply(annotation), hints);
            }
        }
    }

    private void registerReflectionTypeIfNecessary(Class<?> type, RuntimeHints hints) {
        if (!type.isPrimitive() && !type.getName().startsWith("java")) {
            hints.reflection().registerType(type, MemberCategory.values());
        }
    }

}
