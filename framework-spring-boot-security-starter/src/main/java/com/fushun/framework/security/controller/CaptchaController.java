package com.fushun.framework.security.controller;

import com.fushun.framework.base.SpringContextUtil;
import com.fushun.framework.redis.utils.RedisUtil;
//import com.fushun.framework.security.filter.ImageValidateFilter;
import com.fushun.framework.util.util.UUIDUtil;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 验证码操作处理
 * 
 * @author ruoyi
 */
@RestController
public class CaptchaController
{
//    @Resource(name = "captchaProducer")
//    private Producer captchaProducer;
//
//    @Resource(name = "captchaProducerMath")
//    private Producer captchaProducerMath;

    private static RedisUtil redisUtil;

    static {
        redisUtil =  SpringContextUtil.getBean(RedisUtil.class);
    }
    
    // 验证码类型
    @Value("${ruoyi.captchaType:char}")
    private String captchaType;

    @Autowired(required = false)
    ISysConfigService configService;


    /**
     * 生成验证码
     */
//    @GetMapping("/captchaImage")
//    public Map<String,Object> getCode(HttpServletResponse response) throws IOException
//    {
//        Map<String,Object> ajax=new HashMap<>();
//        boolean captchaOnOff=false;
//        if(configService!=null){
//            captchaOnOff = configService.selectCaptchaOnOff();
//        }
//        ajax.put("captchaOnOff", captchaOnOff);
//        if (!captchaOnOff)
//        {
//            return ajax;
//        }
//        // 保存验证码信息
//        String uuid = UUIDUtil.getUUIDReplaceDelimiter();
//        String verifyKey = ImageValidateFilter.CAPTCHA_CODE_KEY + uuid;
//
//        String capStr = null, code = null;
//        BufferedImage image = null;
////
////        // 生成验证码
////        if ("math".equals(captchaType))
////        {
////            String capText = captchaProducerMath.createText();
////            capStr = capText.substring(0, capText.lastIndexOf("@"));
////            code = capText.substring(capText.lastIndexOf("@") + 1);
////            image = captchaProducerMath.createImage(capStr);
////        }
////        else if ("char".equals(captchaType))
////        {
////            capStr = code = captchaProducer.createText();
////            image = captchaProducer.createImage(capStr);
////        }
////
////        redisUtil.setCacheObject(verifyKey, code, ImageValidateFilter.CAPTCHA_EXPIRATION, TimeUnit.MINUTES);
////        // 转换流信息写出
////        FastByteArrayOutputStream os = new FastByteArrayOutputStream();
////        try
////        {
////            ImageIO.write(image, "jpg", os);
////        }
////        catch (IOException e)
////        {
////            throw new CaptchaException(CaptchaException.CaptchaExceptionEnum.GENERATE_ERROR);
////        }
//
//        ajax.put("uuid", uuid);
////        ajax.put("img", Base64.encode(os.toByteArray()));
//        return ajax;
//    }
}
