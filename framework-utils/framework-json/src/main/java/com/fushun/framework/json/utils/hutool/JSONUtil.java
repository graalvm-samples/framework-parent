package com.fushun.framework.json.utils.hutool;

import cn.hutool.core.io.IORuntimeException;
import cn.hutool.core.lang.TypeReference;
import cn.hutool.json.*;
import cn.hutool.json.serialize.GlobalSerializeMapping;
import cn.hutool.json.serialize.JSONArraySerializer;
import cn.hutool.json.serialize.JSONDeserializer;
import cn.hutool.json.serialize.JSONObjectSerializer;
import com.fushun.framework.json.config.JsonGraalVMNativeBean;

import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;

public class JSONUtil {

    // -------------------------------------------------------------------- Pause start

    /**
     * 创建JSONObject
     *
     * @return JSONObject
     */
    public static JSONObject createObj() {
        return cn.hutool.json.JSONUtil.createObj();
    }

    /**
     * 创建JSONObject
     *
     * @param config JSON配置
     * @return JSONObject
     * @since 5.2.5
     */
    public static JSONObject createObj(JSONConfig config) {
        return cn.hutool.json.JSONUtil.createObj(config);
    }

    /**
     * 创建 JSONArray
     *
     * @return JSONArray
     */
    public static JSONArray createArray() {
        return cn.hutool.json.JSONUtil.createArray();
    }

    /**
     * 创建 JSONArray
     *
     * @param config JSON配置
     * @return JSONArray
     * @since 5.2.5
     */
    public static JSONArray createArray(JSONConfig config) {
        return cn.hutool.json.JSONUtil.createArray(config);
    }

    /**
     * JSON字符串转JSONObject对象
     *
     * @param jsonStr JSON字符串
     * @return JSONObject
     */
    public static JSONObject parseObj(String jsonStr) {
        return cn.hutool.json.JSONUtil.parseObj(jsonStr);
    }

    /**
     * JSON字符串转JSONObject对象<br>
     * 此方法会忽略空值，但是对JSON字符串不影响
     *
     * @param obj Bean对象或者Map
     * @return JSONObject
     */
    public static JSONObject parseObj(JsonGraalVMNativeBean obj) {
        return cn.hutool.json.JSONUtil.parseObj(obj);
    }

    public static JSONObject parseObj(Map obj) {
        return cn.hutool.json.JSONUtil.parseObj(obj);
    }

    public static JSONObject parseObj(Iterable obj) {
        return cn.hutool.json.JSONUtil.parseObj(obj);
    }

    /**
     * JSON字符串转JSONObject对象<br>
     * 此方法会忽略空值，但是对JSON字符串不影响
     *
     * @param obj    Bean对象或者Map
     * @param config JSON配置
     * @return JSONObject
     * @since 5.3.1
     */
    public static JSONObject parseObj(JsonGraalVMNativeBean obj, JSONConfig config) {
        return cn.hutool.json.JSONUtil.parseObj(obj,config);
    }

    public static JSONObject parseObj(Map obj, JSONConfig config) {
        return cn.hutool.json.JSONUtil.parseObj(obj,config);
    }

    public static JSONObject parseObj(Iterable obj, JSONConfig config) {
        return cn.hutool.json.JSONUtil.parseObj(obj,config);
    }

    /**
     * JSON字符串转JSONObject对象
     *
     * @param obj             Bean对象或者Map
     * @param ignoreNullValue 是否忽略空值，如果source为JSON字符串，不忽略空值
     * @return JSONObject
     * @since 3.0.9
     */
    public static JSONObject parseObj(JsonGraalVMNativeBean obj, boolean ignoreNullValue) {
        return cn.hutool.json.JSONUtil.parseObj(obj,ignoreNullValue);
    }
    public static JSONObject parseObj(Map obj, boolean ignoreNullValue) {
        return cn.hutool.json.JSONUtil.parseObj(obj,ignoreNullValue);
    }

    public static JSONObject parseObj(Iterable obj, boolean ignoreNullValue) {
        return cn.hutool.json.JSONUtil.parseObj(obj,ignoreNullValue);
    }

