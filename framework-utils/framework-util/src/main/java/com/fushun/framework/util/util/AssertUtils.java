package com.fushun.framework.util.util;

import com.fushun.framework.exception.BusinessException;

/**
 * 断言工具类
 *
 * @author zpcsa
 */
public abstract class AssertUtils {
    /**
     * 判断表达式为false抛错
     *
     * @param expression
     * @param businessExceptionEnum  验证枚举
     */
    public static void isTrue(boolean expression, BusinessException.IBusinessExceptionEnum businessExceptionEnum) {
        if (!expression) {
            throw new BusinessException(businessExceptionEnum);
        }
    }

    /**
     * 判断表达式为Null抛错
     * 自定义错误
     *
     * @param expression
     * @param businessExceptionEnum
     * @date: 2017-11-02 13:52:38
     * @author:wangfushun
     * @version 1.0
     */
    public static <T> void isNull(T expression, BusinessException.IBusinessExceptionEnum businessExceptionEnum) {
        if (BeanUtils.isNull(expression)) {
            throw new BusinessException(businessExceptionEnum);
        }
    }

    /**
     * 判断表达式不为Null抛错
     *
     * @param expression
     * @author zhoup
     */
    public static <T> void isNotNull(T expression, BusinessException.IBusinessExceptionEnum businessExceptionEnum) {
        if (BeanUtils.isNull(expression)) {
            throw new BusinessException(businessExceptionEnum);
        }
    }

    /**
     * 判断表达式不等于0抛错
     *
     * @param expression
     * @author zhoup
     */
    public static <T> void isNotEqualToZero(Integer expression, BusinessException.IBusinessExceptionEnum businessExceptionEnum) {
        if (expression != 0) {
            throw new BusinessException(businessExceptionEnum);
        }
    }

    /**
     * 判断表达式等于0抛错
     *
     * @param expression
     * @author zhoup
     */
    public static <T> void isEqualToZero(Integer expression, BusinessException.IBusinessExceptionEnum businessExceptionEnum) {
        if (expression == 0) {
            throw new BusinessException(businessExceptionEnum);
        }
    }
}
