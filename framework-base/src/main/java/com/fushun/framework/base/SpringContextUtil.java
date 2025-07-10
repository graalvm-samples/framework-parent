/**
 *
 */
package com.fushun.framework.base;

import cn.hutool.extra.spring.SpringUtil;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 * @author zhoup
 */
@Component
public class SpringContextUtil extends SpringUtil {

    public static ApplicationContext getContext() {
        return SpringContextUtil.getApplicationContext();
    }

    /**
     * 获取spring 启动对象
     * @return
     */
    public static ApplicationContext getAppContext(){
        return SpringContextUtil.getApplicationContext();
    }

}
