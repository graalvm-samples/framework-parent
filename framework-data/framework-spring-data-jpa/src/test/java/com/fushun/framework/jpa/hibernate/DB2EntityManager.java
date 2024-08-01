package com.fushun.framework.jpa.hibernate;

import com.fushun.framework.jpa.CustomerRepositoryImpl;
import com.zaxxer.hikari.HikariDataSource;
import org.hibernate.boot.model.naming.ImplicitNamingStrategy;
import org.hibernate.boot.model.naming.PhysicalNamingStrategy;
import org.springframework.beans.factory.ObjectProvider;
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
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.config.BootstrapMode;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Map;

@Configuration
@EnableTransactionManagement
@EnableAutoConfiguration
@EnableJpaRepositories(transactionManagerRef = "db2TransactionManager",
        entityManagerFactoryRef = "db2EntityManagerFactory",
        repositoryBaseClass = CustomerRepositoryImpl.class,
        bootstrapMode = BootstrapMode.DEFAULT,
        basePackages = "com.fushun.framework.jpa.hibernate")
public class DB2EntityManager extends BaseEntityManager{

    @Bean(name = "db2DataSource")
    @ConfigurationProperties(prefix = "pigcms.datasource")
    public DataSource db2DataSource() throws SQLException {
        HikariDataSource hikariDataSource= DataSourceBuilder.create().type(HikariDataSource.class).build();
//        连接只读数据库时配置为true， 保证安全
//        <property name="readOnly" value="false" />
//        连接池中允许的最大连接数。缺省值：10；推荐的公式：((core_count * 2) + effective_spindle_count)
//        <property name="maximumPoolSize" value="10" />

        //一个连接idle状态的最大时长（毫秒），超时则被释放（retired），缺省:10分钟
        hikariDataSource.setIdleTimeout(30000);
        // 等待连接池分配连接的最大时长（毫秒），超过这个时长还没可用的连接则发生SQLException， 缺省:30秒
        hikariDataSource.setConnectionTimeout(60000);
        //生效超时
        hikariDataSource.setValidationTimeout(3000);
        hikariDataSource.setLoginTimeout(5);
//        一个连接的生命时长（毫秒），超时而且没被使用则被释放（retired），缺省:30分钟，建议设置比数据库超时时长少30秒，参考MySQL wait_timeout参数（show variables like '%timeout%';）
        hikariDataSource.setMaxLifetime(30000);
        hikariDataSource.setMinimumIdle(10);
        return hikariDataSource;
    }

    @Bean(name = "db2EntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean db2EntityManagerFactory(
            ConfigurableListableBeanFactory beanFactory,
            ObjectProvider<SchemaManagementProvider> providers,
            ObjectProvider<PhysicalNamingStrategy> physicalNamingStrategy,
            ObjectProvider<ImplicitNamingStrategy> implicitNamingStrategy,
            HibernateProperties hibernateProperties,
            ObjectProvider<HibernatePropertiesCustomizer> hibernatePropertiesCustomizers,
            EntityManagerFactoryBuilder builder, @Qualifier("db2DataSource")DataSource dataSource, JpaProperties jpaProperties) {
        Map<String,Object> properties= getVendorProperties(physicalNamingStrategy,implicitNamingStrategy,beanFactory,hibernateProperties,hibernatePropertiesCustomizers,providers,dataSource,jpaProperties);
        properties.put("hibernate.hbm2ddl.auto","none");
        return builder
                .dataSource(dataSource)
                .properties(properties)
                .packages("com.fushun.dto")
                .persistenceUnit("pigcms")
                .build();
    }

    @Bean(name = "db2EntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean db2EntityManagerFactory(EntityManagerFactoryBuilder builder,@Qualifier("db2DataSource")DataSource dataSource) {
        return builder
                .dataSource(dataSource)
                .persistenceUnit("pigcms")
                .build();
    }

    @Bean(name = "db2TransactionManager")
    public JpaTransactionManager db2TransactionManager(@Qualifier("db2EntityManagerFactory") final LocalContainerEntityManagerFactoryBean emf) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(emf.getObject());
        return transactionManager;
    }
}
