package com.fushun.framework.util.exception;

import com.fushun.framework.exception.BaseException;
import com.fushun.framework.exception.base.BaseExceptionEnum;

/**
 * 日期异常
 */
public class DateException extends BaseException {

    public DateException(IDateExceptionEnum dateExceptionEnum) {
        super(dateExceptionEnum);
    }

    public DateException(IDateExceptionEnum dateExceptionEnum, String logMessage) {
        super(dateExceptionEnum, logMessage);
    }

    public DateException(Throwable cause, IDateExceptionEnum dateExceptionEnum) {
        super(cause, dateExceptionEnum);
    }

    public DateException(Throwable cause, IDateExceptionEnum dateExceptionEnum, String logMessage) {
        super(cause, dateExceptionEnum, logMessage);
    }

    @Override
    protected String getExceptionCode() {
        return "DATE_";
    }

    /**
     * 异常错误定义枚举接口
     */
    public interface IDateExceptionEnum extends BaseExceptionEnum {
    }

    /**
     * 异常错误定义
     */
    public enum DateExceptionEnum implements IDateExceptionEnum{
        HALF_YEAR("半年计算错误"),
        QUARTER("季度错误");

        private String msg;

        DateExceptionEnum( String msg) {
            this.msg = msg;
        }

        @Override
        public String getMsg() {
            return msg;
        }
    }
}
