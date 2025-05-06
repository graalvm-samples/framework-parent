//package com.fushun.framework.security.handler;
//
//import com.fushun.framework.util.response.ApiResult;
//import com.fushun.framework.util.util.ResponseUtil;
//import com.fushun.framework.util.util.StringUtils;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.web.AuthenticationEntryPoint;
//import com.fushun.framework.security.service.TokenService;
//import org.springframework.stereotype.Component;
//
//import java.io.IOException;
//import java.io.Serializable;
//
///**
// * 需要登陆才能访问的地址，返回错误
// */
//@Component
//public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint, Serializable {
//
//    private static final long serialVersionUID = -8970718410437077606L;
//
//    private Logger logger = LoggerFactory.getLogger(CustomAuthenticationEntryPoint.class);
//
//    @Autowired
//    private TokenService tokenService;
//
//    @Override
//    public void commence(HttpServletRequest request,
//                         HttpServletResponse response,
//                         AuthenticationException e) throws IOException {
//        if(StringUtils.isEmpty(tokenService.getToken())){
//            ResponseUtil.out(response, ApiResult.ofFail("LOGIN_UNLOGIN","尚未登录"));
//        }else{
//            ResponseUtil.out(response, ApiResult.ofFail("LOGIN_EXPIRED","登陆已过期"));
//        }
//
//
//    }
//}