package com.xlent.consultClock;

import java.util.Timer;
import java.util.TimerTask;

import javafx.scene.text.Text;

public class Project {
	
	private String name;
	private boolean ticking;
	private int time;
	private Timer timer;
	private boolean countDown;
	private int startTime;
	
	public Project(String name) {
		this(name, 0, false);
	}

	public Project(String name, int time, boolean countDown) {
		this.name = name;
		this.ticking = false;
		this.time = time;
		this.countDown = countDown;
		this.startTime = time;
	}
	
	public String getName() {
		return name;
	}
	
	public void start(Text timeLabel) {
		ticking = true;
		timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {
			
			@Override
			public void run() {
				if (isCountingDown()) {
					countDown();
				} else {
					addTime();
				}
				timeLabel.setText(getTimeAsString());
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
	
	public boolean isCountingDown() {
		return countDown;
	}
	
	public void addTime() {
		time++;
	}
	
	public void countDown( ) {
		time--;
		if (time == 0) {
			timer.cancel();
		}
	}
	
	public void resetTime() {
		time = startTime;
	}
	
	public String getTimeAsString() {
		StringBuilder timeStr = new StringBuilder();
		int rest = time%3600;
		int min = rest/60;
		int s = rest%60;
		
		timeStr.append(time/3600);
		timeStr.append(":");
		if (min<10) {
			timeStr.append("0");
		}
		timeStr.append(min);
		timeStr.append(":");
		if (s<10) {
			timeStr.append("0");
		}
		timeStr.append(s);		
		
		return timeStr.toString();
	}
	
	public String toString() {
		StringBuilder projectStr = new StringBuilder();
		projectStr.append(getName());
		projectStr.append(":");
		projectStr.append(getTime());
		projectStr.append(":");
		projectStr.append(isCountingDown());
		
		return projectStr.toString();
	}
}
