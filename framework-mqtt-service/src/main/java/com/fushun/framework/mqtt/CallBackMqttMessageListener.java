package com.fushun.framework.mqtt;


import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.fushun.framework.base.SpringContextUtil;
import com.fushun.framework.json.utils.hutool.JSONUtil;
import com.fushun.framework.util.util.UUIDUtil;
import org.eclipse.paho.mqttv5.client.IMqttMessageListener;
import org.eclipse.paho.mqttv5.common.MqttMessage;
import org.eclipse.paho.mqttv5.common.packet.UserProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.core.task.AsyncTaskExecutor;

/**
 * 请求/响应模型 订阅消息 MessageListener
 */
public abstract class CallBackMqttMessageListener implements IMqttMessageListener {

    private Logger logger= LoggerFactory.getLogger(CallBackMqttMessageListener.class);

    private MqttClientService mqttClientService;

    private AsyncTaskExecutor threadPoolTaskExecutor = SpringContextUtil.getBean(AsyncTaskExecutor.class);

    public CallBackMqttMessageListener(MqttClientService mqttClientService){
        this.mqttClientService=mqttClientService;
    }


    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        threadPoolTaskExecutor.submit(new Runnable() {
            @Override
            public void run() {
                String responseTopic=message.getProperties().getResponseTopic();
                byte[] correlationData=message.getProperties().getCorrelationData();

                //设置线程id，让服务链条好处理
                String request_id= UUIDUtil.getUUID().toString();
                if(ObjectUtil.isNotNull(message.getProperties().getUserProperties())){
                    for (UserProperty userProperty:message.getProperties().getUserProperties()) {
                        if("request_id".equals(userProperty.getKey())){
                            request_id=userProperty.getValue();
                        }
                    }
                }
                MDC.put("request_id", request_id);
                String resultBody = null;
                try {
                    resultBody = messageArrived2(topic, message);
                }catch (Exception e){
                    logger.error("messageArrived2 error",e);
                    return;
                }
                if(StrUtil.isNotEmpty(responseTopic)){
                    SubscribeSyncResult subscribeSyncResult=new SubscribeSyncResult();
                    subscribeSyncResult.setSuccessful(false);
                    if(StrUtil.isNotEmpty(resultBody)){
                        subscribeSyncResult.setSuccessful(true);
                        subscribeSyncResult.setBody(resultBody);
                    }
                    mqttClientService.publishMiddle(JSONUtil.toJsonStr(subscribeSyncResult),responseTopic,correlationData);
                }
            }
        });
    }


     public abstract String messageArrived2(String topic, MqttMessage message);
}
