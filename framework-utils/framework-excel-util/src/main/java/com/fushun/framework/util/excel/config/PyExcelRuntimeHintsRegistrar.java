package com.fushun.framework.util.excel.config;

import org.apache.logging.log4j.message.DefaultFlowMessageFactory;
import org.apache.logging.log4j.message.ParameterizedMessageFactory;
import org.apache.poi.schemas.ooxml.system.ooxml.TypeSystemHolder;
import org.apache.xmlbeans.impl.schema.DocumentFactory;
import org.apache.xmlbeans.impl.schema.SchemaTypeImpl;
import org.apache.xmlbeans.impl.store.Cur;
import org.apache.xmlbeans.impl.values.XmlComplexContentImpl;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.CTWorkbook;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.impl.CTWorkbookImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aot.hint.MemberCategory;
import org.springframework.aot.hint.RuntimeHints;
import org.springframework.aot.hint.RuntimeHintsRegistrar;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;

import java.io.IOException;
import java.util.*;
import java.util.stream.Stream;

public class PyExcelRuntimeHintsRegistrar implements RuntimeHintsRegistrar {

    private static Logger logger= LoggerFactory.getLogger(PyExcelRuntimeHintsRegistrar.class);

    private static final List<String> BASE_PACKAGES = Arrays.asList(
            "org.apache.poi",
            "org.apache.xmlbeans",
            "org.openxmlformats.schemas",
            "com.alibaba.excel");


    @Override
    public void registerHints(RuntimeHints hints, ClassLoader classLoader) {
        //Caused by: org.apache.xmlbeans.SchemaTypeLoaderException: XML-BEANS compiled schema: Could not locate compiled schema resource org/apache/poi/schemas/ooxml/system/ooxml/index.xsb (org.apache.poi.schemas.ooxml.system.ooxml.index) - code 0
        hints.resources().registerPattern("*.xsb");

        Stream.of(
                // apache log4j 错误
                ParameterizedMessageFactory.class,
                DefaultFlowMessageFactory.class,
                org.apache.logging.log4j.spi.Provider.class
        ).forEach(x -> hints.reflection().registerType(x, MemberCategory.values()));


        try {
            Set<Class<?>> classesToRegister = scanPackage();
            for (Class<?> clazz : classesToRegister) {
                try{
                    hints.reflection().registerType(clazz, MemberCategory.values());
                }catch (Throwable err){
                    logger.error("registerType clazz:{},{}",clazz,err.getMessage());
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException("Failed to register runtime hints", e);
        }
    }


    public static Set<Class<?>> scanPackage() throws IOException, ClassNotFoundException {
        Set<Class<?>> classes = new HashSet<>();
        for (String pkg : BASE_PACKAGES) {
            MetadataReaderFactory readerFactory = new CachingMetadataReaderFactory(new PathMatchingResourcePatternResolver());
            String packageSearchPath = "classpath*:" + pkg.replace('.', '/') + "/**/*.class";

            for (org.springframework.core.io.Resource resource : new PathMatchingResourcePatternResolver().getResources(packageSearchPath)) {
                if (resource.isReadable()) {
                    MetadataReader reader = readerFactory.getMetadataReader(resource);
                    String className = reader.getClassMetadata().getClassName();
//                    logger.info("scanning:{} " ,className);
                    try{
                        classes.add(Class.forName(className));
                    }catch (Throwable e) {
                        logger.error("scanPackage className:{} {}",className, e.getMessage());
                    }

                }
            }
        }

        return classes;
    }
}