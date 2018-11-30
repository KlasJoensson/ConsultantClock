package com.xlent.consultClock;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Scanner;

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
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class Clock extends Application {
	
	private VBox mainPlane;
	private Stage stage;
	private ArrayList<Project> projects = new ArrayList<Project>();
	
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		stage = primaryStage;
		stage.setTitle("Consultant clock");
		
		mainPlane = new VBox();
		mainPlane.setPadding(new Insets(10));
		
		HBox controlPlane = new HBox();
		
		Button addProject = new Button("New");
		addProject.setOnAction(newProjectEventHandler);
		controlPlane.getChildren().add(addProject);
		Button saveProject = new Button("Save");
		saveProject.setOnAction(saveProjectEventHandler);
		controlPlane.getChildren().add(saveProject);
		Button openProject = new Button("Open");
		controlPlane.getChildren().add(openProject);
		openProject.setOnAction(openProjectEventHandler);
		mainPlane.getChildren().add(controlPlane);
		
		Scene scene = new Scene(mainPlane, 200, 50);
	
		stage.setScene(scene);
		stage.show();
	}
	
	private void addNewProject(String name) {
		Project newProject = new Project(name);
		addNewProject(newProject);
	}
	
	private void addNewProject(Project project) {
		mainPlane.getChildren().add(addProject(project));
		stage.setHeight(stage.getHeight() + 60);
		projects.add(project);
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
					startStopBtn.setText("Start");
				}
			}
		});
		Button removeBtn = new Button("Remove");
		removeBtn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				mainPlane.getChildren().remove(projectBox);
				stage.setHeight(stage.getHeight() - 60);
				projects.remove(project);
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
			NewProjectStage<Project> newProject = new NewProjectStage<Project>();
			Optional<Project> result = newProject.showAndWait();
			result.ifPresent(project -> addNewProject(project));
		}
	};
	
	private EventHandler<ActionEvent> saveProjectEventHandler = new EventHandler<ActionEvent>() {

		@Override
		public void handle(ActionEvent event) {
			FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save projects");
            fileChooser.setInitialDirectory( new File(System.getProperty("user.home")) );
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Consult time projects", "*.ctp"));
            File file = fileChooser.showSaveDialog(stage);
            if (file != null) {     	
            	try {
            		BufferedWriter writer = Files.newBufferedWriter(file.toPath());
        			projects.stream().forEach(project -> {
						try {
							writer.write( project.toString() + "\n" );
						} catch (IOException e) {
							e.printStackTrace();
						}
					} );
        			writer.flush();
            		writer.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                } 
            }
		}
		
	};
	
	private EventHandler<ActionEvent> openProjectEventHandler = new EventHandler<ActionEvent>() {

		@Override
		public void handle(ActionEvent event) {
			FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open projects");
            fileChooser.setInitialDirectory( new File(System.getProperty("user.home")) );
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Consult time projects", "*.ctp"));
            File file = fileChooser.showOpenDialog(stage);
            
            if (file != null) {  
            	try {
					Scanner scanner = new Scanner(file);
					String[] line;
					Project project;
					while (scanner.hasNextLine()) {
						line = scanner.nextLine().split(":");
						if (line.length > 2) {
							project = new Project(line[0], Integer.parseInt(line[1]), Boolean.parseBoolean(line[2]));
						} else {
							// Support for files saved before adding count down
							project = new Project(line[0], Integer.parseInt(line[1]), false);
						}
						addNewProject(project);
					}
					scanner.close();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
            }
		}
	};
}
