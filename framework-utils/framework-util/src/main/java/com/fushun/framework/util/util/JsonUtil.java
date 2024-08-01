package com.fushun.framework.util.util;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.ArrayType;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.MapType;
import com.fasterxml.jackson.databind.type.SimpleType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fushun.framework.json.config.JsonGraalVMNativeBean;
import com.fushun.framework.util.json.JsonMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 *
 */
public class JsonUtil {

    private static final Logger log = LoggerFactory.getLogger(JsonUtil.class);

    /**
     * jsonStr转换为 HashMap<String,Object>类型
     *
     * @param jsonStr
     * @return
     * @author fushun
     * @version dev706
     * @creation 2017年6月7日
     * @records <p>  fushun 2017年6月7日</p>
     */
    @SuppressWarnings("unchecked")
    public static HashMap<String, Object> jsonToHashMap(String jsonStr) {
        ObjectMapper mapper = JsonMapper.getObjectMapper();

        HashMap<String, Object> map = new HashMap<String, Object>();
        try {
            map = mapper.readValue(jsonStr, HashMap.class);
        } catch (Exception e) {
            log.warn("jsonToHashMap 转换失败：",e);
        }
        return map;
    }

    /**
     * json转为继承Map的对象类型
     *
     * @param jsonStr
     * @param T          继承Map的对象类型
     * @param keyClas    Map.Key class类型
     * @param valueClass Map.value class类型
     * @return
     * @author fushun
     * @version dev706
     * @creation 2017年6月7日
     * @records <p>  fushun 2017年6月7日</p>
     */
    public static <K, V, T extends Map<K, V>> T jsonToMap(String jsonStr, Class<T> T, Class<K> keyClas, Class<V> valueClass) {
        ObjectMapper mapper = JsonMapper.getObjectMapper();
        try {

            MapType mapType = MapType.construct(T, SimpleType.construct(keyClas), SimpleType.construct(valueClass));
            return mapper.readValue(jsonStr, mapType);
        } catch (JsonParseException e) {
            log.warn("json转换为map错误,jsonStr:[{}],keyClas:[{}],valueClass:[{}]",jsonStr,keyClas,valueClass,e);
        } catch (JsonProcessingException e) {
            log.warn("json转换为map错误,jsonStr:[{}],keyClas:[{}],valueClass:[{}]",jsonStr,keyClas,valueClass,e);
        }
        return null;
    }

    /**
     * json转换为T对象类型
     *
     * @param json
     * @param cl
     * @return
     * @author fushun
     * @version dev706
     * @creation 2017年6月7日
     * @records <p>  fushun 2017年6月7日</p>
     */
    public static <T extends JsonGraalVMNativeBean> T jsonToClass(String json, Class<T> cl) {
        return JsonMapper.getInstance().fromJson(json,cl);
    }

    /**
     * 转换为对象
     * @param file
     * @param cl
     * @param <T>
     * @return
     */
    public static <T extends JsonGraalVMNativeBean> T jsonToClass(File file, Class<T> cl) {
        return JsonMapper.getInstance().fromJson(file,cl);
    }

    /**
     * 转换为对象
     * @param inputStream
     * @param javaType
     * @param <T>
     * @return
     */
    public static <T extends JsonGraalVMNativeBean> T jsonToClass(InputStream inputStream, JavaType javaType) {
        return JsonMapper.getInstance().fromJson(inputStream,javaType);
    }

    /**
     * json转换为对象类型
     * 返回 包装的类型 Map&ltString, AppFirstOrderRuleDTO&gt
     *
     * @param jsonStr
     * @param valueTypeRef 转为的对象类型，是一种包装的类型
     *                     <p>例如：TypeReference&ltMap&ltString, AppFirstOrderRuleDTO&gt&gt ref = new TypeReference&ltMap&ltString, AppFirstOrderRuleDTO&gt&gt() { };
     * @return 返回 包装的类型 Map&ltString, AppFirstOrderRuleDTO&gt
     * @author fushun
     * @version dev706
     * @creation 2017年6月7日
     * @records <p>  fushun 2017年6月7日</p>
     */
    public static <T extends JsonGraalVMNativeBean> T jsonToClass(String jsonStr, TypeReference<T> valueTypeRef) {
        return JsonMapper.getInstance().fromJson(jsonStr,valueTypeRef);
    }

    /**
     * 集合中 泛型无法无法确认是java类 的名称，如：List<T>
     * 获取 转化为JavaType
     * @param collectionClass
     * @param elementClasses
     * @return
     */
    public static JavaType getCollectionJavaType(Class<?> collectionClass, Class<?>... elementClasses) {
        return JsonMapper.getObjectMapper().getTypeFactory().constructParametricType(collectionClass, elementClasses);
    }

