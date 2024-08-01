package com.fushun.framework.util.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aot.hint.RuntimeHints;
import org.springframework.aot.hint.RuntimeHintsRegistrar;

public class PyUtilRuntimeHintsRegistrar implements RuntimeHintsRegistrar {

    private Logger logger= LoggerFactory.getLogger(this.getClass());
    @Override
    public void registerHints(RuntimeHints hints, ClassLoader classLoader) {
//        hints.reflection().registerType(org.springframework.cglib.beans.BeanCopier.class, MemberCategory.values());
//        hints.serialization().registerType(TypeReference.of(org.springframework.cglib.beans.BeanCopier.class));
//
//        hints.reflection().registerType(BeanCopier.Generator.class,MemberCategory.values());
//        hints.serialization().registerType(TypeReference.of(BeanCopier.Generator.class));
        logger.info("PyUtilRuntimeHintsRegistrar registerHints");
    }
}