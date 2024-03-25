package com.example.spring.design.commonObjectPool;

import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import com.fasterxml.jackson.databind.ObjectMapper; // 引入Jackson来处理JSON

public class ResponseDataFactory extends BasePooledObjectFactory<ResponseData> {

    @Override
    public ResponseData create() throws Exception {
        // 这里只是为了示例，实际中你可能不需要在创建时就进行初始化
        return new ResponseData();
    }

    @Override
    public PooledObject<ResponseData> wrap(ResponseData obj) {
        return new DefaultPooledObject<>(obj);
    }
    
    // 如果需要，可以重写destroyObject等方法来处理对象销毁逻辑
}
