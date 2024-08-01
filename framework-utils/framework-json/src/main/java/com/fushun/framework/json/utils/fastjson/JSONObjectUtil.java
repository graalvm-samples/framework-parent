package com.fushun.framework.json.utils.fastjson;

import com.alibaba.fastjson2.JSONException;
import com.alibaba.fastjson2.JSONReader;
import com.alibaba.fastjson2.TypeReference;
import com.fushun.framework.json.config.JsonGraalVMNativeBean;

import java.lang.reflect.Type;
import java.util.List;
import java.util.function.Function;

public class JSONObjectUtil {

    public static  <T extends JsonGraalVMNativeBean> List<T> getList(com.alibaba.fastjson2.JSONObject jsonObject,String key, Class<T> itemClass, JSONReader.Feature... features) {
        return jsonObject.getList(key,itemClass,features);
    }


    /**
     * @since 2.0.4
     */
    public static <T extends JsonGraalVMNativeBean> T to(com.alibaba.fastjson2.JSONObject jsonObject,Function<com.alibaba.fastjson2.JSONObject, T> function) {
        return function.apply(jsonObject);
    }

    /**
     * Convert this {@link com.alibaba.fastjson2.JSONObject} to the specified Object
     *
     * <pre>{@code
     * JSONObject obj = ...
     * Map<String, User> users = obj.to(new TypeReference<HashMap<String, User>>(){}.getType());
     * }</pre>
     *
     * @param type specify the {@link Type} to be converted
     * @param features features to be enabled in parsing
     * @since 2.0.4
     */
    @SuppressWarnings("unchecked")
    public static <T extends JsonGraalVMNativeBean> T to(com.alibaba.fastjson2.JSONObject jsonObject,Type type, JSONReader.Feature... features) {
        return jsonObject.to(type,features);
    }

    /**
     * Convert this {@link com.alibaba.fastjson2.JSONObject} to the specified Object
     *
     * <pre>{@code
     * JSONObject obj = ...
     * Map<String, User> users = obj.to(new TypeReference<HashMap<String, User>>(){});
     * }</pre>
     *
     * @param typeReference specify the {@link TypeReference} to be converted
     * @param features features to be enabled in parsing
     * @since 2.0.7
     */
    public static <T extends JsonGraalVMNativeBean> T to(com.alibaba.fastjson2.JSONObject jsonObject,TypeReference<T> typeReference, JSONReader.Feature... features) {
        return jsonObject.to(typeReference,features);
    }

    /**
     * Convert this {@link com.alibaba.fastjson2.JSONObject} to the specified Object
     *
     * <pre>{@code
     * JSONObject obj = ...
     * User user = obj.to(User.class);
     * }</pre>
     *
     * @param clazz specify the {@code Class<T>} to be converted
     * @param features features to be enabled in parsing
     * @since 2.0.4
     */
    @SuppressWarnings("unchecked")
    public static <T extends JsonGraalVMNativeBean> T to(com.alibaba.fastjson2.JSONObject jsonObject, Class<T> clazz, JSONReader.Feature... features) {
        return jsonObject.to(clazz,features);
    }

    /**
     * Convert this {@link com.alibaba.fastjson2.JSONObject} to the specified Object
     *
     * @param clazz specify the {@code Class<T>} to be converted
     * @param features features to be enabled in parsing
     */
    public static <T> T toJavaObject(com.alibaba.fastjson2.JSONObject jsonObject,Class<T> clazz, JSONReader.Feature... features) {
        return jsonObject.toJavaObject(clazz,features);
    }

    /**
     * Convert this {@link com.alibaba.fastjson2.JSONObject} to the specified Object
     *
     * @param type specify the {@link Type} to be converted
     * @param features features to be enabled in parsing
     * @deprecated since 2.0.4, please use {@link #to(Type, JSONReader.Feature...)}
     */
    public <T extends JsonGraalVMNativeBean> T toJavaObject(com.alibaba.fastjson2.JSONObject jsonObject,Type type, JSONReader.Feature... features) {
        return jsonObject.toJavaObject(type,features);
    }

