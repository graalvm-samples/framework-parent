package com.fushun.framework.util.json;

import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonStreamContext;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import com.fushun.framework.base.IBaseEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;

/**
 * 枚举反序列化
 * zhangyd
 * 2020/7/17 11:24
 */
@Slf4j
public class EnumDeSerializer extends JsonDeserializer<IBaseEnum> {
    @Override
    public IBaseEnum deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        JsonNode node = jp.getCodec().readTree(jp);
        String currentName = jp.currentName();
        Object currentValue = jp.getCurrentValue();
        Class findPropertyType = null;
        if (currentValue instanceof Collection) {

            JsonStreamContext parsingContext = jp.getParsingContext();
            JsonStreamContext parent = parsingContext.getParent();
            Object currentValue3 = parent.getCurrentValue();
            String currentName3 = parent.getCurrentName();
            try {
                Field listField = currentValue3.getClass().getDeclaredField(currentName3);
                ParameterizedType listGenericType = (ParameterizedType) listField.getGenericType();
                Type listActualTypeArguments = listGenericType.getActualTypeArguments()[0];
                findPropertyType = (Class) listActualTypeArguments;
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            findPropertyType = BeanUtils.findPropertyType(currentName, currentValue.getClass());
        }
        if (findPropertyType == null) {
            log.error("EnumDeSerializer 枚举反序列化 数据格式异常");
        }
        String asText = null;
        if (node.getNodeType() == JsonNodeType.STRING) {
            asText = node.asText();
        } else {
            asText = node.get("code").asText();
        }
        IBaseEnum valueOf = null;
        if (StrUtil.isNotBlank(asText)) {
            valueOf = IBaseEnum.valueOf(asText, findPropertyType);
        }
        return valueOf;
    }
}