    public static <T extends JsonGraalVMNativeBean> T jsonToClass(String jsonStr, JavaType javaType) {
        return JsonMapper.getInstance().fromJson(jsonStr,javaType);
    }

    /**
     * 对象转为json
     *
     * @param obj
     * @return
     * @author fushun
     * @version dev706
     * @creation 2017年6月7日
     * @records <p>  fushun 2017年6月7日</p>
     */
    public static String classToJson(JsonGraalVMNativeBean obj) {
        return classToJson_(obj);
    }

    public static String classToJson(Map obj) {
        return classToJson_(obj);
    }

    public static String classToJson(Object[] obj) {
        return classToJson_(obj);
    }

    public static String classToJson(Iterable obj) {
        return classToJson_(obj);
    }
    private static String classToJson_(Object obj) {
        ObjectMapper mapper = JsonMapper.getObjectMapper();
        try {

            return mapper.writeValueAsString(obj);
        } catch (IOException e) {
            log.warn("classToJson 转换失败",e);
        }
        return null;
    }

    /**
     * 对象转为json
     *
     * @param obj
     * @return
     * @author fushun
     * @version dev706
     * @creation 2017年6月7日
     * @records <p>  fushun 2017年6月7日</p>
     */
    public static String classToJson(JsonGraalVMNativeBean obj,String dateFormat) {
        return classToJson_(obj,dateFormat);
    }

    public static String classToJson(Map obj,String dateFormat) {
        return classToJson_(obj,dateFormat);
    }

    public static String classToJson(Iterable obj,String dateFormat) {
        return classToJson_(obj,dateFormat);
    }

    private static String classToJson_(Object obj,String dateFormat) {
        ObjectMapper mapper = JsonMapper.getObjectMapper();
        try {
            SimpleDateFormat formatter=new SimpleDateFormat(dateFormat);
            mapper.setDateFormat(formatter);
            mapper.setTimeZone(TimeZone.getDefault());//getTimeZone("GMT+8:00")
            mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
            mapper.configure(MapperFeature.DEFAULT_VIEW_INCLUSION, false);
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            return mapper.writeValueAsString(obj);
        } catch (IOException e) {
            e.printStackTrace();;
            log.warn("classToJson ",e);
        }
        return null;
    }

    public static String toJson(JsonGraalVMNativeBean obj) {
        return toJson_(obj);
    }
    public static String toJson(Map obj) {
        return toJson_(obj);
    }
    public static String toJson(Iterable obj) {
        return toJson_(obj);
    }

    private static String toJson_(Object obj) {
        ObjectMapper mapper = JsonMapper.getObjectMapper();
        try {
            String str = mapper.writeValueAsString(obj);
            return str;
        } catch (IOException e) {
            log.warn("tojson 转换失败,",e);
        }
        return null;
    }


    @SuppressWarnings("unchecked")
    /**
     * 对象转化为HashMap
     * @param obj
     * @return 对象的属性名为HashMap.Key，对象的属性值为HashMap.Value
     * @author fushun
     * @version dev706
     * @creation 2017年6月7日
     * @records <p>  fushun 2017年6月7日</p>
     */
    public static HashMap<String, Object> classToHashMap(JsonGraalVMNativeBean obj) {
        ObjectMapper mapper = JsonMapper.getObjectMapper();
        try {
            String str = mapper.writeValueAsString(obj);
            return mapper.readValue(str, HashMap.class);
        } catch (IOException e) {
            log.warn("classToHashMap 转换失败,",e);
        }
        return null;
    }


    /**
     * @param map    转换为Class
     * @param classs
     * @return
     * @author fushun
     * @version V2.3全文检索
     * @creation 2016年8月17日
     * @records <p>  fushun 2016年8月17日</p>
     */
    public static <T extends JsonGraalVMNativeBean> T hashMapToClass(Map<String, Object> map, Class<T> classs) {
        ObjectMapper mapper = JsonMapper.getObjectMapper();
        try {
            String str = mapper.writeValueAsString(map);
            return mapper.readValue(str, classs);
        } catch (IOException e) {
            log.warn("hashMapToClass 转换失败,",e);
        }
        return null;
    }

