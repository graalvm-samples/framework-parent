package com.fushun.framework.json.utils.fastjson;

import com.alibaba.fastjson2.JSONException;
import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.JSONReader;
import com.alibaba.fastjson2.TypeReference;
import com.alibaba.fastjson2.filter.Filter;
import com.alibaba.fastjson2.util.MultiType;
import com.fushun.framework.json.config.JsonGraalVMNativeBean;

import java.io.InputStream;
import java.io.Reader;
import java.lang.reflect.Type;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.function.Function;

public interface JSON extends com.alibaba.fastjson2.JSON {


    /**
     * Parses the json string as a {@link JSONObject}. Returns {@code null}
     * if received {@link String} is {@code null} or empty or its content is null.
     *
     * @param text the specified string to be parsed
     * @return {@link JSONObject} or {@code null}
     * @throws JSONException If a parsing error occurs
     */
    static JSONObject parseObject(String text) {
        return com.alibaba.fastjson2.JSON.parseObject(text);
    }

    /**
     * Parses the json string as a {@link JSONObject}. Returns {@code null}
     * if received {@link String} is {@code null} or empty or its content is null.
     *
     * @param text the specified string to be parsed
     * @param features the specified features is applied to parsing
     * @return {@link JSONObject} or {@code null}
     * @throws JSONException If a parsing error occurs
     */
    static JSONObject parseObject(String text, JSONReader.Feature... features) {
        return com.alibaba.fastjson2.JSON.parseObject(text,features);
    }

    /**
     * Parses the json string as a {@link JSONObject}. Returns {@code null} if received
     * {@link String} is {@code null} or empty or length is 0 or its content is null.
     *
     * @param text the specified text to be parsed
     * @param offset the starting index of string
     * @param length the specified length of string
     * @param features the specified features is applied to parsing
     * @return {@link JSONObject} or {@code null}
     * @throws JSONException If a parsing error occurs
     */
    static JSONObject parseObject(String text, int offset, int length, JSONReader.Feature... features) {
        return com.alibaba.fastjson2.JSON.parseObject(text,offset,length,features);
    }

    /**
     * Parses the json string as a {@link JSONObject}. Returns {@code null} if received
     * {@link String} is {@code null} or empty or length is 0 or its content is null.
     *
     * @param text the specified text to be parsed
     * @param offset the starting index of string
     * @param length the specified length of string
     * @param context the specified custom context
     * @return {@link JSONObject} or {@code null}
     * @throws JSONException If a parsing error occurs
     * @throws NullPointerException If received context is null
     * @since 2.0.30
     */
    static JSONObject parseObject(String text, int offset, int length, JSONReader.Context context) {
        return com.alibaba.fastjson2.JSON.parseObject(text, offset,length,context);
    }

    /**
     * Parses the json string as a {@link JSONObject}. Returns {@code null} if
     * received {@link String} is {@code null} or empty or its content is null.
     *
     * @param text the specified string to be parsed
     * @param context the specified custom context
     * @return {@link JSONObject} or {@code null}
     * @throws JSONException If a parsing error occurs
     * @throws NullPointerException If received context is null
     */
    static JSONObject parseObject(String text, JSONReader.Context context) {
        return com.alibaba.fastjson2.JSON.parseObject(text,context);
    }

    /**
     * Parses the json reader as a {@link JSONObject}. Returns {@code null}
     * if received {@link Reader} is {@code null} or its content is null.
     *
     * @param input the specified reader to be parsed
     * @param features the specified features is applied to parsing
     * @return {@link JSONObject} or {@code null}
     * @throws JSONException If a parsing error occurs
     */
    static JSONObject parseObject(Reader input, JSONReader.Feature... features) {
        return com.alibaba.fastjson2.JSON.parseObject(input,features);
    }

    /**
     * Parses the json stream as a {@link JSONObject}. Returns {@code null} if
     * received {@link InputStream} is {@code null} or closed or its content is null.
     *
     * @param input the specified stream to be parsed
     * @param features the specified features is applied to parsing
     * @return {@link JSONObject} or {@code null}
     * @throws JSONException If a parsing error occurs
     */
    static JSONObject parseObject(InputStream input, JSONReader.Feature... features) {
        return com.alibaba.fastjson2.JSON.parseObject(input,features);
    }

    /**
     * Parses the json byte array as a {@link JSONObject}. Returns {@code null}
     * if received byte array is {@code null} or empty or its content is null.
     *
     * @param bytes the specified UTF8 text to be parsed
     * @return {@link JSONObject} or {@code null}
     * @throws JSONException If a parsing error occurs
     */
    static JSONObject parseObject(byte[] bytes) {
        return com.alibaba.fastjson2.JSON.parseObject(bytes);
    }

