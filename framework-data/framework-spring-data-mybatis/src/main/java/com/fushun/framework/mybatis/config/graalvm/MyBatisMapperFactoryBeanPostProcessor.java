package com.fushun.framework.mybatis.config.graalvm;

import org.mybatis.spring.mapper.MapperFactoryBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.beans.factory.config.ConstructorArgumentValues;
import org.springframework.beans.factory.support.MergedBeanDefinitionPostProcessor;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.core.ResolvableType;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;

@Component
public class MyBatisMapperFactoryBeanPostProcessor implements MergedBeanDefinitionPostProcessor, BeanFactoryAware {

    private static final Logger logger = LoggerFactory.getLogger(MyBatisMapperFactoryBeanPostProcessor.class);

    private static final String MAPPER_FACTORY_BEAN = "org.mybatis.spring.mapper.MapperFactoryBean";

    private ConfigurableBeanFactory beanFactory;

    @Override
    public void setBeanFactory(BeanFactory beanFactory) {
        logger.info("MyBatisMapperFactoryBeanPostProcessor setBeanFactory");
        this.beanFactory = (ConfigurableBeanFactory) beanFactory;
    }

    @Override
    public void postProcessMergedBeanDefinition(RootBeanDefinition beanDefinition, Class<?> beanType, String beanName) {
//      logger.info("MyBatisMapperFactoryBeanPostProcessor postProcessMergedBeanDefinition beanType:{} beanName:{}",beanType,beanName);
        if (ClassUtils.isPresent(MAPPER_FACTORY_BEAN, this.beanFactory.getBeanClassLoader())) {
            resolveMapperFactoryBeanTypeIfNecessary(beanDefinition);
        }
    }

    private void resolveMapperFactoryBeanTypeIfNecessary(RootBeanDefinition beanDefinition) {
        if (!beanDefinition.hasBeanClass() || !MapperFactoryBean.class.isAssignableFrom(beanDefinition.getBeanClass())) {
            return;
        }
        if (beanDefinition.getResolvableType().hasUnresolvableGenerics()) {
            Class<?> mapperInterface = getMapperInterface(beanDefinition);
            if (mapperInterface != null) {
                // Exposes a generic type information to context for prevent early initializing
                ConstructorArgumentValues constructorArgumentValues = new ConstructorArgumentValues();
                constructorArgumentValues.addGenericArgumentValue(mapperInterface);
                beanDefinition.setConstructorArgumentValues(constructorArgumentValues);
                beanDefinition.setTargetType(ResolvableType.forClassWithGenerics(beanDefinition.getBeanClass(), mapperInterface));
            }
        }
    }

    private Class<?> getMapperInterface(RootBeanDefinition beanDefinition) {
        try {
            return (Class<?>) beanDefinition.getPropertyValues().get("mapperInterface");
        }
        catch (Exception e) {
            logger.debug("Fail getting mapper interface type.", e);
            return null;
        }
    }

}

