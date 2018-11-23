package com.xlent.consultClock;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
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
		Label time = new Label(""+project.getTime());
		
		projectBox.getChildren().add(new Label(project.getName()));
		
		Button startStopBtn = new Button("Start");
		startStopBtn.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				if (project.isTicking()) {
					project.pause();
					startStopBtn.setText("Start");
				} else {
					project.start();
					startStopBtn.setText("Pause");
				}
			}
		});
		projectBox.getChildren().add(startStopBtn);
		projectBox.getChildren().add(time);
		
		return projectBox;
	}
}