    /**
     * JSON字符串转JSONObject对象
     *
     * @param obj             Bean对象或者Map
     * @param ignoreNullValue 是否忽略空值，如果source为JSON字符串，不忽略空值
     * @param isOrder         是否有序
     * @return JSONObject
     * @since 4.2.2
     * @deprecated isOrder参数不再有效
     */
    @SuppressWarnings("unused")
    @Deprecated
    public static JSONObject parseObj(JsonGraalVMNativeBean obj, boolean ignoreNullValue, boolean isOrder) {
        return cn.hutool.json.JSONUtil.parseObj(obj,ignoreNullValue, isOrder);
    }

    public static JSONObject parseObj(Map obj, boolean ignoreNullValue, boolean isOrder) {
        return cn.hutool.json.JSONUtil.parseObj(obj,ignoreNullValue, isOrder);
    }

    public static JSONObject parseObj(Iterable obj, boolean ignoreNullValue, boolean isOrder) {
        return cn.hutool.json.JSONUtil.parseObj(obj,ignoreNullValue, isOrder);
    }

    /**
     * JSON字符串转JSONArray
     *
     * @param jsonStr JSON字符串
     * @return JSONArray
     */
    public static JSONArray parseArray(String jsonStr) {
        return cn.hutool.json.JSONUtil.parseArray(jsonStr);
    }

    /**
     * JSON字符串转JSONArray
     *
     * @param arrayOrCollection 数组或集合对象
     * @return JSONArray
     * @since 3.0.8
     */
    public static JSONArray parseArray(JsonGraalVMNativeBean arrayOrCollection) {
        return cn.hutool.json.JSONUtil.parseArray(arrayOrCollection);
    }
    public static JSONArray parseArray(Map arrayOrCollection) {
        return cn.hutool.json.JSONUtil.parseArray(arrayOrCollection);
    }

    public static JSONArray parseArray(Iterable arrayOrCollection) {
        return cn.hutool.json.JSONUtil.parseArray(arrayOrCollection);
    }

    /**
     * JSON字符串转JSONArray
     *
     * @param arrayOrCollection 数组或集合对象
     * @param config            JSON配置
     * @return JSONArray
     * @since 5.3.1
     */
    public static JSONArray parseArray(JsonGraalVMNativeBean arrayOrCollection, JSONConfig config) {
        return cn.hutool.json.JSONUtil.parseArray(arrayOrCollection, config);
    }
    public static JSONArray parseArray(Map arrayOrCollection, JSONConfig config) {
        return cn.hutool.json.JSONUtil.parseArray(arrayOrCollection, config);
    }

    public static JSONArray parseArray(Iterable arrayOrCollection, JSONConfig config) {
        return cn.hutool.json.JSONUtil.parseArray(arrayOrCollection, config);
    }

    /**
     * JSON字符串转JSONArray
     *
     * @param arrayOrCollection 数组或集合对象
     * @param ignoreNullValue   是否忽略空值
     * @return JSONArray
     * @since 3.2.3
     */
    public static JSONArray parseArray(JsonGraalVMNativeBean arrayOrCollection, boolean ignoreNullValue) {
        return cn.hutool.json.JSONUtil.parseArray(arrayOrCollection, ignoreNullValue);
    }
    public static JSONArray parseArray(Map arrayOrCollection, boolean ignoreNullValue) {
        return cn.hutool.json.JSONUtil.parseArray(arrayOrCollection, ignoreNullValue);
    }

    public static JSONArray parseArray(Iterable arrayOrCollection, boolean ignoreNullValue) {
        return cn.hutool.json.JSONUtil.parseArray(arrayOrCollection, ignoreNullValue);
    }

    /**
     * 转换对象为JSON，如果用户不配置JSONConfig，则JSON的有序与否与传入对象有关。<br>
     * 支持的对象：
     * <ul>
     *     <li>String: 转换为相应的对象</li>
     *     <li>Array、Iterable、Iterator：转换为JSONArray</li>
     *     <li>Bean对象：转为JSONObject</li>
     * </ul>
     *
     * @param obj 对象
     * @return JSON
     */
    public static JSON parse(JsonGraalVMNativeBean obj) {
        return cn.hutool.json.JSONUtil.parse(obj);
    }
    public static JSON parse(Map obj) {
        return cn.hutool.json.JSONUtil.parse(obj);
    }
    public static JSON parse(Iterable obj) {
        return cn.hutool.json.JSONUtil.parse(obj);
    }

