package com.fushun.framework.bouncycastle.crypto.security.config;

import com.google.common.reflect.ClassPath;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aot.hint.MemberCategory;
import org.springframework.aot.hint.RuntimeHints;
import org.springframework.aot.hint.RuntimeHintsRegistrar;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PyBouncycastleRuntimeHintsRegistrar implements RuntimeHintsRegistrar {

    private static Logger logger= LoggerFactory.getLogger(PyBouncycastleRuntimeHintsRegistrar.class);

    @Override
    public void registerHints(RuntimeHints hints, ClassLoader classLoader) {

//        hints.resources().registerPattern("bouncycastle/provider/configuration");
//        hints.resources().registerPattern("org/bouncycastle/.*");
//        hints.resources().registerPattern("crypto.policy");
//        hints.reflection().registerType()
//        hints.reflection().registerType(BouncyCastleProvider.class,MemberCategory.values());
//        hints.reflection().registerType(java.security.AllPermission.class,MemberCategory.values());
//        hints.reflection().registerType(java.security.SecurityPermission.class,MemberCategory.values());
//        hints.reflection().registerType(java.security.cert.PKIXRevocationChecker.class,MemberCategory.values());
//        {
//          "name":"java.security.AllPermission"
//        },
//        {
//          "name":"java.security.SecurityPermission"
//        },
//        {
//          "name":"java.security.cert.PKIXRevocationChecker"
//        },
//        hints.reflection().registerType(org.bouncycastle.crypto.digests.SHA256Digest.class,MemberCategory.values());
//        hints.reflection().registerType(javax.crypto.Cipher.class,MemberCategory.values());
//        hints.reflection().registerType(org.bouncycastle.crypto.paddings.PKCS7Padding.class,MemberCategory.values());
//        hints.reflection().registerType(org.bouncycastle.jcajce.provider.symmetric.AES.ECB.class,MemberCategory.values());


        try {
            Set<Class<?>> classesToRegister = scanPackage();
            for (Class<?> clazz : classesToRegister) {
                try{
                    hints.reflection().registerType(clazz, MemberCategory.values());
                    logger.info("registerType clazz:{}",clazz);
                }catch (Throwable err){
                    logger.error("registerType clazz:{},{}",clazz,err.getMessage());
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to register runtime hints", e);
        }

    }

    private static final List<String> BASE_PACKAGES = Arrays.asList(
            "org.bouncycastle.jcajce.provider.symmetric",
            "org.bouncycastle.jcajce.provider.digest",
            "org.bouncycastle.jcajce.provider.keystore",
            "org.bouncycastle.jcajce.provider.drbg"

    );

    public  Set<Class<?>> scanPackage() throws IOException {
        Set<Class<?>> classes = new HashSet<>();
        for (String pkg : BASE_PACKAGES) {
            ClassPath classpath = ClassPath.from(Thread.currentThread().getContextClassLoader());

            for (ClassPath.ClassInfo classInfo : classpath.getTopLevelClassesRecursive(pkg)) {
                Class<?> clazz = classInfo.load();
                classes.add(clazz);
                addNestedClasses(classes, clazz);
            }
        }
        Set<Class<?>> classes2 = new HashSet<>();
        classes.forEach(class_cl->{
            String name=extractAfterLastDollar(class_cl.getName());
//            if(name!=null && "Mappings".equals(name)){
                classes2.add(class_cl);
//            }
        });
        return classes2;
    }

    private  void addNestedClasses(Set<Class<?>> classes, Class<?> clazz) {
        for (Class<?> nestedClass : clazz.getDeclaredClasses()) {
            classes.add(nestedClass);
            addNestedClasses(classes, nestedClass); // 递归检查嵌套类
        }
    }

    public  String extractAfterLastDollar(String str) {
        if (str == null || str.isEmpty()) {
            return null;
        }

        int lastDollarIndex = str.lastIndexOf('$');

        if (lastDollarIndex == -1 || lastDollarIndex == str.length() - 1) {
            return null; // No '$' found or nothing after '$'
        }

        return str.substring(lastDollarIndex + 1);
    }



}