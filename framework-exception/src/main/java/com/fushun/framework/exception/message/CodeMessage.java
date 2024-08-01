package com.fushun.framework.exception.message;

public interface CodeMessage {

    public String getMessageByCodeNo(String code);

    public String getMessageForRedis(String code);
}
