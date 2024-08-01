package com.fushun.framework.security.config;


import org.springframework.aot.hint.RuntimeHints;
import org.springframework.aot.hint.RuntimeHintsRegistrar;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportRuntimeHints;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.HashSet;

/**
 * SecurityJsonNativeConfiguration
 */
@Configuration(proxyBeanMethods = false)
@ImportRuntimeHints(SecurityJsonNativeConfiguration.SecurityJsonRuntimeHintsRegistrar.class)
public class SecurityJsonNativeConfiguration {


  static class SecurityJsonRuntimeHintsRegistrar implements RuntimeHintsRegistrar {

    @Override
    public void registerHints(RuntimeHints hints, ClassLoader classLoader) {
      hints.reflection().registerType(GrantedAuthority.class);
      hints.proxies().registerJdkProxy(GrantedAuthority.class);
      hints.serialization().registerType(GrantedAuthority.class);
    }
  }


}
