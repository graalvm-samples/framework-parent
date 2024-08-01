package com.fushun.framework.util.google.zxing;

import org.junit.jupiter.api.Test;

import java.util.Base64;


public class QRCodeUtilTest {

    @Test
    public void testQr(){
        byte[] qr = QRCodeUtil.zxingCodeCreate("doorPass.getEvidence()", 200,
                200, "png");
        System.out.println(Base64.getEncoder().encodeToString(qr));
    }
}