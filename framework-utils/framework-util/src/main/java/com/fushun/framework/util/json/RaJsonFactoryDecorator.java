//package com.fushun.framework.util.json;
//
//import com.fasterxml.jackson.core.JsonGenerator;
//import com.fasterxml.jackson.databind.MappingJsonFactory;
//import net.logstash.logback.decorate.JsonFactoryDecorator;
//
//public class RaJsonFactoryDecorator implements JsonFactoryDecorator {
//    @Override
//    public MappingJsonFactory decorate(MappingJsonFactory mappingJsonFactory) {
//        mappingJsonFactory.disable(JsonGenerator.Feature.ESCAPE_NON_ASCII);
//        return mappingJsonFactory;
//    }
//}
