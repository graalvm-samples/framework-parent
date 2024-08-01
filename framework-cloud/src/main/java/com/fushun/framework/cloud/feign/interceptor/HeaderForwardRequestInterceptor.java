package com.fushun.framework.cloud.feign.interceptor;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Data;
import org.slf4j.MDC;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Enumeration;

/**
 * @author Lanny
 * @since 2021/2/27
 */
@Data
public class HeaderForwardRequestInterceptor implements RequestInterceptor {


    /**
     * 会话ID
     */
    public final static String MDC_KEY_REQ_ID = "request_id";

    @Override
    public void apply(RequestTemplate requestTemplate) {
        //将请求的request传递到下一个请求
        String request_id=MDC.get(MDC_KEY_REQ_ID);
        requestTemplate.header(MDC_KEY_REQ_ID,request_id);

        ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attrs != null) {
            HttpServletRequest request = attrs.getRequest();
            Enumeration<String> headerNames = request.getHeaderNames();
            if (headerNames != null) {
                String name;
                while (headerNames.hasMoreElements()) {
                    name = headerNames.nextElement();
                    // 跳过 content-length，解决too many bites written的问题
                    if (name.equalsIgnoreCase("content-length") ||
                            name.equalsIgnoreCase("content-type")){
                        continue;
                    }
                    requestTemplate.header(name, request.getHeader(name));
                    requestTemplate.header(name.toUpperCase(), request.getHeader(name));
                }
            }
        }
    }
}
