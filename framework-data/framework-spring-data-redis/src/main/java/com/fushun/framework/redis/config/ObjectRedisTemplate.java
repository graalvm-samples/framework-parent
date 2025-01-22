//package com.fushun.framework.redis.config;
//
//import com.fasterxml.jackson.annotation.JsonAutoDetect;
//import com.fasterxml.jackson.annotation.JsonTypeInfo;
//import com.fasterxml.jackson.annotation.PropertyAccessor;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
//import com.fushun.framework.util.json.JsonMapper;
//import org.springframework.data.redis.connection.DefaultStringRedisConnection;
//import org.springframework.data.redis.connection.RedisConnection;
//import org.springframework.data.redis.connection.RedisConnectionFactory;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.data.redis.serializer.RedisSerializer;
//
//public class ObjectRedisTemplate<T> extends RedisTemplate<String, T> {
//    public ObjectRedisTemplate() {
//        ObjectMapper mapper = JsonMapper.getObjectMapper();
//        mapper.activateDefaultTyping(LaissezFaireSubTypeValidator.instance, ObjectMapper.DefaultTyping.NON_FINAL, JsonTypeInfo.As.PROPERTY);
//        mapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
//        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(mapper, Object.class);
//
//        this.setKeySerializer(RedisSerializer.string()); // key采用String的序列化方式
//        this.setHashKeySerializer(RedisSerializer.string()); // hash的key也采用String的序列化方式
//        this.setValueSerializer(jackson2JsonRedisSerializer); // value序列化方式采用jackson
//        this.setHashValueSerializer(jackson2JsonRedisSerializer); // hash的value序列化方式采用jackson
//    }
//
//    public ObjectRedisTemplate(RedisConnectionFactory connectionFactory) {
//        this();
//        this.setConnectionFactory(connectionFactory);
//        this.afterPropertiesSet();
//    }
//
//    protected RedisConnection preProcessConnection(RedisConnection connection, boolean existingConnection) {
//        return new DefaultStringRedisConnection(connection);
//    }
//}
