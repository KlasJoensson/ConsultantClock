package com.xlent.consultClock;

import java.util.Timer;
import java.util.TimerTask;

import javafx.scene.text.Text;

public class Project {
	
	private String name;
	private boolean ticking;
	private int time;
	private Timer timer;
	
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
		timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {
			
			@Override
			public void run() {
				addTime();
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
	
	public void addTime() {
		time++;
	}
	
	public void resetTime() {
		time = 0;
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
	
}
