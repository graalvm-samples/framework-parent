package com.fushun.framework.mqtt;

import com.fushun.framework.json.config.JsonGraalVMNativeBean;
import lombok.Data;

@Data
public class SubscribeSyncResult implements JsonGraalVMNativeBean {

    /**
     * 返回信息
     */
    String body;

    /**
     * 是否成功
     */
    boolean isSuccessful;

}