    /**
     * 转换对象为JSON，如果用户不配置JSONConfig，则JSON的有序与否与传入对象有关。<br>
     * 支持的对象：
     * <ul>
     *     <li>String: 转换为相应的对象</li>
     *     <li>Array、Iterable、Iterator：转换为JSONArray</li>
     *     <li>Bean对象：转为JSONObject</li>
     * </ul>
     *
     * @param obj    对象
     * @param config JSON配置，{@code null}使用默认配置
     * @return JSON
     * @since 5.3.1
     */
    public static JSON parse(JsonGraalVMNativeBean obj, JSONConfig config) {
        return cn.hutool.json.JSONUtil.parse(obj,config);
    }
    public static JSON parse(Map obj, JSONConfig config) {
        return cn.hutool.json.JSONUtil.parse(obj,config);
    }
    public static JSON parse(Iterable obj, JSONConfig config) {
        return cn.hutool.json.JSONUtil.parse(obj,config);
    }

    /**
     * XML字符串转为JSONObject
     *
     * @param xmlStr XML字符串
     * @return JSONObject
     */
    public static JSONObject parseFromXml(String xmlStr) {
        return cn.hutool.json.JSONUtil.parseFromXml(xmlStr);
    }

    // -------------------------------------------------------------------- Parse end

    // -------------------------------------------------------------------- Read start

    /**
     * 读取JSON
     *
     * @param file    JSON文件
     * @param charset 编码
     * @return JSON（包括JSONObject和JSONArray）
     * @throws IORuntimeException IO异常
     */
    public static JSON readJSON(File file, Charset charset) throws IORuntimeException {
        return cn.hutool.json.JSONUtil.readJSON(file, charset);
    }

    /**
     * 读取JSONObject
     *
     * @param file    JSON文件
     * @param charset 编码
     * @return JSONObject
     * @throws IORuntimeException IO异常
     */
    public static JSONObject readJSONObject(File file, Charset charset) throws IORuntimeException {
        return cn.hutool.json.JSONUtil.readJSONObject(file, charset);
    }

    /**
     * 读取JSONArray
     *
     * @param file    JSON文件
     * @param charset 编码
     * @return JSONArray
     * @throws IORuntimeException IO异常
     */
    public static JSONArray readJSONArray(File file, Charset charset) throws IORuntimeException {
        return cn.hutool.json.JSONUtil.readJSONArray(file, charset);
    }
    // -------------------------------------------------------------------- Read end

    // -------------------------------------------------------------------- toString start

    /**
     * 转为JSON字符串
     *
     * @param json         JSON
     * @param indentFactor 每一级别的缩进
     * @return JSON字符串
     */
    public static String toJsonStr(JSON json, int indentFactor) {
        return cn.hutool.json.JSONUtil.toJsonStr(json, indentFactor);
    }

    /**
     * 转为JSON字符串
     *
     * @param json JSON
     * @return JSON字符串
     */
    public static String toJsonStr(JSON json) {
        return cn.hutool.json.JSONUtil.toJsonStr(json);
    }

    /**
     * 转为JSON字符串，并写出到write
     *
     * @param json JSON
     * @param writer Writer
     * @since 5.3.3
     */
    public static void toJsonStr(JSON json, Writer writer) {
        cn.hutool.json.JSONUtil.toJsonStr(json,writer);
    }

    /**
     * 转为JSON字符串
     *
     * @param json JSON
     * @return JSON字符串
     */
    public static String toJsonPrettyStr(JSON json) {
        return cn.hutool.json.JSONUtil.toJsonPrettyStr(json);
    }

    /**
     * 转换为JSON字符串
     *
     * @param obj 被转为JSON的对象
     * @return JSON字符串
     */
    public static String toJsonStr(JsonGraalVMNativeBean obj) {
        return cn.hutool.json.JSONUtil.toJsonStr(obj);
    }
    public static String toJsonStr(Map obj) {
        return cn.hutool.json.JSONUtil.toJsonStr(obj);
    }
    public static String toJsonStr(Iterable obj) {
        return cn.hutool.json.JSONUtil.toJsonStr(obj);
    }

