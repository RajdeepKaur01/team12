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
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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


public class SearchView extends Application {
	static final String SEARCHTITLE = "title";
	StackPane finalLayout;
	Stage searchStage;
	Label title;
	TextField searchInput;
	Button searchButton;
	Hyperlink advanceSearch;
	static final String FONTSTYLE = "Tahoma";
	
	@Override
	public void start(Stage stage) throws Exception {
		searchStage = stage;
		searchStage.setTitle("Search Publications");
		
		// Header Image
		Image image = new Image(getClass().getResourceAsStream("/main/java/images/FullSizeRender.jpg"));
		Label label1 = new Label();
		ImageView imageView = new ImageView(image);
		imageView.setFitHeight(50);
	    imageView.setFitWidth(400);
		
		// Layout for Search page
		VBox vlayout = new  VBox(20);
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
		searchInput.setMaxWidth(450);
		
		// Search Button
		searchButton = new Button("Search");
		searchButton.setId("searchButton");
		searchButton.setFont(Font.font(FONTSTYLE, FontWeight.NORMAL, 15));
		searchButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				SearchResultView searchRes = new SearchResultView();
				try {
					List<Author> authors = new ArrayList<>(new FindResearcher().
							findAuthorsByResearchPaperTitle(searchInput.getText(), 10));
					ObservableList<Author> data = FXCollections.observableList(authors);
					searchRes.start(searchStage,data);
				} catch (Exception e) {
					Logger logger = Logger.getLogger("logger");
					logger.log(Level.FINE, "Search Result Stage not found", e);
				}
			}
		});
		
		// Advanced Search Hyperlink
		advanceSearch = new Hyperlink();
		advanceSearch.setText("Advanced Search");
		advanceSearch.setId("advanceSearch");
		advanceSearch.setFont(Font.font(FONTSTYLE, FontWeight.NORMAL, 15));
		advanceSearch.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				AdvanceSearchView advance = new AdvanceSearchView();
				try {
					advance.start(searchStage);
				} catch (Exception e) {
					Logger logger = Logger.getLogger("logger");
					logger.log(Level.FINE, "Advance Search Stage not found", e);
				}
				
			}
		});
		
		// Horizontal layout for search button and hyperlink
		HBox hlayout = new HBox(70);
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
		finalLayout.setStyle("-fx-background-color: DARKGRAY; -fx-padding: 10;");
		finalLayout.getChildren().addAll(vlayout);
		
		Scene searchScene = new Scene(finalLayout, 1000, 800);
		searchStage.setScene(searchScene);
		searchStage.show();
	}
	
	
}
