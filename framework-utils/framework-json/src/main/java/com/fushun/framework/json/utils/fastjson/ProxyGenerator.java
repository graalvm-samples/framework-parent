//package com.fushun.framework.json.utils.fastjson;
//
//
//import javassist.*;
//
//public class ProxyGenerator {
//
//    public static void generateProxy(Class<?> originalClass, String proxyClassName) throws Exception {
//        ClassPool pool = ClassPool.getDefault();
//        CtClass original = pool.get(originalClass.getName());
//
//        // 创建代理类
//        CtClass proxy = pool.makeClass(proxyClassName);
//
//        // 为每个方法创建代理方法
//        for (CtMethod method : original.getDeclaredMethods()) {
//            // 跳过非静态方法
//            if (!Modifier.isStatic(method.getModifiers())) {
//                continue;
//            }
//
//            CtMethod proxyMethod = new CtMethod(method.getReturnType(), method.getName(), method.getParameterTypes(), proxy);
//            proxyMethod.setModifiers(Modifier.STATIC | Modifier.PUBLIC);
//
//            // 修改方法体，增加额外逻辑
//            String body;
//            if (method.getReturnType() == CtClass.voidType) {
//                body = "{"
//                        + "    // 这里可以增加额外的逻辑\n"
//                        + "    " + originalClass.getName() + "." + method.getName() + "($$);"
//                        + "}";
//            } else {
//                body = "{"
//                        + "    // 这里可以增加额外的逻辑\n"
//                        + "    return " + originalClass.getName() + "." + method.getName() + "($$);"
//                        + "}";
//            }
//            System.out.println("Generated method body for " + method.getName() + ": " + body); // 调试输出生成的方法体
//            proxyMethod.setBody(body);
//            proxy.addMethod(proxyMethod);
//        }
//
//        // 增加自定义方法
//        CtMethod newMethod = new CtMethod(CtClass.voidType, "newMethod", new CtClass[]{pool.get("java.lang.Object")}, proxy);
//        newMethod.setModifiers(Modifier.STATIC | Modifier.PUBLIC);
//        newMethod.setBody("{ System.out.println(\"Custom logic\"); }");
//        proxy.addMethod(newMethod);
//
//        // 保存生成的代理类
//        proxy.writeFile("framework-utils/framework-json/src/main/java");
//    }
//
//    public static void main(String[] args) {
//        try {
//            generateProxy(com.alibaba.fastjson2.JSON.class, "com.fushun.framework.json.utils.fastjson.JSONProxy");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//}
