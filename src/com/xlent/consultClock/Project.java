package com.xlent.consultClock;

public class Project {
	
	private String name;
	private boolean ticking;
	private int time;
	
	public Project(String name) {
		this.name = name;
		this.ticking = false;
		this.time = 0;
	}
	
	public Project(String name, int time) {
		this.name = name;
		this.ticking = false;
		this.time = time;
	}
	
	public String getName() {
		return name;
	}
	
	public void start() {
		ticking = true;
	}
	
	public void pause() {
		ticking = false;
	}
	
	public boolean isTicking() {
		return ticking;
	}
	
	public int getTime() {
		return time;
	}
	
	public void addTime() {
		time++;
	}
	
	public void resetTime() {
		time = 0;
	}
	
	private void startTimer() {
		//TODO Write, this method shall start the timer and update the time variable...
		// Or shuld this be done in this class at all?
	}
}