    /**
     * json转换为对应的数组
     *
     * @param obj
     * @param classs
     * @return
     * @author fushun
     * @version VS1.3
     * @creation 2016年4月6日
     */
    @SuppressWarnings("unchecked")
    public static <T extends JsonGraalVMNativeBean> T[] jsonToArray(String obj, Class<T> classs) {
        if (StringUtils.isEmpty(obj)) {
            return null;
        }
        ObjectMapper mapper = JsonMapper.getObjectMapper();

        ArrayType valueType = TypeFactory.defaultInstance().constructArrayType(classs);
        try {
            Object pb = mapper.readValue(obj, valueType);
            return (T[]) pb;
        } catch (IOException e) {
            log.warn("hashMapToClass 转换失败,",e);
        }
        return null;
    }

    /**
     * Json 转换为hashMap
     *
     * @param obj
     * @return
     * @author fushun
     * @version VS1.3
     * @creation 2016年4月8日
     */
    @SuppressWarnings("unchecked")
    public static List<HashMap<String, Object>> jsonToList(String obj) {
        if (StringUtils.isEmpty(obj)) {
            return null;
        }
        ObjectMapper mapper = JsonMapper.getObjectMapper();
        MapType j = MapType.construct(HashMap.class, SimpleType.construct(String.class), SimpleType.construct(Object.class));
        CollectionType collectionType = CollectionType.construct(List.class, j);
        try {
            Object pb = mapper.readValue(obj, collectionType);
            return (List<HashMap<String, Object>>) pb;
        } catch (IOException e) {
            log.warn("jsonToList 转换失败,",e);
        }
        return null;
    }

    /**
     * 转换为K,V为指定对象类型的HashMap
     * <p>
     * json转换为List<HashMap<K,V>>
     *
     * @param jsonStr    json字符串
     * @param keyCls     HashMap.Key 类型
     * @param valueClass HashMap.Value 类型
     * @return
     * @author fushun
     * @version dev706
     * @creation 2017年6月7日
     * @records <p>  fushun 2017年6月7日</p>
     */
    @SuppressWarnings("unchecked")
    public static <K, V> List<HashMap<K, V>> jsonToList(String jsonStr, Class<K> keyCls, Class<V> valueClass) {
        if (StringUtils.isEmpty(jsonStr)) {
            return null;
        }
        ObjectMapper mapper = JsonMapper.getObjectMapper();
        MapType mapType = MapType.construct(HashMap.class, SimpleType.construct(keyCls), SimpleType.construct(valueClass));
        CollectionType collectionType = CollectionType.construct(List.class, mapType);
        try {
            Object pb = mapper.readValue(jsonStr, collectionType);
            return (List<HashMap<K, V>>) pb;
        } catch (IOException e) {
            log.warn("jsonToList 转换失败,",e);
        }
        return null;
    }

    public static <K, V> List<LinkedHashMap<K, V>> jsonToLinkedList(String jsonStr,
                                                         Class<K> keyCls, Class<V> valueClass) {
        if (StringUtils.isEmpty(jsonStr)) {
            return null;
        }
        ObjectMapper mapper = JsonMapper.getObjectMapper();
//        MapType mapType = MapType.construct(LinkedHashMap.class, SimpleType.construct(keyCls), SimpleType.construct(valueClass));
        TypeFactory typeFactory = mapper.getTypeFactory();
        MapType mapType = typeFactory.constructMapType(LinkedHashMap.class, keyCls, valueClass);
        CollectionType collectionType = typeFactory.constructCollectionType(LinkedList.class, mapType);
//        CollectionType collectionType = CollectionType.construct(LinkedList.class, mapType);
        try {
            Object pb = mapper.readValue(jsonStr, collectionType);
            return (List<LinkedHashMap<K, V>>) pb;
        } catch (IOException e) {
            log.warn("jsonToList 转换失败,",e);
        }
        return null;
    }

    /**
     * 转换为List<T>
     *
     * @param jsonStr
     * @param cls     对象类型
     * @return
     * @author fushun
     * @version dev706
     * @creation 2017年6月7日
     * @records <p>  fushun 2017年6月7日</p>
     */
    @SuppressWarnings("unchecked")
    public static <T extends JsonGraalVMNativeBean> List<T> jsonToList(String jsonStr, Class<T> cls) {
        if (StringUtils.isEmpty(jsonStr)) {
            return null;
        }
        ObjectMapper mapper = JsonMapper.getObjectMapper().setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        JavaType j = SimpleType.construct(cls);
        CollectionType collectionType = CollectionType.construct(List.class, j);
        try {
            Object pb = mapper.readValue(jsonStr, collectionType);
            return (List<T>) pb;
        } catch (IOException e) {
            log.warn("jsonToList 转换失败,",e);
        }
        return null;
    }

}
