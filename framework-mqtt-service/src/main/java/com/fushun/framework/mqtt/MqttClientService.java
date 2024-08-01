package com.fushun.framework.mqtt;
import cn.hutool.core.util.ObjectUtil;
import com.fushun.framework.exception.DynamicBaseException;
import com.fushun.framework.json.utils.hutool.JSONUtil;
import jakarta.annotation.PostConstruct;
import org.eclipse.paho.mqttv5.client.*;
import org.eclipse.paho.mqttv5.client.persist.MemoryPersistence;
import org.eclipse.paho.mqttv5.common.MqttException;
import org.eclipse.paho.mqttv5.common.MqttMessage;
import org.eclipse.paho.mqttv5.common.MqttSubscription;
import org.eclipse.paho.mqttv5.common.packet.MqttProperties;
import org.eclipse.paho.mqttv5.common.packet.UserProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * @author Sirius
 * @date 2020/12/11 17:01
 */

@Component
@Order(value = Ordered.HIGHEST_PRECEDENCE)
public class MqttClientService {

    protected final Logger logger = LoggerFactory.getLogger(MqttClientService.class);

    private MqttClient client;

    private boolean first=false;

    @Autowired
    private MqttConfig mqttConfig;

    private ConcurrentHashMap<String,MqttSubscription> topicMap=new ConcurrentHashMap();

    private ConcurrentHashMap<String,IMqttMessageListener> topicCallBackMap=new ConcurrentHashMap();

    @PostConstruct
    public void init() throws MqttException {
        logger.info("初始化 mqtt client");
        connect();
        logger.info("mqtt 初始化成功");
    }

    /**
     * 普通推送
     * @param message
     * @param topic
     */
    public void publishMiddle(String message,String topic) {
        if (client != null) {
            MqttMessage mqttMessage = new MqttMessage(message.getBytes());
            mqttMessage.setQos(1);
            mqttMessage.setRetained(false);
            try {
                client.publish(topic, mqttMessage);
            } catch (MqttException e) {
                logger.error("mqtt publish fair 普通推送,exception:{}", e.getMessage());
            }
        }
    }

    /**
     * 客户端 响应发布主题
     * @param message
     * @param responseTopic
     * @param correlationData
     */
    @Async
    public void publishMiddle(String message,String responseTopic, byte[] correlationData) {
        if (client != null) {
            MqttMessage mqttMessage = new MqttMessage(message.getBytes());
            mqttMessage.setQos(0);
            mqttMessage.setRetained(false);
            MqttProperties mqttProperties=new MqttProperties();
            mqttProperties.setCorrelationData(correlationData);
            mqttMessage.setProperties(mqttProperties);
            try {
                client.publish(responseTopic, mqttMessage);
            } catch (MqttException e) {
                logger.error("mqtt publish 客户端 响应发布主题 fair,exception:{}", e.getMessage());
            }
        }
    }

    /**
     * 发送消息，并告知返回的topic，
     * 但是需要代码中自己实现监听返回topic的监听
     * @param message 消息
     * @param topic 生产者topic
     * @param responseTopic 订阅者， 返回时作为生产者的topic
     * @param correlationData 对比数据
     */
    public void publishMiddleAsync(String message, String topic, String responseTopic, byte[] correlationData) {
        //发布信息
        MqttMessage mqttMessage = new MqttMessage(message.getBytes());
        mqttMessage.setQos(1);
        mqttMessage.setRetained(false);
        MqttProperties mqttProperties = new MqttProperties();
        mqttProperties.setResponseTopic(responseTopic);
        mqttProperties.setCorrelationData(correlationData);
        mqttMessage.setProperties(mqttProperties);
        try {
            client.publish(topic, mqttMessage);
        } catch (MqttException e) {
            logger.error("publishMiddleAsync mqtt publish fair ，,exception:{}", e.getMessage());
        }
    }

    /**
     * 采用异步模型，同步获取客户端的效应信息
     * @param message 发送内容
     * @param topic 发布topic
     * @param responseTopic 响应返回topic
     * @param correlationData 对比数据
     * @param timeOut 超时时间，秒
     */
    @Async
    public Future<SubscribeSyncResult> publishMiddleSync(String message, String topic, String responseTopic, byte[] correlationData,long timeOut) {
        SubscribeSyncResult subscribeSyncResult=new SubscribeSyncResult();
        subscribeSyncResult.setSuccessful(false);
        try{
            if (client == null) {
                logger.error("client为空,不能进行此次同步推送");
                return new AsyncResult<SubscribeSyncResult>(subscribeSyncResult);
            }else {
                CountDownLatch countDownLatch=new CountDownLatch(1);
                MqttSubscription mqttSubscription=new MqttSubscription(responseTopic,1);
                //订阅响应
                client.subscribe(new MqttSubscription[]{mqttSubscription},new IMqttMessageListener[]{new IMqttMessageListener() {
                    @Override
                    public void messageArrived(String topic, MqttMessage message) throws Exception {
                        SubscribeSyncResult temp= JSONUtil.toBean(new String(message.getPayload(),StandardCharsets.UTF_8), SubscribeSyncResult.class);
                        subscribeSyncResult.setBody(temp.getBody());
                        subscribeSyncResult.setSuccessful(temp.isSuccessful());
                        countDownLatch.countDown();
                    }
                }});

                //发布信息
                MqttMessage mqttMessage = new MqttMessage(message.getBytes());
                mqttMessage.setQos(1);
                mqttMessage.setRetained(false);
                MqttProperties mqttProperties = new MqttProperties();
                mqttProperties.setResponseTopic(responseTopic);
                mqttProperties.setCorrelationData(correlationData);
                mqttProperties.getUserProperties().add(new UserProperty("request_id",MDC.get("request_id")));
                mqttMessage.setProperties(mqttProperties);
                try {
                    client.publish(topic, mqttMessage);
                } catch (MqttException e) {
                    logger.error("mqtt publish fair,exception:{}", e.getMessage());
                }

                //等待返回
                //
                boolean bool=countDownLatch.await(timeOut,TimeUnit.MILLISECONDS);
                //取消订阅，
                client.unsubscribe(responseTopic);
                if(!bool){
                    logger.error("等待响应超时");
                    return new AsyncResult<SubscribeSyncResult>(subscribeSyncResult);
                }
                //返回响应信息
                subscribeSyncResult.setSuccessful(true);
                return new AsyncResult<SubscribeSyncResult>(subscribeSyncResult);
            }
        }catch (Exception e){
            logger.error("publishMiddleSync publishMiddleSync_error",e);
        }
        return new AsyncResult<SubscribeSyncResult>(subscribeSyncResult);
    }