    /**
     * Parses the json char array as a {@link JSONObject}. Returns {@code null}
     * if received char array is {@code null} or empty or its content is null.
     *
     * @param chars the specified char array to be parsed
     * @return {@link JSONObject} or {@code null}
     * @throws JSONException If a parsing error occurs
     */
    static JSONObject parseObject(char[] chars) {
        return com.alibaba.fastjson2.JSON.parseObject(chars);
    }

    /**
     * Parses the json stream as a {@link JSONObject}. Returns {@code null}
     * if received {@link InputStream} is {@code null} or its content is null.
     *
     * @param in the specified stream to be parsed
     * @param charset the specified charset of the stream
     * @return {@link JSONObject} or {@code null}
     * @throws JSONException If a parsing error occurs
     */
    static JSONObject parseObject(InputStream in, Charset charset) {
        return com.alibaba.fastjson2.JSON.parseObject(in,charset);
    }

    /**
     * Parses the json stream of the url as a {@link JSONObject}.
     * Returns {@code null} if received {@link URL} is {@code null}.
     *
     * @param url the specified url to be parsed
     * @return {@link JSONObject} or {@code null}
     * @throws JSONException If an I/O error or parsing error occurs
     * @see URL#openStream()
     * @see com.alibaba.fastjson2.JSON#parseObject(InputStream, Charset)
     */
    static JSONObject parseObject(URL url) {
        return com.alibaba.fastjson2.JSON.parseObject(url);
    }

    /**
     * Parses the json byte array as a {@link JSONObject}. Returns {@code null}
     * if received byte array is {@code null} or empty or its content is null.
     *
     * @param bytes the specified UTF8 text to be parsed
     * @param features the specified features is applied to parsing
     * @return {@link JSONObject} or {@code null}
     * @throws JSONException If a parsing error occurs
     */
    static JSONObject parseObject(byte[] bytes, JSONReader.Feature... features) {
        return com.alibaba.fastjson2.JSON.parseObject(bytes,features);
    }

    /**
     * Parses the json byte array as a {@link JSONObject}. Returns {@code null} if
     * received byte array is {@code null} or empty or length is 0 or its content is null.
     *
     * @param bytes the specified UTF8 text to be parsed
     * @param offset the starting index of array
     * @param length the specified length of array
     * @param features the specified features is applied to parsing
     * @return {@link JSONObject} or {@code null}
     * @throws JSONException If a parsing error occurs
     */
    static JSONObject parseObject(byte[] bytes, int offset, int length, JSONReader.Feature... features) {
        return com.alibaba.fastjson2.JSON.parseObject(bytes, offset, length, features);
    }

    /**
     * Parses the json chars array as a {@link JSONObject}. Returns {@code null} if
     * received chars array is {@code null} or empty or length is 0 or its content is null.
     *
     * @param chars the specified chars array to be parsed
     * @param offset the starting index of array
     * @param length the specified length of array
     * @param features the specified features is applied to parsing
     * @return {@link JSONObject} or {@code null}
     * @throws JSONException If a parsing error occurs
     */
    static JSONObject parseObject(char[] chars, int offset, int length, JSONReader.Feature... features) {
        return com.alibaba.fastjson2.JSON.parseObject(chars, offset, length, features);
    }

    /**
     * Parses the json byte array as a {@link JSONObject}. Returns {@code null} if
     * received byte array is {@code null} or empty or length is 0 or its content is null.
     *
     * @param bytes the specified UTF8 text to be parsed
     * @param offset the starting index of array
     * @param length the specified length of array
     * @param charset the specified charset of the stream
     * @param features the specified features is applied to parsing
     * @return {@link JSONObject} or {@code null}
     * @throws JSONException If a parsing error occurs
     * @see com.alibaba.fastjson2.JSON#parseObject(byte[], int, int, JSONReader.Feature...)
     */
    static JSONObject parseObject(
            byte[] bytes,
            int offset,
            int length,
            Charset charset,
            JSONReader.Feature... features
    ) {
        return com.alibaba.fastjson2.JSON.parseObject(bytes, offset, length, charset, features);
    }

    static <T extends JsonGraalVMNativeBean> T parseObject(String text, Class<T> clazz) {
       return com.alibaba.fastjson2.JSON.parseObject(text,clazz);
    }

