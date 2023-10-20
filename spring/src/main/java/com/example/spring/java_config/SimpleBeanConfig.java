package com.example.spring.java_config;

import com.example.spring.base.SimpleBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableLoadTimeWeaving;
import org.springframework.context.annotation.Import;

/**
 * {@link com.example.spring.base.SimpleBean}配置
 *
 * @author skywalker
 */
@Configuration
@Import(java_config.StudentConfig.class)
public class SimpleBeanConfig {

    @Autowired
    private java_config.StudentConfig studentConfig;

    @Bean("simpleBean2")
    public SimpleBean simpleBean() {
        SimpleBean simpleBean = new SimpleBean(studentConfig.student());
        return simpleBean;
    }

}
