package com.example.spring.design.commonObjectPool;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.InjectableValues;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @program: myjava
 * @description:
 * @author: soulx
 * @create: 2024-03-25 19:34
 **/
public class Test2 {

	/**
	 * 镶嵌对象的序列化池化技术
	 * @param args
	 * @throws JsonProcessingException
	 */
	public static void main(String[] args) throws JsonProcessingException {
		String s = "{\n"
				+ "\t\"t\": {\n"
				+ "\t\t\"id\": 1,\n"
				+ "\t\t\"description\": \"这是一个示例描述\"\n"
				+ "\t}\n"
				+ "}";
		ObjectMapper mapper = new ObjectMapper();
		// 注册injectableValues以提供已存在的ResponseData实例
//		HttpResponse responseData = mapper.readValue(s, HttpResponse.class);
		HttpResponse responseData = new HttpResponse();
		responseData.setT(new ResponseData());
		ResponseData t = responseData.getT();
		InjectableValues.Std injectableValues = new InjectableValues.Std();
		injectableValues.addValue(ResponseData.class.getName(), t);
		mapper.setInjectableValues(injectableValues);
		mapper.readerForUpdating(responseData).readValue(s);
		System.out.println(t == responseData.body());
	}
}
