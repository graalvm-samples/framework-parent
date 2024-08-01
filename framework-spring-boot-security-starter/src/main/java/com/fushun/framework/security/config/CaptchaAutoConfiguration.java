package com.fushun.framework.security.config;


import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(prefix = CaptchaConfig.PREFIX, name = "enable", havingValue = "true", matchIfMissing = true)
@EnableConfigurationProperties({CaptchaConfig.class})
public class CaptchaAutoConfiguration {
}
