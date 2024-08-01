package com.fushun.framework.mybatis.config.graalvm;

import com.baomidou.mybatisplus.annotation.IEnum;
import com.baomidou.mybatisplus.core.MybatisParameterHandler;
import com.baomidou.mybatisplus.core.MybatisXMLLanguageDriver;
import com.baomidou.mybatisplus.core.conditions.AbstractWrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.handlers.CompositeEnumTypeHandler;
import com.baomidou.mybatisplus.core.handlers.MybatisEnumTypeHandler;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.core.toolkit.support.SerializedLambda;
import com.baomidou.mybatisplus.extension.handlers.FastjsonTypeHandler;
import com.baomidou.mybatisplus.extension.handlers.GsonTypeHandler;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import org.apache.ibatis.cache.decorators.FifoCache;
import org.apache.ibatis.cache.decorators.LruCache;
import org.apache.ibatis.cache.decorators.SoftCache;
import org.apache.ibatis.cache.decorators.WeakCache;
import org.apache.ibatis.cache.impl.PerpetualCache;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.executor.resultset.ResultSetHandler;
import org.apache.ibatis.executor.statement.BaseStatementHandler;
import org.apache.ibatis.executor.statement.RoutingStatementHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.javassist.util.proxy.ProxyFactory;
import org.apache.ibatis.javassist.util.proxy.RuntimeSupport;
import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.commons.JakartaCommonsLoggingImpl;
import org.apache.ibatis.logging.jdk14.Jdk14LoggingImpl;
import org.apache.ibatis.logging.log4j2.Log4j2Impl;
import org.apache.ibatis.logging.nologging.NoLoggingImpl;
import org.apache.ibatis.logging.slf4j.Slf4jImpl;
import org.apache.ibatis.logging.stdout.StdOutImpl;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.scripting.defaults.RawLanguageDriver;
import org.apache.ibatis.scripting.xmltags.XMLLanguageDriver;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.EnumTypeHandler;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aot.hint.MemberCategory;
import org.springframework.aot.hint.RuntimeHints;
import org.springframework.aot.hint.RuntimeHintsRegistrar;
import org.springframework.aot.hint.TypeReference;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import java.io.IOException;
import java.util.*;
import java.util.stream.Stream;

public class MyBaitsRuntimeHintsRegistrar implements RuntimeHintsRegistrar {

    private Logger logger= LoggerFactory.getLogger(MyBaitsRuntimeHintsRegistrar.class);

    public Resource[] resolveMapperLocations(String[] mapperLocations, ResourcePatternResolver resourceResolver) {
        return Stream.of(Optional.ofNullable(mapperLocations).orElse(new String[0]))
                .flatMap(location -> Stream.of(getResources(location, resourceResolver))).toArray(Resource[]::new);
    }

    private Resource[] getResources(String location, ResourcePatternResolver resourceResolver) {
        try {
            return resourceResolver.getResources(location);
        } catch (IOException e) {
            return new Resource[0];
        }
    }

