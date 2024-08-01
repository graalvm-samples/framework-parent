package com.fushun.framework.exception.base;

/**
 * 示例代码
 * @author wangfushun
 * @version 1.0
 * @creation 2018年12月16日16时21分
 */
public enum BaseExceptionSampleEnum implements BaseExceptionEnum {
    BASECODE_EXCEPTION("名称为空");


    private String msg;

    BaseExceptionSampleEnum(String msg) {
        this.msg = msg;
    }

    @Override
    public String getMsg() {
        return msg;
    }
}
