package com.fushun.framework.redis.config;

import com.fasterxml.jackson.databind.ObjectMapper;

public class Jackson2JsonRedisSerializer<T> extends org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer<T> {
    public Jackson2JsonRedisSerializer(ObjectMapper mapper, Class<T> type) {
        super(mapper, type);
    }
}
