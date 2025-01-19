//package com.fushun.framework.web.config.filter;
//
//import com.fushun.framework.util.util.JsonUtil;
//import eu.bitwalker.useragentutils.UserAgent;
//import jakarta.servlet.*;
//import jakarta.servlet.annotation.WebFilter;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletRequestWrapper;
//import jakarta.servlet.http.HttpServletResponse;
//import jakarta.servlet.http.HttpServletResponseWrapper;
//import org.apache.commons.io.IOUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.Ordered;
//import org.springframework.core.annotation.Order;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.MediaType;
//import org.springframework.web.multipart.MultipartFile;
//import org.springframework.web.multipart.MultipartHttpServletRequest;
//import org.springframework.web.multipart.MultipartResolver;
//import org.springframework.web.multipart.support.StandardServletMultipartResolver;
//
//import java.io.ByteArrayInputStream;
//import java.io.ByteArrayOutputStream;
//import java.io.IOException;
//import java.nio.charset.StandardCharsets;
//import java.util.HashMap;
//import java.util.Map;
//
///**
// * 如果包含header参数，不做输出
// * 顺便 输出对应url 请求体，响应体，耗时
// */
//@Configuration
//@WebFilter(urlPatterns = "/*",filterName = "logFilter")
//@Order(Ordered.HIGHEST_PRECEDENCE+2)
//public class LogFilter implements Filter {
//
//    private static final Logger log = LoggerFactory.getLogger(LogFilter.class);
//
//
//    @Override
//    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
//        HttpServletRequest request = (HttpServletRequest)servletRequest;
//        HttpServletResponse response = (HttpServletResponse)servletResponse;
//        long requestTime = System.currentTimeMillis();
//        String uri = request.getRequestURI();
//        String contextPath = request.getContextPath();
//        String url = uri.substring(contextPath.length());
//        //静态资源 跳过
//        if (url.contains(".")) {
//            filterChain.doFilter(request, response);
//            return;
//        }
////		输出请求体
//        String requestBody = "";
//        String requestContentType = request.getHeader(HttpHeaders.CONTENT_TYPE);
//
//        if (requestContentType != null){
////			xml json
//            if (requestContentType.startsWith(MediaType.APPLICATION_JSON_VALUE) || requestContentType.startsWith(MediaType.APPLICATION_XML_VALUE)){
//                requestBody = getRequestBody(request);
//                final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(requestBody.getBytes(StandardCharsets.UTF_8));
//                request = new HttpServletRequestWrapper(request) {
//                    @Override
//                    public ServletInputStream getInputStream() throws IOException {
//                        return new ByteArrayServletInputStream(byteArrayInputStream);
//                    }
//                };
////		    普通表单提交
//            }else if (requestContentType.startsWith(MediaType.APPLICATION_FORM_URLENCODED_VALUE)){
//                requestBody = JsonUtil.toJson(request.getParameterMap());
////			文件表单提交
//            }else if (requestContentType.startsWith(MediaType.MULTIPART_FORM_DATA_VALUE)){
//                requestBody = getFormParam(request);
//            }
//        }
//        //都需要获取文化
//        String parameterBody=JsonUtil.toJson(request.getParameterMap());
//
//        final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//        response = new HttpServletResponseWrapper(response) {
//            @Override
//            public ServletOutputStream getOutputStream() throws IOException {
//                return new TeeServletOutputStream(super.getOutputStream(), byteArrayOutputStream);
//            }
//        };
//
//        filterChain.doFilter(request, response);
//
//        long costTime = System.currentTimeMillis() - requestTime;
//        String responseBody = "";
////		暂定只有json 输出响应体
//        String contentType = response.getHeader(HttpHeaders.CONTENT_TYPE);
//        if (contentType != null && contentType.startsWith(MediaType.APPLICATION_JSON_VALUE)){
//            responseBody = byteArrayOutputStream.toString();
//        }
//
//        UserAgent userAgent = UserAgent.parseUserAgentString(request.getHeader("user-agent"));
//        //客户端类型
//        String clientType = userAgent.getOperatingSystem().getDeviceType().getName();
//        //客户端操作系统类型
//        String osType = userAgent.getOperatingSystem().getName();
//        //客户端ip
//        String clientIp = request.getRemoteAddr();
//        //客户端port
//        int clientPort = request.getRemotePort();
//        //请求方式
//        String requestMethod = request.getMethod();
//        //客户端请求URI
//        String requestURI = request.getRequestURI();
//        //客户端整体请求信息
//        StringBuilder clientInfo = new StringBuilder();
//        clientInfo.append("客户端信息:[类型:").append(clientType)
//                .append(", 操作系统类型:").append(osType)
//                .append(", ip:").append(clientIp)
//                .append(", port:").append(clientPort)
//                .append(", 请求方式:").append(requestMethod)
//                .append(", ContentType:").append(requestContentType)
//                .append(", URI:").append(requestURI)
//                .append("]");
//        log.info(clientInfo.toString());
//        if (response.getStatus() >= 200 && response.getStatus() < 300) {
//            log.info(">>>>>>>>>>AfterReturning URL:{}, total time:{} ms, responseCode:{}, requestBody:{}，parameterBody:{}, responseBody:{}", url, costTime, response.getStatus(), requestBody,parameterBody, responseBody);
//        }else {
//            log.error(">>>>>>>>>>AfterReturning URL:{}, total time:{} ms, responseCode:{}, requestBody:{}，parameterBody:{}, responseBody:{}", url, costTime, response.getStatus(), requestBody,parameterBody, responseBody);
//        }
//    }
//
//    private String getRequestBody(HttpServletRequest request) {
//        int contentLength = request.getContentLength();
//        if(contentLength <= 0){
//            return "";
//        }
//        try {
//            return IOUtils.toString(request.getReader());
//        } catch (IOException e) {
//            log.error("获取请求体失败", e);
//            return "";
//        }
//    }
//
//    private String getFormParam(HttpServletRequest request) {
//        MultipartResolver resolver = new StandardServletMultipartResolver();
//        MultipartHttpServletRequest mRequest = resolver.resolveMultipart(request);
//
//        Map<String,Object> param = new HashMap<>();
//        Map<String,String[]> parameterMap = mRequest.getParameterMap();
//        if (!parameterMap.isEmpty()){
//            param.putAll(parameterMap);
//        }
//        Map<String, MultipartFile> fileMap = mRequest.getFileMap();
//        if(!fileMap.isEmpty()){
//            for (Map.Entry<String, MultipartFile> fileEntry : fileMap.entrySet()) {
//                MultipartFile file = fileEntry.getValue();
//                param.put(fileEntry.getKey(), file.getOriginalFilename()+ "(" + file.getSize()+" byte)");
//            }
//        }
//        return JsonUtil.toJson(param);
//    }
//
//
//}