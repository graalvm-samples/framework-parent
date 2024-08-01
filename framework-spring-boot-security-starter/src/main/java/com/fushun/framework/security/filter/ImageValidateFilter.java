//package com.fushun.framework.security.filter;
//
//import com.fushun.framework.redis.utils.RedisUtil;
//import com.fushun.framework.security.config.CaptchaConfig;
//import com.fushun.framework.util.util.ResponseUtil;
//import com.fushun.framework.util.util.StringUtils;
//import com.fushun.framework.util.response.ApiResult;
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.util.PathMatcher;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import java.io.IOException;
//
//
///**
// * 图形验证码过滤器
// * @author Exrick
// */
//@Slf4j
//@Configuration
//public class ImageValidateFilter extends OncePerRequestFilter {
//
//    /**
//     * 验证码 redis key
//     */
//    public static final String CAPTCHA_CODE_KEY = "captcha_codes:";
//
//    public static final Integer CAPTCHA_EXPIRATION=2;
//
//
//    @Autowired
//    private CaptchaConfig captchaConfig;
//
//    @Autowired
//    private RedisUtil redisUtil;
//
//    @Autowired
//    private PathMatcher pathMatcher;
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
//
//        // 判断URL是否需要验证
//        Boolean flag = false;
//        String requestUrl = request.getRequestURI();
//        for(String url : captchaConfig.getImage()){
//            if(pathMatcher.match(url, requestUrl)){
//                flag = true;
//                break;
//            }
//        }
//        if(flag){
//            String captchaId = request.getParameter("uuid");
//            String code = request.getParameter("code");
//            if(StringUtils.isEmpty(captchaId)|| StringUtils.isEmpty(code)){
//                ResponseUtil.out(response, ApiResult.ofFail("CAPTCHAID_NULL","请传入图形验证码所需参数captchaId或code"));
//                return;
//            }
//            String verifyKey = CAPTCHA_CODE_KEY + captchaId;
//            Object redisCode = redisUtil.getCacheObject(verifyKey);
//            if(StringUtils.isEmpty(redisCode)){
//                ResponseUtil.out(response, ApiResult.ofFail("CAPTCHAID_TIMEOUT","验证码已过期，请重新获取"));
//                return;
//            }
//
//            if(!redisCode.toString().toLowerCase().equals(code.toLowerCase())) {
//                log.info("验证码错误：code:[{}]，redisCode:[{}]",code,redisCode);
//
//                ResponseUtil.out(response, ApiResult.ofFail("CAPTCHAID_INPUT_ERROR","图形验证码输入错误"));
//                return;
//            }
//            // 已验证清除key
//            redisUtil.deleteObject(captchaId);
//            // 验证成功 放行
//            chain.doFilter(request, response);
//            return;
//        }
//        // 无需验证 放行
//        chain.doFilter(request, response);
//    }
//}