    @Override
    public void registerHints(RuntimeHints hints, ClassLoader classLoader) {
        logger.info("MyBaitsRuntimeHintsRegistrar registerHints");
        // xml加载
        String[] mapperLocations = new String[]{
                "classpath:/fund/mapper/**/*Mapper.xml",
                "classpath:/community/mapper/**/*Mapper.xml",
                "classpath:/adminweb/mapper/**/*Mapper.xml"
        };
        ResourcePatternResolver resourceResolver = new PathMatchingResourcePatternResolver();
        Resource[] resources= resolveMapperLocations(mapperLocations,resourceResolver);
        for (Resource resource:resources){
            if(resource instanceof ClassPathResource){
                hints.resources().registerPattern(((ClassPathResource)resource).getPath());
                System.out.println("registerHints "+((ClassPathResource)resource).getPath());
            }else{
                System.out.println("registerHints resource "+resource);
                System.out.println("registerHints resource.getClass() "+resource.getClass());
                System.out.println("registerHints resource.getDescription() "+resource.getDescription());
                System.out.println("registerHints resource.getFilename()"+resource.getFilename());
                System.out.println("=================");
            }
        }
        /**
         *
         *
         */

        hints.reflection().registerType(TypeReference.of("org.springframework.core.annotation.TypeMappedAnnotation[]"),
                MemberCategory.INVOKE_DECLARED_CONSTRUCTORS);

        Stream.of(RawLanguageDriver.class,
                // TODO 增加了MybatisXMLLanguageDriver.class
                XMLLanguageDriver.class, MybatisXMLLanguageDriver.class,
                RuntimeSupport.class,
                ProxyFactory.class,
                Slf4jImpl.class,
                Log.class,
                JakartaCommonsLoggingImpl.class,
                Log4j2Impl.class,
                Jdk14LoggingImpl.class,
                StdOutImpl.class,
                NoLoggingImpl.class,
                SqlSessionFactory.class,
                PerpetualCache.class,
                FifoCache.class,
                LruCache.class,
                SoftCache.class,
                WeakCache.class,
                //TODO 增加了MybatisSqlSessionFactoryBean.class
                SqlSessionFactoryBean.class, MybatisSqlSessionFactoryBean.class,
                ArrayList.class,
                LinkedHashMap.class,
                HashMap.class,
                TreeSet.class,
                HashSet.class,
                EnumTypeHandler.class
        ).forEach(x -> hints.reflection().registerType(x, MemberCategory.values()));
        Stream.of(
                "org/apache/ibatis/builder/xml/*.dtd",
                "org/apache/ibatis/builder/xml/*.xsd"
        ).forEach(hints.resources()::registerPattern);

        hints.serialization().registerType(SerializedLambda.class);
        hints.serialization().registerType(SFunction.class);
        hints.serialization().registerType(java.lang.invoke.SerializedLambda.class);
        hints.reflection().registerType(SFunction.class);
        hints.reflection().registerType(SerializedLambda.class);
        hints.reflection().registerType(java.lang.invoke.SerializedLambda.class);

        hints.proxies().registerJdkProxy(StatementHandler.class);
        hints.proxies().registerJdkProxy(Executor.class);
        hints.proxies().registerJdkProxy(ResultSetHandler.class);
        hints.proxies().registerJdkProxy(ParameterHandler.class);

//        hints.reflection().registerType(MybatisPlusInterceptor.class);
        hints.reflection().registerType(AbstractWrapper.class,MemberCategory.values());
        hints.reflection().registerType(LambdaQueryWrapper.class,MemberCategory.values());
        hints.reflection().registerType(LambdaUpdateWrapper.class,MemberCategory.values());
        hints.reflection().registerType(UpdateWrapper.class,MemberCategory.values());
        hints.reflection().registerType(QueryWrapper.class,MemberCategory.values());

        hints.reflection().registerType(BoundSql.class,MemberCategory.DECLARED_FIELDS);
        hints.reflection().registerType(RoutingStatementHandler.class,MemberCategory.DECLARED_FIELDS);
        hints.reflection().registerType(BaseStatementHandler.class,MemberCategory.DECLARED_FIELDS);
        hints.reflection().registerType(MybatisParameterHandler.class,MemberCategory.DECLARED_FIELDS);


        hints.reflection().registerType(IEnum.class,MemberCategory.INVOKE_PUBLIC_METHODS);
        // register typeHandler
        hints.reflection().registerType(CompositeEnumTypeHandler.class, MemberCategory.INVOKE_PUBLIC_CONSTRUCTORS);
        hints.reflection().registerType(FastjsonTypeHandler.class, MemberCategory.INVOKE_PUBLIC_CONSTRUCTORS);
        hints.reflection().registerType(GsonTypeHandler.class, MemberCategory.INVOKE_PUBLIC_CONSTRUCTORS);
        hints.reflection().registerType(JacksonTypeHandler.class, MemberCategory.INVOKE_PUBLIC_CONSTRUCTORS);
        hints.reflection().registerType(MybatisEnumTypeHandler.class, MemberCategory.INVOKE_PUBLIC_CONSTRUCTORS);

        System.out.println("MyBatisNativeConfiguration end");
    }
}
