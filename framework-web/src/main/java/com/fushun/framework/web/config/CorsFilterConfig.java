//package com.fushun.framework.web.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.cors.CorsConfiguration;
//import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
//import org.springframework.web.filter.CorsFilter;
//
//import java.util.Arrays;
//import java.util.Collections;
//
///**
// * Global Configuration CORS
// *
// * @author wangfushun
// * @version 1.0
// * @description
// * @creation 2019年03月03日01时50分
// */
//@Configuration
//public class CorsFilterConfig {
//
//
//    private CorsConfiguration buildConfig() {
//        CorsConfiguration corsConfiguration = new CorsConfiguration();
//        corsConfiguration.addAllowedOrigin("*"); // 1允许任何域名使用
//        corsConfiguration.addAllowedHeader("*"); // 2允许任何头
//        corsConfiguration.addAllowedMethod("*"); // 3允许任何方法（post、get等）
//        corsConfiguration.addExposedHeader("*");
////        corsConfiguration.setExposedHeaders(
////                Arrays.asList("content-disposition",
////                        "access-control-allow-headers",
////                "access-control-allow-methods",
////                "access-control-allow-origin",
////                "access-control-max-age",
////                "X-Frame-Options"));
//        corsConfiguration.setAllowCredentials(true);
//        corsConfiguration.setMaxAge(3600L);
////        Access-Control-Allow-Origin
////        corsConfiguration.seta
//        return corsConfiguration;
//    }
//
//    /**
//     * 详细介绍 https://www.jianshu.com/p/723a6e32c0e9
//     * @return
//     */
//    @Bean
//    public CorsFilter corsFilter() {
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", buildConfig()); // 4
//        return new CorsFilter(source);
//    }
//}