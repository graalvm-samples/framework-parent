package com.fushun.framework.security.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author Exrickx
 */
@Getter
@Setter
@ConfigurationProperties(prefix = BasicTokenConfig.PREFIX)
public class BasicTokenConfig {

    public static final String PREFIX = "basic.token";


    /**
     * 单点登陆
     */
    private Boolean sdl = true;


    /**
     * token默认过期时间
     */
    private Integer tokenExpireTime = 60 * 2;

    /**
     * 用户选择保存登录状态对应token过期时间（天）
     */
    private Integer saveLoginTime = 7;

    /**
     * 限制用户登陆错误次数（次）
     */
    private Integer loginTimeLimit = 10;

    /**
     * 错误超过次数后多少分钟后才能继续登录（分钟）
     */
    private Integer loginAfterTime = 10;
}
