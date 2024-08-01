package com.fushun.framework.security.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Exrickx
 */
@Getter
@Setter
@ConfigurationProperties(prefix = IgnoredUrlsConfig.PREFIX)
public class IgnoredUrlsConfig {
    public static final String PREFIX = "ignored";


    /**
     * 需要鉴权流程校验。但是不需要登陆的
     */
    private List<String> urls = new ArrayList<>();

    /**
     * 不需要鉴权流程校验。前端直接访问的资源
     */
    private List<String> noLoginVerification = new ArrayList<>();

    /**
     *
     */
    private List<String> limitUrls = new ArrayList<>();
}
