package com.fushun.framework.dynamic.datasource;//package com.fushun.framework.dynamic.datasource;
//
//import com.alibaba.druid.filter.Filter;
//import com.alibaba.druid.spring.boot3.autoconfigure.DruidDataSourceBuilder;
//import com.alibaba.druid.spring.boot3.autoconfigure.DruidDataSourceWrapper;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Primary;
//import org.springframework.jdbc.datasource.DataSourceTransactionManager;
//import org.springframework.transaction.PlatformTransactionManager;
//
//import javax.sql.DataSource;
//import java.util.Collections;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//@Configuration(proxyBeanMethods=false)
//@Slf4j
//public class DataSourceConfig {
//
//
//    @Bean(name = "firstDataSourceProperties")
//    @ConfigurationProperties("spring.datasource.druid.first")
//    public DataSourceProperties firstDataSourceProperties(){
//        return new DataSourceProperties();
//    }
//
//    @Bean(name = "firstDataSource")
//    public DataSource firstDataSource(@Qualifier("firstDataSourceProperties") DataSourceProperties basicProperties, List<Filter> filters) {
//        log.info("firstDataSource");
//        DruidDataSourceWrapper druidDataSourceWrapper = new DruidDataSourceWrapper();
//        //增加手动设置
//        druidDataSourceWrapper.setBasicProperties(basicProperties);
//        List<Filter> filterList=druidDataSourceWrapper.getProxyFilters();
//        for(Filter filter:filters){
//            boolean bool= false;
//            for (Filter filter1:filterList){
//                if(filter1.getClass()==filter.getClass()){
//                    bool=true;
//                    break;
//                }
//            }
//            if(!bool){
//                druidDataSourceWrapper.autoAddFilters(Collections.singletonList(filter));
//            }
//        }
//        return druidDataSourceWrapper;
//    }
//
//    @Bean(name = "secondDataSourceProperties")
//    @ConfigurationProperties("spring.datasource.druid.second")
//    public DataSourceProperties secondDataSourceProperties(){
//        return new DataSourceProperties();
//    }
//
//    @Bean(name = "secondDataSource")
//    public DataSource secondDataSource(@Qualifier("secondDataSourceProperties") DataSourceProperties basicProperties, List<Filter> filters) {
//        log.info("secondDataSource");
//        DruidDataSourceWrapper druidDataSourceWrapper = new DruidDataSourceWrapper();
//        //增加手动设置
//        druidDataSourceWrapper.setBasicProperties(basicProperties);
//        List<Filter> filterList=druidDataSourceWrapper.getProxyFilters();
//        for(Filter filter:filters){
//            boolean bool= false;
//            for (Filter filter1:filterList){
//                if(filter1.getClass()==filter.getClass()){
//                    bool=true;
//                    break;
//                }
//            }
//            if(!bool){
//                druidDataSourceWrapper.autoAddFilters(Collections.singletonList(filter));
//            }
//        }
//        return druidDataSourceWrapper;
//    }
//
//    @Primary
//    @Bean
//    public DataSource dynamicDataSource(@Qualifier("firstDataSource") DataSource firstDataSource,
//                                        @Qualifier("secondDataSource") DataSource secondDataSource) {
//        log.info("dynamicDataSource");
//        DynamicDataSource dynamicDataSource = new DynamicDataSource();
//        Map<Object, Object> dataSourceMap = new HashMap<>();
//        dataSourceMap.put("first", firstDataSource);
//        dataSourceMap.put("second", secondDataSource);
//        dynamicDataSource.setTargetDataSources(dataSourceMap);
//        dynamicDataSource.setDefaultTargetDataSource(firstDataSource);
//        return dynamicDataSource;
//    }
//
//    @Bean
//    public PlatformTransactionManager transactionManager(DataSource dynamicDataSource) {
//        return new DataSourceTransactionManager(dynamicDataSource);
//    }
//}
