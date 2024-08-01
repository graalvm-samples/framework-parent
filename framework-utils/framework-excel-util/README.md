# 1、awt的问题解决办法，

配置这个属性，告知不要使用awt
```java
System.setProperty("org.apache.poi.ss.ignoreMissingFontSystem", "true");
```
