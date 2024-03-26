package com.example.spring.design.commonObjectPool;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/**
 * @program: myjava
 * @description:
 * @author: soulx
 * @create: 2024-03-25 15:22
 **/
public class HttpResponse {
	@JsonDeserialize(using = TDeserializer.class)
	private ResponseData t;

	public ResponseData getT() {
		return t;
	}

	public void setT(ResponseData t) {
		this.t = t;
	}

	public ResponseData body() {
		return t;
	}
}
