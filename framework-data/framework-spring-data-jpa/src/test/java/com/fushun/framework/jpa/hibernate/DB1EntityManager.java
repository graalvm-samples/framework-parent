package com.fushun.framework.jpa.hibernate;

import com.fushun.framework.jpa.CustomerRepositoryImpl;
import com.zaxxer.hikari.HikariDataSource;
import org.hibernate.boot.model.naming.ImplicitNamingStrategy;
import org.hibernate.boot.model.naming.PhysicalNamingStrategy;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateProperties;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.jdbc.SchemaManagementProvider;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.config.BootstrapMode;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Map;

/**
 * 配置示例  多数据源
 */
@Configuration
@EnableTransactionManagement
@EnableAutoConfiguration

@EnableJpaRepositories(
        transactionManagerRef = "db1TransactionManager",
        entityManagerFactoryRef = "db1EntityManagerFactory",
        repositoryBaseClass = CustomerRepositoryImpl.class,
        bootstrapMode = BootstrapMode.DEFAULT,
        basePackages = "com.fushun.framework.jpa.hibernate"
)
public class DB1EntityManager extends BaseEntityManager{

    @Autowired
    private HibernateProperties hibernateProperties;
    @Autowired
    private JpaProperties jpaProperties;


    @Bean(name = "db1DataSource")
    @Primary
    @ConfigurationProperties(prefix = "app.datasource")
    public DataSource db1DataSource() {
        return DataSourceBuilder.create().type(HikariDataSource.class).build();
    }

    @Bean(name = "db1EntityManagerFactory")
    @Primary
    public LocalContainerEntityManagerFactoryBean db1EntityManagerFactory(
            ConfigurableListableBeanFactory beanFactory,
            ObjectProvider<SchemaManagementProvider> providers,
            ObjectProvider<PhysicalNamingStrategy> physicalNamingStrategy,
            ObjectProvider<ImplicitNamingStrategy> implicitNamingStrategy,
            HibernateProperties hibernateProperties,
            ObjectProvider<HibernatePropertiesCustomizer> hibernatePropertiesCustomizers,
            EntityManagerFactoryBuilder builder, @Qualifier("db1DataSource")DataSource dataSource,JpaProperties jpaProperties) {
        Map<String,Object> properties= getVendorProperties(physicalNamingStrategy,implicitNamingStrategy,beanFactory,hibernateProperties,hibernatePropertiesCustomizers,providers,dataSource,jpaProperties);
//        org.springframework.boot.orm.jpa.hibernate.SpringPhysicalNamingStrategy
        return builder
                .dataSource(dataSource)
                .properties(properties)
                .packages("com.aiworkhelper.supermarket.bld.dto",
                        "com.aiworkhelper.supermarket.sixun.dto",
                        "com.fushun.pay.infrastructure.*.tunnel.database.dataobject",
                        "com.fushun.framework.base")
                .persistenceUnit("app")
                .build();
    }

    @Bean(name = "db1TransactionManager")
    @Primary
    public JpaTransactionManager db1TransactionManager(@Qualifier("db1EntityManagerFactory") final LocalContainerEntityManagerFactoryBean emf) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(emf.getObject());
        return transactionManager;
    }
}