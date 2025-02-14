package com.fushun.framework.util.json;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonParser.Feature;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.StreamReadFeature;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.cfg.ConstructorDetector;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import com.fushun.framework.base.IBaseEnum;
import com.fushun.framework.exception.DynamicBaseException;
import com.fushun.framework.util.json.databind.CustomLocalDateTimeDeserializer;
import com.fushun.framework.util.json.databind.NumberSerializer;
import com.fushun.framework.util.json.databind.TimestampLocalDateTimeDeserializer;
import com.fushun.framework.util.json.databind.TimestampLocalDateTimeSerializer;
import com.fushun.framework.util.util.BeanUtils;
import com.fushun.framework.util.util.DateUtil;
import com.fushun.framework.util.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.TimeZone;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 简单封装Jackson，实现JSON String<->Java Object的Mapper.
 * 封装不同的输出风格, 使用不同的builder函数创建实例.
 * @author ThinkGem
 * @version 2013-11-15
 */
public class JsonMapper{

    private static final long serialVersionUID = 1L;

    private static Logger logger = LoggerFactory.getLogger(JsonMapper.class);

    private static JsonMapper jsonMapper;

    private static volatile ObjectMapper objectMapper;

    private static final ConcurrentHashMap<JsonEnum,ObjectMapper> contextHolder = new ConcurrentHashMap<>();

    /**
     * 初始化 objectMapper 属性
     * <p>
     * 通过这样的方式，使用 Spring 创建的 ObjectMapper Bean
     *
     * @param objectMapper ObjectMapper 对象
     */
    public static void init(ObjectMapper objectMapper) {
        if (JsonMapper.objectMapper == null) { // 第一次检查
            synchronized (JsonMapper.class) {
                if (JsonMapper.objectMapper == null) { // 第二次检查
                    logger.info("JsonMapper init");
                    JsonMapper.objectMapper = objectMapper;
                    getObjectMapperObj();
                }
            }
        }
    }


    public static ObjectMapper getObjectMapper(JsonEnum jsonEnum){
        if (JsonMapper.objectMapper == null) {
            throw new DynamicBaseException("OBJECTMAPPER_IS_NULL","ObjectMapper 是 null");
        }
        ObjectMapper objectMapper1=contextHolder.get(jsonEnum);
        if(objectMapper1!=null){
            return objectMapper1;
        }

        ObjectMapper mapper = JsonMapper.objectMapper.copy();
        contextHolder.put(jsonEnum, mapper);
        return mapper;
    }

    public static ObjectMapper getRedisObjectMapper(JsonEnum jsonEnum){
        if (JsonMapper.objectMapper == null) {
            throw new DynamicBaseException("OBJECTMAPPER_IS_NULL","ObjectMapper 是 null");
        }
        ObjectMapper objectMapper1=contextHolder.get(jsonEnum);
        if(objectMapper1!=null){
            return objectMapper1;
        }

        ObjectMapper mapper = JsonMapper.objectMapper.copy();
        mapper.activateDefaultTyping(LaissezFaireSubTypeValidator.instance, ObjectMapper.DefaultTyping.NON_FINAL, JsonTypeInfo.As.PROPERTY);
        mapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        contextHolder.put(jsonEnum, mapper);
        return mapper;
    }