    /**
     * Parses the json string as {@link T}. Returns {@code null}
     * if received {@link String} is {@code null} or empty or its content is null.
     *
     * @param text the specified string to be parsed
     * @param clazz the specified class of {@link T}
     * @param filter the specified filter is applied to parsing
     * @param features the specified features is applied to parsing
     * @return {@link T} or {@code null}
     * @throws JSONException If a parsing error occurs
     */
    @SuppressWarnings("unchecked")
    static <T extends JsonGraalVMNativeBean> T parseObject(
            String text,
            Class<T> clazz,
            Filter filter,
            JSONReader.Feature... features
    ) {
        return com.alibaba.fastjson2.JSON.parseObject(text,clazz,filter,features);
    }


//    /**
//     * Parses the json string as {@link T}. Returns {@code null}
//     * if received {@link String} is {@code null} or empty or its content is null.
//     *
//     * @param text the specified string to be parsed
//     * @param type the specified actual type of {@link T}
//     * @param format the specified date format
//     * @param filters the specified filters is applied to parsing
//     * @param features the specified features is applied to parsing
//     * @return {@link T} or {@code null}
//     * @throws JSONException If a parsing error occurs
//     */
//    @SuppressWarnings("unchecked")
//    static <T extends JsonGraalVMNativeBean> T parseObject(
//            String text,
//            Type type,
//            String format,
//            Filter[] filters,
//            JSONReader.Feature... features
//    ) {
//        return com.alibaba.fastjson2.JSON.parseObject( text,
//                 type,
//                 format,
//                 filters,
//                features);
//    }

//    /**
//     * Parses the json string as {@link T}. Returns
//     * {@code null} if received {@link String} is {@code null} or empty.
//     *
//     * @param text the specified string to be parsed
//     * @param type the specified actual type of {@link T}
//     * @return {@link T} or {@code null}
//     * @throws JSONException If a parsing error occurs
//     */
//    static <T extends JsonGraalVMNativeBean> T parseObject(String text, Type type) {
//        return com.alibaba.fastjson2.JSON.parseObject(text,type);
//    }


    /**
     * Parses the json string as {@link T}. Returns
     * {@code null} if received {@link String} is {@code null} or empty.
     *
     * @param text the specified string to be parsed
     * @param types the specified actual parameter types
     * @return {@link T} or {@code null}
     * @throws JSONException If a parsing error occurs
     * @see MultiType
     * @see com.alibaba.fastjson2.JSON#parseObject(String, Type)
     */
//    static <T extends JsonGraalVMNativeBean> T parseObject(String text, Type... types) {
//        return com.alibaba.fastjson2.JSON.parseObject(text,types);
//    }

    /**
     * Parses the json string as {@link T}. Returns
     * {@code null} if received {@link String} is {@code null} or empty.
     *
     * @param text the specified string to be parsed
     * @param typeReference the specified actual type
     * @param features the specified features is applied to parsing
     * @return {@link T} or {@code null}
     * @throws JSONException If a parsing error occurs
     */
    @SuppressWarnings("unchecked")
    static <T extends JsonGraalVMNativeBean> T parseObject(String text, TypeReference<T> typeReference, JSONReader.Feature... features) {
        return com.alibaba.fastjson2.JSON.parseObject(text,typeReference,features);
    }

    /**
     * Parses the json string as {@link T}. Returns
     * {@code null} if received {@link String} is {@code null} or empty.
     *
     * @param text the specified string to be parsed
     * @param typeReference the specified actual type
     * @param filter the specified filter is applied to parsing
     * @param features the specified features is applied to parsing
     * @return {@link T} or {@code null}
     * @throws JSONException If a parsing error occurs
     */
    @SuppressWarnings("unchecked")
    static <T extends JsonGraalVMNativeBean> T parseObject(
            String text,
            TypeReference<T> typeReference,
            Filter filter,
            JSONReader.Feature... features
    ) {
        return com.alibaba.fastjson2.JSON.parseObject(text,typeReference, filter,features);
    }

    /**
     * Parses the json string as {@link T}. Returns
     * {@code null} if received {@link String} is {@code null} or empty.
     *
     * @param text the specified string to be parsed
     * @param clazz the specified class of {@link T}
     * @param features the specified features is applied to parsing
     * @return {@link T} or {@code null}
     * @throws JSONException If a parsing error occurs
     */
    @SuppressWarnings("unchecked")
    static <T extends JsonGraalVMNativeBean> T parseObject(String text, Class<T> clazz, JSONReader.Feature... features) {
        return com.alibaba.fastjson2.JSON.parseObject(text,clazz,features);
    }

    /**
     * Parses the json string as {@link T}. Returns {@code null}
     * if received {@link String} is {@code null} or empty or length is 0.
     *
     * @param text the specified string to be parsed
     * @param offset the starting index of string
     * @param length the specified length of string
     * @param clazz the specified class of {@link T}
     * @param features the specified features is applied to parsing
     * @return {@link T} or {@code null}
     * @throws JSONException If a parsing error occurs
     */
    @SuppressWarnings("unchecked")
    static <T extends JsonGraalVMNativeBean> T parseObject(String text, int offset, int length, Class<T> clazz, JSONReader.Feature... features) {
        return com.alibaba.fastjson2.JSON.parseObject(text,offset,length,clazz,features);
    }

