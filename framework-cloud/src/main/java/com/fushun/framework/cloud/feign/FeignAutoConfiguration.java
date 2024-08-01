package com.fushun.framework.cloud.feign;

import com.fushun.framework.cloud.feign.interceptor.HeaderForwardRequestInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Lanny
 * @since 2021/2/27
 */
@Configuration(proxyBeanMethods = false)
public class FeignAutoConfiguration {

    Logger logger= LoggerFactory.getLogger(this.getClass());
    @Bean
    public HeaderForwardRequestInterceptor headerForwardRequestInterceptor() {
        HeaderForwardRequestInterceptor headerForwardRequestInterceptor = new HeaderForwardRequestInterceptor();
        return headerForwardRequestInterceptor;
    }

}
