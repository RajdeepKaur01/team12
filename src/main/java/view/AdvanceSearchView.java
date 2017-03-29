package main.java.view;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class AdvanceSearchView extends Application implements EventHandler<ActionEvent> {

	private Stage advanceStage;
	private RadioButton publicationTitleCheck; 
	private RadioButton locationCheck;
	private RadioButton areaOfExpertiseCheck;
	private RadioButton pastExperienceCheck;
	private RadioButton noOfPaperCheck;
	private RadioButton authorNameCheck;
	private RadioButton yopCheck;
	private RadioButton conferenceNameCheck;
	private RadioButton acronymCheck;
	private TextField publicationTitleText; 
	private TextField locationText;
	private TextField areaOfExpertiseText;
	private TextField pastExperienceText;
	private TextField noOfPaperText;
	private TextField authorNameText;
	private TextField yopText;
	private TextField conferenceNameText;
	private TextField acronymText;
	private Button search;
	private Button backToNormalSearch;
	static final String FONTSTYLE = "Tahoma";
	final ToggleGroup group = new ToggleGroup();
	
	@Override
	public void start(Stage stage) throws Exception {
		
		advanceStage = stage;
		advanceStage.setTitle("Advanced Search");
		
		// Grid Layout
		GridPane gridLayout = new GridPane();
		gridLayout.setPadding(new Insets(10, 10, 10, 10));
		gridLayout.setAlignment(Pos.CENTER);
		gridLayout.setVgap(8);
		gridLayout.setHgap(10);
		
		// Elements in Grids
		publicationTitleCheck = new RadioButton("Publication Title:");
		publicationTitleCheck.setId("publicationtitle");
		publicationTitleCheck.setToggleGroup(group);
		publicationTitleCheck.setFont(Font.font(FONTSTYLE, FontWeight.NORMAL, 15));
		GridPane.setConstraints(publicationTitleCheck, 0, 0);
		
		locationCheck = new RadioButton("Location:");
		locationCheck.setToggleGroup(group);
		locationCheck.setFont(Font.font(FONTSTYLE, FontWeight.NORMAL, 15));
		GridPane.setConstraints(locationCheck, 0, 1);
		
		areaOfExpertiseCheck = new RadioButton("Area Of Expertise:");
		areaOfExpertiseCheck.setToggleGroup(group);
		areaOfExpertiseCheck.setFont(Font.font(FONTSTYLE, FontWeight.NORMAL, 15));
		GridPane.setConstraints(areaOfExpertiseCheck, 0, 2);
		
		pastExperienceCheck = new RadioButton("Past Experience as PC Member(in years)");
		pastExperienceCheck.setToggleGroup(group);
		pastExperienceCheck.setFont(Font.font(FONTSTYLE, FontWeight.NORMAL, 15));
		GridPane.setConstraints(pastExperienceCheck, 0, 3);
		
		noOfPaperCheck = new RadioButton("Number of Research Paper Published:");
		noOfPaperCheck.setToggleGroup(group);
		noOfPaperCheck.setFont(Font.font(FONTSTYLE, FontWeight.NORMAL, 15));
		GridPane.setConstraints(noOfPaperCheck, 0, 4);
		
		authorNameCheck = new RadioButton("Author Name:");
		authorNameCheck.setToggleGroup(group);
		authorNameCheck.setFont(Font.font(FONTSTYLE, FontWeight.NORMAL, 15));
		GridPane.setConstraints(authorNameCheck, 0, 5);
		
		yopCheck = new RadioButton("Year of Publication:");
		yopCheck.setToggleGroup(group);
		yopCheck.setFont(Font.font(FONTSTYLE, FontWeight.NORMAL, 15));
		GridPane.setConstraints(yopCheck, 0, 6);
		
		conferenceNameCheck = new RadioButton("Name of Conference:");
		conferenceNameCheck.setToggleGroup(group);
		conferenceNameCheck.setFont(Font.font(FONTSTYLE, FontWeight.NORMAL, 15));
		GridPane.setConstraints(conferenceNameCheck, 0, 7);
		
		acronymCheck = new RadioButton("Conference Acronym:");
		acronymCheck.setToggleGroup(group);
		acronymCheck.setFont(Font.font(FONTSTYLE, FontWeight.NORMAL, 15));
		GridPane.setConstraints(acronymCheck, 0, 8);
		
		publicationTitleText = new TextField();
		publicationTitleText.setId("pubTitle");;
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
		search = new Button("Search");
		search.setId("searchadvance");
		search.setFont(Font.font(FONTSTYLE, FontWeight.NORMAL, 15));
		GridPane.setConstraints(search, 1, 10);
		
		// button to get Back to Normal search 
		backToNormalSearch = new Button("Back");
		backToNormalSearch.setId("back");
		backToNormalSearch.setFont(Font.font(FONTSTYLE, FontWeight.NORMAL, 15));
		GridPane.setConstraints(backToNormalSearch, 2, 10);
		
		backToNormalSearch.setOnAction(this);
		
		// Add components to gridLayout
		gridLayout.getChildren().addAll(backToNormalSearch, publicationTitleCheck, publicationTitleText, locationCheck, areaOfExpertiseCheck, pastExperienceCheck, noOfPaperCheck, authorNameCheck, yopCheck, conferenceNameCheck, acronymCheck, locationText, areaOfExpertiseText, pastExperienceText, noOfPaperText, authorNameText, yopText, conferenceNameText, acronymText, search);
		
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
        advanceStage.setTitle("Advanced Search");
        advanceStage.setScene(scene);
        advanceStage.show();
		
	}

	@Override
	public void handle(ActionEvent event) {
		//if(event.getSource() == publicationTitleCheck)
		//!(publicationTitleText.isDisabled())
			publicationTitleText.setDisable(!publicationTitleCheck.isSelected());
		//if(event.getSource() == locationCheck)
			locationText.setDisable(!locationCheck.isSelected());
		//if(event.getSource() == areaOfExpertiseCheck)
			areaOfExpertiseText.setDisable(!areaOfExpertiseCheck.isSelected());
		//if(event.getSource() == pastExperienceCheck)
			pastExperienceText.setDisable(!pastExperienceCheck.isSelected());
		//if(event.getSource() == noOfPaperCheck)
			noOfPaperText.setDisable(!noOfPaperCheck.isSelected());
		//if(event.getSource() == authorNameCheck)
			authorNameText.setDisable(!(authorNameCheck.isSelected()));
		//if(event.getSource() == acronymCheck)
			acronymText.setDisable(!acronymCheck.isSelected());
		//if(event.getSource() == yopCheck)
			yopText.setDisable(!yopCheck.isSelected());
		//if(event.getSource() == conferenceNameCheck)
			conferenceNameText.setDisable(!conferenceNameCheck.isSelected());
		if(event.getSource() == backToNormalSearch){
			SearchView redirectToSearch = new SearchView();
			try {
				redirectToSearch.start(advanceStage);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
