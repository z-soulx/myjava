package com.example.spring.base;

import org.springframework.stereotype.Component;

@Component("Student2")
public class Student extends BaseStudent {

	private String name;
	private int age;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}

    public Student(String name, int age) {
        this.age = age;
        this.name = name;
    }

    public Student() {
    }

    @Override
	public String toString() {
		return "Student [name=" + name + ", age=" + age + "]";
	}
	
}
