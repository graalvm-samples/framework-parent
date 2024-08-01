# 1、Caused by: java.nio.charset.UnsupportedCharsetException: CP1252
```yaml
<BP_NATIVE_IMAGE_BUILD_ARGUMENTS>
-H:+AddAllCharsets
</BP_NATIVE_IMAGE_BUILD_ARGUMENTS>
```

# 2、没有日志org.apache.logging.log4j.spi.Provider
```yaml
<BP_NATIVE_IMAGE_BUILD_ARGUMENTS>
<!-- log4j -->
--initialize-at-build-time=org.apache.logging.log4j.spi.Provider
--initialize-at-build-time=org.apache.logging.log4j.core.impl.Log4jProvider
--initialize-at-build-time=org.apache.logging.log4j.spi.LoggerRegistry$ConcurrentMapFactory
--initialize-at-build-time=org.apache.logging.log4j.util.EnvironmentPropertySource
--initialize-at-build-time=org.apache.logging.log4j.util.PropertyFilePropertySource
--initialize-at-build-time=org.apache.logging.log4j.util.PropertySource$Comparator
--initialize-at-build-time=org.apache.logging.log4j.util.SystemPropertiesPropertySource
--initialize-at-build-time=org.apache.logging.slf4j.SLF4JProvider
--initialize-at-build-time=org.springframework.boot.logging.log4j2.SpringBootPropertySource
--initialize-at-build-time=org.apache.logging.log4j.message.ParameterizedNoReferenceMessageFactory
--initialize-at-build-time=org.apache.logging.log4j.status.StatusLogger$BoundedQueue
--initialize-at-build-time=org.apache.logging.log4j.util.LoaderUtil$ThreadContextClassLoaderGetter
--initialize-at-build-time=org.apache.logging.log4j.util.PropertiesUtil$Environment
--initialize-at-build-time=org.apache.logging.slf4j.SLF4JLoggerContext
--initialize-at-build-time=org.apache.logging.log4j.util.ProviderUtil
--initialize-at-build-time=org.apache.logging.log4j.core.util.Loader
--initialize-at-build-time=org.apache.logging.log4j.spi.LoggerRegistry
--initialize-at-build-time=org.apache.logging.log4j.core.impl.Log4jContextFactory
--initialize-at-build-time=org.apache.logging.slf4j.SLF4JLoggerContextFactory
--initialize-at-build-time=org.apache.logging.log4j.LogManager
--initialize-at-build-time=org.apache.logging.slf4j.Log4jLogger
--initialize-at-build-time=org.apache.logging.log4j.spi.StandardLevel
--initialize-at-build-time=org.apache.logging.log4j.simple.SimpleLogger
--initialize-at-build-time=org.apache.logging.log4j.util.Strings
--initialize-at-build-time=org.apache.logging.log4j.util.Constants
--initialize-at-build-time=org.apache.logging.log4j.util.PropertySource$Util
--initialize-at-build-time=org.apache.logging.log4j.status.StatusLogger
--initialize-at-build-time=org.apache.logging.log4j.spi.AbstractLogger
--initialize-at-build-time=org.apache.logging.log4j.util.OsgiServiceLocator
--initialize-at-build-time=org.apache.logging.log4j.util.LoaderUtil
--initialize-at-build-time=org.apache.logging.log4j.util.PropertiesUtil
--initialize-at-build-time=org.apache.logging.log4j.Level
<!-- log4j -->
</BP_NATIVE_IMAGE_BUILD_ARGUMENTS>
```

# 3、获取需要initialize-at-build-time的类
```yaml
<BP_NATIVE_IMAGE_BUILD_ARGUMENTS>
--features=com.fushun.framework.sample.excel.natives.demo.config.LambdaRegistrationFeature
</BP_NATIVE_IMAGE_BUILD_ARGUMENTS>
```

