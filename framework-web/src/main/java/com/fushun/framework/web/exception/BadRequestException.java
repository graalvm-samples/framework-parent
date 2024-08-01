package com.fushun.framework.web.exception;

import com.fushun.framework.exception.BusinessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.OK)
public class BadRequestException extends BusinessException {


    public BadRequestException(IBadRequestExceptionEnum badRequestExceptionEnum) {
        super(badRequestExceptionEnum);
    }

    public BadRequestException(IBadRequestExceptionEnum badRequestExceptionEnum, String logMessage) {
        super(badRequestExceptionEnum, logMessage);
    }

    public BadRequestException(IBadRequestExceptionEnum badRequestExceptionEnum, String logMessage,String errMessage) {
        super(badRequestExceptionEnum, logMessage, errMessage);
    }

    public BadRequestException(Throwable cause, IBadRequestExceptionEnum badRequestExceptionEnum) {
        super(cause, badRequestExceptionEnum);
    }

    public BadRequestException(Throwable cause, IBadRequestExceptionEnum badRequestExceptionEnum, String logMessage) {
        super(cause, badRequestExceptionEnum, logMessage);
    }


    @Override
    protected String getExceptionCode() {
        return "REQ_";
    }

    /**
     * 异常错误定义枚举接口
     */
    public interface IBadRequestExceptionEnum extends IBusinessExceptionEnum {
    }
}
