package com.fushun.framework.security.config;


import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(prefix = BasicTokenConfig.PREFIX, name = "enable", havingValue = "true", matchIfMissing = true)
@EnableConfigurationProperties({BasicTokenConfig.class})
public class BasicTokenAutoConfiguration {
}
