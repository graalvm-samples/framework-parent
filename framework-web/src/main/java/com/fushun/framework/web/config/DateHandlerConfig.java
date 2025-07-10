package com.fushun.framework.web.config;

import cn.hutool.core.collection.CollUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fushun.framework.util.json.JsonMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import java.util.List;

import static com.fushun.framework.util.json.JsonEnum.BASE;

/**
 * 参照 ：
 * Springboot 关于日期时间格式化处理方式总结 https://juejin.im/post/5e62817fe51d4526d05962a2
 */
@Configuration
@Order(value = Ordered.HIGHEST_PRECEDENCE+1)
public class DateHandlerConfig {

    @Primary
    @Bean
    @Order(value = Ordered.HIGHEST_PRECEDENCE+1)
    public ObjectMapper getJsonMapper(){
        JsonMapper.init(null);
       return JsonMapper.getObjectMapper(BASE);
    }
}