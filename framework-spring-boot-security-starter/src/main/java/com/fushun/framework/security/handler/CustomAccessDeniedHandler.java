package com.fushun.framework.security.handler;

import com.fushun.framework.util.response.ApiResult;
import com.fushun.framework.util.util.ResponseUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;


/**
 * 已登陆，访问权限不足的情况
 *
 * @author yangrong67
 * @date 2020-10-23 15:05
 */
@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    private Logger logger = LoggerFactory.getLogger(CustomAccessDeniedHandler.class);

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) {
        logger.warn("没有权限");
        ResponseUtil.out(response, ApiResult.ofFail("LOGIN_ACCESS_DENIED", "没有权限"));
    }
}