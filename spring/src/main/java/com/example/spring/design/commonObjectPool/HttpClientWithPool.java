package com.example.spring.design.commonObjectPool;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.Reader;
import org.apache.commons.pool2.impl.GenericObjectPool;
import com.fasterxml.jackson.databind.ObjectMapper;

public class HttpClientWithPool {
    private static  GenericObjectPool<ResponseData> pool = new GenericObjectPool<>(new ResponseDataFactory());
    // 静态初始化块和对象池配置保持不变
    public static void main(String[] args) throws IOException {
        String s = "{\n"
            + "  \"id\": 1,\n"
//            + "  \"name\": \"示例名称\",\n"
            + "  \"description\": \"这是一个示例描述。\"\n"
            + "}\n";
        ObjectMapper mapper = new ObjectMapper();
        ResponseData responseData = mapper.readValue(s, ResponseData.class);
        System.out.println(responseData);
        responseData.setId(0);
        responseData.setDescription(null);
        responseData.setName("null");
        ResponseData borrowedData = responseData;
        mapper.readerForUpdating(borrowedData).readValue(s);
//        mapper.readerForUpdating(borrowedData).readValue(new Reader(){
//            @Override
//            public int read(char[] cbuf, int off, int len) throws IOException {
//                return 0;
//            }
//
//            @Override
//            public void close() throws IOException {
//
//            }
//        });
        System.out.println(borrowedData);

    }
    public static ResponseData fetchData(String url) throws Exception {
        HttpResponse response = HttpUtil.sendRequest(url);
        ResponseData borrowedData = pool.borrowObject(); // 从池中借出一个对象

        // 在已存在的对象上反序列化JSON数据
        ObjectMapper mapper = new ObjectMapper();
        mapper.readerForUpdating(borrowedData).readValue(response.body().toString());

        // 假设在这里你不需要立即返回对象给池（依赖于你的使用场景）
         pool.returnObject(borrowedData);

        // 返回更新后的对象
        return borrowedData;
    }
}
