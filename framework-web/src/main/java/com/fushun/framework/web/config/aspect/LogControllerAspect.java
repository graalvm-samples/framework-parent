package com.fushun.framework.web.config.aspect;

import cn.hutool.core.util.ObjectUtil;
import com.fushun.framework.util.util.JsonUtil;
import com.fushun.framework.util.util.MethodTimeConsuming;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Aspect
@Component
@Order(Ordered.HIGHEST_PRECEDENCE+1)
public class LogControllerAspect {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Pointcut("within(@org.springframework.stereotype.Controller *)")
    public void controller() {
    }

    @Pointcut("within(@org.springframework.web.bind.annotation.RestController *)")
    public void restController() {
    }
    /**
     * 记录HTTP请求结束时的日志
     */
    @Before("controller() || restController()")
    public void doBefore(JoinPoint joinPoint) {

        // 接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if(attributes == null){
            return;
        }
        MethodTimeConsuming.begin();
        HttpServletRequest request = attributes.getRequest();
        // 记录下请求内容
//        String controllerMethod = joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName();
        String args = this.argsArrayToString(joinPoint.getArgs(), request.getMethod());
        Map<String, Object> headers = new HashMap<>();


        Collections.list(request.getHeaderNames()).forEach(name -> headers.put(name, request.getHeader(name)));

        logger.info("headers:[{}]",JsonUtil.toJson(headers));
        logger.info(" >>>>>>>>>>Before REQUEST_URL:[{}]\n " +
                        ">>>>>>>>>>platform:[{}]\n," +
                        "X-USER-ID:[{}]\n,ACCESS_TOKEN:[{}]\nContent-Type:[{}]\n" +
                        " " +
                        ">>>>>>>>>>ARGS:[{}]",
                request.getRequestURL().toString(),
                headers.getOrDefault("platform",""),
                headers.getOrDefault("x-user-id",""),
                headers.getOrDefault("access_token",""),
                headers.getOrDefault("Content-Type",""),
                args);
    }

    @AfterReturning(returning = "obj", pointcut = "controller() || restController()")
    public void doAfterReturning(JoinPoint joinPoint, Object obj) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if(attributes == null){
            return;
        }
        HttpServletRequest request = attributes.getRequest();
        String method = joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName();
        logger.info(" >>>>>>>>>>AfterReturning REQUEST_URL:[{}],METHOD:[{}]\n" +
                ">>>>>>>>>>耗时:{}s",      request.getRequestURL().toString(),method, MethodTimeConsuming.consuming());
    }

    /**
     * 过滤不需要日志处理的对象
     */
    public static String argsArrayToString(Object[] paramsArray, String httpMethod) {

        if (paramsArray == null) {
            return null;
        }
        if (paramsArray.length == 0) {
            return JsonUtil.classToJson(paramsArray);
        }

        Object[] result = new Object[paramsArray.length];
        for (int i = 0; i < paramsArray.length; i++) {
            if (!LogControllerAspect.isFilterObject(paramsArray[i])) {
                result[i] = paramsArray[i];
            }
        }


        if (HttpMethod.PUT.name().equals(httpMethod) || HttpMethod.POST.name().equals(httpMethod)) {
            String params = JsonUtil.classToJson(result);
            if (params.length() > 4000) {
                params = params.substring(0, 4000);
            }
            return params;
        } else {
            return JsonUtil.classToJson(result);
        }
    }

    /**
     * 判断是否需要过滤的对象。
     *
     * @param o 对象信息。
     * @return 如果是需要过滤的对象，则返回true；否则返回false。
     */
    private static boolean isFilterObject(final Object o) {
        return o instanceof MultipartFile || o instanceof HttpServletRequest || o instanceof HttpServletResponse;
    }
}
