package com.fushun.framework.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 动态的异常处理
 */
public class DynamicBaseException extends RuntimeException {

    private Logger logger = LoggerFactory.getLogger(BaseException.class);

    /**
     * 错误的前缀
     */
    private static final String baseCode="DYNAMIC_";

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
    private DynamicBaseException() {
        super();
    }

    /**
     * @param code 定义返回前端的code
     * @param msg 定义异常错误信息 返回用户提示
     * @return
     * @date:
     * @author:
     * @version 1.0
     */
    public DynamicBaseException(String code,String msg) {
        initCodeMessage(code, msg);
    }

    /**
     *
     * @param code 定义返回前端的code
     * @param msg 定义异常错误信息 返回用户提示
     * @param logMessage 服务端日志打印错误信息
     */
    public DynamicBaseException(String code,String msg,String logMessage) {
        this.logMessage = logMessage;
        initCodeMessage(code, msg);
    }

    /**
     *
     * @param cause             抓到异常
     * @param code 定义返回前端的code
     * @param msg 定义异常错误信息 返回用户提示
     * @return
     * @date:
     * @author:
     * @version 1.0
     */
    public DynamicBaseException(Throwable cause, String code,String msg) {
        if (cause != null) {
            this.initCause(cause);
        }
        initCodeMessage(code, msg);
    }

    /**
     *
     * @param cause 异常case
     * @param code 定义返回前端的code
     * @param msg 定义异常错误信息 返回用户提示
     * @param logMessage 服务端日志打印错误信息
     */
    public DynamicBaseException(Throwable cause, String code,String msg,String logMessage) {
        this.logMessage = logMessage;
        if (cause != null) {
            this.initCause(cause);
        }
        initCodeMessage(code, msg);
    }

    /**
     * @param code 定义返回前端的code
     * @param msg 定义异常错误信息 返回用户提示
     * @return void
     * @date: 2018年12月16日16时06分
     * @author:wangfushun
     * @version 1.0
     */
    private void initCodeMessage(String code,String msg) {
        this.detailMessage = msg;
        this.errorCode = (baseCode+code).toUpperCase();
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

}
