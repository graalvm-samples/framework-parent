package com.fushun.framework.json.utils.fastjson;

import com.alibaba.fastjson2.*;
import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.filter.Filter;
import com.alibaba.fastjson2.modules.ObjectReaderModule;
import com.alibaba.fastjson2.modules.ObjectWriterModule;
import com.alibaba.fastjson2.reader.ObjectReader;
import com.alibaba.fastjson2.util.MapMultiValueType;
import com.alibaba.fastjson2.writer.ObjectWriter;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.lang.reflect.Type;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.time.ZoneId;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;

public interface JSONProxy extends   JSON{
    public static Object parse(String var0) {
        return JSON.parse(var0);
    }

    public static Object parse(String var0, JSONReader.Feature[] var1) {
        return JSON.parse(var0, var1);
    }

    public static Object parse(String var0, int var1, int var2, JSONReader.Feature[] var3) {
        return JSON.parse(var0, var1, var2, var3);
    }

    public static Object parse(String var0, JSONReader.Context var1) {
        return JSON.parse(var0, var1);
    }

    public static Object parse(byte[] var0, JSONReader.Feature[] var1) {
        return JSON.parse(var0, var1);
    }

    public static Object parse(char[] var0, JSONReader.Feature[] var1) {
        return JSON.parse(var0, var1);
    }

    public static JSONObject parseObject(String var0) {
        return JSON.parseObject(var0);
    }

    public static JSONObject parseObject(String var0, JSONReader.Feature[] var1) {
        return JSON.parseObject(var0, var1);
    }

    public static JSONObject parseObject(String var0, int var1, int var2, JSONReader.Feature[] var3) {
        return JSON.parseObject(var0, var1, var2, var3);
    }

    public static JSONObject parseObject(String var0, int var1, int var2, JSONReader.Context var3) {
        return JSON.parseObject(var0, var1, var2, var3);
    }

    public static JSONObject parseObject(String var0, JSONReader.Context var1) {
        return JSON.parseObject(var0, var1);
    }

    public static JSONObject parseObject(Reader var0, JSONReader.Feature[] var1) {
        return JSON.parseObject(var0, var1);
    }

    public static JSONObject parseObject(InputStream var0, JSONReader.Feature[] var1) {
        return JSON.parseObject(var0, var1);
    }

    public static JSONObject parseObject(byte[] var0) {
        return JSON.parseObject(var0);
    }

    public static JSONObject parseObject(char[] var0) {
        return JSON.parseObject(var0);
    }

    public static JSONObject parseObject(InputStream var0, Charset var1) {
        return JSON.parseObject(var0, var1);
    }

    public static JSONObject parseObject(URL var0) {
        return JSON.parseObject(var0);
    }

    public static JSONObject parseObject(byte[] var0, JSONReader.Feature[] var1) {
        return JSON.parseObject(var0, var1);
    }

    public static JSONObject parseObject(byte[] var0, int var1, int var2, JSONReader.Feature[] var3) {
        return JSON.parseObject(var0, var1, var2, var3);
    }

    public static JSONObject parseObject(char[] var0, int var1, int var2, JSONReader.Feature[] var3) {
        return JSON.parseObject(var0, var1, var2, var3);
    }

    public static JSONObject parseObject(byte[] var0, int var1, int var2, Charset var3, JSONReader.Feature[] var4) {
        return JSON.parseObject(var0, var1, var2, var3, var4);
    }

    public static Object parseObject(String var0, Class var1) {
        return JSON.parseObject(var0, var1);
    }

    public static Object parseObject(String var0, Class var1, Filter var2, JSONReader.Feature[] var3) {
        return JSON.parseObject(var0, var1, var2, var3);
    }

    public static Object parseObject(String var0, Type var1, String var2, Filter[] var3, JSONReader.Feature[] var4) {
        return JSON.parseObject(var0, var1, var2, var3, var4);
    }

    public static Object parseObject(String var0, Type var1) {
        return JSON.parseObject(var0, var1);
    }

