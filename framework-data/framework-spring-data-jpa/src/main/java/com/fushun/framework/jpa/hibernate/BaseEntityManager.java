package com.fushun.framework.jpa.hibernate;

import org.hibernate.boot.model.naming.ImplicitNamingStrategy;
import org.hibernate.boot.model.naming.PhysicalNamingStrategy;
import org.hibernate.cfg.AvailableSettings;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.autoconfigure.orm.jpa.*;
import org.springframework.boot.jdbc.SchemaManagementProvider;
import org.springframework.orm.hibernate5.SpringBeanContainer;
import org.springframework.util.ClassUtils;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * 多数据源 的配置 {@link HibernateJpaAutoConfiguration}
 */
public abstract class BaseEntityManager {


    private List<HibernatePropertiesCustomizer> determineHibernatePropertiesCustomizers(
            PhysicalNamingStrategy physicalNamingStrategy,
            ImplicitNamingStrategy implicitNamingStrategy,
            ConfigurableListableBeanFactory beanFactory,
            List<HibernatePropertiesCustomizer> hibernatePropertiesCustomizers) {
        List<HibernatePropertiesCustomizer> customizers = new ArrayList<>();
        if (ClassUtils.isPresent(
                "org.hibernate.resource.beans.container.spi.BeanContainer",
                getClass().getClassLoader())) {
            customizers
                    .add((properties) -> properties.put(AvailableSettings.BEAN_CONTAINER,
                            new SpringBeanContainer(beanFactory)));
        }
        if (physicalNamingStrategy != null || implicitNamingStrategy != null) {
            customizers.add(new NamingStrategiesHibernatePropertiesCustomizer(
                    physicalNamingStrategy, implicitNamingStrategy));
        }
        customizers.addAll(hibernatePropertiesCustomizers);
        return customizers;
    }

    private static class NamingStrategiesHibernatePropertiesCustomizer
            implements HibernatePropertiesCustomizer {

        private final PhysicalNamingStrategy physicalNamingStrategy;

        private final ImplicitNamingStrategy implicitNamingStrategy;

        NamingStrategiesHibernatePropertiesCustomizer(
                PhysicalNamingStrategy physicalNamingStrategy,
                ImplicitNamingStrategy implicitNamingStrategy) {
            this.physicalNamingStrategy = physicalNamingStrategy;
            this.implicitNamingStrategy = implicitNamingStrategy;
        }

        @Override
        public void customize(Map<String, Object> hibernateProperties) {
            if (this.physicalNamingStrategy != null) {
                hibernateProperties.put("hibernate.physical_naming_strategy",
                        this.physicalNamingStrategy);
            }
            if (this.implicitNamingStrategy != null) {
                hibernateProperties.put("hibernate.implicit_naming_strategy",
                        this.implicitNamingStrategy);
            }
        }

    }


    protected Map<String, Object> getVendorProperties(ObjectProvider<PhysicalNamingStrategy> physicalNamingStrategy,
                                                      ObjectProvider<ImplicitNamingStrategy> implicitNamingStrategy,
                                                      ConfigurableListableBeanFactory beanFactory,
                                                      HibernateProperties hibernateProperties,
                                                      ObjectProvider<HibernatePropertiesCustomizer> hibernatePropertiesCustomizers,
                                                      ObjectProvider<SchemaManagementProvider> providers, DataSource dataSource, JpaProperties jpaProperties) {
        HibernateDefaultDdlAutoProvider defaultDdlAutoProvider = new HibernateDefaultDdlAutoProvider(providers);
        Supplier<String> defaultDdlMode = () -> defaultDdlAutoProvider
                .getDefaultDdlAuto(dataSource);


        List<HibernatePropertiesCustomizer> hibernatePropertiesCustomizers2 = determineHibernatePropertiesCustomizers(
                physicalNamingStrategy.getIfAvailable(),
                implicitNamingStrategy.getIfAvailable(), beanFactory,
                hibernatePropertiesCustomizers.orderedStream()
                        .collect(Collectors.toList()));

        return new LinkedHashMap<>(hibernateProperties.determineHibernateProperties(
                jpaProperties.getProperties(),
                new HibernateSettings().ddlAuto(defaultDdlMode)
                        .hibernatePropertiesCustomizers(
                                hibernatePropertiesCustomizers2)));
    }
}
