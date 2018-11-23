package com.xlent.consultClock;

import java.util.ArrayList;
import java.util.Optional;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Clock extends Application {
	
	private VBox mainPlane;
	private Stage stage;
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		stage = primaryStage;
		stage.setTitle("Consultant clock");
		
		mainPlane = new VBox();
		mainPlane.setPadding(new Insets(10));
		
		Button addProject = new Button("New");
		addProject.setOnAction(newProjectEventHandler);
		mainPlane.getChildren().add(addProject);
		
		Scene scene = new Scene(mainPlane, 200, 50);
	
		stage.setScene(scene);
		stage.show();
	}
	
	private void addNewProject(String name) {
		Project newProject = new Project(name);
		mainPlane.getChildren().add(addProject(newProject));
		stage.setHeight(stage.getHeight() + 60);
	}
	
	private VBox addProject(Project project) {
		VBox projectBox = new VBox();
		Text timeLabel = new Text(project.getTimeAsString());
		
		projectBox.getChildren().add(new Label(project.getName()));
				
		Button startStopBtn = new Button("Start");
		startStopBtn.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				if (project.isTicking()) {
					project.pause();
					startStopBtn.setText("Start");
				} else {
					project.start(timeLabel);
					startStopBtn.setText("Pause");					
				}
			}
		});
		Button resetBtn = new Button("Reset");
		resetBtn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				if (!project.isTicking()) {
					project.resetTime();
					timeLabel.setText(project.getTimeAsString());
				}
			}
		});
		Button removeBtn = new Button("Remove");
		removeBtn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				mainPlane.getChildren().remove(projectBox);
				stage.setHeight(stage.getHeight() - 60);
			}
		});
		HBox buttons = new HBox();
		buttons.getChildren().add(startStopBtn);
		buttons.getChildren().add(resetBtn);
		buttons.getChildren().add(removeBtn);
		buttons.setAlignment(Pos.TOP_CENTER);
		
		projectBox.getChildren().add(buttons);
		projectBox.getChildren().add(timeLabel);
		
		projectBox.setAlignment(Pos.TOP_CENTER);
		return projectBox;
	}
	
	private EventHandler<ActionEvent> newProjectEventHandler = new EventHandler<ActionEvent>() {

		@Override
		public void handle(ActionEvent event) {
			TextInputDialog dialog = new TextInputDialog();
			dialog.setTitle("New project");
			dialog.setHeaderText("");
			dialog.setContentText("Please enter a project name:");
			Optional<String> result = dialog.showAndWait();
			result.ifPresent(name -> addNewProject(name));
		}
	};
}
