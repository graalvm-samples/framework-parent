/*
 * Copyright 1999-2018 Alibaba Group Holding Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.alibaba.druid.spring.boot3.autoconfigure;

import com.alibaba.druid.filter.Filter;
import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.spring.boot3.autoconfigure.properties.DruidStatProperties;
import com.alibaba.druid.spring.boot3.autoconfigure.stat.DruidFilterConfiguration;
import com.alibaba.druid.spring.boot3.autoconfigure.stat.DruidSpringAopConfiguration;
import com.alibaba.druid.spring.boot3.autoconfigure.stat.DruidStatViewServletConfiguration;
import com.alibaba.druid.spring.boot3.autoconfigure.stat.DruidWebStatFilterConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author lihengming [89921218@qq.com]
 */
@Configuration
@ConditionalOnProperty(name = "spring.datasource.type",
        havingValue = "com.alibaba.druid.pool.DruidDataSource",
        matchIfMissing = true)
@ConditionalOnClass(DruidDataSource.class)
@AutoConfigureBefore(DataSourceAutoConfiguration.class)
@EnableConfigurationProperties({DruidStatProperties.class, DataSourceProperties.class})
@Import({DruidSpringAopConfiguration.class,
        DruidStatViewServletConfiguration.class,
        DruidWebStatFilterConfiguration.class,
        DruidFilterConfiguration.class})
public class DruidDataSourceAutoConfigure {
    private static final Logger LOGGER = LoggerFactory.getLogger(DruidDataSourceAutoConfigure.class);

    /**
     * Not setting initMethod of annotation {@code @Bean} is to avoid failure when inspecting
     * the bean definition at the build time. The {@link DruidDataSource#init()} will be called
     * at the end of {@link DruidDataSourceWrapper#afterPropertiesSet()}.
     *
     * @return druid data source wrapper
     */
    @Bean
    @ConditionalOnMissingBean
    @ConfigurationProperties("spring.datasource.druid")
    //方法入参把DataSourceProperties带上, 让spring能自动识别注入
    public DataSource dataSource(DataSourceProperties basicProperties, List<Filter> filters) {
        LOGGER.info("Init DruidDataSource");
        DruidDataSourceWrapper druidDataSourceWrapper = new DruidDataSourceWrapper();
        //增加手动设置
        druidDataSourceWrapper.setBasicProperties(basicProperties);
        List<Filter> filterList=druidDataSourceWrapper.getProxyFilters();
        for(Filter filter:filters){
            boolean bool= false;
            for (Filter filter1:filterList){
                if(filter1.getClass()==filter.getClass()){
                    bool=true;
                    break;
                }
            }
            if(!bool){
                druidDataSourceWrapper.autoAddFilters(Collections.singletonList(filter));
            }
        }
        return druidDataSourceWrapper;
    }
}
