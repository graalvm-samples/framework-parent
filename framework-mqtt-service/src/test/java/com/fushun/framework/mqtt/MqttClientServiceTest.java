package com.fushun.framework.mqtt;

import org.eclipse.paho.mqttv5.common.MqttException;
import org.eclipse.paho.mqttv5.common.MqttMessage;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.Future;

@SpringBootTest
@SpringBootApplication
public class MqttClientServiceTest {

    private Logger logger= LoggerFactory.getLogger(this.getClass());

    @Autowired
    MqttClientService mqttClientService;

    @Test
    void publishMiddleSync() throws MqttException {
        mqttClientService.subscribe("test", 1, new CallBackMqttMessageListener(mqttClientService) {
            @Override
            public String messageArrived2(String topic, MqttMessage message) {
                logger.info(new String(message.getPayload(), StandardCharsets.UTF_8));
                return "response";
            }
        });

        try{
            while (true){
                Future<SubscribeSyncResult> future= mqttClientService.publishMiddleSync("send data","test","test-111","correlationData".getBytes(StandardCharsets.UTF_8),10000);
                SubscribeSyncResult data=future.get();
                logger.info(data.body);

                Thread.sleep(1000L);
            }
        }catch (Exception e){
            logger.warn("publishMiddleSync",e);
        }

        try {
            Thread.sleep(1111111L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}