    /**
     * 转换为JSON字符串
     *
     * @param obj 被转为JSON的对象
     * @param jsonConfig JSON配置
     * @return JSON字符串
     * @since 5.7.12
     */
    public static String toJsonStr(JsonGraalVMNativeBean obj, JSONConfig jsonConfig) {
        return cn.hutool.json.JSONUtil.toJsonStr(obj,jsonConfig);
    }
    public static String toJsonStr(Map obj, JSONConfig jsonConfig) {
        return cn.hutool.json.JSONUtil.toJsonStr(obj,jsonConfig);
    }
    public static String toJsonStr(Iterable obj, JSONConfig jsonConfig) {
        return cn.hutool.json.JSONUtil.toJsonStr(obj,jsonConfig);
    }

    /**
     * 转换为JSON字符串并写出到writer
     *
     * @param obj 被转为JSON的对象
     * @param writer Writer
     * @since 5.3.3
     */
    public static void toJsonStr(JsonGraalVMNativeBean obj, Writer writer) {
        cn.hutool.json.JSONUtil.toJsonStr(obj,writer);
    }
    public static void toJsonStr(Map obj, Writer writer) {
        cn.hutool.json.JSONUtil.toJsonStr(obj,writer);
    }
    public static void toJsonStr(Iterable obj, Writer writer) {
        cn.hutool.json.JSONUtil.toJsonStr(obj,writer);
    }


    /**
     * 转换为格式化后的JSON字符串
     *
     * @param obj Bean对象
     * @return JSON字符串
     */
    public static String toJsonPrettyStr(JsonGraalVMNativeBean obj) {
        return cn.hutool.json.JSONUtil.toJsonPrettyStr(obj);
    }
    public static String toJsonPrettyStr(Map obj) {
        return cn.hutool.json.JSONUtil.toJsonPrettyStr(obj);
    }
    public static String toJsonPrettyStr(Iterable obj) {
        return cn.hutool.json.JSONUtil.toJsonPrettyStr(obj);
    }

    /**
     * 转换为XML字符串
     *
     * @param json JSON
     * @return XML字符串
     */
    public static String toXmlStr(JSON json) {
        return cn.hutool.json.JSONUtil.toXmlStr(json);
    }
    // -------------------------------------------------------------------- toString end

    // -------------------------------------------------------------------- toBean start

    /**
     * JSON字符串转为实体类对象，转换异常将被抛出
     *
     * @param <T>        Bean类型
     * @param jsonString JSON字符串
     * @param beanClass  实体类对象
     * @return 实体类对象
     * @since 3.1.2
     */
    public static <T extends JsonGraalVMNativeBean> T toBean(String jsonString, Class<T> beanClass) {
        return cn.hutool.json.JSONUtil.toBean(jsonString,beanClass);
    }

    /**
     * JSON字符串转为实体类对象，转换异常将被抛出<br>
     * 通过{@link JSONConfig}可选是否忽略大小写、忽略null等配置
     *
     * @param <T>        Bean类型
     * @param jsonString JSON字符串
     * @param config     JSON配置
     * @param beanClass  实体类对象
     * @return 实体类对象
     * @since 5.8.0
     */
    public static <T extends JsonGraalVMNativeBean> T toBean(String jsonString, JSONConfig config, Class<T> beanClass) {
        return cn.hutool.json.JSONUtil.toBean(jsonString, config,beanClass);
    }

    /**
     * 转为实体类对象，转换异常将被抛出
     *
     * @param <T>       Bean类型
     * @param json      JSONObject
     * @param beanClass 实体类对象
     * @return 实体类对象
     */
    public static <T extends JsonGraalVMNativeBean> T toBean(JSONObject json, Class<T> beanClass) {
        return cn.hutool.json.JSONUtil.toBean(json, beanClass);
    }

    /**
     * JSON字符串转为实体类对象，转换异常将被抛出
     *
     * @param <T>           Bean类型
     * @param jsonString    JSON字符串
     * @param typeReference {@link TypeReference}类型参考子类，可以获取其泛型参数中的Type类型
     * @param ignoreError   是否忽略错误
     * @return 实体类对象
     * @since 4.3.2
     */
    public static <T extends JsonGraalVMNativeBean> T toBean(String jsonString, TypeReference<T> typeReference, boolean ignoreError) {
        return cn.hutool.json.JSONUtil.toBean(jsonString, typeReference,ignoreError);
    }

