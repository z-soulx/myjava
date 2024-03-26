package com.example.spring.design.commonObjectPool;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class TDeserializer extends JsonDeserializer<ResponseData> {

    @Override
    public ResponseData deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        // 获取当前T对象
        ResponseData currentT = (ResponseData) ctxt.findInjectableValue(ResponseData.class.getName(), null, null);
        if (currentT == null) {
            currentT = new ResponseData();  // 如果当前对象不存在，则创建一个新的
        }

        // 更新当前T对象
        ObjectMapper mapper = (ObjectMapper) p.getCodec();
        mapper.readerForUpdating(currentT).readValue(p);
        return currentT;
    }
}