    public static ObjectMapper getObjectMapperObj(){
        ObjectMapper mapper = JsonMapper.objectMapper;
        //https://blog.csdn.net/weixin_44130081/article/details/89678450
        // 配置常用设置
        mapper.setSerializationInclusion(Include.NON_NULL);

        // 1.1 创建 SimpleModule 对象

        SimpleModule javaTimeModule = new SimpleModule();
        javaTimeModule
                // 新增 Long 类型序列化规则，数值超过 2^53-1，在 JS 会出现精度丢失问题，因此 Long 自动序列化为字符串类型
                .addSerializer(Long.class, NumberSerializer.INSTANCE)
                .addSerializer(Long.TYPE, NumberSerializer.INSTANCE)
//                .addSerializer(LocalDate.class, LocalDateSerializer.INSTANCE)
//                .addDeserializer(LocalDate.class, LocalDateDeserializer.INSTANCE)
                .addSerializer(LocalDate.class, new LocalDateSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                .addDeserializer(LocalDate.class, new LocalDateDeserializer(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
//                .addSerializer(LocalTime.class, LocalTimeSerializer.INSTANCE)
//                .addDeserializer(LocalTime.class, LocalTimeDeserializer.INSTANCE)
                .addSerializer(LocalTime.class, new LocalTimeSerializer(DateTimeFormatter.ofPattern("HH:mm:ss")))
                .addDeserializer(LocalTime.class, new LocalTimeDeserializer(DateTimeFormatter.ofPattern("HH:mm:ss")))
                // 新增 LocalDateTime 序列化、反序列化规则，使用 Long 时间戳
//                .addSerializer(LocalDateTime.class, TimestampLocalDateTimeSerializer.INSTANCE)
//                .addDeserializer(LocalDateTime.class, TimestampLocalDateTimeDeserializer.INSTANCE)
                .addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .addDeserializer(LocalDateTime.class, new CustomLocalDateTimeDeserializer())
//                .addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
        ;
        mapper.registerModule(javaTimeModule);

        //注册一个统一的枚举序列方法，实现IEnum接口的枚举序列化时取desc字段
        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addSerializer(IBaseEnum.class, new EnumsCodec());
        mapper.registerModule(simpleModule);

        // 允许单引号、允许不带引号的字段名称
        mapper.configure(Feature.ALLOW_SINGLE_QUOTES, true);
        mapper.configure(Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        // 设置输入时忽略在JSON字符串中存在但Java对象实际没有的属性
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        // 忽略空Bean转json的错误
        mapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        mapper.disable(MapperFeature.DEFAULT_VIEW_INCLUSION);
        mapper.disable(DeserializationFeature.FAIL_ON_NULL_CREATOR_PROPERTIES);

        ////             为mapper注册一个带有SerializerModifier的Factory，此modifier主要做的事情为：当序列化类型为array，list、set时，当值为空时，序列化成[]
        //                    this.setSerializerFactory(this.getSerializerFactory().withSerializerModifier(new SerializerModifier()));
        //                    // 空值处理为空串
        //                    mapper.getSerializerProvider().setNullValueSerializer(new JsonSerializer<Object>(){
        //                        @Override
        //                        public void serialize(Object value, JsonGenerator jgen,
        //                                              SerializerProvider provider) throws IOException,
        //                                JsonProcessingException {
        //                            jgen.writeString("");
        //                        }
        //                    });
        SimpleDateFormat formatter=new SimpleDateFormat(DateUtil.FORMAT_STR);
        mapper.setDateFormat(formatter);
        // 设置时区
        mapper.setTimeZone(TimeZone.getDefault());//getTimeZone("GMT+8:00")
        return mapper;
    }


    private JsonMapper() {
    }

    public static JsonMapper getInstance() {
        if(jsonMapper==null){
            jsonMapper=new JsonMapper();
        }
        return jsonMapper;
    }

    /**
     * 创建只输出初始值被改变的属性到Json字符串的Mapper, 最节约的存储方式，建议在内部接口中使用。
     */
    public static ObjectMapper nonDefaultMapper() {
        return new ObjectMapper();
    }

    /**
     * Object可以是POJO，也可以是Collection或数组。
     * 如果对象为Null, 返回"null".
     * 如果集合为空集合, 返回"[]".
     */
    public String toJson(Object object) {
        try {
            return getObjectMapper(JsonEnum.BASE).writeValueAsString(object);
        } catch (IOException e) {
            logger.warn("write to json string error:" + object, e);
            return null;
        }
    }

    /**
     * 反序列化POJO或简单Collection如List<String>.
     *
     * 如果JSON字符串为Null或"null"字符串, 返回Null.
     * 如果JSON字符串为"[]", 返回空集合.
     *
     * 如需反序列化复杂Collection如List<MyBean>, 请使用fromJson(String,JavaType)
     * @see #fromJson(String, JavaType)
     */
    public <T> T fromJson(String jsonString, Class<T> clazz) {
        if (StringUtils.isEmpty(jsonString)) {
            return null;
        }
        try {
            return getObjectMapper(JsonEnum.BASE).readValue(jsonString, clazz);
        } catch (IOException e) {
            logger.warn("parse json string error:" + jsonString, e);
            return null;
        }
    }

    /**
     * 反序列化复杂Collection如List<Bean>, 先使用函數createCollectionType构造类型,然后调用本函数.
     * @see #createCollectionType(Class, Class...)
     */
    public <T> T fromJson(String jsonString, JavaType javaType) {
        if (StringUtils.isEmpty(jsonString)) {
            return null;
        }
        try {
            return (T) getObjectMapper(JsonEnum.BASE).readValue(jsonString, javaType);
        } catch (IOException e) {
            logger.warn("parse json string error:" + jsonString, e);
            return null;
        }
    }

    public <T> T fromJson(String jsonString, TypeReference<T> valueTypeRef) {
        if (StringUtils.isEmpty(jsonString)) {
            return null;
        }
        try {
            return (T) getObjectMapper(JsonEnum.BASE).readValue(jsonString, valueTypeRef);
        } catch (IOException e) {
            logger.warn("parse json string error:" + jsonString, e);
            return null;
        }
    }

    /**
     * 转换文件内容为对象
     * @param file
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> T fromJson(File file, Class<T> clazz) {
        if (BeanUtils.isNull(file)) {
            return null;
        }
        try {
            return getObjectMapper(JsonEnum.BASE).readValue(file, clazz);
        } catch (IOException e) {
            logger.warn("parse json file error:" + file.getAbsolutePath(), e);
            return null;
        }
    }

    /**
     * 转换文件内容为对象
     * @param inputStream
     * @param javaType
     * @param <T>
     * @return
     */
    public <T> T fromJson(InputStream inputStream, JavaType javaType) {
        if (BeanUtils.isNull(inputStream)) {
            return null;
        }
        try {
            return getObjectMapper(JsonEnum.BASE).readValue(inputStream, javaType);
        } catch (IOException e) {
            logger.warn("parse json file error:" , e);
            return null;
        }
    }

    /**
     * 構造泛型的Collection Type如:
     * ArrayList<MyBean>, 则调用constructCollectionType(ArrayList.class,MyBean.class)
     * HashMap<String,MyBean>, 则调用(HashMap.class,String.class, MyBean.class)
     */
    public JavaType createCollectionType(Class<?> collectionClass, Class<?>... elementClasses) {
        return getObjectMapper(JsonEnum.BASE).getTypeFactory().constructParametricType(collectionClass, elementClasses);
    }

    /**
     * 當JSON裡只含有Bean的部分屬性時，更新一個已存在Bean，只覆蓋該部分的屬性.
     */
    @SuppressWarnings("unchecked")
    public <T> T update(String jsonString, T object) {
        try {
            return (T) getObjectMapper(JsonEnum.BASE).readerForUpdating(object).readValue(jsonString);
        } catch (IOException e) {
            logger.warn("update json string:" + jsonString + " to object:" + object + " error.", e);
        }
        return null;
    }

    /**
     * 輸出JSONP格式數據.
     */
    public String toJsonP(String functionName, Object object) {
        return toJson(new JSONPObject(functionName, object));
    }
}