    /**
     * JSON字符串转为实体类对象，转换异常将被抛出
     *
     * @param <T>         Bean类型
     * @param jsonString  JSON字符串
     * @param beanType    实体类对象类型
     * @param ignoreError 是否忽略错误
     * @return 实体类对象
     * @since 4.3.2
     */
    public static <T extends JsonGraalVMNativeBean> T toBean(String jsonString, Type beanType, boolean ignoreError) {
        return cn.hutool.json.JSONUtil.toBean(jsonString, beanType,ignoreError);
    }

    /**
     * 转为实体类对象
     *
     * @param <T>           Bean类型
     * @param json          JSONObject
     * @param typeReference {@link TypeReference}类型参考子类，可以获取其泛型参数中的Type类型
     * @param ignoreError   是否忽略转换错误
     * @return 实体类对象
     * @since 4.6.2
     */
    public static <T extends JsonGraalVMNativeBean> T toBean(JSON json, TypeReference<T> typeReference, boolean ignoreError) {
        return cn.hutool.json.JSONUtil.toBean(json, typeReference,ignoreError);
    }

    /**
     * 转为实体类对象
     *
     * @param <T>         Bean类型
     * @param json        JSONObject
     * @param beanType    实体类对象类型
     * @param ignoreError 是否忽略转换错误
     * @return 实体类对象
     * @since 4.3.2
     */
    @SuppressWarnings("deprecation")
    public static <T extends JsonGraalVMNativeBean> T toBean(JSON json, Type beanType, boolean ignoreError) {
        return cn.hutool.json.JSONUtil.toBean(json, beanType,ignoreError);
    }
    // -------------------------------------------------------------------- toBean end

    /**
     * 将JSONArray字符串转换为Bean的List，默认为ArrayList
     *
     * @param <T>         Bean类型
     * @param jsonArray   JSONArray字符串
     * @param elementType List中元素类型
     * @return List
     * @since 5.5.2
     */
    public static <T extends JsonGraalVMNativeBean> List<T> toList(String jsonArray, Class<T> elementType) {
        return cn.hutool.json.JSONUtil.toList(jsonArray, elementType);
    }

    /**
     * 将JSONArray转换为Bean的List，默认为ArrayList
     *
     * @param <T>         Bean类型
     * @param jsonArray   {@link JSONArray}
     * @param elementType List中元素类型
     * @return List
     * @since 4.0.7
     */
    public static <T extends JsonGraalVMNativeBean> List<T> toList(JSONArray jsonArray, Class<T> elementType) {
        return cn.hutool.json.JSONUtil.toList(jsonArray, elementType);
    }

    /**
     * 通过表达式获取JSON中嵌套的对象<br>
     * <ol>
     * <li>.表达式，可以获取Bean对象中的属性（字段）值或者Map中key对应的值</li>
     * <li>[]表达式，可以获取集合等对象中对应index的值</li>
     * </ol>
     * <p>
     * 表达式栗子：
     *
     * <pre>
     * persion
     * persion.name
     * persons[3]
     * person.friends[5].name
     * </pre>
     *
     * @param json       {@link JSON}
     * @param expression 表达式
     * @return 对象
     * @see JSON#getByPath(String)
     */
    public static Object getByPath(JSON json, String expression) {
        return cn.hutool.json.JSONUtil.getByPath(json, expression);
    }

    /**
     * 通过表达式获取JSON中嵌套的对象<br>
     * <ol>
     * <li>.表达式，可以获取Bean对象中的属性（字段）值或者Map中key对应的值</li>
     * <li>[]表达式，可以获取集合等对象中对应index的值</li>
     * </ol>
     * <p>
     * 表达式栗子：
     *
     * <pre>
     * persion
     * persion.name
     * persons[3]
     * person.friends[5].name
     * </pre>
     *
     * @param <T> 值类型
     * @param json       {@link JSON}
     * @param expression 表达式
     * @param defaultValue 默认值
     * @return 对象
     * @see JSON#getByPath(String)
     * @since 5.6.0
     */
    @SuppressWarnings("unchecked")
    public static <T> T getByPath(JSON json, String expression, T defaultValue) {
        return cn.hutool.json.JSONUtil.getByPath(json, expression, defaultValue);
    }

