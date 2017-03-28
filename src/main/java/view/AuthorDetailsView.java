package main.java.view;
import main.java.entities.*;

import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class AuthorDetailsView extends Application implements EventHandler<ActionEvent>{
	private Stage authorDetailsStage;
	private Button similarProfileButton;
	private GridPane authorGrid;
	private Author selectedAuthor;
	static final String FONTSTYLE = "Tahoma";

	@Override
	public void start(Stage primaryStage) throws Exception {
		authorDetailsStage = primaryStage;
		authorDetailsStage.setTitle("Author Details");
		
		// Layout for Login page
		authorGrid = new GridPane();
		authorGrid.setPadding(new Insets(10, 10, 10, 10));
		authorGrid.setAlignment(Pos.CENTER);
		authorGrid.setVgap(15);
		authorGrid.setHgap(20);
		authorGrid.setPrefHeight(50);
		
		// Author Name Label
		Label authorNameLabel = new Label("Author Name:");
		authorNameLabel.setFont(Font.font(FONTSTYLE, FontWeight.NORMAL, 15));
		authorNameLabel.setAlignment(Pos.TOP_LEFT);
		GridPane.setConstraints(authorNameLabel, 0, 0);
		
		
		Label authorName = new Label();
		authorName.setFont(Font.font(FONTSTYLE, FontWeight.NORMAL, 15));
		authorName.setAlignment(Pos.TOP_LEFT);
		GridPane.setConstraints(authorName, 1, 0);
		
		// location of Author Label
		Label locationLabel = new Label("Location:");
		locationLabel.setFont(Font.font(FONTSTYLE, FontWeight.NORMAL, 15));
		GridPane.setConstraints(locationLabel, 0, 1);
		
		Label location = new Label();
		location.setFont(Font.font(FONTSTYLE, FontWeight.NORMAL, 15));
		GridPane.setConstraints(location, 1, 1);
		
		// Age Label
		Label ageLabel = new Label("Age:");
		ageLabel.setFont(Font.font(FONTSTYLE, FontWeight.NORMAL, 15));
		GridPane.setConstraints(ageLabel, 0, 2);
		
		Label age = new Label(""+selectedAuthor.getAge());
		age.setFont(Font.font(FONTSTYLE, FontWeight.NORMAL, 15));
		GridPane.setConstraints(age, 1, 2);
		
		// Gender Label
		Label genderLabel = new Label("Gender:");
		genderLabel.setFont(Font.font(FONTSTYLE, FontWeight.NORMAL, 15));
		GridPane.setConstraints(genderLabel, 0, 3);
		
		Label gender = new Label();
		gender.setFont(Font.font(FONTSTYLE, FontWeight.NORMAL, 15));
		GridPane.setConstraints(gender, 1, 3);
		
		// Alias Label
		Label aliasLabel = new Label("Alias:");
		aliasLabel.setFont(Font.font(FONTSTYLE, FontWeight.NORMAL, 15));
		GridPane.setConstraints(aliasLabel, 0, 4);
		
		Label alias = new Label();
		alias.setFont(Font.font(FONTSTYLE, FontWeight.NORMAL, 15));
		GridPane.setConstraints(alias, 1, 4);
		
		// number of publications published Label
		Label numpublicationLabel = new Label("Number Of Publications:");
		numpublicationLabel.setFont(Font.font(FONTSTYLE, FontWeight.NORMAL, 15));
		GridPane.setConstraints(numpublicationLabel, 0, 5);
		
		Label numpublication = new Label();
		numpublication.setFont(Font.font(FONTSTYLE, FontWeight.NORMAL, 15));
		GridPane.setConstraints(numpublication, 1, 5);

		// Area of Expertise Label
		Label areaOfExpertiseLabel = new Label("Area Of Expertise:");
		areaOfExpertiseLabel.setFont(Font.font(FONTSTYLE, FontWeight.NORMAL, 15));
		GridPane.setConstraints(areaOfExpertiseLabel, 0, 6);
		
		Label areaOfExpertise = new Label(selectedAuthor.getAreaOfExpertise());
		areaOfExpertise.setFont(Font.font(FONTSTYLE, FontWeight.NORMAL, 15));
		GridPane.setConstraints(areaOfExpertise, 1, 6);
		
		// Research papers
		Label researchPaperLabel = new Label("Research Papers:");
		researchPaperLabel.setFont(Font.font(FONTSTYLE, FontWeight.NORMAL, 15));
		GridPane.setConstraints(researchPaperLabel, 0, 7);
		
		Label researchPaper = new Label();
		researchPaper.setFont(Font.font(FONTSTYLE, FontWeight.NORMAL, 15));
		GridPane.setConstraints(researchPaper, 1, 7);
		
		// Search Similar profile
		similarProfileButton = new Button("Search Similar Profiles");
		similarProfileButton.setFont(Font.font(FONTSTYLE, FontWeight.NORMAL, 15));
		GridPane.setConstraints(similarProfileButton, 1, 9);
		
		similarProfileButton.setOnAction(this);
		
		//add all elements to grid
		authorGrid.getChildren().addAll(authorNameLabel, authorName, locationLabel, location, ageLabel, age, genderLabel, gender, researchPaperLabel, researchPaper, 
				aliasLabel, alias, numpublicationLabel, numpublication, areaOfExpertiseLabel, areaOfExpertise, similarProfileButton);
		
		// Final Layout using Stack Pane for setting background color
		StackPane finalLayout = new StackPane();
		finalLayout.setStyle("-fx-background-color: DARKGRAY; -fx-padding: 10;");
		finalLayout.getChildren().addAll(authorGrid);
		
		// Login Scene
		Scene authorDetailsScene = new Scene(finalLayout, 800, 800);
		authorDetailsStage.setScene(authorDetailsScene);
		authorDetailsStage.show();
	}

	@Override
	public void handle(ActionEvent event) {
		SearchResultView searchRes = new SearchResultView();
		try {
			searchRes.start(authorDetailsStage);
		} catch (Exception e) {
			Logger logger = Logger.getLogger("logger");
			logger.log(Level.FINE, "Search Result  Stage not found", e);
		}
	}

	public void sendAuthorDetails(Author selectedItem) {
		selectedAuthor = selectedItem;
		
	}
	
	
}
