package com.example.spring.base.scope;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@Scope("prototype")
public class MyScope {
    @PostConstruct
    public void sayMyScope() {
        System.out.println("xxxxx");
    }
}
