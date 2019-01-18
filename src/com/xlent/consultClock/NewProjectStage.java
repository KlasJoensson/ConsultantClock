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
	
	/**
	 * Creates the window for setting up a new project.
	 */
	public NewProjectStage() {
		GridPane newProjectPlane = new GridPane();
		newProjectPlane.setPadding(new Insets(10));
		
		Label nameLabel = new Label("Name: ");
		nameField = new TextField("New Project");
		
		Label timeLabel = new Label("Start time (hh:mm:ss): ");
		timeField = new TextField("00:00:00");
		
		countDownBox = new CheckBox("Count down the time");
		
		HBox btnBox = new HBox();
		Button createBtn = Trans.buttonForKey("button.create");
		createBtn.setOnAction(createProjectHandler);
		Button cancelBtn = Trans.buttonForKey("button.cancel");
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
	
	/**
	 * Reads the field with the name of the project and returns it as a {@code String}.
	 * 
	 * @return The name of the project
	 */
	private String getProjectName() {
		return nameField.getText();
	}
	
	/**
	 * Reads the text field for the time and parse it to an integer (i.e. from [hh:mm:ss] to [ss]).
	 * 
	 * @return The start time in seconds.
	 */
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
	
	/**
	 * Check if the project shall count upwards or downwards.
	 * 
	 * @return {@code true} if the check-box for counting down is checked
	 */
	private boolean isCountDown() {
		return countDownBox.selectedProperty().get();
	}
	
	/**
	 * Creates a new clock and exits the stage.
	 */
    EventHandler<ActionEvent> createProjectHandler = new EventHandler<ActionEvent>() {
		
		@Override
		public void handle(ActionEvent event) {
			Project newProject = new Project(getProjectName(), getStartTime(), isCountDown() );
			setResult(newProject);
			close();
		}
	};
	
	/**
	 * Discards the clock and exits the stage.
	 */
	EventHandler<ActionEvent> cancelProjectHandler = new EventHandler<ActionEvent>() {
		
		@Override
		public void handle(ActionEvent ae) {
			getDialogPane().getScene().getWindow().hide();
		}
	};
}