    public static Map parseObject(String var0, MapMultiValueType var1) {
        return JSON.parseObject(var0, var1);
    }

    public static Object parseObject(String var0, Type[] var1) {
        return JSON.parseObject(var0, var1);
    }

    public static Object parseObject(String var0, TypeReference var1, JSONReader.Feature[] var2) {
        return JSON.parseObject(var0, var1, var2);
    }

    public static Object parseObject(String var0, TypeReference var1, Filter var2, JSONReader.Feature[] var3) {
        return JSON.parseObject(var0, var1, var2, var3);
    }

    public static Object parseObject(String var0, Class var1, JSONReader.Feature[] var2) {
        return JSON.parseObject(var0, var1, var2);
    }

    public static Object parseObject(String var0, int var1, int var2, Class var3, JSONReader.Feature[] var4) {
        return JSON.parseObject(var0, var1, var2, var3, var4);
    }

    public static Object parseObject(String var0, Class var1, JSONReader.Context var2) {
        return JSON.parseObject(var0, var1, var2);
    }

    public static Object parseObject(String var0, Class var1, String var2, JSONReader.Feature[] var3) {
        return JSON.parseObject(var0, var1, var2, var3);
    }

    public static Object parseObject(String var0, Type var1, JSONReader.Feature[] var2) {
        return JSON.parseObject(var0, var1, var2);
    }

    public static Object parseObject(String var0, Type var1, Filter var2, JSONReader.Feature[] var3) {
        return JSON.parseObject(var0, var1, var2, var3);
    }

    public static Object parseObject(String var0, Type var1, String var2, JSONReader.Feature[] var3) {
        return JSON.parseObject(var0, var1, var2, var3);
    }

    public static Object parseObject(char[] var0, int var1, int var2, Type var3, JSONReader.Feature[] var4) {
        return JSON.parseObject(var0, var1, var2, var3, var4);
    }

    public static Object parseObject(char[] var0, Class var1) {
        return JSON.parseObject(var0, var1);
    }

    public static Object parseObject(byte[] var0, int var1, int var2, Type var3, JSONReader.Feature[] var4) {
        return JSON.parseObject(var0, var1, var2, var3, var4);
    }

    public static Object parseObject(byte[] var0, Type var1) {
        return JSON.parseObject(var0, var1);
    }

    public static Object parseObject(byte[] var0, Class var1) {
        return JSON.parseObject(var0, var1);
    }

    public static Object parseObject(byte[] var0, Class var1, Filter var2, JSONReader.Feature[] var3) {
        return JSON.parseObject(var0, var1, var2, var3);
    }

    public static Object parseObject(byte[] var0, Class var1, JSONReader.Context var2) {
        return JSON.parseObject(var0, var1, var2);
    }

    public static Object parseObject(byte[] var0, Type var1, String var2, Filter[] var3, JSONReader.Feature[] var4) {
        return JSON.parseObject(var0, var1, var2, var3, var4);
    }

    public static Object parseObject(byte[] var0, Class var1, JSONReader.Feature[] var2) {
        return JSON.parseObject(var0, var1, var2);
    }

    public static Object parseObject(byte[] var0, Type var1, JSONReader.Feature[] var2) {
        return JSON.parseObject(var0, var1, var2);
    }

    public static Object parseObject(char[] var0, Class var1, JSONReader.Feature[] var2) {
        return JSON.parseObject(var0, var1, var2);
    }

    public static Object parseObject(char[] var0, Type var1, JSONReader.Feature[] var2) {
        return JSON.parseObject(var0, var1, var2);
    }

    public static Object parseObject(byte[] var0, Type var1, Filter var2, JSONReader.Feature[] var3) {
        return JSON.parseObject(var0, var1, var2, var3);
    }

    public static Object parseObject(byte[] var0, Type var1, String var2, JSONReader.Feature[] var3) {
        return JSON.parseObject(var0, var1, var2, var3);
    }

