package com.fushun.framework.security.service;


import cn.hutool.core.util.ObjectUtil;
import com.fushun.framework.redis.utils.RedisUtil;
import com.fushun.framework.security.model.BaseLoginInfo;
import com.fushun.framework.security.utils.SecurityUtils;
import com.fushun.framework.util.ip.AddressUtils;
import com.fushun.framework.util.ip.IpUtils;
import com.fushun.framework.util.util.ServletUtils;
import com.fushun.framework.util.util.StringUtils;
import com.fushun.framework.util.util.UUIDUtil;
import eu.bitwalker.useragentutils.UserAgent;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * token验证处理
 *
 * @author ruoyi
 */
@Component
public class TokenService
{
    private Logger logger= LoggerFactory.getLogger(this.getClass());
    // 令牌自定义标识
    @Value("${token.header}")
    private String header;

    // 令牌秘钥
    @Value("${token.secret}")
    private String secret;

    // 令牌有效期（默认30分钟）
    @Value("${token.expireTime}")
    private int expireTime;


    /**
     * token前缀
     */
    public static final String LOGIN_USER_KEY = "login_user_key";

    /**
     * 令牌前缀
     */
    public static final String TOKEN_PREFIX = "Bearer ";

    /**
     * 登录用户 redis key
     */
    public static final String LOGIN_TOKEN_KEY = "login_tokens:";


    protected static final long MILLIS_SECOND = 1000;

    protected static final long MILLIS_MINUTE = 60 * MILLIS_SECOND;

    private static final Long MILLIS_MINUTE_TEN = 20 * 60 * 1000L;

    @Autowired
    private RedisUtil redisCache;

    /**
     * 获取用户身份信息
     *
     * @return 用户信息
     */
    public <T extends BaseLoginInfo> T getLoginUser()
    {
        try{
            // 获取请求携带的令牌
            String token = getToken();
            if (StringUtils.isNotEmpty(token))
            {
                //如果token直接存储的登录信息，则不需要解密了，目前是C端采用此方案
                String userKey = getTokenKey(token);
                T user = redisCache.getCacheObject(userKey);
                if(ObjectUtil.isNotNull(user)){
                    return user;
                }
            }
        }catch (Exception e){
            logger.error("获取登录用户错误",e);
        }

        return null;
    }

    public <T extends BaseLoginInfo> T getLoginUser(HttpServletRequest httpServletRequest)
    {
        return this.getLoginUser();
    }

    /**
     * 设置用户身份信息
     */
    public void setLoginUser(BaseLoginInfo loginUser)
    {
        if (StringUtils.isNotEmpty(loginUser) && StringUtils.isNotEmpty(loginUser.getToken()))
        {
            refreshToken(loginUser);
        }
    }

    /**
     * 删除用户身份信息
     */
    public void delLoginUser(String token)
    {
        if (StringUtils.isNotEmpty(token))
        {
            String userKey = getTokenKey(token);
            redisCache.deleteObject(userKey);
        }
    }

    /**
     * 创建令牌
     *
     * @param loginUser 用户信息
     * @return 令牌
     */
    public <T extends BaseLoginInfo> String createToken(T loginUser)
    {
        String token = UUIDUtil.getUUID().toString();
        loginUser.setToken(token);
        setUserAgent(loginUser);
        refreshToken(loginUser);

        return token;
    }

    /**
     * 验证令牌有效期，相差不足20分钟，自动刷新缓存
     *
     * @param loginUser
     * @return 令牌
     */
    public void verifyToken(BaseLoginInfo loginUser)
    {
        long expireTime = loginUser.getExpireTime();
        long currentTime = System.currentTimeMillis();
        if (expireTime - currentTime <= MILLIS_MINUTE_TEN)
        {
            refreshToken(loginUser);
        }
    }

    /**
     * 刷新令牌有效期
     *
     * @param loginUser 登录信息
     */
    public <T extends BaseLoginInfo>  void refreshToken(T loginUser)
    {
        loginUser.setLoginTime(System.currentTimeMillis());
        loginUser.setExpireTime(loginUser.getLoginTime() + expireTime * MILLIS_MINUTE);
        // 根据uuid将loginUser缓存
        String userKey = getTokenKey(loginUser.getToken());
        redisCache.setCacheObject(userKey, loginUser, expireTime, TimeUnit.MINUTES);
    }

    /**
     * 设置用户代理信息
     *
     * @param loginUser 登录信息
     */
    public <T extends BaseLoginInfo> void setUserAgent(T loginUser)
    {
        SecurityUtils.setUserAgent(loginUser);
    }

    /**
     * 获取请求token
     *
     * @return token
     */
    public String getToken()
    {
        String token = ServletUtils.getRequest().getHeader(header);
        if (StringUtils.isNotEmpty(token) && token.startsWith(TOKEN_PREFIX))
        {
            token = token.replace(TOKEN_PREFIX, "");
        }
        return token;
    }

    private String getTokenKey(String uuid)
    {
        return LOGIN_TOKEN_KEY + uuid;
    }

    public String getTokenKeyName(){
        return header;
    }
}
