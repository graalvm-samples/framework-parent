package com.fushun.framework.filestorage.exception;

import com.fushun.framework.exception.BusinessException;
import com.fushun.framework.util.exception.ConverterException;

public class FileResourceException extends BusinessException {


    public FileResourceException(IBusinessExceptionEnum businessExceptionEnum) {
        super(businessExceptionEnum);
    }

    public FileResourceException(IBusinessExceptionEnum businessExceptionEnum, String logMessage) {
        super(businessExceptionEnum, logMessage);
    }

    public FileResourceException(Throwable cause, IBusinessExceptionEnum businessExceptionEnum) {
        super(cause, businessExceptionEnum);
    }

    public FileResourceException(Throwable cause, IBusinessExceptionEnum businessExceptionEnum, String logMessage) {
        super(cause, businessExceptionEnum, logMessage);
    }

    @Override
    protected String getExceptionCode() {
        return "FILE_";
    }

    /**
     * 异常错误定义枚举接口
     */
    public interface IFileResourceExceptionEnum extends IBusinessExceptionEnum {
    }

    /**
     * 异常错误定义
     */
    public enum FileResourceExceptionEnum implements FileResourceException.IFileResourceExceptionEnum {
        UPLOAD_FILE_ERROR( "系统错误"),
        GET_OSS_URL_ERROR("获取oss文件URL失败")
        ;


        private String msg;

        FileResourceExceptionEnum(String msg) {
            this.msg = msg;
        }

        @Override
        public String getMsg() {
            return msg;
        }
    }
}
