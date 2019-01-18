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
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
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
	
	/**
	 * Creates the main stage.
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {
		stage = primaryStage;
		stage.titleProperty().bind(Trans.createStringBinding("window.title"));
			
		mainPlane = new VBox(getMenuBar());
		
		HBox controlPlane = new HBox();
		controlPlane.setPadding(new Insets(10));
		
		Button addProject = Trans.buttonForKey("button.new");
		addProject.setOnAction(newProjectEventHandler);
		controlPlane.getChildren().add(addProject);
		Button saveProject = Trans.buttonForKey("button.save");
		saveProject.setOnAction(saveProjectEventHandler);
		controlPlane.getChildren().add(saveProject);
		Button openProject = Trans.buttonForKey("button.open");
		controlPlane.getChildren().add(openProject);
		openProject.setOnAction(openProjectEventHandler);
		mainPlane.getChildren().add(controlPlane);
		
		Scene scene = new Scene(mainPlane, 200, 75);
		
		stage.setScene(scene);
		stage.show();
	}
	
	private MenuBar getMenuBar() {
		MenuBar menuBar = new MenuBar();
		Menu fileMenu = new Menu("File");
		Menu settingsMenu = new Menu("Settings");
		
		MenuItem newItem = new MenuItem("New...");
		newItem.setOnAction(newProjectEventHandler);
		MenuItem openItem = new MenuItem("Open...");
		openItem.setOnAction(openProjectEventHandler);
		MenuItem saveItem = new MenuItem("save...");
		saveItem.setOnAction(saveProjectEventHandler);
		MenuItem quitItem = new MenuItem("Quit");
		quitItem.setOnAction(quitEventHandler);
		
		Menu langMenu = new Menu("Language");
		MenuItem langSwItem = new MenuItem("Swedish");
		langSwItem.setOnAction(swLangEventHandler);
		MenuItem langEngItem = new MenuItem("English");
		langEngItem.setOnAction(engLangEventHandler);
		MenuItem aboutItem = new MenuItem("About");
		aboutItem.setOnAction(aboutEventHandler);
		
		SeparatorMenuItem separator = new SeparatorMenuItem();
		
		fileMenu.getItems().add(newItem);
		fileMenu.getItems().add(openItem);
		fileMenu.getItems().add(saveItem);
		fileMenu.getItems().add(separator);
		fileMenu.getItems().add(quitItem);
		
		langMenu.getItems().add(langSwItem);
		langMenu.getItems().add(langEngItem);
		
		settingsMenu.getItems().add(langMenu);
		settingsMenu.getItems().add(aboutItem);
		
		menuBar.getMenus().add(fileMenu);
		menuBar.getMenus().add(settingsMenu);
		
		return menuBar;
	}
	
	/**
	 * Adds a new clock to the list of clocks on the main stage.
	 * 
	 * @param project The clock to be added
	 */
	private void addNewProject(Project project) {
		mainPlane.getChildren().add(addProject(project));
		stage.setHeight(stage.getHeight() + 60);
		projects.add(project);
	}
	
	/**
	 * Creates the UI for the new clock to be added to the main stage.
	 * 
	 * @param project The clock to be added
	 * @return A {@code VBox} with the components for the new clock
	 */
	private VBox addProject(Project project) {
		VBox projectBox = new VBox();
		Text timeLabel = new Text(project.getTimeAsString());
		
		projectBox.getChildren().add(new Label(project.getName()));
				
		Button startStopBtn = Trans.buttonForKey("button.start");
		startStopBtn.setOnAction(new EventHandler<ActionEvent>() {
			
			// TODO Fix translation...
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
		Button resetBtn = Trans.buttonForKey("button.reset");
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
		Button removeBtn = Trans.buttonForKey("button.remove");
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
	
	/**
	 * Saves the clocks on the main stage.
	 */
	private void saveProjects() {
		FileChooser fileChooser = new FileChooser();
        fileChooser.titleProperty().bind(Trans.createStringBinding("save.title"));
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
	
	/**
	 * Opens clocks from a file and adds them to the main stage.
	 */
	private void openProjects() {
		FileChooser fileChooser = new FileChooser();
        fileChooser.titleProperty().bind(Trans.createStringBinding("open.title"));
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
						project = new Project(line[0], Integer.parseInt(line[1]), Integer.parseInt(line[2]), Boolean.parseBoolean(line[3]));
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
	
	/**
	 * Handles clicks on the button for creating new clocks.
	 */
	private EventHandler<ActionEvent> newProjectEventHandler = new EventHandler<ActionEvent>() {

		@Override
		public void handle(ActionEvent event) {
			NewProjectStage<Project> newProject = new NewProjectStage<Project>();
			Optional<Project> result = newProject.showAndWait();
			result.ifPresent(project -> addNewProject(project));
		}
	};
	
	/**
	 * Handles clicks on the save button.
	 */
	private EventHandler<ActionEvent> saveProjectEventHandler = new EventHandler<ActionEvent>() {

		@Override
		public void handle(ActionEvent event) {
			saveProjects();
		}
		
	};
	
	/**
	 * Handles clicks on the open button.
	 */
	private EventHandler<ActionEvent> openProjectEventHandler = new EventHandler<ActionEvent>() {

		@Override
		public void handle(ActionEvent event) {
			openProjects();
		}
	};
	
	/**
	 * Handles clicks on the language menu item for Swedish.
	 */
	private EventHandler<ActionEvent> swLangEventHandler = new EventHandler<ActionEvent>() {

		@Override
		public void handle(ActionEvent event) {
			// TODO Implement!
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Information Dialog");
			alert.setHeaderText(null);
			alert.setContentText("Not implemented...");
			alert.showAndWait();
		}
	};
	
	/**
	 * Handles clicks on the language menu item English.
	 */
	private EventHandler<ActionEvent> engLangEventHandler = new EventHandler<ActionEvent>() {

		@Override
		public void handle(ActionEvent event) {
			// TODO Implement!
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Information Dialog");
			alert.setHeaderText(null);
			alert.setContentText("Not implemented...");
			alert.showAndWait();
		}
	};
	
	/**
	 * Handles clicks on the exit menu item.
	 */
	private EventHandler<ActionEvent> quitEventHandler = new EventHandler<ActionEvent>() {

		@Override
		public void handle(ActionEvent event) {
			System.exit(0);
		}
	};
	
	/**
	 * Handles clicks on the about menu item.
	 */
	private EventHandler<ActionEvent> aboutEventHandler = new EventHandler<ActionEvent>() {

		@Override
		public void handle(ActionEvent event) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("About Cunsultant clock");
			alert.setHeaderText(null);
			alert.setContentText("This small program has been done by Klas Jönsson.\nIt's released under \"GNU general public licence\".");
			alert.showAndWait();
		}
	};
}
