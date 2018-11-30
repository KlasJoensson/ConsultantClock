package com.xlent.consultClock;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class NewProjectStage extends Stage {

	public NewProjectStage() {
		VBox newProjectPlane = new VBox();
		newProjectPlane.setPadding(new Insets(10));
		
		HBox nameBox = new HBox();
		nameBox.setPadding(new Insets(5));
		Label nameLabel = new Label("Name: ");
		TextField nameField = new TextField();
		nameBox.getChildren().addAll(nameLabel, nameField);
		
		HBox timeBox = new HBox();
		nameBox.setPadding(new Insets(5));
		Label timeLabel = new Label("Start time (hh:mm:ss): ");
		TextField timeField = new TextField("00:00:00");
		timeBox.getChildren().addAll(timeLabel, timeField);
		
		CheckBox countDownBox = new CheckBox("Count down the time");
		
		HBox btnBox = new HBox();
		btnBox.setPadding(new Insets(5));
		Button createBtn = new Button("Create");
		Button cancelBtn = new Button("Cancel");
		
		newProjectPlane.getChildren().addAll(nameBox, timeBox, countDownBox, btnBox);
		
		Scene newProjectScene = new Scene(newProjectPlane, 250, 200);
		setTitle("Create New project");
		setScene(newProjectScene);
	}
}
