package com.fushun.framework.security.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/check")
public class HealthExaminationController {


    @PostMapping("/health")
    public String health () {
        return "OK";
    }
}
