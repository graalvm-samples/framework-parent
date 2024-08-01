package com.fushun.framework.util.excel.config;

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

import java.util.ArrayList;
import java.util.List;

@Component
public class BeanExcelPropertiesBeanFactoryInitializationAotProcessor
        implements BeanFactoryInitializationAotProcessor {

    Logger logger= LoggerFactory.getLogger(BeanExcelPropertiesBeanFactoryInitializationAotProcessor.class);


    BeanExcelPropertiesBeanFactoryInitializationAotProcessor() {
    }

    @Override
    public BeanFactoryInitializationAotContribution processAheadOfTime(ConfigurableListableBeanFactory beanFactory) {
        // 扫描系统第二级开始的包
        //所有的包
        ClassPathScanningCandidateComponentProvider scanner = new ClassPathScanningCandidateComponentProvider(false);
        scanner.addIncludeFilter(new AssignableTypeFilter(com.alibaba.excel.converters.Converter.class));


        List<Class<?>> classes = new ArrayList<>();
        for (BeanDefinition bd : scanner.findCandidateComponents("")) {
            Class<?> clazz = null;
            try {
                clazz = Class.forName(bd.getBeanClassName());
                logger.info("BeanExcelPropertiesNativeConfiguration: {}",bd.getBeanClassName());
            } catch (ClassNotFoundException e) {
                logger.error(e.getMessage(), e);
                continue;
            }
            classes.add(clazz);
        }

        return (context, code) -> {
            RuntimeHints hints = context.getRuntimeHints();
            for(Class<?> clazz :classes){
                hints.reflection().registerType(clazz, MemberCategory.values());
            }
        };
    }


}
