package com.example.javabase.design_patterns.offcial.headfirsts.designpatterns.command.simpleremote;

public class LightOnCommand implements Command {
	Light light;
  
	public LightOnCommand(Light light) {
		this.light = light;
	}
 
	public void execute() {
		light.on();
	}
}
