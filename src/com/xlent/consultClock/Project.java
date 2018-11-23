package com.xlent.consultClock;

import java.util.Timer;
import java.util.TimerTask;

import javafx.scene.control.Label;
import javafx.scene.text.Text;

public class Project {
	
	private String name;
	private boolean ticking;
	private int time;
	private Timer timer = new Timer();
	
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
	
	public void start(Text timeLabel) {
		ticking = true;
		timer.scheduleAtFixedRate(new TimerTask() {
			
			@Override
			public void run() {
				addTime();
				timeLabel.setText(""+getTime());
			}
		}, 0, 1000);
	}
	
	public void pause() {
		ticking = false;
		timer.cancel();
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
