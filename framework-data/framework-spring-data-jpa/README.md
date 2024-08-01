# spring-data-jpa

## 1、支持数据源的配置


```yaml
spring:
  jpa:
    generate-ddl: true
    show-sql: false
    hibernate:
      ddl-auto: none

```

示例：
```java
com.fushun.framework.jpa.hibernate.DB1EntityManager

com.fushun.framework.jpa.hibernate.DB2EntityManager
```


## 2、支持  @Column(name = "zbwId") 自定义字段名称的 处理

```yaml
spring:
  jpa:
    database-platform: org.hibernate.dialect.MySQL5InnoDBDialect
    hibernate:
      dialect: org.hibernate.dialect.MySQLDialect
      naming:
        implicit-strategy: com.fushun.framework.jpa.hibernate.SpringImplicitNamingStrategy
        physical-strategy: com.fushun.framework.jpa.hibernate.SpringPhysicalNamingStrategy

```

## 3、CustomerRepositoryImpl 添加使用了，setResultTransformer的支持

主要是解决了 createNativeQuery sql的的 自定义返回字段的对象。

查询返回的对象，不是托管对象

## TODO

 1、hibernate自动创建表，字段顺序的问题
