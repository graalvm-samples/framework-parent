//package com.fushun.framework.filestorage.aliyun;
//
//import org.junit.jupiter.api.Test;
//import org.springframework.boot.SpringBootConfiguration;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.context.annotation.ComponentScan;
//
//
//@SpringBootTest
//@SpringBootConfiguration
//@SpringBootApplication
//@ComponentScan(basePackages = { "com.fushun.framework"})
//public class OSSUtilTest {
//
//    @Test
//    public void getImgUrl() {
//        String fileName="community/receive-house/template//20220117140743403/物业前端代码问题及规范.pdf";
//       String out= OSSUtil.getDownloadUrl(fileName,"");
//        System.out.println(out);
//         out= OSSUtil.getImgUrl(fileName,"");
//        System.out.println(out);
//        out= OSSUtil.getImgUrl(fileName,1920,1080);
//        System.out.println(out);
//
//
//    }
//}