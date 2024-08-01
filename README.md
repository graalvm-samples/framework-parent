# framework-parent 
java GraaLVM 基础项目，帮忙项目快速的集成GraaLVM


# 已GraaLVM集成的框架和项目
| 名称                   | 说明  | 是否修改源码                                                                        | maven build 检查插件 |
|----------------------|-----|-------------------------------------------------------------------------------|------------------|
| mybatis-plus         | 已完成 | [druid-spring-boot-3-starter](druid-spring-boot-3-starter)                    | -                |
| jap（hibernate）       | 已完成 | 无                                                                             | -                |
| redis（RedisTemplate） | 已完成 | 无                                                                             | -                |
| redisson             | 已完成 | 无                                                                             | -                |
| easyexcel            | 已完成 | [GitHub easyexcel](https://github.com/graalvm-samples/easyexcel) <br/>        | -                |
| apache poi           | 已完成 | 否                                                                             | -                |
| 多数据源                 | 已完成 | [framework-dynamic-datasource](framework-data%2Fframework-dynamic-datasource) | -                |
| AES（bouncycastle）    | 已完成 | 否                                                                             | -                |

## 已完成的Json框架
| 名称                  | 说明  | 是否修改源码 | maven build 检查插件 |
|---------------------|-----|--------|------------------|
| JSONUtil（hutool   ） | 已完成 | 否      | 有                |
| Gson                | 已完成 | 否      | 有                |
| JSON（fastjson2）     | 已完成 | 否      | 有                |
| JsonUtil（jackson）   | 已完成 | 否      | 有                |

## 已完成的 BeanUtil.copy 集成
| 名称                            | 说明  | 是否修改源码 | maven build 检查插件 |
|-------------------------------|-----|--------|------------------|
| BeanUtil（hutool   ）           | 已完成 | 否      | 有                |
| BeanUtils（springframework   ） | 已完成 | 否      | 有                |

# maven build 检查插件
1、检查插件的作用，是已有项目，进行升级集成GraaLVM。帮忙快速发现需要做json序列化和BeanUtil.copy 反射处理的编译检查工具


# 项目目的：
1. 减少重复轮子，
2. 记录一下解决问题的思路和解决办法，避免下次再次重新处理相同问题


# mvn 版本更新
```
mvn --batch-mode release:update-versions -DdevelopmentVersion=1.0.2-SNAPSHOT
```

# maven 分析包依赖
```shell
mvn dependency:tree -Dverbose

# 查找字符串
(com.google.zxing:core:jar:3.5.0:compile - omitted for conflict with 3.3.1)
omitted for conflict with
```

spring-boot 对应的spring-cloud版本 https://spring.io/projects/spring-cloud#overview
```
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-dependencies</artifactId>
    <version>Hoxton.SR4</version>
    <type>pom</type>
    <scope>import</scope>
</dependency>
```

Spring Boot 2 实战：利用Redis的Geo功能实现查找附近的位置
https://juejin.im/post/5eec330fe51d457426157325

swagger

https://blog.csdn.net/qq_17231297/article/details/123749057


虚拟线程
> https://docs.oracle.com/en/java/javase/21/core/virtual-threads.html#GUID-04C03FFC-066D-4857-85B9-E5A27A875AF9