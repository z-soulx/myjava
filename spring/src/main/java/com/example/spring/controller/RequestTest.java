package com.example.spring.controller;

import javax.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @program: myjava
 * @description: 测试request的一些特性
 * @author: soulx
 * @create: 2022-07-18 10:23
 **/
public class RequestTest {

	@GetMapping("/getParams")
	public String getParams(String a, int b) {
		return "get success";
	}


	@PostMapping("/postTest")
	public String postTest(HttpServletRequest request,String age, String name) {

		new Thread(new Runnable() {
			@Override
			public void run() {
				String age2 = request.getParameter("age");
				String name2 = request.getParameter("name");
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					throw new RuntimeException(e);
				}
				String age3 = request.getParameter("age");
				String name3 = request.getParameter("name");
				System.out.println("age1: " + age + " , name1: " + name + " , age2: " + age2 + " , name2: " + name2 + " , age3: " + age3 + " , name3: " + name3);
			}
		}).start();
		return "post success";
	}

}
