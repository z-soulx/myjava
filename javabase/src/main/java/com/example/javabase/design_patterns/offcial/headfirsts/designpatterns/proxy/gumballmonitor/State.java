package com.example.javabase.design_patterns.offcial.headfirsts.designpatterns.proxy.gumballmonitor;

import java.io.*;

public interface State extends Serializable {
 
	public void insertQuarter();
	public void ejectQuarter();
	public void turnCrank();
	public void dispense();
}
