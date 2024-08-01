package com.fushun.framework.filestorage.config;


import org.springframework.aot.hint.MemberCategory;
import org.springframework.aot.hint.RuntimeHints;
import org.springframework.aot.hint.RuntimeHintsRegistrar;

import java.util.stream.Stream;

public class FileResourceRuntimeHintsRegistrar implements RuntimeHintsRegistrar {

    @Override
    public void registerHints(RuntimeHints hints, ClassLoader classLoader) {
        Stream.of(
                com.aliyun.oss.internal.OSSUtils.class

        ).forEach(x -> hints.reflection().registerType(x, MemberCategory.values()));

    }
}