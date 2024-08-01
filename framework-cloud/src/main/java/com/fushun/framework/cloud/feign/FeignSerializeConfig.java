package com.fushun.framework.cloud.feign;

import com.fushun.framework.exception.DynamicBaseException;
import com.fushun.framework.util.json.JsonMapper;
import feign.Logger;
import feign.Response;
import feign.Util;
import feign.codec.Decoder;
import feign.codec.Encoder;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.support.SpringDecoder;
import org.springframework.cloud.openfeign.support.SpringEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Configuration(proxyBeanMethods = false)
@Slf4j
public class FeignSerializeConfig {

    @Primary
    @Bean
    public Encoder feignEncoder() {
        return new SpringEncoder(() -> new HttpMessageConverters(new MappingJackson2HttpMessageConverter(JsonMapper.getObjectMapper())));
    }

    @Primary
    @Bean
    public Decoder feignDecoder() {
        MessageConverter wxConverter = new MessageConverter();
        return new SpringDecoder(() -> new HttpMessageConverters(wxConverter, new MappingJackson2HttpMessageConverter(JsonMapper.getObjectMapper())));
    }

    @Primary
    @Bean
    Logger.Level feignLoggerLevel() {
        //这里记录所有，根据实际情况选择合适的日志level
        return Logger.Level.FULL;
    }

    @Primary
    @Bean
    public ErrorDecoder feignErrorDecoder() {
        return new ErrorDecoder() {
            @Override
            public Exception decode(String methodKey, Response response) {
                String message = null;
                try {
                    message = Util.toString(response.body().asReader(StandardCharsets.UTF_8));
                    log.error("服务调用异常 methodKey:[{}],message:[{}],ulr:{}",methodKey, message,response.request().url());
                } catch (IOException e) {
                    log.error("feignErrorDecoder ulr:{}",response.request().url(),e);
                }
                return new DynamicBaseException("feign_call_error","feign调用失败");
            }
        };
    }

    /**
     * 创建一个新的转换器 解析 [text/plain]
     */
    class MessageConverter extends MappingJackson2HttpMessageConverter {
        public MessageConverter() {
            List<MediaType> mediaTypes = new ArrayList<>();
            mediaTypes.add(MediaType.TEXT_PLAIN);
            setSupportedMediaTypes(mediaTypes);
        }
    }
}
