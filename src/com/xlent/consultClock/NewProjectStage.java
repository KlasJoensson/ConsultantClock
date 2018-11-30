package com.xlent.consultClock;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Window;

public class NewProjectStage<R> extends Dialog<Project> {

	private TextField timeField;
	private TextField nameField;
	private CheckBox countDownBox;
	
	public NewProjectStage() {
		GridPane newProjectPlane = new GridPane();
		newProjectPlane.setPadding(new Insets(10));
		
		Label nameLabel = new Label("Name: ");
		nameField = new TextField("New Project");
		
		Label timeLabel = new Label("Start time (hh:mm:ss): ");
		timeField = new TextField("00:00:00");
		
		countDownBox = new CheckBox("Count down the time");
		
		HBox btnBox = new HBox();
		Button createBtn = new Button("Create");
		createBtn.setOnAction(createProjectHandler);
		Button cancelBtn = new Button("Cancel");
		cancelBtn.setOnAction(cancelProjectHandler);
		btnBox.getChildren().addAll(createBtn, cancelBtn);
		
		newProjectPlane.add(nameLabel, 0, 0);
		newProjectPlane.add(nameField, 1, 0, 2, 1);
		newProjectPlane.add(timeLabel, 0, 1, 2, 1);
		newProjectPlane.add(timeField, 2, 1);
		newProjectPlane.add(countDownBox, 1, 2, 2, 1);
		newProjectPlane.add(new Label(), 0, 3);
		newProjectPlane.add(btnBox, 2, 5);
		
		getDialogPane().setContent(newProjectPlane);
		setTitle("Create New project");
		
		Window window = getDialogPane().getScene().getWindow();
		window.setOnCloseRequest(event -> window.hide());
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
		public void handle(ActionEvent ae) {
			getDialogPane().getScene().getWindow().hide();
		}
	};
}