    /**
     * 设置表达式指定位置（或filed对应）的值<br>
     * 若表达式指向一个JSONArray则设置其坐标对应位置的值，若指向JSONObject则put对应key的值<br>
     * 注意：如果为JSONArray，则设置值得下标不能大于已有JSONArray的长度<br>
     * <ol>
     * <li>.表达式，可以获取Bean对象中的属性（字段）值或者Map中key对应的值</li>
     * <li>[]表达式，可以获取集合等对象中对应index的值</li>
     * </ol>
     * <p>
     * 表达式栗子：
     *
     * <pre>
     * persion
     * persion.name
     * persons[3]
     * person.friends[5].name
     * </pre>
     *
     * @param json       JSON，可以为JSONObject或JSONArray
     * @param expression 表达式
     * @param value      值
     */
    public static void putByPath(JSON json, String expression, Object value) {
        cn.hutool.json.JSONUtil.putByPath(json, expression, value);
    }

    /**
     * 对所有双引号做转义处理（使用双反斜杠做转义）<br>
     * 为了能在HTML中较好的显示，会将&lt;/转义为&lt;\/<br>
     * JSON字符串中不能包含控制字符和未经转义的引号和反斜杠
     *
     * @param string 字符串
     * @return 适合在JSON中显示的字符串
     */
    public static String quote(String string) {
        return cn.hutool.json.JSONUtil.quote(string);
    }

    /**
     * 对所有双引号做转义处理（使用双反斜杠做转义）<br>
     * 为了能在HTML中较好的显示，会将&lt;/转义为&lt;\/<br>
     * JSON字符串中不能包含控制字符和未经转义的引号和反斜杠
     *
     * @param string 字符串
     * @param isWrap 是否使用双引号包装字符串
     * @return 适合在JSON中显示的字符串
     * @since 3.3.1
     */
    public static String quote(String string, boolean isWrap) {
        return cn.hutool.json.JSONUtil.quote(string,isWrap);
    }

    /**
     * 对所有双引号做转义处理（使用双反斜杠做转义）<br>
     * 为了能在HTML中较好的显示，会将&lt;/转义为&lt;\/<br>
     * JSON字符串中不能包含控制字符和未经转义的引号和反斜杠
     *
     * @param str    字符串
     * @param writer Writer
     * @return Writer
     * @throws IOException IO异常
     */
    public static Writer quote(String str, Writer writer) throws IOException {
        return cn.hutool.json.JSONUtil.quote(str,writer);
    }

    /**
     * 对所有双引号做转义处理（使用双反斜杠做转义）<br>
     * 为了能在HTML中较好的显示，会将&lt;/转义为&lt;\/<br>
     * JSON字符串中不能包含控制字符和未经转义的引号和反斜杠
     *
     * @param str    字符串
     * @param writer Writer
     * @param isWrap 是否使用双引号包装字符串
     * @return Writer
     * @throws IOException IO异常
     * @since 3.3.1
     */
    public static Writer quote(String str, Writer writer, boolean isWrap) throws IOException {
        return cn.hutool.json.JSONUtil.quote(str,writer,isWrap);
    }

    /**
     * 转义显示不可见字符
     *
     * @param str 字符串
     * @return 转义后的字符串
     */
    public static String escape(String str) {
        return cn.hutool.json.JSONUtil.escape(str);
    }

    /**
     * 在需要的时候包装对象<br>
     * 包装包括：
     * <ul>
     * <li>{@code null} =》 {@code JSONNull.NULL}</li>
     * <li>array or collection =》 JSONArray</li>
     * <li>map =》 JSONObject</li>
     * <li>standard property (Double, String, et al) =》 原对象</li>
     * <li>来自于java包 =》 字符串</li>
     * <li>其它 =》 尝试包装为JSONObject，否则返回{@code null}</li>
     * </ul>
     *
     * @param object     被包装的对象
     * @param jsonConfig JSON选项
     * @return 包装后的值，null表示此值需被忽略
     */
    public static Object wrap(Object object, JSONConfig jsonConfig) {
        return cn.hutool.json.JSONUtil.wrap(object,jsonConfig);
    }

    /**
     * 格式化JSON字符串，此方法并不严格检查JSON的格式正确与否
     *
     * @param jsonStr JSON字符串
     * @return 格式化后的字符串
     * @since 3.1.2
     */
    public static String formatJsonStr(String jsonStr) {
        return cn.hutool.json.JSONUtil.formatJsonStr(jsonStr);
    }

