package com.fushun.framework.exception;


import com.fushun.framework.exception.base.BaseExceptionEnum;

/**
 * @author Administrator
 */
public class BusinessException extends BaseException {

    private static final long serialVersionUID = 1L;

    public BusinessException(IBusinessExceptionEnum businessExceptionEnum) {
        super(businessExceptionEnum);
    }

    public BusinessException(IBusinessExceptionEnum businessExceptionEnum, String logMessage) {
        super(businessExceptionEnum, logMessage);
    }

    public BusinessException(IBusinessExceptionEnum businessExceptionEnum, String logMessage,String errMessage) {
        super(businessExceptionEnum, logMessage , errMessage);
    }

    public BusinessException(Throwable cause, IBusinessExceptionEnum businessExceptionEnum) {
        super(cause, businessExceptionEnum);
    }

    public BusinessException(Throwable cause, IBusinessExceptionEnum businessExceptionEnum, String logMessage) {
        super(cause, businessExceptionEnum, logMessage);
    }

    @Override
    protected String getExceptionCode() {
        return "BUS_";
    }

    /**
     * 异常错误定义枚举接口
     */
    public interface IBusinessExceptionEnum extends BaseExceptionEnum {
    }

    /**
     * 异常错误定义
     */
    public enum BusinessExceptionEnum implements IBusinessExceptionEnum {
        CONVERSION_TO_UNICODE("字符串转换为Unicode错误"),
        GENERATE_QR_CODE("生成二维码错误");

        private String msg;

        BusinessExceptionEnum( String msg) {
            this.msg = msg;
        }

        @Override
        public String getMsg() {
            return msg;
        }
    }


}
