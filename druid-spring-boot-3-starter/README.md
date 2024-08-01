# 修改项目源码的原因
1. graalvm 中不支持条件注入  @ConditionalOnProperty 配置
2. druid 的sql打印 sql 无法配置下面的配置
```yaml
        filter:
          slf4j:
            enabled: true
            statementExecuteAfterLogEnable: false
            statementCloseAfterLogEnable: false
            connectionCloseAfterLogEnable: false
            statementPrepareAfterLogEnable: false
            statementPrepareCallAfterLogEnable: false
            statementParameterSetLogEnable: false
            statementCreateAfterLogEnable: false
            statementLogEnabled: false
            statementExecutableSqlLogEnable: true
            statementExecuteQueryAfterLogEnable: false
            connectionCloseAfterLogEnabled: false
            statementParameterClearLogEnable: false
            StatementParameterSetLogEnabled: false
            StatementCloseAfterLogEnabled: false
            StatementExecuteAfterLogEnabled: false
            statementLogErrorEnabled: true
            resultSetLogEnabled: false
```
3、在集成graalvm 中 需要明确声明注入变量
```java
//com.alibaba.druid.spring.boot3.autoconfigure.DruidDataSourceAutoConfigure.dataSource
//增加手动设置
druidDataSourceWrapper.setBasicProperties(basicProperties);
```

# TODO web的查看监控页面。