    /**
     * 是否为JSON字符串，首尾都为大括号或中括号判定为JSON字符串
     *
     * @param str 字符串
     * @return 是否为JSON字符串
     * @since 3.3.0
     * @deprecated 方法名称有歧义，请使用 {@link #isTypeJSON(String)}
     */
    @Deprecated
    public static boolean isJson(String str) {
        return cn.hutool.json.JSONUtil.isJson(str);
    }

    /**
     * 是否为JSON类型字符串，首尾都为大括号或中括号判定为JSON字符串
     *
     * @param str 字符串
     * @return 是否为JSON类型字符串
     * @since 5.7.22
     */
    public static boolean isTypeJSON(String str) {
        return cn.hutool.json.JSONUtil.isTypeJSON(str);
    }

    /**
     * 是否为JSONObject字符串，首尾都为大括号判定为JSONObject字符串
     *
     * @param str 字符串
     * @return 是否为JSON字符串
     * @since 3.3.0
     * @deprecated 方法名称有歧义，请使用 {@link #isTypeJSONObject(String)}
     */
    @Deprecated
    public static boolean isJsonObj(String str) {
        return cn.hutool.json.JSONUtil.isJsonObj(str);
    }

    /**
     * 是否为JSONObject类型字符串，首尾都为大括号判定为JSONObject字符串
     *
     * @param str 字符串
     * @return 是否为JSON字符串
     * @since 5.7.22
     */
    public static boolean isTypeJSONObject(String str) {
        return cn.hutool.json.JSONUtil.isTypeJSONObject(str);
    }

    /**
     * 是否为JSONArray字符串，首尾都为中括号判定为JSONArray字符串
     *
     * @param str 字符串
     * @return 是否为JSON字符串
     * @since 3.3.0
     * @deprecated 方法名称有歧义，请使用 {@link #isTypeJSONArray(String)}
     */
    @Deprecated
    public static boolean isJsonArray(String str) {
        return cn.hutool.json.JSONUtil.isJsonArray(str);
    }

    /**
     * 是否为JSONArray类型的字符串，首尾都为中括号判定为JSONArray字符串
     *
     * @param str 字符串
     * @return 是否为JSONArray类型字符串
     * @since 5.7.22
     */
    public static boolean isTypeJSONArray(String str) {
        return cn.hutool.json.JSONUtil.isTypeJSONArray(str);
    }

    /**
     * 是否为null对象，null的情况包括：
     *
     * <pre>
     * 1. {@code null}
     * 2. {@link JSONNull}
     * </pre>
     *
     * @param obj 对象
     * @return 是否为null
     * @since 4.5.7
     */
    public static boolean isNull(Object obj) {
        return cn.hutool.json.JSONUtil.isNull(obj);
    }

    /**
     * XML转JSONObject<br>
     * 转换过程中一些信息可能会丢失，JSON中无法区分节点和属性，相同的节点将被处理为JSONArray。
     *
     * @param xml XML字符串
     * @return JSONObject
     * @since 4.0.8
     */
    public static JSONObject xmlToJson(String xml) {
        return cn.hutool.json.JSONUtil.xmlToJson(xml);
    }

    /**
     * 加入自定义的序列化器
     *
     * @param type       对象类型
     * @param serializer 序列化器实现
     * @see GlobalSerializeMapping#put(Type, JSONArraySerializer)
     * @since 4.6.5
     */
    public static void putSerializer(Type type, JSONArraySerializer<?> serializer) {
         cn.hutool.json.JSONUtil.putSerializer(type, serializer);
    }

    /**
     * 加入自定义的序列化器
     *
     * @param type       对象类型
     * @param serializer 序列化器实现
     * @see GlobalSerializeMapping#put(Type, JSONObjectSerializer)
     * @since 4.6.5
     */
    public static void putSerializer(Type type, JSONObjectSerializer<?> serializer) {
        cn.hutool.json.JSONUtil.putSerializer(type, serializer);
    }

    /**
     * 加入自定义的反序列化器
     *
     * @param type         对象类型
     * @param deserializer 反序列化器实现
     * @see GlobalSerializeMapping#put(Type, JSONDeserializer)
     * @since 4.6.5
     */
    public static void putDeserializer(Type type, JSONDeserializer<?> deserializer) {
        cn.hutool.json.JSONUtil.putDeserializer(type, deserializer);
    }

}
