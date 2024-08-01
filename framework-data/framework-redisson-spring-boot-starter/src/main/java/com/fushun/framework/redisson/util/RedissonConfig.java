package com.fushun.framework.redisson.util;


import org.redisson.api.RedissonClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
public class RedissonConfig {

    @Bean
    RedissonUtil redissonUtil(RedissonClient redissonClient){
        RedissonUtil redissonUtil=new RedissonUtil(redissonClient);
        return redissonUtil;
    }

}
