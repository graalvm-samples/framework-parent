package com.fushun.framework.exception;


import com.fushun.framework.exception.base.BaseExceptionEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 异常 顶级类
 */
public abstract class BaseException extends RuntimeException {

    private Logger logger = LoggerFactory.getLogger(BaseException.class);

    /**
     * 重写定义了 detailMessage
     * 因{@linkRuntimeException.detailMessage}是priate
     */
    protected String detailMessage;

    /**
     * 日志打印输出，不作为错误提示返回前端
     */
    private String logMessage;

    /**
     * 异常的错误码
     */
    private String errorCode=null;

    private boolean isPrinted = false;//用于防止重复打印异常堆栈

    /**
     * 禁用无参数构造
     */
    private BaseException() {
        super();
    }

    /**
     * @param baseExceptionEnum  定义异常错误信息 返回用户提示
     * @return
     * @date:
     * @author:
     * @version 1.0
     */
    public BaseException(BaseExceptionEnum baseExceptionEnum) {
        initCodeMessage(baseExceptionEnum.getMsg(), baseExceptionEnum);
    }

    /**
     *
     * @param baseExceptionEnum 定义异常错误信息 返回用户提示
     * @param logMessage 服务端日志打印错误信息
     */
    public BaseException(BaseExceptionEnum baseExceptionEnum,String logMessage) {
        this.logMessage = logMessage;
        initCodeMessage(baseExceptionEnum.getMsg(), baseExceptionEnum);
    }

    /**
     *
     * @param baseExceptionEnum 定义异常错误信息 返回用户提示
     * @param logMessage 服务端日志打印错误信息
     * @param errMessage 自定义的错误信息
     *
     */
    public BaseException(BaseExceptionEnum baseExceptionEnum,String logMessage,String errMessage) {
        this.logMessage = logMessage;
        initCodeMessage(errMessage, baseExceptionEnum);
    }

    /**
     *
     * @param cause             抓到异常
     * @param baseExceptionEnum  定义异常错误信息 返回用户提示
     * @return
     * @date:
     * @author:
     * @version 1.0
     */
    public BaseException(Throwable cause, BaseExceptionEnum baseExceptionEnum) {
        if (cause != null) {
            this.initCause(cause);
        }
        initCodeMessage(baseExceptionEnum.getMsg(), baseExceptionEnum);
    }

    /**
     *
     * @param cause 异常case
     * @param baseExceptionEnum  定义异常错误信息 返回用户提示
     * @param logMessage 服务端日志打印错误信息
     */
    public BaseException(Throwable cause, BaseExceptionEnum baseExceptionEnum,String logMessage) {
        this.logMessage = logMessage;
        if (cause != null) {
            this.initCause(cause);
        }
        initCodeMessage(baseExceptionEnum.getMsg(), baseExceptionEnum);
    }




    @Override
    public StackTraceElement[] getStackTrace() {
        isPrinted = true;
        return super.getStackTrace();
    }

    public boolean isPrinted() {
        return isPrinted;
    }

    /**
     * @param message           错误信息
     * @param baseExceptionEnum  定义异常错误信息 返回用户提示
     * @return void
     * @date: 2018年12月16日16时06分
     * @author:wangfushun
     * @version 1.0
     */
    private void initCodeMessage(String message, BaseExceptionEnum baseExceptionEnum) {
        String code = getExceptionCode() + ((Enum)baseExceptionEnum).name();
//        CodeMessage codeMessage = null;
//        String msg = "";
//        if (codeMessage != null) {
//            try {
//                msg = codeMessage.getMessageByCodeNo(code);
//            } catch (Exception e) {
//                msg = codeMessage.getMessageForRedis(code);
//            }
//            if (msg != null && !"".equals(msg)) {
//                logger.error("application error msg:[{}]", message);
//                message = msg;
//            }
//        }

        this.detailMessage = message;
        this.errorCode = code;
    }

    /**
     * 获取错误消息
     * @return
     */
    @Override
    public String getMessage() {
        return this.detailMessage;
    }

    /**
     * 获取错误码
     * @return
     */
    public String getErrorCode(){
        return this.errorCode;
    }

    /**
     * 获取日志打印错误信息
     * @return
     */
    public String getLogMessage(){
        return this.logMessage;
    }


    /**
     *获取异常的前缀码
     *
     */
    protected abstract String getExceptionCode();
}
