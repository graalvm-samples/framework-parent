package com.fushun.framework.filestorage.config;

import com.fushun.framework.base.IBaseEnum;
import lombok.Data;

public enum FilesStorageEnum implements IBaseEnum<String> {

    ALIYUN_OSS("ALIYUN_OSS","阿里云oss"),
    QiNIU_OSS("QiNIU_OSS","千牛oss");


    FilesStorageEnum(String code, String desc) {
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
