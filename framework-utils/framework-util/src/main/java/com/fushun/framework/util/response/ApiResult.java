package com.fushun.framework.util.response;


import com.fushun.framework.json.config.JsonGraalVMNativeBean;
import com.fushun.framework.util.util.StringUtils;
import lombok.Data;
import org.slf4j.MDC;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 接口返回对象
 *
 * @param <T>
 */
@Data
public class ApiResult<T> implements JsonGraalVMNativeBean {
    /**
     * 如果是成功，则code为ok
     */
    private String code = "ok";
    /**
     * 对错误的具体解释
     */
    private String message;
    private String requestId;
    /**
     * 返回的结果包装在value中，value可以是单个对象
     */
    private T data;
    //忽略getter和setter，以及构造函数

    public static <T> ApiResult<T> of(T value) {
        ApiResult<T> apiResult = new ApiResult<>();
        apiResult.setData(value);
        apiResult.setRequestId(MDC.get("request_id"));
        return apiResult;
    }

    public static <T> ApiResult<T> ofFail(String errorCode, String message) {
        ApiResult<T> apiResult = new ApiResult<>();
        apiResult.setCode(errorCode);
        apiResult.setMessage(message);
        apiResult.setRequestId(MDC.get("request_id"));
        return apiResult;
    }

    public boolean isSuccess() {
        return StringUtils.isNotEmpty(this.code) && "ok".equals(this.code);
    }
}