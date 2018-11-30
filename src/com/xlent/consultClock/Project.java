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
	
	/**
	 * Creates a standard project: The time starts from zero and counting upwards.
	 * 
	 * @param name The name of the project
	 */
	public Project(String name) {
		this(name, 0, false);
	}

	/**
	 * Creates a generic project with specified parameters.
	 * 
	 * @param name The name of the project
	 * @param time The start time of the project
	 * @param countDown If it shall count down ({@code true}) or up wards ({@code false})
	 */
	public Project(String name, int time, boolean countDown) {
		this.name = name;
		this.ticking = false;
		this.time = time;
		this.countDown = countDown;
		this.startTime = time;
	}
	
	/**
	 * Creates a generic project with specified parameters.
	 * 
	 * @param name The name of the project
	 * @param time The start time of the project
	 * @param countDown If it shall count down ({@code true}) or up wards ({@code false})
	 */
	public Project(String name, int time, int startTime, boolean countDown) {
		this.name = name;
		this.ticking = false;
		this.time = time;
		this.countDown = countDown;
		this.startTime = startTime;
	}
	
	/**
	 * Returns the name of the project.
	 * 
	 * @return The project name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Starts counting the time.
	 * 
	 * @param timeLabel The label displaying the time
	 */
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
	
	/**
	 * Pauses the counting.
	 */
	public void pause() {
		ticking = false;
		timer.cancel();
	}
	
	/**
	 * Check if the project is counting or not.
	 * 
	 * @return {@code true} if the project is counting
	 */
	public boolean isTicking() {
		return ticking;
	}
	
	/**
	 * Get the current time of the project.
	 * 
	 * @return The time in seconds
	 */
	public int getTime() {
		return time;
	}
	
	/**
	 * Get the start time of the project.
	 * 
	 * @return The time in seconds
	 */
	public int getStartTime() {
		return startTime;
	}
	
	/**
	 * Check if the project is set for counting upwards or downwards.
	 * 
	 * @return {@code true} if the project is counting down
	 */
	public boolean isCountingDown() {
		return countDown;
	}
	
	/**
	 * Add one second to the project timer.
	 */
	public void addTime() {
		time++;
	}
	
	/**
	 * Removes one second to the project timer, if it reaches zero it stops the timer.
	 */
	public void countDown( ) {
		time--;
		if (time == 0) {
			timer.cancel();
			ticking = false;
		}
	}
	
	/**
	 * Sets the time to the time used when the project was initiated, i.e. the start time.
	 */
	public void resetTime() {
		time = startTime;
	}
	
	/**
	 * Get the time as at string on the format hh:mm:ss.
	 * 
	 * @return The time on the format hh:mm:ss
	 */
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
	
	/**
	 * Creates a {@code String} of the data in project. 
	 */
	@Override
	public String toString() {
		StringBuilder projectStr = new StringBuilder();
		projectStr.append(getName());
		projectStr.append(":");
		projectStr.append(getTime());
		projectStr.append(":");
		projectStr.append(getStartTime());
		projectStr.append(":");
		projectStr.append(isCountingDown());
		
		return projectStr.toString();
	}
}
