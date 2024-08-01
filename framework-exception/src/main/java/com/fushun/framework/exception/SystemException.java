package com.fushun.framework.exception;


import com.fushun.framework.exception.base.BaseExceptionEnum;

/**
 * 系统异常
 */
public class SystemException extends BaseException {

    public SystemException(ISystemExceptionEnum systemExceptionEnum) {
        super(systemExceptionEnum);
    }

    public SystemException(ISystemExceptionEnum systemExceptionEnum, String logMessage) {
        super(systemExceptionEnum, logMessage);
    }

    public SystemException(Throwable cause, ISystemExceptionEnum systemExceptionEnum) {
        super(cause, systemExceptionEnum);
    }

    public SystemException(Throwable cause, ISystemExceptionEnum systemExceptionEnum, String logMessage) {
        super(cause, systemExceptionEnum, logMessage);
    }

    @Override
    protected String getExceptionCode() {
        return "SYSTEM_";
    }

    /**
     * 异常错误定义枚举接口
     */
    public interface ISystemExceptionEnum extends BaseExceptionEnum {
    }

    /**
     * 异常错误定义
     */
    public enum SystemExceptionEnum implements ISystemExceptionEnum {
        UNKNOWN("未知异常"),
        REQUEST("请求错误"),
        RESPONSE("输出错误"),
        PERSIST("unable to inject id"),
        JMS("jms错误"),
        DATE("日期错误"),
        JAXB( "jaxb错误"),
        TYPE_MISMATCH("类型匹配错误"),
        ENCRYPTION_ERROR("加密异常"),
        SERVICE_INVOCATION_ERROR("服务调用错误"),
        REFLECTION("反射错误"),
        DEEPCLONE("深度克隆错误"),
        PROPERTIE_LOAD("属性加载异常"),
        INCONSISTENCE( "不一致异常"),
        TEMPLATE_PARSE( "模板解析异常"),
        FILE_EXPORT("文件导出异常"),
        FUNCTION_NOT_SUPPORTED("function不支持异常"),
        COMPRESS("function不支持异常"),
        DATA_PARSE( "日期解析异常"),
        UNSUPPORTED_CHARSET("function不支持异常"),
        NETWORK("网络异常,请稍后重试"),
        REPEAT_COMMIT_ERROR("三秒内不能重复提交，请稍后提交"),
        ;


        private String msg;

        SystemExceptionEnum(String msg) {
            this.msg = msg;
        }

        @Override
        public String getMsg() {
            return msg;
        }
    }
}
