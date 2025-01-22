package com.fushun.framework.redis.config;

import cn.hutool.core.collection.CollUtil;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import com.fushun.framework.util.json.JsonMapper;
import org.springframework.data.redis.connection.DefaultStringRedisConnection;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;

import java.util.List;

import static com.fushun.framework.util.json.JsonEnum.REDIS_TEMPLATE;

public class LongRedisTemplate extends RedisTemplate<String, Long> {
    public LongRedisTemplate(List<ObjectMapper> objectMappers) {
        JsonMapper.init(CollUtil.getFirst(objectMappers));
        ObjectMapper mapper = JsonMapper.getRedisObjectMapper(REDIS_TEMPLATE);
        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(mapper, Object.class);

        this.setKeySerializer(RedisSerializer.string()); // key采用String的序列化方式
        this.setHashKeySerializer(RedisSerializer.string()); // hash的key也采用String的序列化方式
        this.setValueSerializer(jackson2JsonRedisSerializer); // value序列化方式采用jackson
        this.setHashValueSerializer(jackson2JsonRedisSerializer); // hash的value序列化方式采用jackson
    }

    public LongRedisTemplate(RedisConnectionFactory connectionFactory, List<ObjectMapper> objectMappers) {
        this(objectMappers);
        this.setConnectionFactory(connectionFactory);
        this.afterPropertiesSet();
    }

    protected RedisConnection preProcessConnection(RedisConnection connection, boolean existingConnection) {
        return new DefaultStringRedisConnection(connection);
    }
}
