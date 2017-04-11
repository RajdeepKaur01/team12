package main.java.view;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Shadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.*;
import main.java.entities.Author;
import main.java.search.FindResearcher;


public class SearchView extends Application implements EventHandler<ActionEvent> {
	static final String SEARCHTITLE = "title";
	StackPane finalLayout;
	Stage searchStage;
	Label title;
	TextField searchInput;
	Button searchButton;
	Button advanceSearch;
	DropShadow shadow = new DropShadow();
	SearchResultView searchRes = new SearchResultView();
	static final String FONTSTYLE = "Arial";
	
	@Override
	public void start(Stage stage) throws Exception {
		searchStage = stage;
		searchStage.setTitle("Search Publications");
		
		// Header Image
		Image image = new Image(getClass().getResourceAsStream("/main/java/uiresources/FullSizeRender.jpg"));
		Label label1 = new Label();
		ImageView imageView = new ImageView(image);
		imageView.setFitHeight(50);
	    imageView.setFitWidth(400);
		
		// Layout for Search page
		VBox vlayout = new  VBox(30);
		vlayout.setAlignment(Pos.CENTER);
		vlayout.setFillWidth(true);
		
		// Title Label
		title = new Label("Search Publications");
		title.setFont(Font.font(FONTSTYLE, FontWeight.NORMAL, 20));
		title.setPrefHeight(100);
	    title.setPrefWidth(200);
		
		// Search textbox
		searchInput = new TextField();
		searchInput.setId("searchInput");
		searchInput.setPromptText("Enter publication title or part of title");
		searchInput.setFocusTraversable(false);
		searchInput.setMaxWidth(650);
		searchInput.setPrefHeight(30);
		searchInput.setFocusTraversable(true);
		searchInput.setFont(Font.font(FONTSTYLE, FontWeight.NORMAL, 15));
		
		// Search Button
		searchButton = new Button("Search");
		searchButton.setId("searchButton");
		searchButton.setFont(Font.font(FONTSTYLE, FontWeight.NORMAL, 15));
		searchButton.setOnAction(this);
		searchButton.setPrefHeight(30);
		searchButton.setPrefWidth(150);
		searchButton.setEffect(null);
		searchButton.setStyle(  "-fx-background-radius: 30, 30, 30, 30;"+
									"-fx-padding: 3px 10px 3px 10px;"+
									"-fx-background-color: linear-gradient(white, white );");
		
		// Advanced Search Hyperlink
		advanceSearch = new Button();
		advanceSearch.setText("Advanced Search");
		advanceSearch.setId("advanceSearch");
		advanceSearch.setFont(Font.font(FONTSTYLE, FontWeight.NORMAL, 15));
		advanceSearch.setOnAction(this);
		advanceSearch.setPrefHeight(30);
		advanceSearch.setPrefWidth(150);
		advanceSearch.setStyle(  "-fx-background-radius: 30, 30, 29, 28;"+
				"-fx-padding: 3px 10px 3px 10px;"+
				"-fx-background-color: linear-gradient(white, white );");
		
		// Horizontal layout for search button and hyperlink
		HBox hlayout = new HBox(80);
		hlayout.getChildren().addAll(searchButton,advanceSearch, imageView);
		hlayout.setAlignment(Pos.CENTER);
		
		// Add all components to VBOX
		vlayout.getChildren().addAll(imageView, searchInput, hlayout);
		
		// Adding Scene
		BorderPane borderLayout = new BorderPane();
		borderLayout.setPadding(new Insets(30));
		borderLayout.setCenter(vlayout);
		
		// Final Layout using Stack Pane for setting background color
		finalLayout = new StackPane();
	//Color.WHITESMOKE
		finalLayout.setStyle("-fx-background-color:  linear-gradient(lightblue, white);"+
       " -fx-border-color: white;"+
       " -fx-border-radius: 20;"+
       "-fx-padding: 10 10 10 10;"+
        "-fx-background-radius: 20;");
		finalLayout.getChildren().addAll(vlayout);
		
		Scene searchScene = new Scene(finalLayout, 1000, 700);
		
		// Handle Key Events
		searchScene.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				if(event.getCode()==KeyCode.ENTER)
					handleSearchEvent();
				
			}
		});
		
		searchStage.setScene(searchScene);
		searchStage.setResizable(false);
		searchStage.show();
	}

	// Action Handler Method
	@Override
	public void handle(ActionEvent event) {
			
				// Handle action on search Button
				if(event.getSource() == searchButton){
					handleSearchEvent();
				}
				
				// Handle Action on Advance Search
				
				if(event.getSource() == advanceSearch){
					AdvanceSearchView advance = new AdvanceSearchView();
					try {
						advanceSearch.setEffect(shadow);
						advance.start(searchStage);
					} catch (Exception e) {
						
						e.printStackTrace();
					}
				}
		
			

		}

	private void handleSearchEvent() {
		// TODO Auto-generated method stub
		if(searchInput.getText().isEmpty()){
			generateAlert("Enter publication title to search!");
		}
		else{
			searchButton.setEffect(shadow);
			List<Author> authors = new ArrayList<>(new FindResearcher().
					findAuthorsByResearchPaperTitle(searchInput.getText()));
			ObservableList<Author> data = FXCollections.observableList(authors);
			try {
				searchRes.start(searchStage,data);
			} catch (Exception e) {

				
				e.printStackTrace();

			}
		}
	}

	// Generate Alert
	
	private void generateAlert(String string) {
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle("Warning Dialog");
		alert.setContentText(string);
		alert.show();
	}
}
