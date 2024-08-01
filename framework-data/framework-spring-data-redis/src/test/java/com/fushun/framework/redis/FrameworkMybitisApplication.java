package com.fushun.framework.redis;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@ComponentScan(basePackages = { "com.fushun.framework"})
public class FrameworkMybitisApplication {
    public static void main(String[] args) {
        SpringApplication.run(FrameworkMybitisApplication.class, args);
    }
}