    /**
     * Parses the json string as {@link T}. Returns
     * {@code null} if received {@link String} is {@code null} or empty.
     *
     * @param text the specified string to be parsed
     * @param clazz the specified class of {@link T}
     * @param context the specified custom context
     * @return {@link T} or {@code null}
     * @throws JSONException If a parsing error occurs
     * @throws NullPointerException If received context is null
     */
    @SuppressWarnings("unchecked")
    static <T extends JsonGraalVMNativeBean> T parseObject(String text, Class<T> clazz, JSONReader.Context context) {
        return com.alibaba.fastjson2.JSON.parseObject(text,clazz,context);
    }

    /**
     * Parses the json string as {@link T}. Returns
     * {@code null} if received {@link String} is {@code null} or empty.
     *
     * @param text the specified string to be parsed
     * @param clazz the specified class of {@link T}
     * @param format the specified date format
     * @param features the specified features is applied to parsing
     * @return {@link T} or {@code null}
     * @throws JSONException If a parsing error occurs
     */
    @SuppressWarnings("unchecked")
    static <T extends JsonGraalVMNativeBean> T parseObject(String text, Class<T> clazz, String format, JSONReader.Feature... features) {
        return com.alibaba.fastjson2.JSON.parseObject(text,clazz,format,features);
    }

//    /**
//     * Parses the json string as {@link T}. Returns
//     * {@code null} if received {@link String} is {@code null} or empty.
//     *
//     * @param text the specified string to be parsed
//     * @param type the specified actual type
//     * @param features the specified features is applied to parsing
//     * @return {@link T} or {@code null}
//     * @throws JSONException If a parsing error occurs
//     */
//    @SuppressWarnings("unchecked")
//    static <T extends JsonGraalVMNativeBean> T parseObject(String text, Type type, JSONReader.Feature... features) {
//        return com.alibaba.fastjson2.JSON.parseObject(text,type,features);
//    }

//    /**
//     * Parses the json string as {@link T}. Returns
//     * {@code null} if received {@link String} is {@code null} or empty.
//     *
//     * @param text the specified string to be parsed
//     * @param type the specified actual type
//     * @param filter the specified filter is applied to parsing
//     * @param features the specified features is applied to parsing
//     * @return {@link T} or {@code null}
//     * @throws JSONException If a parsing error occurs
//     */
//    @SuppressWarnings("unchecked")
//    static <T extends JsonGraalVMNativeBean> T parseObject(String text, Type type, Filter filter, JSONReader.Feature... features) {
//        return com.alibaba.fastjson2.JSON.parseObject(text,type,filter,features);
//    }

//    /**
//     * Parses the json string as {@link T}. Returns
//     * {@code null} if received {@link String} is {@code null} or empty.
//     *
//     * @param text the specified string to be parsed
//     * @param type the specified actual type
//     * @param format the specified date format
//     * @param features the specified features is applied to parsing
//     * @return {@link T} or {@code null}
//     * @throws JSONException If a parsing error occurs
//     */
//    @SuppressWarnings("unchecked")
//    static <T extends JsonGraalVMNativeBean> T parseObject(String text, Type type, String format, JSONReader.Feature... features) {
//        return com.alibaba.fastjson2.JSON.parseObject(text,type,format,features);
//    }

//    /**
//     * Parses the json char array as {@link T}. Returns {@code null}
//     * if received char array is {@code null} or empty or length is 0.
//     *
//     * @param chars the specified char array to be parsed
//     * @param type the specified actual type
//     * @param offset the starting index of array
//     * @param length the specified length of array
//     * @param features the specified features is applied to parsing
//     * @return {@link T} or {@code null}
//     * @throws JSONException If a parsing error occurs
//     * @since 2.0.13
//     */
//    @SuppressWarnings("unchecked")
//    static <T extends JsonGraalVMNativeBean> T parseObject(char[] chars, int offset, int length, Type type, JSONReader.Feature... features) {
//        return com.alibaba.fastjson2.JSON.parseObject(chars,offset,length,type,features);
//    }

    /**
     * Parses the json char array as {@link T}. Returns
     * {@code null} if received char array is {@code null} or empty.
     *
     * @param chars the specified char array to be parsed
     * @param clazz the specified class of {@link T}
     * @return {@link T} or {@code null}
     * @throws JSONException If a parsing error occurs
     */
    @SuppressWarnings("unchecked")
    static <T extends JsonGraalVMNativeBean> T parseObject(char[] chars, Class<T> clazz) {
        return com.alibaba.fastjson2.JSON.parseObject(chars,clazz);
    }

//    /**
//     * Parses the json byte array as {@link T}. Returns {@code null}
//     * if received byte array is {@code null} or empty or length is 0.
//     *
//     * @param bytes the specified UTF8 text to be parsed
//     * @param offset the starting index of array
//     * @param length the specified length of array
//     * @param type the specified actual type
//     * @param features the specified features is applied to parsing
//     * @return {@link T} or {@code null}
//     * @throws JSONException If a parsing error occurs
//     * @since 2.0.13
//     */
//    @SuppressWarnings("unchecked")
//    static <T extends JsonGraalVMNativeBean> T parseObject(byte[] bytes, int offset, int length, Type type, JSONReader.Feature... features) {
//        return com.alibaba.fastjson2.JSON.parseObject(bytes,offset,length,type, features);
//    }

//    /**
//     * Parses the json byte array as {@link T}. Returns
//     * {@code null} if received byte array is {@code null} or empty.
//     *
//     * @param bytes the specified UTF8 text to be parsed
//     * @param type the specified actual type of {@link T}
//     * @return {@link T} or {@code null}
//     * @throws JSONException If a parsing error occurs
//     */
//    @SuppressWarnings("unchecked")
//    static <T extends JsonGraalVMNativeBean> T parseObject(byte[] bytes, Type type) {
//        return com.alibaba.fastjson2.JSON.parseObject(bytes,type);
//    }

