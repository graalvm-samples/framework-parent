package com.fushun.framework.base;

/**
 * @author wenzc
 */
public interface IBaseEnum<T> {

    public T getCode();

    public String getDesc();

    static <E extends Enum<E>> IBaseEnum valueOf(String enumCode, Class<E> clazz) {
        return (IBaseEnum) Enum.valueOf(clazz, enumCode);
    }
}
