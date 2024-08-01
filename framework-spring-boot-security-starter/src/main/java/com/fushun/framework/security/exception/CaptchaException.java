package com.fushun.framework.security.exception;

import com.fushun.framework.exception.BaseException;
import com.fushun.framework.exception.base.BaseExceptionEnum;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 验证码错误异常
 */
@ResponseStatus(HttpStatus.OK)
public class CaptchaException extends BaseException {

    private static final long serialVersionUID = 1L;

    public CaptchaException(CaptchaException.ICaptchaExceptionEnum captchaExceptionEnum) {
        super(captchaExceptionEnum);
    }

    public CaptchaException(CaptchaException.ICaptchaExceptionEnum captchaExceptionEnum, String logMessage) {
        super(captchaExceptionEnum, logMessage);
    }

    public CaptchaException(Throwable cause, CaptchaException.ICaptchaExceptionEnum captchaExceptionEnum) {
        super(cause, captchaExceptionEnum);
    }

    public CaptchaException(Throwable cause, CaptchaException.ICaptchaExceptionEnum captchaExceptionEnum, String logMessage) {
        super(cause, captchaExceptionEnum, logMessage);
    }

    @Override
    protected String getExceptionCode() {
        return "LOGIN_";
    }

    /**
     * 异常错误定义枚举接口
     */
    public interface ICaptchaExceptionEnum extends BaseExceptionEnum {
    }

    /**
     * 异常错误定义
     */
    public enum CaptchaExceptionEnum implements CaptchaException.ICaptchaExceptionEnum {
        GENERATE_ERROR( "生成验证码失败");

        private String msg;

        CaptchaExceptionEnum( String msg) {
            this.msg = msg;
        }

        @Override
        public String getMsg() {
            return msg;
        }
    }


}
