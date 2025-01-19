//package com.fushun.framework.web.config;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fushun.framework.util.json.JsonMapper;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Primary;
//
///**
// * 参照 ：
// * Springboot 关于日期时间格式化处理方式总结 https://juejin.im/post/5e62817fe51d4526d05962a2
// */
//@Configuration
//public class DateHandlerConfig {
//
//
//    @Primary
//    @Bean
//    public ObjectMapper getJsonMapper(){
//       return JsonMapper.getObjectMapper();
//    }
//}