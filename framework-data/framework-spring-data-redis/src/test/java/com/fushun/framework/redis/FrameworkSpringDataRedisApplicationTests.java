package com.fushun.framework.redis;

import com.fushun.framework.redis.utils.RedisUtil;
import com.fushun.framework.util.util.JsonUtil;
import com.hsh.platform.security.admin.model.AdminLoginInfo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.concurrent.TimeUnit;

@SpringBootTest(
        classes = FrameworkMybitisApplication.class
)
class FrameworkSpringDataRedisApplicationTests {

    @Autowired
    private RedisUtil redisUtil;

//    @Autowired
//    private RedisTemplate<String, AdminLoginInfo> redisTemplate;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Test
    public void test(){
        redisUtil.setString("1","1");
        redisTemplate.opsForValue().setIfAbsent("SUBMIT_KEY:4acbc2eb7db741ff80d259dcd95455f1", "submitKey", 5, TimeUnit.MINUTES);
        redisUtil.setStringIfAbsent("SUBMIT_KEY:4acbc2eb7db741ff80d259dcd95455f1",
                "submitKey", 5, TimeUnit.MINUTES);
        System.out.println(redisUtil.getString("SUBMIT_KEY:4acbc2eb7db741ff80d259dcd95455f1"));

        redisUtil.setIngeter("2",2);
        System.out.println(redisUtil.getIngeter("2"));

        redisUtil.setLong("3",3L);
        System.out.println(redisUtil.getLong("3"));

        redisUtil.setCacheObject("1111",new AdminLoginInfo());
        AdminLoginInfo baseLoginInfo=redisUtil.getCacheObject("1111");
        System.out.println(JsonUtil.toJson(baseLoginInfo));
        System.out.println("");
    }

}
