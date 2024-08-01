package com.fushun.framework.json.config;

import cn.hutool.json.JSON;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Component
public class JsonBeanFactoryInitializationAotProcessor
        implements BeanFactoryInitializationAotProcessor {

    Logger logger= LoggerFactory.getLogger(JsonBeanFactoryInitializationAotProcessor.class);


    @Override
    public BeanFactoryInitializationAotContribution processAheadOfTime(ConfigurableListableBeanFactory beanFactory) {
        // 扫描系统第二级开始的包
        //所有的包
        ClassPathScanningCandidateComponentProvider scanner = new ClassPathScanningCandidateComponentProvider(false);
        scanner.addIncludeFilter(new AssignableTypeFilter(JsonGraalVMNativeBean.class));

        List<Class<?>> classes = new ArrayList<>();
        for (BeanDefinition bd : scanner.findCandidateComponents("")) {
            Class<?> clazz = null;
            try {
                clazz = Class.forName(bd.getBeanClassName());
                logger.info("JsonGraalVMNativeBean: {}",bd.getBeanClassName());
            } catch (ClassNotFoundException e) {
                logger.error(e.getMessage(), e);
                continue;
            }
            classes.add(clazz);
        }
        classes.add(JSON.class);
        classes.add(JSONObject.class);
        classes.add(JSONArray.class);

        return (context, code) -> {
            RuntimeHints hints = context.getRuntimeHints();
            hints.reflection().registerType(cn.hutool.json.JSONConverter.class,MemberCategory.values());
            for(Class<?> clazz :classes){
                hints.serialization().registerType((Class<?  extends Serializable>) clazz);
                hints.reflection().registerType(clazz, MemberCategory.values());
            }
        };
    }


}