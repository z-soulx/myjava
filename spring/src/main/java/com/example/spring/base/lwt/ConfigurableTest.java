package com.example.spring.base.lwt;

import com.example.spring.base.SimpleBean;
import org.springframework.beans.factory.annotation.Configurable;

import javax.annotation.Resource;

/**
 * @program: spring
 * @description:
 * @author: soulx
 * @create: 2020-04-01 14:48
 **/
@Configurable(preConstruction = true)
public class ConfigurableTest {
    @Resource
    private SimpleBean simpleBean;
    public void send(){
        System.out.println(simpleBean.getStudent());
    }
}
