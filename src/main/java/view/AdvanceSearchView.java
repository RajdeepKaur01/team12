package main.java.view;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class AdvanceSearchView extends Application implements EventHandler<ActionEvent> {

	private Stage AdvanceStage;
	private CheckBox publicationTitleCheck, locationCheck, areaOfExpertiseCheck, pastExperienceCheck, noOfPaperCheck, authorNameCheck, yopCheck, conferenceNameCheck, acronymCheck;
	private TextField publicationTitleText, locationText, areaOfExpertiseText, pastExperienceText, noOfPaperText, authorNameText, yopText, conferenceNameText, acronymText;
	
	@Override
	public void start(Stage stage) throws Exception {
		
		// TODO Auto-generated method stub
		AdvanceStage = stage;
		AdvanceStage.setTitle("Advanced Search");
		
		// Grid Layout
		GridPane gridLayout = new GridPane();
		gridLayout.setPadding(new Insets(10, 10, 10, 10));
		gridLayout.setAlignment(Pos.CENTER);
		gridLayout.setVgap(8);
		gridLayout.setHgap(10);
		
		// Elements in Grids
		publicationTitleCheck = new CheckBox("Publication Title:");
		publicationTitleCheck.setFont(Font.font("Tahoma", FontWeight.NORMAL, 15));
		GridPane.setConstraints(publicationTitleCheck, 0, 0);
		
		locationCheck = new CheckBox("Location:");
		locationCheck.setFont(Font.font("Tahoma", FontWeight.NORMAL, 15));
		GridPane.setConstraints(locationCheck, 0, 1);
		
		areaOfExpertiseCheck = new CheckBox("Area Of Expertise:");
		areaOfExpertiseCheck.setFont(Font.font("Tahoma", FontWeight.NORMAL, 15));
		GridPane.setConstraints(areaOfExpertiseCheck, 0, 2);
		
		pastExperienceCheck = new CheckBox("Past Experience as PC Member(in years)");
		pastExperienceCheck.setFont(Font.font("Tahoma", FontWeight.NORMAL, 15));
		GridPane.setConstraints(pastExperienceCheck, 0, 3);
		
		noOfPaperCheck = new CheckBox("Number of Research Paper Published:");
		noOfPaperCheck.setFont(Font.font("Tahoma", FontWeight.NORMAL, 15));
		GridPane.setConstraints(noOfPaperCheck, 0, 4);
		
		authorNameCheck = new CheckBox("Author Name:");
		authorNameCheck.setFont(Font.font("Tahoma", FontWeight.NORMAL, 15));
		GridPane.setConstraints(authorNameCheck, 0, 5);
		
		yopCheck = new CheckBox("Year of Publication:");
		yopCheck.setFont(Font.font("Tahoma", FontWeight.NORMAL, 15));
		GridPane.setConstraints(yopCheck, 0, 6);
		
		conferenceNameCheck = new CheckBox("Name of Conference:");
		conferenceNameCheck.setFont(Font.font("Tahoma", FontWeight.NORMAL, 15));
		GridPane.setConstraints(conferenceNameCheck, 0, 7);
		
		acronymCheck = new CheckBox("Conference Acronym:");
		acronymCheck.setFont(Font.font("Tahoma", FontWeight.NORMAL, 15));
		GridPane.setConstraints(acronymCheck, 0, 8);
		
		publicationTitleText = new TextField();
		GridPane.setConstraints(publicationTitleText, 1, 0);
		
		locationText = new TextField();
		GridPane.setConstraints(locationText, 1, 1);
		
		areaOfExpertiseText = new TextField();
		GridPane.setConstraints(areaOfExpertiseText, 1, 2);
		
		pastExperienceText = new TextField();
		GridPane.setConstraints(pastExperienceText, 1, 3);
		
		noOfPaperText = new TextField();
		GridPane.setConstraints(noOfPaperText, 1, 4);
		
		authorNameText = new TextField();
		GridPane.setConstraints(authorNameText, 1, 5);
		
		yopText = new TextField();
		GridPane.setConstraints(yopText, 1, 6);
		
		conferenceNameText = new TextField();
		GridPane.setConstraints(conferenceNameText, 1, 7);
		
		acronymText = new TextField();
		GridPane.setConstraints(acronymText, 1, 8);
		
		// Disable textboxes
		publicationTitleText.setDisable(true);
		locationText.setDisable(true);
		areaOfExpertiseText.setDisable(true);
		pastExperienceText.setDisable(true);
		noOfPaperText.setDisable(true);
		authorNameText.setDisable(true);
		yopText.setDisable(true);
		conferenceNameText.setDisable(true);
		acronymText.setDisable(true);
		
		// Enable TextBoxes
		publicationTitleCheck.setOnAction(this);
		locationCheck.setOnAction(this);
		areaOfExpertiseCheck.setOnAction(this);
		pastExperienceCheck.setOnAction(this);
		noOfPaperCheck.setOnAction(this);
		authorNameCheck.setOnAction(this);
		yopCheck.setOnAction(this);
		conferenceNameCheck.setOnAction(this);
		acronymCheck.setOnAction(this);
		
		// Search Button
		Button search = new Button("Search");
		search.setFont(Font.font("Tahoma", FontWeight.NORMAL, 15));
		GridPane.setConstraints(search, 1, 10);
		
		// Add components to gridLayout
		gridLayout.getChildren().addAll(publicationTitleCheck, publicationTitleText, locationCheck, areaOfExpertiseCheck, pastExperienceCheck, noOfPaperCheck, authorNameCheck, yopCheck, conferenceNameCheck, acronymCheck, locationText, areaOfExpertiseText, pastExperienceText, noOfPaperText, authorNameText, yopText, conferenceNameText, acronymText, search);
		
		// Create Scene
		FlowPane root = new FlowPane();
        root.setHgap(20);
        root.setAlignment(Pos.BASELINE_CENTER);
        root.setPadding(new Insets(50, 10, 10, 10));
        root.getChildren().addAll(gridLayout);
        
        // Final Layout using Stack Pane for setting background color
 		StackPane finalLayout = new StackPane();
 		finalLayout.setStyle("-fx-background-color: DARKGRAY; -fx-padding: 10;");
     	finalLayout.getChildren().addAll(root);
            
        Scene scene = new Scene(finalLayout, 800, 800);
        AdvanceStage.setTitle("Advanced Search");
        AdvanceStage.setScene(scene);
        AdvanceStage.show();
		
	}

	@Override
	public void handle(ActionEvent event) {
		// TODO Auto-generated method stub
		if(event.getSource()==publicationTitleCheck)
			publicationTitleText.setDisable(!(publicationTitleText.isDisabled()));
		if(event.getSource()==locationCheck)
			locationText.setDisable(!(locationText.isDisabled()));
		if(event.getSource()==areaOfExpertiseCheck)
			areaOfExpertiseText.setDisable(!(areaOfExpertiseText.isDisabled()));
		if(event.getSource()==pastExperienceCheck)
			pastExperienceText.setDisable(!(pastExperienceText.isDisabled()));
		if(event.getSource()==noOfPaperCheck)
			noOfPaperText.setDisable(!(noOfPaperText.isDisabled()));
		if(event.getSource()==authorNameCheck)
			authorNameText.setDisable(!(authorNameText.isDisabled()));
		if(event.getSource()==acronymCheck)
			acronymText.setDisable(!(acronymText.isDisabled()));
		if(event.getSource()==yopCheck)
			yopText.setDisable(!(yopText.isDisabled()));
		if(event.getSource()==conferenceNameCheck)
			conferenceNameText.setDisable(!(conferenceNameText.isDisabled()));
		
	}

}
