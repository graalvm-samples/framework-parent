package com.fushun.framework.exception;


import org.junit.jupiter.api.Test;

public class BaseExceptionTest {

    @Test
    public void oneException(){
        BusinessException businessException= new BusinessException(BusinessException.BusinessExceptionEnum.CONVERSION_TO_UNICODE);
        System.out.println(businessException.getErrorCode());
        System.out.println(businessException.getLogMessage());
//        businessException.printStackTrace();
    }

}