package com.fushun.framework.security.handler;

import com.fushun.framework.base.SpringContextUtil;
import com.fushun.framework.log.dto.LoginLogEventDTO;
import com.fushun.framework.log.events.CustomLoginLogEvent;
import com.fushun.framework.security.constant.SecurityConstant;
import com.fushun.framework.security.model.BaseLoginInfo;
import com.fushun.framework.security.service.TokenService;
import com.fushun.framework.util.response.ApiResult;
import com.fushun.framework.util.util.JsonUtil;
import com.fushun.framework.util.util.ResponseUtil;
import com.fushun.framework.util.util.StringUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 登录成功处理类
 *
 * @author Exrickx
 */
@Slf4j
@Component
public class CustomAuthenticationSuccessHandler<T extends BaseLoginInfo> extends SavedRequestAwareAuthenticationSuccessHandler {

    @Autowired
    private TokenService tokenService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        log.info("onAuthenticationSuccess 登录成功 enter...");

        T adminLoginInfo = (T) authentication.getPrincipal();

        String token=adminLoginInfo.getToken();
        //不存在，则创建token
        if(StringUtils.isEmpty(token)){
            token = tokenService.createToken(adminLoginInfo);
        }else{
            tokenService.setUserAgent(adminLoginInfo);
            tokenService.refreshToken(adminLoginInfo);
        }
        log.info("adminLoginInfo:{}",adminLoginInfo);
        log.info("adminLoginInfo json:{}", JsonUtil.toJson(adminLoginInfo));

        LoginLogEventDTO loginLogEventDTO = new LoginLogEventDTO();
        loginLogEventDTO.setUsername(adminLoginInfo.getUsername());
        loginLogEventDTO.setStatus(SecurityConstant.LOGIN_SUCCESS);
        loginLogEventDTO.setMessage("登录成功");
        SpringContextUtil.getAppContext().publishEvent(new CustomLoginLogEvent(loginLogEventDTO));

        Map<String, Object> data = new HashMap<>();
        data.put(SecurityConstant.ACCESS_TOKEN, token);
        data.put("userId", adminLoginInfo.getUserId());
        data.put("userName", adminLoginInfo.getNickName());
        data.put("expireTime",adminLoginInfo.getExpireTime());
        ResponseUtil.out(response, ApiResult.of(data));
    }
}
