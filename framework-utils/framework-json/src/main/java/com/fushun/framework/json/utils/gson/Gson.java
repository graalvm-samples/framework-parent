package com.fushun.framework.json.utils.gson;

import com.fushun.framework.json.config.JsonGraalVMNativeBean;
import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import java.io.Reader;
import java.lang.reflect.Type;
import java.util.Map;

public class Gson {

    private com.google.gson.Gson gson;

    public Gson() {
        gson = new com.google.gson.Gson();
    }

    public com.google.gson.Gson getGsonInstance(){
        return gson;
    }

    public JsonElement toJsonTree(JsonGraalVMNativeBean src) {
        return gson.toJsonTree(src);
    }
    public JsonElement toJsonTree(Map src) {
        return gson.toJsonTree(src);
    }
    public JsonElement toJsonTree(Iterable src) {
        return gson.toJsonTree(src);
    }

    public JsonElement toJsonTree(JsonGraalVMNativeBean src, Type typeOfSrc) {
        return gson.toJsonTree(src, typeOfSrc);
    }
    public JsonElement toJsonTree(Map src, Type typeOfSrc) {
        return gson.toJsonTree(src, typeOfSrc);
    }
    public JsonElement toJsonTree(Iterable src, Type typeOfSrc) {
        return gson.toJsonTree(src, typeOfSrc);
    }

    public String toJson(JsonGraalVMNativeBean src) {
        return gson.toJson(src);
    }
    public String toJson(Map src) {
        return gson.toJson(src);
    }
    public String toJson(Iterable src) {
        return gson.toJson(src);
    }

    public <T extends JsonGraalVMNativeBean> T fromJson(String json, Class<T> classOfT) throws JsonSyntaxException {
        return gson.fromJson(json, classOfT);
    }

    public <T> T fromJson(String json, Type typeOfT) throws JsonSyntaxException {
        return gson.fromJson(json, typeOfT);
    }

    public <T> T fromJson(String json, TypeToken<T> typeOfT) throws JsonSyntaxException {
        return gson.fromJson(json, typeOfT);
    }

    public <T extends JsonGraalVMNativeBean> T fromJson(Reader json, Class<T> classOfT) throws JsonSyntaxException, JsonIOException {
        return gson.fromJson(json, classOfT);
    }

    public <T> T fromJson(Reader json, Type typeOfT) throws JsonIOException, JsonSyntaxException {
        return gson.fromJson(json, typeOfT);
    }

    public <T> T fromJson(Reader json, TypeToken<T> typeOfT) throws JsonIOException, JsonSyntaxException {
        return gson.fromJson(json, typeOfT);
    }

    public <T> T fromJson(JsonReader reader, Type typeOfT) throws JsonIOException, JsonSyntaxException {
        return gson.fromJson(reader, typeOfT);
    }

    public <T> T fromJson(JsonReader reader, TypeToken<T> typeOfT) throws JsonIOException, JsonSyntaxException {
        return gson.fromJson(reader, typeOfT);
    }

    public <T extends JsonGraalVMNativeBean> T fromJson(JsonElement json, Class<T> classOfT) throws JsonSyntaxException {
        return gson.fromJson(json, classOfT);
    }

    public <T> T fromJson(JsonElement json, Type typeOfT) throws JsonSyntaxException {
        return gson.fromJson(json, typeOfT);
    }


    public <T> T fromJson(JsonElement json, TypeToken<T> typeOfT) throws JsonSyntaxException {
        return gson.fromJson(json, typeOfT);
    }
}