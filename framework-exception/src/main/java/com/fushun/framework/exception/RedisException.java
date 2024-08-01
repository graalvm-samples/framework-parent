package com.fushun.framework.exception;

import com.fushun.framework.exception.base.BaseExceptionEnum;

/**
 * redis 错误异常
 *
 * @verson 1.0
 * @return a
 * @date: 2018年12月16日18时03分
 * @author:wangfushun
 */
public class RedisException extends BaseException {
    public static final long BASECODE_EXCEPTION = 180101;

    public RedisException(IRedisExceptionEnum iRedisExceptionEnum) {
        super(iRedisExceptionEnum);
    }

    public RedisException(IRedisExceptionEnum iRedisExceptionEnum, String logMessage) {
        super(iRedisExceptionEnum, logMessage);
    }

    public RedisException(Throwable cause, IRedisExceptionEnum iRedisExceptionEnum) {
        super(cause, iRedisExceptionEnum);
    }

    public RedisException(Throwable cause, IRedisExceptionEnum iRedisExceptionEnum, String logMessage) {
        super(cause, iRedisExceptionEnum, logMessage);
    }

    @Override
    protected String getExceptionCode() {
        return "REDIS_";
    }

    /**
     * 异常错误定义枚举接口
     */
    public interface IRedisExceptionEnum extends BaseExceptionEnum {
    }

    /**
     * 异常错误定义
     */
    public enum RedisExceptionEnum implements IRedisExceptionEnum {
//        BASECODE_EXCEPTION("name_is_null", "名称为空")
        ;

        private String msg;

        RedisExceptionEnum(String msg) {
            this.msg = msg;
        }

        @Override
        public String getMsg() {
            return msg;
        }
    }
}
