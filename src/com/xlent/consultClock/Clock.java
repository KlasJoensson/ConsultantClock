package com.xlent.consultClock;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Clock extends Application {

	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("Consultant clock");
		
		VBox mainPlane = new VBox();
		mainPlane.setPadding(new Insets(10));
		
		Project project = new Project("InfoGlue");
		mainPlane.getChildren().add(addProject(project));
		
		Scene scene = new Scene(mainPlane, 200, 100);
		primaryStage.setScene(scene);
		primaryStage.show();
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
		HBox buttons = new HBox();
		buttons.getChildren().add(startStopBtn);
		buttons.getChildren().add(resetBtn);
		buttons.setAlignment(Pos.TOP_CENTER);
		
		projectBox.getChildren().add(buttons);
		projectBox.getChildren().add(timeLabel);
		
		projectBox.setAlignment(Pos.TOP_CENTER);
		return projectBox;
	}
}
