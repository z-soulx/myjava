package com.example.javabase.design_patterns.offcial.headfirsts.designpatterns.adapter.ducks.challenge;

public class SuperDrone implements Drone {
	public void beep() {
		System.out.println("Beep beep beep");
	}
	public void spin_rotors() {
		System.out.println("Rotors are spinning");
	}
	public void take_off() {
		System.out.println("Taking off");
	}
}
