package com.example.javabase.design_patterns.offcial.headfirsts.designpatterns.decorator.starbuzz;

public abstract class Beverage {
	String description = "Unknown Beverage";
  
	public String getDescription() {
		return description;
	}
 
	public abstract double cost();
}
