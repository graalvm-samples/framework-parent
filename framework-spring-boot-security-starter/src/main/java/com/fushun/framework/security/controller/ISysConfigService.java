package com.fushun.framework.security.controller;

public interface ISysConfigService {

    /**
     * 获取验证码开关
     *
     * @return true开启，false关闭
     */
    default boolean selectCaptchaOnOff(){return true;} ;
}