    public static Object parseObject(ByteBuffer var0, Class var1) {
        return JSON.parseObject(var0, var1);
    }

    public static Object parseObject(Reader var0, Type var1, JSONReader.Feature[] var2) {
        return JSON.parseObject(var0, var1, var2);
    }

    public static Object parseObject(InputStream var0, Type var1, JSONReader.Feature[] var2) {
        return JSON.parseObject(var0, var1, var2);
    }

    public static Object parseObject(URL var0, Type var1, JSONReader.Feature[] var2) {
        return JSON.parseObject(var0, var1, var2);
    }

    public static Object parseObject(URL var0, Class var1, JSONReader.Feature[] var2) {
        return JSON.parseObject(var0, var1, var2);
    }

    public static Object parseObject(URL var0, Function var1, JSONReader.Feature[] var2) {
        return JSON.parseObject(var0, var1, var2);
    }

    public static Object parseObject(InputStream var0, Type var1, String var2, JSONReader.Feature[] var3) {
        return JSON.parseObject(var0, var1, var2, var3);
    }

    public static Object parseObject(InputStream var0, Charset var1, Type var2, JSONReader.Feature[] var3) {
        return JSON.parseObject(var0, var1, var2, var3);
    }

    public static Object parseObject(byte[] var0, int var1, int var2, Charset var3, Type var4) {
        return JSON.parseObject(var0, var1, var2, var3, var4);
    }

    public static Object parseObject(byte[] var0, int var1, int var2, Charset var3, Class var4) {
        return JSON.parseObject(var0, var1, var2, var3, var4);
    }

    public static Object parseObject(byte[] var0, int var1, int var2, Charset var3, Class var4, JSONReader.Feature[] var5) {
        return JSON.parseObject(var0, var1, var2, var3, var4, var5);
    }

    public static void parseObject(InputStream var0, Type var1, Consumer var2, JSONReader.Feature[] var3) {
        JSON.parseObject(var0, var1, var2, var3);
    }

    public static void parseObject(InputStream var0, Charset var1, char var2, Type var3, Consumer var4, JSONReader.Feature[] var5) {
        JSON.parseObject(var0, var1, var2, var3, var4, var5);
    }

    public static void parseObject(Reader var0, char var1, Type var2, Consumer var3) {
        JSON.parseObject(var0, var1, var2, var3);
    }

    public static JSONArray parseArray(String var0) {
        return JSON.parseArray(var0);
    }

    public static JSONArray parseArray(byte[] var0) {
        return JSON.parseArray(var0);
    }

    public static JSONArray parseArray(byte[] var0, int var1, int var2, Charset var3) {
        return JSON.parseArray(var0, var1, var2, var3);
    }

    public static JSONArray parseArray(char[] var0) {
        return JSON.parseArray(var0);
    }

    public static JSONArray parseArray(String var0, JSONReader.Feature[] var1) {
        return JSON.parseArray(var0, var1);
    }

    public static JSONArray parseArray(URL var0, JSONReader.Feature[] var1) {
        return JSON.parseArray(var0, var1);
    }

    public static JSONArray parseArray(InputStream var0, JSONReader.Feature[] var1) {
        return JSON.parseArray(var0, var1);
    }

    public static List parseArray(String var0, Type var1, JSONReader.Feature[] var2) {
        return JSON.parseArray(var0, var1, var2);
    }

    public static List parseArray(String var0, Type var1) {
        return JSON.parseArray(var0, var1);
    }

    public static List parseArray(String var0, Class var1) {
        return JSON.parseArray(var0, var1);
    }

    public static List parseArray(String var0, Type[] var1) {
        return JSON.parseArray(var0, var1);
    }

    public static List parseArray(String var0, Class var1, JSONReader.Feature[] var2) {
        return JSON.parseArray(var0, var1, var2);
    }

    public static List parseArray(char[] var0, Class var1, JSONReader.Feature[] var2) {
        return JSON.parseArray(var0, var1, var2);
    }

