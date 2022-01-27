package com.example.javabase.design_patterns.offcial.headfirsts.designpatterns.command.remote;

public class LivingroomLightOffCommand implements Command {
	Light light;

	public LivingroomLightOffCommand(Light light) {
		this.light = light;
	}

	public void execute() {
		light.off();
	}
}
