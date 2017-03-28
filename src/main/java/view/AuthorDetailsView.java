package main.java.view;
import main.java.entities.*;
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
	Label authorNameLabel, authorName, locationLabel, location, ageLabel, age, genderLabel, gender, researchPaperLabel, researchPaper, 
	aliasLabel, alias, numpublicationLabel, numpublication, areaOfExpertiseLabel, areaOfExpertise;
	Button similarProfileButton;
	GridPane authorGrid;
	Author selectedAuthor;

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		authorDetailsStage = primaryStage;
		authorDetailsStage.setTitle("Author Details");
		
		// Layout for Login page
		authorGrid = new GridPane();
		authorGrid.setPadding(new Insets(10, 10, 10, 10));
		authorGrid.setAlignment(Pos.CENTER);
		authorGrid.setVgap(15);
		authorGrid.setHgap(20);
		
		// Author Name Label
		authorNameLabel = new Label("Author Name:");
		authorNameLabel.setFont(Font.font("Tahoma", FontWeight.NORMAL, 15));
		GridPane.setConstraints(authorNameLabel, 0, 0);
		
		authorName = new Label();
		authorName.setFont(Font.font("Tahoma", FontWeight.NORMAL, 15));
		GridPane.setConstraints(authorName, 1, 0);
		
		// location of Author Label
		locationLabel = new Label("Location:");
		locationLabel.setFont(Font.font("Tahoma", FontWeight.NORMAL, 15));
		GridPane.setConstraints(locationLabel, 0, 1);
		
		location = new Label();
		location.setFont(Font.font("Tahoma", FontWeight.NORMAL, 15));
		GridPane.setConstraints(location, 1, 1);
		
		// Age Label
		ageLabel = new Label("Age:");
		ageLabel.setFont(Font.font("Tahoma", FontWeight.NORMAL, 15));
		GridPane.setConstraints(ageLabel, 0, 2);
		
		age = new Label(""+selectedAuthor.getAge());
		age.setFont(Font.font("Tahoma", FontWeight.NORMAL, 15));
		GridPane.setConstraints(age, 1, 2);
		
		// Gender Label
		genderLabel = new Label("Gender:");
		genderLabel.setFont(Font.font("Tahoma", FontWeight.NORMAL, 15));
		GridPane.setConstraints(genderLabel, 0, 3);
		
		gender = new Label();
		gender.setFont(Font.font("Tahoma", FontWeight.NORMAL, 15));
		GridPane.setConstraints(gender, 1, 3);
		
		// Alias Label
		aliasLabel = new Label("Alias:");
		aliasLabel.setFont(Font.font("Tahoma", FontWeight.NORMAL, 15));
		GridPane.setConstraints(aliasLabel, 0, 4);
		
		alias = new Label();
		alias.setFont(Font.font("Tahoma", FontWeight.NORMAL, 15));
		GridPane.setConstraints(alias, 1, 4);
		
		// number of publications published Label
		numpublicationLabel = new Label("Number Of Publications:");
		numpublicationLabel.setFont(Font.font("Tahoma", FontWeight.NORMAL, 15));
		GridPane.setConstraints(numpublicationLabel, 0, 5);
		
		numpublication = new Label();
		numpublication.setFont(Font.font("Tahoma", FontWeight.NORMAL, 15));
		GridPane.setConstraints(numpublication, 1, 5);

		// Area of Expertise Label
		areaOfExpertiseLabel = new Label("Area Of Expertise:");
		areaOfExpertiseLabel.setFont(Font.font("Tahoma", FontWeight.NORMAL, 15));
		GridPane.setConstraints(areaOfExpertiseLabel, 0, 6);
		
		//areaOfExpertise = new Label(selectedAuthor.getAreaOfExpertise());
		areaOfExpertise.setFont(Font.font("Tahoma", FontWeight.NORMAL, 15));
		GridPane.setConstraints(areaOfExpertise, 1, 6);
		
		// Research papers
		researchPaperLabel = new Label("Research Papers:");
		researchPaperLabel.setFont(Font.font("Tahoma", FontWeight.NORMAL, 15));
		GridPane.setConstraints(researchPaperLabel, 0, 7);
		
		researchPaper = new Label();
		researchPaper.setFont(Font.font("Tahoma", FontWeight.NORMAL, 15));
		GridPane.setConstraints(researchPaper, 1, 7);
		
		// Search Similar profile
		similarProfileButton = new Button("Search Similar Profiles");
		similarProfileButton.setFont(Font.font("Tahoma", FontWeight.NORMAL, 15));
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
		// TODO Auto-generated method stub
		SearchResultView searchRes = new SearchResultView();
		try {
			searchRes.start(authorDetailsStage);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void sendAuthorDetails(Author selectedItem) {
		selectedAuthor = selectedItem;
		
	}
	
	
}
