package com.fushun.framework.security.exception;


import com.fushun.framework.exception.BaseException;
import com.fushun.framework.exception.base.BaseExceptionEnum;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Administrator
 */
@ResponseStatus(HttpStatus.OK)
public class UserLoginException extends BaseException {

    private static final long serialVersionUID = 1L;

    public UserLoginException(IUserLoginExceptionEnum businessExceptionEnum) {
        super(businessExceptionEnum);
    }

    public UserLoginException(IUserLoginExceptionEnum loginExceptionEnum, String logMessage) {
        super(loginExceptionEnum, logMessage);
    }

    public UserLoginException(Throwable cause, IUserLoginExceptionEnum loginExceptionEnum) {
        super(cause, loginExceptionEnum);
    }

    public UserLoginException(Throwable cause, IUserLoginExceptionEnum loginExceptionEnum, String logMessage) {
        super(cause, loginExceptionEnum, logMessage);
    }

    @Override
    protected String getExceptionCode() {
        return "LOGIN_";
    }

    /**
     * 异常错误定义枚举接口
     */
    public interface IUserLoginExceptionEnum extends BaseExceptionEnum {
    }

    /**
     * 异常错误定义
     */
    public enum UserLoginExceptionEnum implements IUserLoginExceptionEnum {
        UNLOGIN( "尚未登录"),
        LOGIN_FAIL("用户名或密码错误"),
        USER_NOT_EXIST("用户不存在或者已经被删除"),
        NO_LOGIN_ERROR( "禁止登录");

        private String msg;

        UserLoginExceptionEnum( String msg) {
            this.msg = msg;
        }

        @Override
        public String getMsg() {
            return msg;
        }
    }


}
