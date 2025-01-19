//package com.fushun.framework.web.config.advice;
//
//import com.fushun.framework.util.response.ApiResult;
//import com.fushun.framework.util.util.JsonUtil;
//import jakarta.servlet.http.HttpServletRequest;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.core.annotation.AnnotatedElementUtils;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.HttpStatusCode;
//import org.springframework.http.ResponseEntity;
//import org.springframework.lang.Nullable;
//import org.springframework.validation.BindException;
//import org.springframework.validation.BindingResult;
//import org.springframework.validation.FieldError;
//import org.springframework.validation.ObjectError;
//import org.springframework.web.bind.MethodArgumentNotValidException;
//import org.springframework.web.bind.annotation.ResponseStatus;
//import org.springframework.web.context.request.ServletRequestAttributes;
//import org.springframework.web.context.request.ServletWebRequest;
//import org.springframework.web.context.request.WebRequest;
//import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
//import org.springframework.web.util.WebUtils;
//
//import java.util.Collections;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.stream.Collectors;
//
///**
// * 默认的异常处理
// */
//public class DefaultControllerAdvice extends ResponseEntityExceptionHandler {
//
//    protected Logger logger = LoggerFactory.getLogger(DefaultControllerAdvice.class);
//
//    private static final String ARGUMENT_NOT_VALID_ERROR = "ARGUMENT_NOT_VALID_ERROR";
//
//    protected HttpStatus getStatus(Throwable ex) {
//        ResponseStatus responseStatus = AnnotatedElementUtils.findMergedAnnotation(ex.getClass(), ResponseStatus.class);
//        if (responseStatus == null) {
//            return HttpStatus.OK;
//        }
//        return responseStatus.value();
//    }
//
//    /**
//     * 异常处理
//     *
//     * @param ex         the exception
//     * @param body       the body for the response
//     * @param headers    the headers for the response
//     * @param status     the response status
//     * @param webRequest the current request
//     */
//    @Override
//    protected ResponseEntity<Object> handleExceptionInternal(
//            Exception ex, @Nullable Object body, HttpHeaders headers, HttpStatusCode status, WebRequest webRequest) {
////        ServletWebRequest servletWebRequest= (ServletWebRequest)request;
//        // 接收到请求，记录请求内容
//        ServletRequestAttributes attributes = (ServletRequestAttributes) webRequest;
//        HttpServletRequest httpServletRequest = attributes.getRequest();
//        Map<String, Object> headersMap = new HashMap<>();
//        Collections.list(httpServletRequest.getHeaderNames())
//                .stream()
//                .forEach(name -> headersMap.put(name, httpServletRequest.getHeader(name)));
//        // 记录下请求内容
//        logger.error("错误请求 URL:[{}],hears:[{}],HTTP_METHOD:[{}],IP:[{}],PATH:[{}],ARGS[{}]",
//                httpServletRequest.getRequestURL().toString(),
//                JsonUtil.toJson(headersMap),
//                httpServletRequest.getMethod(),
//                httpServletRequest.getRemoteAddr(),
//                httpServletRequest.getServletPath(),
//                JsonUtil.classToJson(httpServletRequest.getParameterMap()),
//                ex
//        );
//        String errorCode = "error";
//        if (HttpStatus.INTERNAL_SERVER_ERROR.equals(status)) {
//            errorCode = "INTERNAL_SERVER_ERROR";
//            webRequest.setAttribute(WebUtils.ERROR_EXCEPTION_ATTRIBUTE, ex, WebRequest.SCOPE_REQUEST);
//        }
//
//        return new ResponseEntity<>(ApiResult.ofFail(errorCode, "请求错误"), status);
//    }
//
//    /**
//     * 重新校验失败错误提示
//     * @param ex
//     * @param headers
//     * @param status
//     * @param request
//     * @return
//     */
//    @Override
//    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
//                                                                  HttpHeaders headers,
//                                                                  HttpStatusCode status,
//                                                                  WebRequest request) {
//        BindingResult bindingResult = ex.getBindingResult();
//        StringBuilder sb = new StringBuilder("校验失败:");
//        for (FieldError fieldError : bindingResult.getFieldErrors()) {
//            sb.append(fieldError.getField()).append("：").append(fieldError.getDefaultMessage()).append(", ");
//        }
//        String message = sb.toString();
//
//        if(request instanceof ServletWebRequest){
//            ServletRequestAttributes attributes = (ServletRequestAttributes) request;
//            HttpServletRequest httpServletRequest = attributes.getRequest();
//            Map<String, Object> headersMap = new HashMap<>();
//            Collections.list(httpServletRequest.getHeaderNames())
//                    .stream()
//                    .forEach(name -> headersMap.put(name, httpServletRequest.getHeader(name)));
//            // 记录下请求内容
//            logger.warn("错误请求 URL:[{}],hears:[{}],HTTP_METHOD:[{}],IP:[{}],PATH:[{}],ARGS[{}]",
//                    httpServletRequest.getRequestURL().toString(),
//                    JsonUtil.toJson(headersMap),
//                    httpServletRequest.getMethod(),
//                    httpServletRequest.getRemoteAddr(),
//                    httpServletRequest.getServletPath(),
//                    JsonUtil.classToJson(httpServletRequest.getParameterMap()),
//                    ex
//            );
//        }
//        logger.error("参数不合法异常,错误资料:[{}]",message,ex);
//        return new ResponseEntity<>(ApiResult.ofFail(ARGUMENT_NOT_VALID_ERROR, message), HttpStatus.OK);
//    }
//}