LambdaRegistrationFeature
```java
package com.fushun.framework.sample.excel.natives.demo.config;


import cn.hutool.core.util.ObjectUtil;
import com.fushun.framework.util.util.JsonUtil;
import org.graalvm.nativeimage.hosted.Feature;
import org.graalvm.nativeimage.hosted.RuntimeSerialization;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.Set;

/**
 * lambda 表达式注入到graal中
 * @author ztp
 * @date 2023/8/18 11:53
 */
public class LambdaRegistrationFeature implements Feature {

    Logger log= LoggerFactory.getLogger(this.getClass());


    private Class<?> getTableNameByAnntation(BeanDefinition definition) {
        String beanClassName = definition.getBeanClassName();
        log.info("beanClassName:{}",beanClassName);
        try {
            Class<?> aClass = Class.forName(beanClassName);
            Service annotation = aClass.getAnnotation(Service.class);
            if (ObjectUtil.isNotNull(annotation)) {
                return aClass;
            }
            RestController annotation2 = aClass.getAnnotation(RestController.class);
            if (ObjectUtil.isNotNull(annotation2)) {
                return aClass;
            }
            Controller annotation3 = aClass.getAnnotation(Controller.class);
            if (ObjectUtil.isNotNull(annotation3)) {
                return aClass;
            }
            //获取到注解name的值并返回
            return null;
        } catch (ClassNotFoundException e) {
            log.error("error",e);
        }
        return null;
    }

    @Override
    public void duringSetup(DuringSetupAccess access) {
        // TODO 这里需要将lambda表达式所使用的成员类都注册上来,具体情况视项目情况而定,一般扫描@Controller和@Service的会多点.
        Set<Class<?>> classes= new HashSet<>();
//        "com.community.service.community.impl.CommunityPropertyInformationServiceImpl"
//        RuntimeSerialization.registerLambdaCapturingClass(CommunityPropertyInformationServiceImpl.class);
        log.info("LambdaRegistrationFeature");
        // 不使用默认的TypeFilter
        ClassPathScanningCandidateComponentProvider provider =
                new ClassPathScanningCandidateComponentProvider(false);
        // 添加扫描规律规则，这里指定了内置的注解过滤规则
        provider.addIncludeFilter(new AnnotationTypeFilter(Service.class));
        provider.addIncludeFilter(new AnnotationTypeFilter(RestController.class));
        provider.addIncludeFilter(new AnnotationTypeFilter(Controller.class));

        // 扫描指定包，如果有多个包，这个过程可以执行多次
        String[] packages = new String[]{ "com.fushun.framework.sample.web.starter","com.fushun.framework.sample.security",
                "com.fushun.framework.sample.mybatis","com.fushun.framework.sample.jpa","com.fushun.framework"};
        for (String aPackage : packages) {
            log.info("aPackage:{}",aPackage);
            Set<BeanDefinition> beanDefinitionSet = provider.findCandidateComponents(aPackage);
            log.info("beanDefinitionSet:{}",beanDefinitionSet.size());
            beanDefinitionSet.forEach(beanDefinition -> {
                Class<?> tableName = getTableNameByAnntation(beanDefinition);
                if(ObjectUtil.isNotNull(tableName)){
                    classes.add(tableName);
                    log.info("tableName:{}",tableName.toString());
                }
            });
        }
        log.info("classes:{}", JsonUtil.toJson(classes));
        for(Class<?> clazz :classes){
            RuntimeSerialization.registerLambdaCapturingClass(clazz);
        }

    }

}

```

# 4、//Caused by: org.apache.xmlbeans.SchemaTypeLoaderException: XML-BEANS compiled schema: Could not locate compiled schema resource org/apache/poi/schemas/ooxml/system/ooxml/index.xsb (org.apache.poi.schemas.ooxml.system.ooxml.index) - code 0
```java
hints.resources().registerPattern("*.xsb");
```


# 5、增加cache跳过下面的下载，只需要下载一次
```text
[INFO]     [creator]       BellSoft Liberica NIK 22.0.1: Contributing to layer
[INFO]     [creator]         Downloading from https://github.com/bell-sw/LibericaNIK/releases/download/24.0.1+1-22.0.1+10/bellsoft-liberica-vm-openjdk22.0.1+10-24.0.1+1-linux-amd64.tar.gz
[INFO]     [creator]         Verifying checksum
[INFO]     [creator]         Expanding to /layers/paketo-buildpacks_bellsoft-liberica/native-image-svm
[INFO]     [creator]         Adding 137 container CA certificates to JVM truststore
[INFO]     [creator]         Writing env.build/JAVA_HOME.override
[INFO]     [creator]         Writing env.build/JDK_HOME.override
[INFO]     [creator]     
[INFO]     [creator]     Paketo Buildpack for Syft 1.47.1
[INFO]     [creator]       https://github.com/paketo-buildpacks/syft
[INFO]     [creator]         Downloading from https://github.com/anchore/syft/releases/download/v0.105.1/syft_0.105.1_linux_amd64.tar.gz
[INFO]     [creator]         Verifying checksum
[INFO]     [creator]         Writing env.build/SYFT_CHECK_FOR_APP_UPDATE.default
```

