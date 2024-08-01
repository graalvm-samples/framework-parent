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
@ConfigurationProperties(prefix = CaptchaConfig.PREFIX)
public class CaptchaConfig {
    public static final String PREFIX = "captcha";

    private List<String> image = new ArrayList<>();

    private List<String> sms = new ArrayList<>();

    private List<String> vaptcha = new ArrayList<>();

    private List<String> email = new ArrayList<>();
}