    /**
     * 添加订阅topic
     * @param topicFilter
     * @param qos
     * @param messageListener
     * @return
     * @throws MqttException
     */
    public IMqttToken subscribe(String topicFilter,int qos,  IMqttMessageListener messageListener) throws MqttException {
        MqttSubscription mqttSubscription=new MqttSubscription(topicFilter,qos);
        topicMap.put(topicFilter,mqttSubscription);
        topicCallBackMap.put(topicFilter,messageListener);
        return client.subscribe(new MqttSubscription[]{mqttSubscription},new IMqttMessageListener[]{messageListener});
    }


    /**
     * 添加订阅topic 请求响应模式
     * @param topicFilter
     * @param qos
     * @param callBackMqttMessageListener
     * @return
     * @throws MqttException
     */
    public IMqttToken subscribe(String topicFilter,int qos,  CallBackMqttMessageListener callBackMqttMessageListener) throws MqttException {
        MqttSubscription mqttSubscription=new MqttSubscription(topicFilter,qos);
        topicMap.put(topicFilter,mqttSubscription);
        topicCallBackMap.put(topicFilter,callBackMqttMessageListener);
        return client.subscribe(new MqttSubscription[]{mqttSubscription},new IMqttMessageListener[]{callBackMqttMessageListener});
    }

    /**
     * 取消订阅
     * @param topicFilter
     * @throws MqttException
     */
    public void unsubscribe(String topicFilter) throws MqttException {
        topicMap.remove(topicFilter);
        topicCallBackMap.remove(topicFilter);
        client.unsubscribe(topicFilter);
    }


    private void connect() throws MqttException, DynamicBaseException  {
        //防止重复创建MQTTClient实例
        if (client == null) {
            client = new MqttClient(mqttConfig.URL, UUID.randomUUID().toString(), new MemoryPersistence());
            client.setCallback(new MqttCallback() {
                @Override
                public void disconnected(MqttDisconnectResponse disconnectResponse) {
                    String mesage="";
                    if(ObjectUtil.isNotNull(disconnectResponse)){
                        mesage=disconnectResponse.getException().getMessage();
                    }
                    logger.error("MQTT连接丢失，code:[{}],message:{}",disconnectResponse.getReturnCode(),disconnectResponse.getReasonString(),disconnectResponse.getException());

                }

                @Override
                public void mqttErrorOccurred(MqttException exception) {
                    logger.error("MQTT 错误异常",exception);
                }

                @Override
                public void messageArrived(String topic, MqttMessage message) throws Exception {}

                @Override
                public void deliveryComplete(IMqttToken token) {}

                @Override
                public void connectComplete(boolean reconnect, String serverURI) {
                    logger.info("MQTT 连接成功");
                    if(first){
                        try{
                            //取消订阅
                            for (MqttSubscription mqttSubscription : topicMap.values()) {
                                client.unsubscribe(mqttSubscription.getTopic());
                            }
                            //重新连接，重新订阅
                            client.subscribe(topicMap.values().toArray(new MqttSubscription[topicMap.values().size()]),topicCallBackMap.values().toArray(new IMqttMessageListener[topicCallBackMap.values().size()]));

                        }catch (Exception e){
                            logger.error("connectComplete 错误",e);
                        }

                    }
                    first=true;
                }

                @Override
                public void authPacketArrived(int reasonCode, MqttProperties properties) {}
            });
            client.connect(getOptions());
            if(!client.isConnected()){
                throw new DynamicBaseException("MQTT_CONNECT_FAIL","MQTT 连接失败");
            }
        }
    }

    private MqttConnectionOptions getOptions() {
        MqttConnectionOptions connOpts = new MqttConnectionOptions();
        connOpts.setAutomaticReconnect(true);
        connOpts.setCleanStart(true);
        connOpts.setUserName(mqttConfig.USER_NAME);
        connOpts.setPassword(mqttConfig.PASSWORD.getBytes(StandardCharsets.UTF_8));
        // 设置超时时间
        connOpts.setConnectionTimeout(10);
        // 设置会话心跳时间
        connOpts.setKeepAliveInterval(20);
        return connOpts;
    }
}
