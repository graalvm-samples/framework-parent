package com.fushun.framework.redis.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import com.fushun.framework.redis.utils.RedisUtil;
import com.fushun.framework.util.json.JsonMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.serializer.RedisSerializer;

/**
 * redis配置
 *
 * @author ruoyi
 */
@Configuration(proxyBeanMethods = false)
public class CacheConfig{

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(factory);

        ObjectMapper mapper = JsonMapper.getObjectMapper();
        mapper.activateDefaultTyping(LaissezFaireSubTypeValidator.instance, ObjectMapper.DefaultTyping.NON_FINAL, JsonTypeInfo.As.PROPERTY);
        mapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(mapper, Object.class);

        template.setKeySerializer(RedisSerializer.string()); // key采用String的序列化方式
        template.setHashKeySerializer(RedisSerializer.string()); // hash的key也采用String的序列化方式
        template.setValueSerializer(jackson2JsonRedisSerializer); // value序列化方式采用jackson
        template.setHashValueSerializer(jackson2JsonRedisSerializer); // hash的value序列化方式采用jackson
        template.afterPropertiesSet();
        return template;
    }

    @Bean
    StringRedisTemplate stringRedisTemplate(RedisConnectionFactory redisConnectionFactory) {

        StringRedisTemplate template = new StringRedisTemplate();
        template.setConnectionFactory(redisConnectionFactory);
        return template;
    }

    @Bean
    IntegerRedisTemplate integerRedisTemplate(RedisConnectionFactory redisConnectionFactory) {

        IntegerRedisTemplate template = new IntegerRedisTemplate();
        template.setConnectionFactory(redisConnectionFactory);
        return template;
    }

    @Bean
    LongRedisTemplate longRedisTemplate(RedisConnectionFactory redisConnectionFactory) {

        LongRedisTemplate template = new LongRedisTemplate();
        template.setConnectionFactory(redisConnectionFactory);
        return template;
    }

    @Bean
    public DefaultRedisScript<Long> limitScript()
    {
        DefaultRedisScript<Long> redisScript = new DefaultRedisScript<>();
        redisScript.setScriptText(limitScriptText());
        redisScript.setResultType(Long.class);
        return redisScript;
    }

    @Bean
    @SuppressWarnings("unchecked")
    public RedisUtil redisUtil(RedisTemplate<String, Object> redisTemplate, StringRedisTemplate stringRedisTemplate,
                               IntegerRedisTemplate integerRedisTemplate,LongRedisTemplate longRedisTemplate){
        RedisUtil redisUtil=new RedisUtil();
        redisUtil.setRedisTemplate(redisTemplate);
        redisUtil.setStringRedisTemplate(stringRedisTemplate);
        redisUtil.setIntegerRedisTemplate(integerRedisTemplate);
        redisUtil.setLongRedisTemplate(longRedisTemplate);
        return redisUtil;
    }

    /**
     * 限流脚本
     */
    private String limitScriptText()
    {
        return "local key = KEYS[1]\n" +
                "local count = tonumber(ARGV[1])\n" +
                "local time = tonumber(ARGV[2])\n" +
                "local current = redis.call('get', key);\n" +
                "if current and tonumber(current) > count then\n" +
                "    return tonumber(current);\n" +
                "end\n" +
                "current = redis.call('incr', key)\n" +
                "if tonumber(current) == 1 then\n" +
                "    redis.call('expire', key, time)\n" +
                "end\n" +
                "return tonumber(current);";
    }
}