    /**
     * Parses the json byte array as {@link T}. Returns
     * {@code null} if received byte array is {@code null} or empty.
     *
     * @param bytes the specified UTF8 text to be parsed
     * @param clazz the specified class of {@link T}
     * @return {@link T} or {@code null}
     * @throws JSONException If a parsing error occurs
     */
    @SuppressWarnings("unchecked")
    static <T extends JsonGraalVMNativeBean> T parseObject(byte[] bytes, Class<T> clazz) {
        return com.alibaba.fastjson2.JSON.parseObject(bytes,clazz);
    }

    /**
     * Parses the json byte array as {@link T}. Returns
     * {@code null} if received byte array is {@code null} or empty.
     *
     * @param bytes the specified UTF8 text to be parsed
     * @param clazz the specified class of {@link T}
     * @param filter the specified filter is applied to parsing
     * @param features the specified features is applied to parsing
     * @return {@link T} or {@code null}
     * @throws JSONException If a parsing error occurs
     */
    @SuppressWarnings("unchecked")
    static <T extends JsonGraalVMNativeBean> T parseObject(
            byte[] bytes,
            Class<T> clazz,
            Filter filter,
            JSONReader.Feature... features
    ) {
        return com.alibaba.fastjson2.JSON.parseObject(bytes,clazz,filter,features);
    }

    /**
     * Parses the json byte array as {@link T}. Returns
     * {@code null} if received byte array is {@code null} or empty.
     *
     * @param bytes the specified UTF8 text to be parsed
     * @param clazz the specified class of {@link T}
     * @param context the specified custom context
     * @return {@link T} or {@code null}
     * @throws JSONException If a parsing error occurs
     * @throws NullPointerException If received context is null
     */
    @SuppressWarnings("unchecked")
    static <T extends JsonGraalVMNativeBean> T parseObject(
            byte[] bytes,
            Class<T> clazz,
            JSONReader.Context context
    ) {
        return com.alibaba.fastjson2.JSON.parseObject(bytes,clazz,context);
    }

//    /**
//     * Parses the json byte array as {@link T}. Returns
//     * {@code null} if received byte array is {@code null} or empty.
//     *
//     * @param bytes the specified UTF8 text to be parsed
//     * @param type the specified actual type
//     * @param format the specified date format
//     * @param filters the specified filters is applied to parsing
//     * @param features the specified features is applied to parsing
//     * @return {@link T} or {@code null}
//     * @throws JSONException If a parsing error occurs
//     */
//    @SuppressWarnings("unchecked")
//    static <T extends JsonGraalVMNativeBean> T parseObject(
//            byte[] bytes,
//            Type type,
//            String format,
//            Filter[] filters,
//            JSONReader.Feature... features
//    ) {
//        return com.alibaba.fastjson2.JSON.parseObject(bytes,type,format,filters,features);
//    }

    /**
     * Parses the json byte array as {@link T}. Returns
     * {@code null} if received byte array is {@code null} or empty.
     *
     * @param bytes the specified UTF8 text to be parsed
     * @param clazz the specified class of {@link T}
     * @param features the specified features is applied to parsing
     * @return {@link T} or {@code null}
     * @throws JSONException If a parsing error occurs
     */
    @SuppressWarnings("unchecked")
    static <T extends JsonGraalVMNativeBean> T parseObject(byte[] bytes, Class<T> clazz, JSONReader.Feature... features) {
        return com.alibaba.fastjson2.JSON.parseObject(bytes,clazz,features);
    }

//    /**
//     * Parses the json byte array as {@link T}. Returns
//     * {@code null} if received byte array is {@code null} or empty.
//     *
//     * @param bytes the specified UTF8 text to be parsed
//     * @param type the specified actual type of {@link T}
//     * @param features the specified features is applied to parsing
//     * @return {@link T} or {@code null}
//     * @throws JSONException If a parsing error occurs
//     */
//    @SuppressWarnings("unchecked")
//    static <T extends JsonGraalVMNativeBean> T parseObject(byte[] bytes, Type type, JSONReader.Feature... features) {
//        return com.alibaba.fastjson2.JSON.parseObject(bytes,type,features);
//    }

