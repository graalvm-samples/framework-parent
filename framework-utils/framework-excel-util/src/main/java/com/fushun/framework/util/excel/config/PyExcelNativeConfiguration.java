package com.fushun.framework.util.excel.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportRuntimeHints;

/**
 * SecurityJsonNativeConfiguration
 */
@Configuration(proxyBeanMethods = false)
@ImportRuntimeHints(PyExcelRuntimeHintsRegistrar.class)
public class PyExcelNativeConfiguration {

}
