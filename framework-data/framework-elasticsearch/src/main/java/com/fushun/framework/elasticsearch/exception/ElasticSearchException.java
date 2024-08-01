package com.fushun.framework.elasticsearch.exception;


import com.fushun.framework.exception.BaseException;
import com.fushun.framework.exception.BusinessException;
import com.fushun.framework.util.exception.ConverterException;
import com.fushun.framework.exception.base.BaseExceptionEnum;

/**
 * 搜索异常
 */
public class ElasticSearchException extends BaseException {

    public ElasticSearchException(ConverterExceptionEnum baseExceptionEnum) {
        super(baseExceptionEnum);
    }

    public ElasticSearchException(ConverterExceptionEnum baseExceptionEnum, String logMessage) {
        super(baseExceptionEnum, logMessage);
    }

    public ElasticSearchException(Throwable cause, ConverterExceptionEnum baseExceptionEnum) {
        super(cause, baseExceptionEnum);
    }

    public ElasticSearchException(Throwable cause, ConverterExceptionEnum baseExceptionEnum, String logMessage) {
        super(cause, baseExceptionEnum, logMessage);
    }

    @Override
    protected String getExceptionCode() {
        return "ELASTIC_";
    }

    /**
     * 异常错误定义
     */
    public enum ConverterExceptionEnum implements BaseExceptionEnum {
        ELASTIC_SEARCH( "系统错误"),//搜索错误
        CREATE_DOCUMENT("系统错误"),//创建文档错误
        UPDATE_DOCUMENT( "系统错误"),//更新文档错误
        DELETE_DOCUMENT( "系统错误"),//删除文档错误
        UNKONW( "系统错误"),;//未知错误


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
