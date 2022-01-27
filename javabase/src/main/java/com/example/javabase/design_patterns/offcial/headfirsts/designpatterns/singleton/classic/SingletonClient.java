package com.example.javabase.design_patterns.offcial.headfirsts.designpatterns.singleton.classic;

public class SingletonClient {
	public static void main(String[] args) {
		Singleton singleton = Singleton.getInstance();
		System.out.println(singleton.getDescription());
	}
}
