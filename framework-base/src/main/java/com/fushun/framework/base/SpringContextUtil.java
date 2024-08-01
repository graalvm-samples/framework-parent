/**
 *
 */
package com.fushun.framework.base;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

/**
 * @author zhoup
 */
@Component
public class SpringContextUtil implements ApplicationContextAware, Ordered {

    private static final Logger logger = LoggerFactory.getLogger(SpringContextUtil.class);

    private static ApplicationContext APP_CONTEXT;

    public static ApplicationContext getContext() {
        return APP_CONTEXT;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringContextUtil.APP_CONTEXT = applicationContext;
    }

    /**
     * bean对象class获取bean实例对象
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T getBean(Class<T> clazz) {
        if (APP_CONTEXT == null) {
            logger.warn("get bean fair reason ApplicationContext is null {} class ",clazz.getName());
            return null;
        }
        try{
            return APP_CONTEXT.getBean(clazz);
        }catch (Exception e){
            logger.error("get bean fair class {}",clazz.getName(),e);
        }
        return null;
    }

    /**
     * bean名称字符串获取bean
     * @param beanName
     * @return
     */
    public static Object getBean(String beanName) {
        if (APP_CONTEXT == null) {
            logger.warn("get bean fair reason ApplicationContext is null {} class ",beanName);
            return null;
        }
        if (APP_CONTEXT.containsBean(beanName)) {
            return APP_CONTEXT.getBean(beanName);
        } else {
            logger.error("get bean fair not containsBean class {}",beanName);
            return null;
        }
    }

    /**
     * 获取spring 启动对象
     * @return
     */
    public static ApplicationContext getAppContext(){
        return APP_CONTEXT;
    }

    @Override
    public int getOrder() {
        return 1;
    }
}
