package com.example.spring;


import com.example.spring.service.TBeanTest;
import javax.annotation.Resource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(classes = Application.class)
@RunWith(SpringRunner.class)
public class ApplicationTests {

	@Resource
	private TBeanTest test;

	@Test
	public void contextLoads() {
		System.out.println(test.getBean() == test.getBean());
	}

}
