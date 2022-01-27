package com.example.javabase.design_patterns.offcial.headfirsts.designpatterns.observer.weather;

public interface Subject {
	public void registerObserver(Observer o);
	public void removeObserver(Observer o);
	public void notifyObservers();
}
