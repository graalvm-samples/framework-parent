package com.fushun.framework.mybatis.config.batchConfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
public class BatchConfigMyBatisConfig {

    /**
     * 批量更新的插件
     * @return
     */
    @Bean
    public CustomizedSqlInjector customizedSqlInjector() {
        return new CustomizedSqlInjector();
    }
}
