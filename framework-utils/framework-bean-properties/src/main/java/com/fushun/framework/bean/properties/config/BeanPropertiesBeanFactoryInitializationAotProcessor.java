package com.fushun.framework.bean.properties.config;

import com.fushun.framework.base.IBaseEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aot.hint.MemberCategory;
import org.springframework.aot.hint.RuntimeHints;
import org.springframework.beans.factory.aot.BeanFactoryInitializationAotContribution;
import org.springframework.beans.factory.aot.BeanFactoryInitializationAotProcessor;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AssignableTypeFilter;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

@Component
public class BeanPropertiesBeanFactoryInitializationAotProcessor
        implements BeanFactoryInitializationAotProcessor {

    Logger logger= LoggerFactory.getLogger(BeanPropertiesBeanFactoryInitializationAotProcessor.class);

    @Override
    public BeanFactoryInitializationAotContribution processAheadOfTime(ConfigurableListableBeanFactory beanFactory) {
        // 扫描系统第二级开始的包
        //所有的包
        ClassPathScanningCandidateComponentProvider scanner = new ClassPathScanningCandidateComponentProvider(false);
        scanner.addIncludeFilter(new AssignableTypeFilter(IBeanCopyPropertiesBean.class));
        scanner.addIncludeFilter(new AssignableTypeFilter(IBaseEnum.class));

        List<Class<?>> classes = new ArrayList<>();
        for (BeanDefinition bd : scanner.findCandidateComponents("")) {
            Class<?> clazz = null;
            try {
                clazz = Class.forName(bd.getBeanClassName());
                logger.info("IBeanCopyPropertiesBean: {}",bd.getBeanClassName());
            } catch (ClassNotFoundException e) {
                logger.error(e.getMessage(), e);
                continue;
            }
            processClass(clazz, classes);
        }

        classes.add(IBaseEnum.class);

        return (context, code) -> {
            RuntimeHints hints = context.getRuntimeHints();
            for(Class<?> clazz :classes){
                hints.reflection().registerType(clazz, MemberCategory.values());
            }
        };
    }


    private void processClass(Class<?> cls, List<Class<?>> classes) {
        if(classes.contains(cls)){
            return;
        }
        // 判断是否为数组类型
        if (cls.isArray()) {
            // 获取数组的原始组件类型
            Class<?> componentType = cls.getComponentType();
            logger.info("这是一个数组类型，其组件类型是: {}", componentType.getName());
            if(classes.contains(componentType)){
                return;
            }
            classes.add(cls);
        }else{
            classes.add(cls);
        }

        for (Field field : cls.getDeclaredFields()) {
            // 获取字段的类型
            Type genericFieldType = field.getGenericType();
            // 判断是否为参数化类型
            if (genericFieldType instanceof ParameterizedType parameterizedType) {

                // 获取实际的类型参数
                Type[] typeArguments = parameterizedType.getActualTypeArguments();

                // 打印类型参数
                for (Type typeArgument : typeArguments) {
                    if (typeArgument instanceof Class<?> typeArgClass) {
                        logger.info("泛型类型为: {}", typeArgClass.getName());
                        // 判断是否是自定义类
                        if(isCustomizationClass(typeArgClass)){
                            processClass(typeArgClass, classes);
                        }
                    }
                }
            }

            //根据包的路径，判断是否是自定义类
            Class<?> fieldType = field.getType();
            if(isCustomizationClass(fieldType)){
                processClass(fieldType, classes);
            }
        }
    }

    public boolean isCustomizationClass(Class<?> cls) {
        String packageName="";
        try{
            packageName = cls.getPackageName();
        }catch (Exception e){
            logger.error(e.getMessage(), e);
            throw e;
        }
        // 判断是否是自定义类
        if (!cls.isPrimitive() &&
                !packageName.startsWith("java.") &&
                !packageName.startsWith("org.slf4j")
        ) {
            if(cls==Logger.class){
                logger.info("");
            }
            return true;
        }
        return false;
    }


}