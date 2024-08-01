package com.fushun.framework.security.handler;

import cn.hutool.core.util.ObjectUtil;
import com.fushun.framework.base.SpringContextUtil;
import com.fushun.framework.log.dto.LoginLogEventDTO;
import com.fushun.framework.log.events.CustomLoginLogEvent;
import com.fushun.framework.security.constant.SecurityConstant;
import com.fushun.framework.security.model.BaseLoginInfo;
import com.fushun.framework.security.service.TokenService;
import com.fushun.framework.util.response.ApiResult;
import com.fushun.framework.util.util.ResponseUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * 自定义退出处理类 返回成功
 *
 * @author ruoyi
 */
@Component
public class LogoutSuccessHandlerImpl implements LogoutSuccessHandler {

    @Autowired
    private TokenService tokenService;
    /**
     * 退出处理
     *
     * @return
     */
    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException
    {
        BaseLoginInfo loginUser =tokenService.getLoginUser();
        if (ObjectUtil.isNotNull(loginUser))
        {
            // 删除用户缓存记录
            tokenService.delLoginUser(loginUser.getToken());
            // 记录用户退出日志
            LoginLogEventDTO loginLogEventDTO = new LoginLogEventDTO();
            loginLogEventDTO.setUsername(loginUser.getUsername());
            loginLogEventDTO.setStatus(SecurityConstant.LOGIN_SUCCESS);
            loginLogEventDTO.setMessage("登录成功");
            SpringContextUtil.getAppContext().publishEvent(new CustomLoginLogEvent(loginLogEventDTO));
        }
        ResponseUtil.out(response, ApiResult.of("用户退出成功"));
    }
}
