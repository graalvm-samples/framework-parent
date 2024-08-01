package com.fushun.framework.filestorage.qiniu;


import com.fushun.framework.exception.BaseException;
import com.fushun.framework.exception.base.BaseExceptionEnum;

public class QiNiuExceptin extends BaseException {

    public QiNiuExceptin(QiNiuExceptinEnum qiNiuExceptinEnum) {
        super(qiNiuExceptinEnum);
    }

    public QiNiuExceptin(QiNiuExceptinEnum qiNiuExceptinEnum, String logMessage) {
        super(qiNiuExceptinEnum, logMessage);
    }

    public QiNiuExceptin(Throwable cause, QiNiuExceptinEnum qiNiuExceptinEnum) {
        super(cause, qiNiuExceptinEnum);
    }

    public QiNiuExceptin(Throwable cause, QiNiuExceptinEnum qiNiuExceptinEnum, String logMessage) {
        super(cause, qiNiuExceptinEnum, logMessage);
    }

    @Override
    protected String getExceptionCode() {
        return null;
    }

    public static enum QiNiuExceptinEnum implements BaseExceptionEnum {
        UPLOAD_IMAGE_ERROR( "上传图片错误"),
        GET_FILE_INFO( "获取文件信息错误");

        private String msg;

        QiNiuExceptinEnum(String msg) {
            this.msg = msg;
        }

        @Override
        public String getMsg() {
            return msg;
        }

    }

}