    public static List parseArray(String var0, Type[] var1, JSONReader.Feature[] var2) {
        return JSON.parseArray(var0, var1, var2);
    }

    public static List parseArray(byte[] var0, Type var1, JSONReader.Feature[] var2) {
        return JSON.parseArray(var0, var1, var2);
    }

    public static List parseArray(byte[] var0, Class var1, JSONReader.Feature[] var2) {
        return JSON.parseArray(var0, var1, var2);
    }

    public static List parseArray(byte[] var0, int var1, int var2, Charset var3, Class var4, JSONReader.Feature[] var5) {
        return JSON.parseArray(var0, var1, var2, var3, var4, var5);
    }

    public static String toJSONString(Object var0) {
        return JSON.toJSONString(var0);
    }

    public static String toJSONString(Object var0, JSONWriter.Context var1) {
        return JSON.toJSONString(var0, var1);
    }

    public static String toJSONString(Object var0, JSONWriter.Feature[] var1) {
        return JSON.toJSONString(var0, var1);
    }

    public static String toJSONString(Object var0, Filter var1, JSONWriter.Feature[] var2) {
        return JSON.toJSONString(var0, var1, var2);
    }

    public static String toJSONString(Object var0, Filter[] var1, JSONWriter.Feature[] var2) {
        return JSON.toJSONString(var0, var1, var2);
    }

    public static String toJSONString(Object var0, String var1, JSONWriter.Feature[] var2) {
        return JSON.toJSONString(var0, var1, var2);
    }

    public static String toJSONString(Object var0, String var1, Filter[] var2, JSONWriter.Feature[] var3) {
        return JSON.toJSONString(var0, var1, var2, var3);
    }

    public static byte[] toJSONBytes(Object var0) {
        return JSON.toJSONBytes(var0);
    }

    public static byte[] toJSONBytes(Object var0, String var1, JSONWriter.Feature[] var2) {
        return JSON.toJSONBytes(var0, var1, var2);
    }

    public static byte[] toJSONBytes(Object var0, Filter[] var1) {
        return JSON.toJSONBytes(var0, var1);
    }

    public static byte[] toJSONBytes(Object var0, JSONWriter.Feature[] var1) {
        return JSON.toJSONBytes(var0, var1);
    }

    public static byte[] toJSONBytes(Object var0, Filter[] var1, JSONWriter.Feature[] var2) {
        return JSON.toJSONBytes(var0, var1, var2);
    }

    public static byte[] toJSONBytes(Object var0, String var1, Filter[] var2, JSONWriter.Feature[] var3) {
        return JSON.toJSONBytes(var0, var1, var2, var3);
    }

    public static int writeTo(OutputStream var0, Object var1) {
        return JSON.writeTo(var0, var1);
    }

    public static int writeTo(OutputStream var0, Object var1, JSONWriter.Feature[] var2) {
        return JSON.writeTo(var0, var1, var2);
    }

    public static int writeTo(OutputStream var0, Object var1, Filter[] var2, JSONWriter.Feature[] var3) {
        return JSON.writeTo(var0, var1, var2, var3);
    }

    public static int writeTo(OutputStream var0, Object var1, String var2, Filter[] var3, JSONWriter.Feature[] var4) {
        return JSON.writeTo(var0, var1, var2, var3, var4);
    }

    public static boolean isValid(String var0) {
        return JSON.isValid(var0);
    }

    public static boolean isValid(String var0, JSONReader.Feature[] var1) {
        return JSON.isValid(var0, var1);
    }

    public static boolean isValid(char[] var0) {
        return JSON.isValid(var0);
    }

    public static boolean isValidObject(String var0) {
        return JSON.isValidObject(var0);
    }

    public static boolean isValidObject(byte[] var0) {
        return JSON.isValidObject(var0);
    }

    public static boolean isValidArray(String var0) {
        return JSON.isValidArray(var0);
    }

    public static boolean isValid(byte[] var0) {
        return JSON.isValid(var0);
    }

    public static boolean isValid(byte[] var0, Charset var1) {
        return JSON.isValid(var0, var1);
    }

