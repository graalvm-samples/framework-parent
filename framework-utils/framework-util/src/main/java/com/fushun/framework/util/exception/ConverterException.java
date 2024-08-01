package com.fushun.framework.util.exception;

import com.fushun.framework.exception.BusinessException;

/**
 * 支付通知异常
 *
 * @author fushun
 * @version dev706
 * @creation 2017年6月2日
 */
public class ConverterException extends BusinessException {

    private static final long serialVersionUID = 1L;

    public ConverterException(IConverterExceptionEnum converterExceptionEnum) {
        super(converterExceptionEnum);
    }

    public ConverterException(IConverterExceptionEnum converterExceptionEnum, String logMessage) {
        super(converterExceptionEnum, logMessage);
    }

    public ConverterException(Throwable cause, IConverterExceptionEnum converterExceptionEnum) {
        super(cause, converterExceptionEnum);
    }

    public ConverterException(Throwable cause, IConverterExceptionEnum converterExceptionEnum, String logMessage) {
        super(cause, converterExceptionEnum, logMessage);
    }

    @Override
    protected String getExceptionCode() {
        return "CONVERTER_";
    }

    /**
     * 异常错误定义枚举接口
     */
    public interface IConverterExceptionEnum extends IBusinessExceptionEnum {
    }

    /**
     * 异常错误定义
     */
    public enum ConverterExceptionEnum implements IConverterExceptionEnum {
        CONVERTER( "系统错误")
        ;


        private String msg;

        ConverterExceptionEnum(String msg) {
            this.msg = msg;
        }

        @Override
        public String getMsg() {
            return msg;
        }
    }
}
