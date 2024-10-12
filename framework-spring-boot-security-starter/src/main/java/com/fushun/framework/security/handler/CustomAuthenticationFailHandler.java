package com.fushun.framework.security.handler;

import com.fushun.framework.base.SpringContextUtil;
import com.fushun.framework.log.dto.LoginLogEventDTO;
import com.fushun.framework.log.events.CustomLoginLogEvent;
import com.fushun.framework.security.constant.SecurityConstant;
import com.fushun.framework.security.exception.LoginDynamicBaseException;
import com.fushun.framework.security.exception.UserLoginException;
import com.fushun.framework.util.response.ApiResult;
import com.fushun.framework.util.util.ResponseUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * 登陆失败
 *
 * @author Exrickx
 * @date 2020-10-23 15:03
 */
@Slf4j
@Component
public class CustomAuthenticationFailHandler implements AuthenticationFailureHandler {

    private Logger logger = LoggerFactory.getLogger(CustomAuthenticationFailHandler.class);

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        String message = e.getMessage();
        String errorCode="LOGIN_ERROR";
        if ("User is disabled".equalsIgnoreCase(e.getMessage())) {
            message = UserLoginException.UserLoginExceptionEnum.NO_LOGIN_ERROR.getMsg();
        } else if ("Bad credentials".equalsIgnoreCase(e.getMessage())) {
            message = UserLoginException.UserLoginExceptionEnum.LOGIN_FAIL.getMsg();
        } else if (e.getMessage().contains("Could not authenticate user")) {
            message = UserLoginException.UserLoginExceptionEnum.LOGIN_FAIL.getMsg();
        }else if (e instanceof LoginDynamicBaseException){
            errorCode=((LoginDynamicBaseException) e).getErrorCode();
            message=e.getMessage();
        }else{
            logger.error(e.getMessage(),e);
            message = e.getMessage();
        }
        LoginLogEventDTO loginLogEventDTO = new LoginLogEventDTO();
        loginLogEventDTO.setUsername(request.getParameter("username"));
        loginLogEventDTO.setStatus(SecurityConstant.LOGIN_FAIL);
        loginLogEventDTO.setMessage(message);
        SpringContextUtil.getAppContext().publishEvent(new CustomLoginLogEvent(loginLogEventDTO));
        ResponseUtil.out(response, ApiResult.ofFail(errorCode, message));
        logger.warn("后台登录登录失败");
    }


}
