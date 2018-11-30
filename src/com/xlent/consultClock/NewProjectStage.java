package com.xlent.consultClock;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class NewProjectStage<R> extends Dialog<Project> {

	private TextField timeField;
	private TextField nameField;
	private CheckBox countDownBox;
	
	public NewProjectStage() {
		VBox newProjectPlane = new VBox();
		newProjectPlane.setPadding(new Insets(10));
		
		HBox nameBox = new HBox();
		nameBox.setPadding(new Insets(5));
		Label nameLabel = new Label("Name: ");
		nameField = new TextField();
		nameBox.getChildren().addAll(nameLabel, nameField);
		
		HBox timeBox = new HBox();
		nameBox.setPadding(new Insets(5));
		Label timeLabel = new Label("Start time (hh:mm:ss): ");
		timeField = new TextField("00:00:00");
		timeBox.getChildren().addAll(timeLabel, timeField);
		
		countDownBox = new CheckBox("Count down the time");
		
		HBox btnBox = new HBox();
		btnBox.setPadding(new Insets(5));
		Button createBtn = new Button("Create");
		createBtn.setOnAction(createProjectHandler);
		Button cancelBtn = new Button("Cancel");
		cancelBtn.setOnAction(cancelProjectHandler);
		btnBox.getChildren().addAll(createBtn, cancelBtn);
		
		newProjectPlane.getChildren().addAll(nameBox, timeBox, countDownBox, btnBox);
		getDialogPane().setContent(newProjectPlane);
		
		setTitle("Create New project");
		
	}
	
	private String getProjectName() {
		return nameField.getText();
	}
	
	private int getStartTime() {
		String timeStr = timeField.getText();
		String[] timeArr = timeStr.split(":");
		int time;
		if (timeArr.length > 2) {
			time = Integer.parseInt(timeArr[0]) * 3600;
			time += Integer.parseInt(timeArr[1]) * 60;
			time += Integer.parseInt(timeArr[2]);
		} else if (timeArr.length == 2) {
			time = Integer.parseInt(timeArr[0]) * 60;
			time += Integer.parseInt(timeArr[1]);
		} else {
			time = Integer.parseInt(timeArr[0]);
		}
		
		return time;
	}
	
	private boolean isCountDown() {
		return countDownBox.selectedProperty().get();
	}
	
    EventHandler<ActionEvent> createProjectHandler = new EventHandler<ActionEvent>() {
		
		@Override
		public void handle(ActionEvent event) {
			Project newProject = new Project(getProjectName(), getStartTime(), isCountDown() );
			setResult(newProject);
			close();
		}
	};
	
	EventHandler<ActionEvent> cancelProjectHandler = new EventHandler<ActionEvent>() {
		
		@Override
		public void handle(ActionEvent event) {
			close();
		}
	};
}
