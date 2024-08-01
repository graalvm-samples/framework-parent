package com.fushun.framework.util.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fushun.framework.base.IBaseEnum;
import com.fushun.framework.util.util.StringUtils;

import java.io.IOException;

/**
 *
 * jackson自定义序列化将枚举处理成平行结构 https://www.jianshu.com/p/489af61bfa8d
 *
 * 枚举必须实现IBaseEnum才能正常使用
 *
 * 使用方法：
 *   mapper.findAndRegisterModules();
 *   SimpleModule simpleModule = new SimpleModule();
 *   //注册一个统一的枚举序列方法，实现IEnum接口的枚举序列化时取desc字段
 *   simpleModule.addSerializer(IBaseEnum.class, new EnumsCodec());
 *   mapper.registerModule(simpleModule);
 *
 */
public class EnumsCodec extends JsonSerializer<IBaseEnum> {


    @Override
    public void serialize(IBaseEnum o, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeObject(o.getCode());
        if(StringUtils.isNotEmpty(jsonGenerator.getOutputContext().getCurrentName())){
            jsonGenerator.writeStringField(jsonGenerator.getOutputContext().getCurrentName() + "Desc", o.getDesc());
        }

    }
}