    /**
     * Parses the json byte array as {@link T}. Returns
     * {@code null} if received byte array is {@code null} or empty.
     *
     * @param chars the specified chars
     * @param objectClass the specified actual type of {@link T}
     * @param features the specified features is applied to parsing
     * @return {@link T} or {@code null}
     * @throws JSONException If a parsing error occurs
     */
    @SuppressWarnings("unchecked")
    static <T extends JsonGraalVMNativeBean> T parseObject(char[] chars, Class<T> objectClass, JSONReader.Feature... features) {
        return com.alibaba.fastjson2.JSON.parseObject(chars,objectClass,features);
    }

//    /**
//     * Parses the json byte array as {@link T}. Returns
//     * {@code null} if received byte array is {@code null} or empty.
//     *
//     * @param chars the specified chars
//     * @param type the specified actual type of {@link T}
//     * @param features the specified features is applied to parsing
//     * @return {@link T} or {@code null}
//     * @throws JSONException If a parsing error occurs
//     */
//    @SuppressWarnings("unchecked")
//    static <T extends JsonGraalVMNativeBean> T parseObject(char[] chars, Type type, JSONReader.Feature... features) {
//        return com.alibaba.fastjson2.JSON.parseObject(chars,type,features);
//    }

//    /**
//     * Parses the json byte array as {@link T}. Returns
//     * {@code null} if received byte array is {@code null} or empty.
//     *
//     * @param bytes the specified UTF8 text to be parsed
//     * @param type the specified actual type of {@link T}
//     * @param filter the specified filter is applied to parsing
//     * @param features the specified features is applied to parsing
//     * @return {@link T} or {@code null}
//     * @throws JSONException If a parsing error occurs
//     */
//    @SuppressWarnings("unchecked")
//    static <T extends JsonGraalVMNativeBean> T parseObject(byte[] bytes, Type type, Filter filter, JSONReader.Feature... features) {
//        return com.alibaba.fastjson2.JSON.parseObject(bytes,type,filter,features);
//    }

//    /**
//     * Parses the json byte array as {@link T}. Returns
//     * {@code null} if received byte array is {@code null} or empty.
//     *
//     * @param bytes the specified UTF8 text to be parsed
//     * @param type the specified actual type of {@link T}
//     * @param format the specified date format
//     * @param features the specified features is applied to parsing
//     * @return {@link T} or {@code null}
//     * @throws JSONException If a parsing error occurs
//     */
//    @SuppressWarnings("unchecked")
//    static <T extends JsonGraalVMNativeBean> T parseObject(byte[] bytes, Type type, String format, JSONReader.Feature... features) {
//        return com.alibaba.fastjson2.JSON.parseObject(bytes,type,format,features);
//    }

    /**
     * Parses the json byte buffer as a {@link T}. Returns
     * {@code null} if received {@link ByteBuffer} is {@code null}.
     *
     * @param buffer the specified buffer to be parsed
     * @param objectClass the specified class of {@link T}
     * @return {@link T} or {@code null}
     * @throws JSONException If a parsing error occurs
     */
    @SuppressWarnings("unchecked")
    static <T extends JsonGraalVMNativeBean> T parseObject(ByteBuffer buffer, Class<T> objectClass) {
        return com.alibaba.fastjson2.JSON.parseObject(buffer,objectClass);
    }

//    /**
//     * Parses the json reader as a {@link T}. Returns {@code null}
//     * if received {@link Reader} is {@code null} or its content is null.
//     *
//     * @param input the specified reader to be parsed
//     * @param type the specified actual type of {@link T}
//     * @param features the specified features is applied to parsing
//     * @return {@link T} or {@code null}
//     * @throws JSONException If a parsing error occurs
//     */
//    @SuppressWarnings("unchecked")
//    static <T extends JsonGraalVMNativeBean> T parseObject(Reader input, Type type, JSONReader.Feature... features) {
//        return com.alibaba.fastjson2.JSON.parseObject(input,type,features);
//    }

//    /**
//     * Parses the json stream as a {@link T}. Returns {@code null}
//     * if received {@link InputStream} is {@code null} or its content is null.
//     *
//     * @param input the specified stream to be parsed
//     * @param type the specified actual type of {@link T}
//     * @param features the specified features is applied to parsing
//     * @return {@link T} or {@code null}
//     * @throws JSONException If a parsing error occurs
//     */
//    @SuppressWarnings("unchecked")
//    static <T extends JsonGraalVMNativeBean> T parseObject(InputStream input, Type type, JSONReader.Feature... features) {
//        return com.alibaba.fastjson2.JSON.parseObject(input,type,features);
//    }

//    /**
//     * Parses the json stream as a {@link T}. Returns {@code null}
//     * if received {@link InputStream} is {@code null} or its content is null.
//     *
//     * @param input the specified stream to be parsed
//     * @param type the specified actual type of {@link T}
//     * @param context the specified custom context
//     * @return {@link T} or {@code null}
//     * @throws JSONException If a parsing error occurs
//     */
    @SuppressWarnings("unchecked")
//    static <T extends JsonGraalVMNativeBean> T parseObject(InputStream input, Charset charset, Type type, JSONReader.Context context) {
//        return com.alibaba.fastjson2.JSON.parseObject(input,charset,type,context);
//    }

//    /**
//     * Parses the json stream as a {@link T}. Returns {@code null}
//     * if received {@link InputStream} is {@code null} or its content is null.
//     *
//     * @param input the specified stream to be parsed
//     * @param type the specified actual type of {@link T}
//     * @param context the specified custom context
//     * @return {@link T} or {@code null}
//     * @throws JSONException If a parsing error occurs
//     */
//    @SuppressWarnings("unchecked")
//    static <T extends JsonGraalVMNativeBean> T parseObject(InputStream input, Charset charset, Class<T> type, JSONReader.Context context) {
//        return com.alibaba.fastjson2.JSON.parseObject(input,charset,type,context);
//    }

//    /**
//     * Parses the json stream of the url as {@link T}.
//     * Returns {@code null} if received {@link URL} is {@code null}.
//     *
//     * @param url the specified url to be parsed
//     * @param type the specified actual type of {@link T}
//     * @param features the specified features is applied to parsing
//     * @return {@link T} or {@code null}
//     * @throws JSONException If an I/O error or parsing error occurs
//     * @see URL#openStream()
//     * @see com.alibaba.fastjson2.JSON#parseObject(InputStream, Type, JSONReader.Feature...)
//     * @since 2.0.4
//     */
//    static <T extends JsonGraalVMNativeBean> T parseObject(URL url, Type type, JSONReader.Feature... features) {
//        return com.alibaba.fastjson2.JSON.parseObject(url,type,features);
//    }

