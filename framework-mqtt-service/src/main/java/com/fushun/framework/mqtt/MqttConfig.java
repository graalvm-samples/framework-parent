package com.fushun.framework.mqtt;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @author Sirius
 * @date 2020/12/11 17:01
 */
@Configuration
public class MqttConfig {


    @Value("${mqtt.control}")
    public String control;

    @Value("${mqtt.deviceTopic}")
    public String deviceTopic;

    @Value("${mqtt.url}")
    public String URL;

    @Value("${mqtt.userName}")
    public String USER_NAME;

    @Value("${mqtt.password}")
    public String PASSWORD;

    @Value("${mqtt.controlResult}")
    public String controlResult;

    @Value("${spring.profiles.active}")
    public String profilesActive;

    @Value("${mqtt.gatewayHeartTopic}")
    public String gatewayHeartTopic;
}
