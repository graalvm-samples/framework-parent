package com.fushun.framework.util.util;

/**
 * 方法耗时计算
 */
public class MethodTimeConsuming {

    private static final ThreadLocal<Long> TIME_CONSUMING = new ThreadLocal<>();


    public static void begin() {
        TIME_CONSUMING.set(System.currentTimeMillis());
    }

    public static float consuming() {
        float consuming = (float)(System.currentTimeMillis() - TIME_CONSUMING.get()) / 1000;
        TIME_CONSUMING.remove();
        return consuming;
    }
}
