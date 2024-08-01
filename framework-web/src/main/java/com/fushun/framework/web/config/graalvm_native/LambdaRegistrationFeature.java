package com.fushun.framework.web.config.graalvm_native;


import cn.hutool.core.util.ClassUtil;
import org.graalvm.nativeimage.hosted.Feature;
import org.graalvm.nativeimage.hosted.RuntimeSerialization;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

/**
 * lambda 表达式注入到graal中
 * @author ztp
 * @date 2023/8/18 11:53
 */
public class LambdaRegistrationFeature implements Feature {


    @Override
    public void duringSetup(DuringSetupAccess access) {
        // TODO 这里需要将lambda表达式所使用的成员类都注册上来,具体情况视项目情况而定,一般扫描@Controller和@Service的会多点.
        Set<Class<?>> classes= new HashSet<>();
        System.out.println("LambdaRegistrationFeature");
        try {

            Set<Class<?>> doScan =ClassUtil.scanPackage("", (clazz) -> {
                return clazz.isAnnotationPresent(Service.class);
            });

            for (Class clazz : doScan) {
                classes.add(clazz);
                System.out.println("Lambda clazz2:"+clazz);
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
        for(Class<?> clazz :classes){
            RuntimeSerialization.registerLambdaCapturingClass(clazz);
        }
    }

}
