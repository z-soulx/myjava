package com.example.javabase.design_patterns.offcial.headfirsts.designpatterns.decorator.pizza;

public abstract class ToppingDecorator extends Pizza {
	Pizza pizza;
	public abstract String getDescription();
}
