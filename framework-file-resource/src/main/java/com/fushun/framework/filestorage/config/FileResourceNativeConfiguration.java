package com.fushun.framework.filestorage.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportRuntimeHints;

/**
 * SecurityJsonNativeConfiguration
 */
@Configuration(proxyBeanMethods = false)
@ImportRuntimeHints(FileResourceRuntimeHintsRegistrar.class)
public class FileResourceNativeConfiguration {


}
