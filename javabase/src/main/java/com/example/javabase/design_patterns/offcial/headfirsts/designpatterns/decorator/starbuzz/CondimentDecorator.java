package com.example.javabase.design_patterns.offcial.headfirsts.designpatterns.decorator.starbuzz;

public abstract class CondimentDecorator extends Beverage {
	Beverage beverage;
	public abstract String getDescription();
}