    /**
     * Parses the json stream of the url as {@link T}.
     * Returns {@code null} if received {@link URL} is {@code null}.
     *
     * @param url the specified url to be parsed
     * @param objectClass the specified class of {@link T}
     * @param features the specified features is applied to parsing
     * @return {@link T} or {@code null}
     * @throws JSONException If an I/O error or parsing error occurs
     * @see URL#openStream()
     * @see com.alibaba.fastjson2.JSON#parseObject(InputStream, Type, JSONReader.Feature...)
     * @since 2.0.9
     */
    static <T extends JsonGraalVMNativeBean> T parseObject(URL url, Class<T> objectClass, JSONReader.Feature... features) {
        return com.alibaba.fastjson2.JSON.parseObject(url,objectClass,features);
    }

    /**
     * Parses the json stream of the url as a {@link JSONObject} and call the function
     * to convert it to {@link T}. Returns {@code null} if received {@link URL} is {@code null}.
     *
     * @param url the specified url to be parsed
     * @param function the specified converter
     * @param features the specified features is applied to parsing
     * @return {@link T} or {@code null}
     * @throws JSONException If an I/O error or parsing error occurs
     * @see URL#openStream()
     * @see com.alibaba.fastjson2.JSON#parseObject(InputStream, JSONReader.Feature...)
     * @since 2.0.4
     */
    static <T extends JsonGraalVMNativeBean> T parseObject(URL url, Function<JSONObject, T> function, JSONReader.Feature... features) {
        return com.alibaba.fastjson2.JSON.parseObject(url,function,features);
    }

//    /**
//     * Parses the json stream as a {@link T}. Returns {@code null}
//     * if received {@link InputStream} is {@code null} or its content is null.
//     *
//     * @param input the specified stream to be parsed
//     * @param type the specified actual type of {@link T}
//     * @param format the specified date format
//     * @param features the specified features is applied to parsing
//     * @return {@link T} or {@code null}
//     * @throws JSONException If a parsing error occurs
//     */
//    @SuppressWarnings("unchecked")
//    static <T extends JsonGraalVMNativeBean> T parseObject(InputStream input, Type type, String format, JSONReader.Feature... features) {
//        return com.alibaba.fastjson2.JSON.parseObject(input,type,format,features);
//    }

//    /**
//     * Parses the json stream as a {@link T}. Returns {@code null}
//     * if received {@link InputStream} is {@code null} or its content is null.
//     *
//     * @param input the specified stream to be parsed
//     * @param charset the specified charset of the stream
//     * @param type the specified actual type of {@link T}
//     * @param features the specified features is applied to parsing
//     * @return {@link T} or {@code null}
//     * @throws JSONException If a parsing error occurs
//     */
//    @SuppressWarnings("unchecked")
//    static <T extends JsonGraalVMNativeBean> T parseObject(InputStream input, Charset charset, Type type, JSONReader.Feature... features) {
//        return com.alibaba.fastjson2.JSON.parseObject(input,type,features);
//    }

//    /**
//     * Parses the json byte array as {@link T}. Returns {@code null}
//     * if received byte array is {@code null} or empty or length is 0.
//     *
//     * @param bytes the specified UTF8 text to be parsed
//     * @param offset the starting index of array
//     * @param length the specified length of array
//     * @param charset the specified charset of the stream
//     * @param type the specified actual type of {@link T}
//     * @return {@link T} or {@code null}
//     * @throws JSONException If a parsing error occurs
//     */
//    @SuppressWarnings("unchecked")
//    static <T extends JsonGraalVMNativeBean> T parseObject(byte[] bytes, int offset, int length, Charset charset, Type type) {
//        return com.alibaba.fastjson2.JSON.parseObject(bytes,offset,length,charset,type);
//    }