    /**
     * Convert this {@link com.alibaba.fastjson2.JSONObject} to the specified Object
     *
     * @param typeReference specify the {@link TypeReference} to be converted
     * @param features features to be enabled in parsing
     * @deprecated since 2.0.4, please use {@link #to(Type, JSONReader.Feature...)}
     */
    public static <T extends JsonGraalVMNativeBean> T toJavaObject(com.alibaba.fastjson2.JSONObject jsonObject,TypeReference<T> typeReference, JSONReader.Feature... features) {
        return jsonObject.toJavaObject(typeReference,features);
    }

    /**
     * Returns the result of the {@link Type} converter conversion of the associated value in this {@link com.alibaba.fastjson2.JSONObject}.
     * <p>
     * {@code User user = jsonObject.getObject("user", User.class);}
     *
     * @param key the key whose associated value is to be returned
     * @param type specify the {@link Class} to be converted
     * @return {@code <T>} or null
     * @throws JSONException If no suitable conversion method is found
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public static <T extends JsonGraalVMNativeBean> T getObject(com.alibaba.fastjson2.JSONObject jsonObject,String key, Class<T> type, JSONReader.Feature... features) {
        return jsonObject.getObject(key,type,features);
    }

    /**
     * Returns the result of the {@link Type} converter conversion of the associated value in this {@link com.alibaba.fastjson2.JSONObject}.
     * <p>
     * {@code User user = jsonObject.getObject("user", User.class);}
     *
     * @param key the key whose associated value is to be returned
     * @param type specify the {@link Type} to be converted
     * @param features features to be enabled in parsing
     * @return {@code <T>} or {@code null}
     * @throws JSONException If no suitable conversion method is found
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public static <T extends JsonGraalVMNativeBean> T getObject(com.alibaba.fastjson2.JSONObject jsonObject,String key, Type type, JSONReader.Feature... features) {
        return jsonObject.getObject(key,type,features);
    }

    /**
     * Returns the result of the {@link Type} converter conversion of the associated value in this {@link com.alibaba.fastjson2.JSONObject}.
     * <p>
     * {@code User user = jsonObject.getObject("user", User.class);}
     *
     * @param key the key whose associated value is to be returned
     * @param typeReference specify the {@link TypeReference} to be converted
     * @param features features to be enabled in parsing
     * @return {@code <T>} or {@code null}
     * @throws JSONException If no suitable conversion method is found
     * @since 2.0.3
     */
    public static <T extends JsonGraalVMNativeBean> T getObject(com.alibaba.fastjson2.JSONObject jsonObject,String key, TypeReference<T> typeReference, JSONReader.Feature... features) {
        return jsonObject.getObject(key,typeReference,features);
    }

    /**
     * @since 2.0.4
     */
    public static <T extends JsonGraalVMNativeBean> T getObject(com.alibaba.fastjson2.JSONObject jsonObject,String key, Function<com.alibaba.fastjson2.JSONObject, T> creator) {
        return jsonObject.getObject(key,creator);
    }

    public static <T extends JsonGraalVMNativeBean> T parseObject(String text, Class<T> objectClass) {
        return com.alibaba.fastjson2.JSONObject.parseObject(text, objectClass);
    }


    public static <T extends JsonGraalVMNativeBean> T parseObject(String text, Class<T> objectClass, JSONReader.Feature... features) {
        return com.alibaba.fastjson2.JSONObject.parseObject(text, objectClass, features);
    }


    public static <T extends JsonGraalVMNativeBean> T parseObject(String text, Type objectType, JSONReader.Feature... features) {
        return com.alibaba.fastjson2.JSONObject.parseObject(text, objectType, features);
    }


    public static <T extends JsonGraalVMNativeBean> T parseObject(String text, TypeReference<T> typeReference, JSONReader.Feature... features) {
        return com.alibaba.fastjson2.JSONObject.parseObject(text, typeReference, features);
    }


}
