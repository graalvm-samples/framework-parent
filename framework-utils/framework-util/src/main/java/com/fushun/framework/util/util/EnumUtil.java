package com.fushun.framework.util.util;

import com.fushun.framework.base.IBaseEnum;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class EnumUtil {

    private static final Map<String, Map<String, ?>> CACHED_ENUMS_MAP = new ConcurrentHashMap<String, Map<String, ?>>();

    /**
     * 更加 code 获取枚举
     *
     * @param clazz
     * @param name  枚举的名称
     * @param <T>
     * @return
     */
    public static <T extends Enum<T>> T getEnumByName(Class<T> clazz, String name) {
        T result = null;
        if (name == null) {
            return result;
        }
        String key = clazz.getName() + "_name";
        Map<String, ?> mapEnum = CACHED_ENUMS_MAP.get(key);
        if (CollectionUtils.isEmpty(mapEnum)) {
            T[] enums = clazz.getEnumConstants();
            Map map = new HashMap();
            for (T iEnum : enums) {
                map.put(((Enum) iEnum).name(), iEnum);
            }
            CACHED_ENUMS_MAP.put(key, map);
            result = (T) map.get(name);
        } else {
            result = (T) mapEnum.get(name);
        }
        return result;
    }

    /**
     * 更加文本获取code
     *
     * @param clazz
     * @param text
     * @param <T>
     * @param <F>
     * @return
     */
    public static <T extends IBaseEnum<F>, F> T getEnumByDesc(Class<T> clazz, F text) {
        T result = null;
        if (text == null) {
            return result;
        }
        String key = clazz.getName() + "_text";
        Map<String, ?> mapEnum = CACHED_ENUMS_MAP.get(key);
        if (CollectionUtils.isEmpty(mapEnum)) {
            T[] enums = clazz.getEnumConstants();
            Map map = new HashMap();
            for (T iEnum : enums) {
                map.put(((IBaseEnum) iEnum).getDesc(), iEnum);
            }
            CACHED_ENUMS_MAP.put(key, map);
            result = (T) map.get(text);
        } else {
            result = (T) mapEnum.get(text);
        }
        return result;
    }

    /**
     * 根据 枚举的定义顺序的静态值，0开始
     *
     * @param clazz
     * @param index
     * @param <T>
     * @return
     */
    public static <T extends Enum<T>> T getEnumByOrdinal(Class<T> clazz, int index) {
        T result = null;
        String key = clazz.getName() + "_value";
        Map<String, ?> mapEnum = CACHED_ENUMS_MAP.get(key);
        if (CollectionUtils.isEmpty(mapEnum)) {
            T[] enums = clazz.getEnumConstants();
            Map map = new HashMap();
            for (T iEnum : enums) {
                map.put(((Enum<T>) iEnum).ordinal(), iEnum);
            }
            CACHED_ENUMS_MAP.put(key, map);
            result = (T) map.get(index);
        } else {
            result = (T) mapEnum.get(index);
        }
        return result;
    }

    /**
     * 根据条件获取枚举
     *
     * @param clazz     枚举类
     * @param isCode    是否通过code获取枚举  true：通过code获取  false：通过desc获取
     * @param typeValue 类型值
     * @return 枚举
     */
    public static <T extends Enum<T>> T getEnumByCodeDesc(Class<T> clazz, boolean isCode, String typeValue) {
        T result = null;
        try {
            //通过Class对象取得所有的enum实例
            T[] arr = clazz.getEnumConstants();
            Method targetMethod;
            if (isCode) {
                targetMethod = clazz.getDeclaredMethod("getCode");
            } else {
                targetMethod = clazz.getDeclaredMethod("getDesc");
            }
            String typeVal = null;
            for (T entity : arr) {
                typeVal = targetMethod.invoke(entity).toString();
                //执行的方法的值等于参数传过来要判断的值
                if (typeVal.equals(typeValue)) {
                    result = entity;
                    break;
                }
            }
        } catch (Exception e) {
            log.error("EnumUtil.getEnumByCode error,msg:{}", e.getMessage());
        }
        return result;
    }


    @SuppressWarnings({"rawtypes", "unchecked"})
    public static <T extends IBaseEnum<F>, F> T getEnumByCode(Class<T> clazz, F code) {
        T result = null;
        synchronized (CACHED_ENUMS_MAP) {
            Map<String, ?> mapEnum = CACHED_ENUMS_MAP.get(clazz.getName());
            if (CollectionUtils.isEmpty(mapEnum)) {
                T[] enums = clazz.getEnumConstants();
                Map map = new HashMap();
                for (T iEnum : enums) {
                    if (iEnum instanceof IBaseEnum) {
                        map.put(((IBaseEnum) iEnum).getCode(), iEnum);
                    }
                }
                CACHED_ENUMS_MAP.put(clazz.getName(), map);
                result = (T) map.get(code);
            } else {
                result = (T) mapEnum.get(code);
            }
        }
        return result;
    }

}
