package com.fushun.framework.web.config.advice;

import com.alibaba.excel.exception.ExcelAnalysisException;
import com.fushun.framework.exception.BaseException;
import com.fushun.framework.exception.DynamicBaseException;
import com.fushun.framework.util.response.ApiResult;
import com.fushun.framework.util.util.StringUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


/**
 * 自定controller 的异常处理
 * 与 error 处理逻辑分开了，独立处理异常的，如果异常被处理了，则error的处理逻辑就执行不了
 * TODO 如果同“error”结合起来使用，则更加好，因为这个可以针对性配置。而error兼容了web错误页面和json等内容类型
 */
@ControllerAdvice
@RestControllerAdvice
@Order(value = Ordered.LOWEST_PRECEDENCE - 2)
public class CommonExceptionAdvice extends DefaultControllerAdvice {

    private final String SERVER_MESSAGE = "系统异常，请联系管理员!";

    @ExceptionHandler({ConstraintViolationException.class})
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResponseEntity<?> handleConstraintViolationException(ConstraintViolationException ex) {
        HttpStatus status = getStatus(ex);
        return new ResponseEntity<>(ApiResult.ofFail("ARGUMENT_NOT_VALID_ERROR", ex.getMessage()), status);
    }

    /**
     * 指定 异常类型的拦截 处理
     *
     * @param request
     * @param ex
     * @return
     */
    @ExceptionHandler(BaseException.class)
    @ResponseBody
    ResponseEntity<?> handleControllerBadRequestException(HttpServletRequest request, BaseException ex) {
        HttpStatus status = getStatus(ex);
        if (!ex.isPrinted()) {
            if(StringUtils.isNotEmpty(ex.getLogMessage())){
                logger.error(ex.getLogMessage(), ex);
            }else {
                logger.error(ex.getMessage(), ex);
            }
        }
        return new ResponseEntity<>(ApiResult.ofFail(ex.getErrorCode(), ex.getMessage()), status);
    }


    /**
     * 指定 异常类型的拦截 处理
     *
     * @param request
     * @param ex
     * @return
     */
    @ExceptionHandler(DynamicBaseException.class)
    @ResponseBody
    ResponseEntity<?> handleControllerDynamicBaseException(HttpServletRequest request, DynamicBaseException ex) {
        HttpStatus status = getStatus(ex);
        if (!ex.isPrinted()) {
            if(StringUtils.isNotEmpty(ex.getLogMessage())){
                logger.error(ex.getLogMessage(), ex);
            }else {
                logger.error(ex.getMessage(), ex);
            }
        }
        return new ResponseEntity<>(ApiResult.ofFail(ex.getErrorCode(), ex.getMessage()), status);
    }



    /**
     * 指定 异常类型的拦截 处理
     *
     * @param request
     * @param ex
     * @return
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    ResponseEntity<?> handleControllerException(HttpServletRequest request, Throwable ex) {
        HttpStatus status = getStatus(ex);
        String code = "SYSTEM_ERROR";
        String message = ex.getMessage();
        String logMessage="";
        if (ex instanceof BaseException ) {
            code = ((BaseException) ex).getErrorCode();
            message = ex.getMessage();
            logMessage=message;
        } else if (ex instanceof ExcelAnalysisException) {
            code = "FILE_UPLOAD_ERROR";
            message = ex.getMessage();
            logMessage=message;
        } else if (ex instanceof NullPointerException) {
            message = SERVER_MESSAGE;
            logMessage="发生了空指针异常!!!!!";
        }else{
            message = SERVER_MESSAGE;
            logMessage=ex.getMessage();
        }
        logger.error(logMessage, ex);
        return new ResponseEntity<>(ApiResult.ofFail(code, message), status);
    }

}