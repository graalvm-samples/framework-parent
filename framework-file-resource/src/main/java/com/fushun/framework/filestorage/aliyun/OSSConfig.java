package com.fushun.framework.filestorage.aliyun;

import com.fushun.framework.filestorage.config.FilesStorageEnum;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("oss.aliyun")
@Data
public class OSSConfig {

    /**
     * 外网
     */
    private String ossEndPoint;
    /**
     * 内网
     */
    private String ossInternalEndPoint;

    //OSS 的key值
    private String ossAccessKeyId;

    //OSS 的secret值
    private String ossAccessKeySecret;

    //OSS 的bucket名字
    private String ossBucketName;
    /**
     * 开发
     */
    private Boolean development;

    private FilesStorageEnum filesStorageEnum=FilesStorageEnum.QiNIU_OSS;
}
