package com.fushun.framework.bean.properties.utils.spring;

import com.fushun.framework.bean.properties.config.IBeanCopyPropertiesBean;
import org.springframework.beans.BeansException;

public class BeanUtils {

    public static void copyProperties(IBeanCopyPropertiesBean source, IBeanCopyPropertiesBean target) throws BeansException {
        org.springframework.beans.BeanUtils.copyProperties(source,target);
    }

    public static void copyProperties(IBeanCopyPropertiesBean source, IBeanCopyPropertiesBean target, Class<?> editable) throws BeansException {
        org.springframework.beans.BeanUtils.copyProperties(source, target, editable);
    }

    public static void copyProperties(IBeanCopyPropertiesBean source, IBeanCopyPropertiesBean target, String... ignoreProperties){
        org.springframework.beans.BeanUtils.copyProperties(source, target, ignoreProperties);
    }
}
