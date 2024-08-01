//package com.fushun.framework.web.config.aspect;
//
//import cn.hutool.core.util.StrUtil;
//import com.fushun.framework.exception.SystemException;
//import com.fushun.framework.redis.utils.RedisUtil;
//import com.fushun.framework.security.service.TokenService;
//import com.fushun.framework.util.util.ExceptionUtils;
//import com.fushun.framework.util.util.ServletUtils;
//import jakarta.annotation.Resource;
//import jakarta.servlet.http.HttpServletRequest;
//import lombok.extern.slf4j.Slf4j;
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.annotation.Around;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Pointcut;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.core.Ordered;
//import org.springframework.core.annotation.Order;
//import org.springframework.stereotype.Component;
//
//import java.util.concurrent.TimeUnit;
//
///**
// * 重复提交切面
// * zhangyd
// * 2020/6/27 10:09
// */
//@Aspect
//@Component
//@Slf4j
//@Order(Ordered.HIGHEST_PRECEDENCE+5)
//public class RepeatControllerAspect {
//
//    private static final String NO_REPEAT_COMMIT = "NO_REPEAT_COMMIT:";
//
//    @Resource
//    RedisUtil redisUtil;
//
//    @Resource
//    TokenService tokenService;
//
//    /**
//     * 切入点
//     */
//    @Pointcut("@annotation(com.fushun.framework.web.annotations.NoRepeatCommit))")
//    public void NoRepeatCommit() {
//    }
//
//    /**
//     * 控制同一请求3秒内只准请求一次
//     */
//    @Around(value = "NoRepeatCommit()")
//    public Object aroundAdvice(ProceedingJoinPoint point) {
//        String token = tokenService.getToken();
//        HttpServletRequest request = ServletUtils.getRequest();
//        String key =  NO_REPEAT_COMMIT + token + "-" + request.getServletPath();
//
//        //第一次请求放入redis
//        if (StrUtil.isBlank(redisUtil.getCacheObject(key))) {
//            redisUtil.setCacheObject(key, "NO_REPEAT_COMMIT", 3, TimeUnit.SECONDS);
//        } else {
//            log.error("重复提交切面 key:{} 3秒内不能重复提交", key);
//            throw new SystemException(SystemException.SystemExceptionEnum.REPEAT_COMMIT_ERROR);
//        }
//
//        log.info("RepeatControllerAspect point.proceed====");
//        //执行完后清除
//        try {
//            Object obj = point.proceed();
//            if (StrUtil.isNotBlank(key) && redisUtil.hasKey(key)) {
//                log.info("RepeatControllerAspect remove key:{}", key);
//                redisUtil.deleteObject(key);
//            }
//            return obj;
//        } catch (Throwable throwable) {
//            ExceptionUtils.rethrow(throwable,log,"RepeatControllerAspect around error");
//        }
//        return null;
//    }
//}