    public static boolean isValidArray(byte[] var0) {
        return JSON.isValidArray(var0);
    }

    public static boolean isValid(byte[] var0, int var1, int var2, Charset var3) {
        return JSON.isValid(var0, var1, var2, var3);
    }

    public static Object toJSON(Object var0) {
        return JSON.toJSON(var0);
    }

    public static Object toJSON(Object var0, JSONWriter.Feature[] var1) {
        return JSON.toJSON(var0, var1);
    }

    public static Object to(Class var0, Object var1) {
        return JSON.to(var0, var1);
    }

    public static Object toJavaObject(Object var0, Class var1) {
        return JSON.toJavaObject(var0, var1);
    }

    public static void mixIn(Class var0, Class var1) {
        JSON.mixIn(var0, var1);
    }

    public static ObjectReader register(Type var0, ObjectReader var1) {
        return JSON.register(var0, var1);
    }

    public static ObjectReader register(Type var0, ObjectReader var1, boolean var2) {
        return JSON.register(var0, var1, var2);
    }

    public static ObjectReader registerIfAbsent(Type var0, ObjectReader var1) {
        return JSON.registerIfAbsent(var0, var1);
    }

    public static ObjectReader registerIfAbsent(Type var0, ObjectReader var1, boolean var2) {
        return JSON.registerIfAbsent(var0, var1, var2);
    }

    public static boolean register(ObjectReaderModule var0) {
        return JSON.register(var0);
    }

    public static void registerSeeAlsoSubType(Class var0) {
        JSON.registerSeeAlsoSubType(var0);
    }

    public static void registerSeeAlsoSubType(Class var0, String var1) {
        JSON.registerSeeAlsoSubType(var0, var1);
    }

    public static boolean register(ObjectWriterModule var0) {
        return JSON.register(var0);
    }

    public static ObjectWriter register(Type var0, ObjectWriter var1) {
        return JSON.register(var0, var1);
    }

    public static ObjectWriter register(Type var0, ObjectWriter var1, boolean var2) {
        return JSON.register(var0, var1, var2);
    }

    public static ObjectWriter registerIfAbsent(Type var0, ObjectWriter var1) {
        return JSON.registerIfAbsent(var0, var1);
    }

    public static ObjectWriter registerIfAbsent(Type var0, ObjectWriter var1, boolean var2) {
        return JSON.registerIfAbsent(var0, var1, var2);
    }

    public static void register(Class var0, Filter var1) {
        JSON.register(var0, var1);
    }

    public static void config(JSONReader.Feature[] var0) {
        JSON.config(var0);
    }

    public static void config(JSONReader.Feature var0, boolean var1) {
        JSON.config(var0, var1);
    }

    public static boolean isEnabled(JSONReader.Feature var0) {
        return JSON.isEnabled(var0);
    }

    public static void configReaderDateFormat(String var0) {
        JSON.configReaderDateFormat(var0);
    }

    public static void configWriterDateFormat(String var0) {
        JSON.configWriterDateFormat(var0);
    }

    public static void configReaderZoneId(ZoneId var0) {
        JSON.configReaderZoneId(var0);
    }

    public static void configWriterZoneId(ZoneId var0) {
        JSON.configWriterZoneId(var0);
    }

    public static void config(JSONWriter.Feature[] var0) {
        JSON.config(var0);
    }

    public static void config(JSONWriter.Feature var0, boolean var1) {
        JSON.config(var0, var1);
    }

    public static boolean isEnabled(JSONWriter.Feature var0) {
        return JSON.isEnabled(var0);
    }

    public static Object copy(Object var0, JSONWriter.Feature[] var1) {
        return JSON.copy(var0, var1);
    }

    public static Object copyTo(Object var0, Class var1, JSONWriter.Feature[] var2) {
        return JSON.copyTo(var0, var1, var2);
    }

    public static void newMethod(Object var0) {
        System.out.println("Custom logic");
    }

}
