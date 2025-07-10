package com.fushun.framework.util.json;

import com.fushun.framework.base.IBaseEnum;

public enum JsonEnum implements IBaseEnum<String> {

    BASE("BASE","BASE"),
    REDIS_TEMPLATE("REDIS_TEMPLATE","REDIS_TEMPLATE");


    JsonEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    private String code;
    private String desc;


    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getDesc() {
        return desc;
    }

}
