package com.fushun.framework.util.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportRuntimeHints;

/**
 * SecurityJsonNativeConfiguration
 */
@Configuration(proxyBeanMethods = false)
@ImportRuntimeHints(PyUtilRuntimeHintsRegistrar.class)
public class PyUtilNativeConfiguration {


}