    /**
     * Parses the json byte array as {@link T}. Returns {@code null}
     * if received byte array is {@code null} or empty or length is 0.
     *
     * @param bytes the specified UTF8 text to be parsed
     * @param offset the starting index of array
     * @param length the specified length of array
     * @param charset the specified charset of the stream
     * @param type the specified actual type of {@link T}
     * @return {@link T} or {@code null}
     * @throws JSONException If a parsing error occurs
     */
    @SuppressWarnings("unchecked")
    static <T extends JsonGraalVMNativeBean> T parseObject(byte[] bytes, int offset, int length, Charset charset, Class<T> type) {
        return com.alibaba.fastjson2.JSON.parseObject(bytes,offset,length,charset,type);
    }

    /**
     * Parses the json byte array as {@link T}. Returns {@code null}
     * if received byte array is {@code null} or empty or length is 0.
     *
     * @param bytes the specified UTF8 text to be parsed
     * @param offset the starting index of array
     * @param length the specified length of array
     * @param charset the specified charset of the stream
     * @param type the specified actual class of {@link T}
     * @return {@link T} or {@code null}
     * @throws JSONException If a parsing error occurs
     */
    @SuppressWarnings("unchecked")
    static <T extends JsonGraalVMNativeBean> T parseObject(
            byte[] bytes,
            int offset,
            int length,
            Charset charset,
            Class<T> type,
            JSONReader.Feature... features
    ) {
        return com.alibaba.fastjson2.JSON.parseObject(bytes,offset,length,charset,type,features);
    }

//    /**
//     * Parses the json stream through the specified delimiter as
//     * {@link T} objects and call the specified consumer to consume it
//     *
//     * @param input the specified stream to be parsed
//     * @param type the specified actual class of {@link T}
//     * @param consumer the specified consumer is called multiple times
//     * @param features the specified features is applied to parsing
//     * @throws JSONException If an I/O error or parsing error occurs
//     * @throws NullPointerException If the specified stream is null
//     * @since 2.0.2
//     */
//    static <T extends JsonGraalVMNativeBean> void parseObject(InputStream input, Type type, Consumer<T> consumer, JSONReader.Feature... features) {
//        com.alibaba.fastjson2.JSON.parseObject(input,type,consumer,features);
//    }

//    /**
//     * Parses the json stream through the specified delimiter as
//     * {@link T} objects and call the specified consumer to consume it
//     *
//     * @param input the specified stream to be parsed
//     * @param charset the specified charset of the stream
//     * @param type the specified actual class of {@link T}
//     * @param delimiter the specified delimiter for the stream
//     * @param consumer the specified consumer is called multiple times
//     * @param features the specified features is applied to parsing
//     * @throws JSONException If an I/O error or parsing error occurs
//     * @throws NullPointerException If the specified stream is null
//     * @since 2.0.2
//     */
//    @SuppressWarnings("unchecked")
//    static <T extends JsonGraalVMNativeBean> void parseObject(
//            InputStream input,
//            Charset charset,
//            char delimiter,
//            Type type,
//            Consumer<T> consumer,
//            JSONReader.Feature... features
//    ) {
//        com.alibaba.fastjson2.JSON.parseObject(input,charset,delimiter,type,consumer,features);
//    }

//    /**
//     * Parses the json reader through the specified delimiter as
//     * {@link T} objects and call the specified consumer to consume it
//     *
//     * @param input the specified reader to be parsed
//     * @param type the specified actual class of {@link T}
//     * @param delimiter the specified delimiter for the stream
//     * @param consumer the specified consumer is called multiple times
//     * @throws JSONException If an I/O error or parsing error occurs
//     * @throws NullPointerException If the specified reader is null
//     * @since 2.0.2
//     */
//    @SuppressWarnings("unchecked")
//    static <T extends JsonGraalVMNativeBean> void parseObject(Reader input, char delimiter, Type type, Consumer<T> consumer) {
//        com.alibaba.fastjson2.JSON.parseObject(input,delimiter,type,consumer);
//    }
}
