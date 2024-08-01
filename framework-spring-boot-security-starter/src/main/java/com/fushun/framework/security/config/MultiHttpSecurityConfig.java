package com.fushun.framework.security.config;


import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = false, securedEnabled = true)
public class MultiHttpSecurityConfig {

}