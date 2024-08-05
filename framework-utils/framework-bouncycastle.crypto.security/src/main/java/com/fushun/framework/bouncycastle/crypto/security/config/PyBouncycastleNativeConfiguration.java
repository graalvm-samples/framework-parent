package com.fushun.framework.bouncycastle.crypto.security.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportRuntimeHints;

/**
 * SecurityJsonNativeConfiguration
 */
@Configuration(proxyBeanMethods = false)
@ImportRuntimeHints(PyBouncycastleRuntimeHintsRegistrar.class)
public class PyBouncycastleNativeConfiguration {


}