```yaml
<BP_JVM_BUILD_CACHE_ENABLED>true</BP_JVM_BUILD_CACHE_ENABLED>
<BP_JVM_BUILD_CACHE_LAYER>true</BP_JVM_BUILD_CACHE_LAYER>
```

# 6、解决com.alibaba.excel.converters.Converter 实现类
```text
com.alibaba.excel.exception.ExcelCommonException: Can not instance custom converter:com.community.service.listener.LocalDateConverter
        at com.alibaba.excel.util.ClassUtils.doDeclaredFieldContentMap(ClassUtils.java:247)
        at com.alibaba.excel.util.ClassUtils.lambda$declaredFieldContentMap$4(ClassUtils.java:204)

```

# 7、 Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding"); 的解密错误
```java
public static byte[] decryptAES(byte[] cipherText, byte[] key, byte[] iv) throws Exception {
    BlockCipher aesEngine = new AESEngine();
    CBCBlockCipher cbcBlockCipher = new CBCBlockCipher(aesEngine);
    PaddedBufferedBlockCipher cipher = new PaddedBufferedBlockCipher(cbcBlockCipher, new PKCS7Padding());
    cipher.init(false, new ParametersWithIV(new KeyParameter(key), iv));

    byte[] output = new byte[cipher.getOutputSize(cipherText.length)];
    int len = cipher.processBytes(cipherText, 0, cipherText.length, output, 0);
    len += cipher.doFinal(output, len);

    byte[] result = new byte[len];
    System.arraycopy(output, 0, result, 0, len);
    return result;
}
```

# 8、mybatis-plus   @EnumValue 反射无法获取值
```java
Caused by: org.graalvm.nativeimage.MissingReflectionRegistrationError: The program tried to reflectively invoke method public java.lang.String com.community.common.enums.door.DeviceStatusEnum.getCode() without it being registered for runtime reflection. Add public java.lang.String com.community.common.enums.door.DeviceStatusEnum.getCode() to the reflection metadata to solve this problem. See https://www.graalvm.org/latest/reference-manual/native-image/metadata/#reflection for help.
at org.graalvm.nativeimage.builder/com.oracle.svm.core.reflect.MissingReflectionRegistrationUtils.forQueriedOnlyExecutable(MissingReflectionRegistrationUtils.java:72)
```
解决办法
增加需要反射获取值的类扫描

# 9 http 和https 不支持
```yaml
 <!-- https://docs.oracle.com/en/graalvm/enterprise/21/docs/reference-manual/native-image/URLProtocols/#supported-and-enabled-by-default -->
--enable-http
--enable-https
--enable-url-protocols=http
```

# 10 无法初始化的问题

```java
Caused by: cn.hutool.core.exceptions.UtilException: No constructor for [class cn.hutool.json.JSONConverter]
        at cn.hutool.core.util.ReflectUtil.newInstance(ReflectUtil.java:858)
        at cn.hutool.core.convert.ConverterRegistry.putCustom(ConverterRegistry.java:101)
        at cn.hutool.json.JSONConverter.<clinit>(JSONConverter.java:33)
        ... 203 common frames omitted
```
解决办法：
```java
hints.reflection().registerType(cn.hutool.json.JSONConverter.class,MemberCategory.values());
//不用添加
// hints.reflection().registerType(cn.hutool.core.convert.ConverterRegistry.class,MemberCategory.values());
```

# 11、所有的枚举类，如果BeanUtil.copy 也需要增加反射处理
```java
classes.add(IBaseEnum.class);
```
```java
//扫描所有的实现类
 scanner.addIncludeFilter(new AssignableTypeFilter(IBaseEnum.class));
```


# 12、awt的问题解决办法，

配置这个属性，告知不要使用awt
```java
System.setProperty("org.apache.poi.ss.ignoreMissingFontSystem", "true");
```
