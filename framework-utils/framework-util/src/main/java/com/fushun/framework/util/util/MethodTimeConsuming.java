package com.fushun.framework.util.util;

import cn.hutool.core.util.ObjectUtil;

/**
 * 方法耗时计算
 */
public class MethodTimeConsuming {

    public static void begin() {
        ThreadLocalUtil.setData("MethodTimeConsuming",System.currentTimeMillis());
        Integer num=ThreadLocalUtil.getData("MethodTimeConsuming_num");
        if (ObjectUtil.isNull(num)){
            ThreadLocalUtil.setData("MethodTimeConsuming_num",1);
        }else{
            num+=1;
            ThreadLocalUtil.setData("MethodTimeConsuming_num",num);
        }

    }

    public static float consuming() {
        long m=ThreadLocalUtil.getData("MethodTimeConsuming");
        float consuming = (float)(System.currentTimeMillis() - m) / 1000;
        Integer num=ThreadLocalUtil.getData("MethodTimeConsuming_num");
        if (ObjectUtil.isNull(num)){
            ThreadLocalUtil.removeKey("MethodTimeConsuming");
            ThreadLocalUtil.removeKey("MethodTimeConsuming_num");
        }
        if(num==1){
            ThreadLocalUtil.removeKey("MethodTimeConsuming");
            ThreadLocalUtil.removeKey("MethodTimeConsuming_num");
        }

        return consuming;
    }
}
