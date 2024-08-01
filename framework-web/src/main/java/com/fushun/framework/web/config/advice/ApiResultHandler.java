package com.fushun.framework.web.config.advice;

import com.fushun.framework.util.response.ApiResult;
import com.fushun.framework.util.util.JsonUtil;
import com.fushun.framework.web.annotations.NoApiResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * api接口重新包装返回对象
 */
@ControllerAdvice
@Order(value = Ordered.LOWEST_PRECEDENCE)
@Slf4j
public class ApiResultHandler implements ResponseBodyAdvice<Object> {


    private static final Class[] ANNOS = {
            RequestMapping.class,
            GetMapping.class,
            PostMapping.class,
            DeleteMapping.class,
            PutMapping.class
    };

    /**
     * 对所有RestController的接口方法进行拦截
     */
    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        AnnotatedElement element = returnType.getAnnotatedElement();
        //需要做数据包装
        //并且MethodParameter 正确
        boolean bool= !(element.isAnnotationPresent(NoApiResult.class)
                || ((Method) element).getDeclaringClass().isAnnotationPresent(NoApiResult.class))
                && Arrays.stream(ANNOS).anyMatch(anno -> anno.isAnnotation() && element.isAnnotationPresent(anno));
        return  bool;
    }

    /**
     *
     * @param body
     * @param returnType
     * @param selectedContentType
     * @param selectedConverterType
     * @param request
     * @param response
     * @return
     */
    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {

        //TODO 访问不存在链接，返回包装数据，暂时处理
        int status=((ServletServerHttpResponse) response).getServletResponse().getStatus();
        if(status==404){
            return body;
        }
        Object out = null;
        if (request.getURI().getPath().contains("swagger")
                || request.getURI().getPath().contains("/v2/api-docs")
                || request.getURI().getPath().contains("/v3/api-docs")) {
            return body;
        }

        if (body instanceof ApiResult) {
            out = body;
        } else {
            if (body instanceof String) {
                return JsonUtil.classToJson(ApiResult.of(body));
            }
            out = ApiResult.of(body);
        }
        return out;

    }

}
