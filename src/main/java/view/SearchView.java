package main.java.view;
import javafx.application.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.*;


public class SearchView extends Application {

	private Stage searchStage;
	private Label title;
	private TextField searchInput;
	private Button searchButton;
	private Hyperlink advanceSearch;
	
	@Override
	public void start(Stage stage) throws Exception {
		
		// TODO Auto-generated method stub
		searchStage = stage;
		searchStage.setTitle("Search Publications");
		
		// Layout for Search page
		VBox vlayout = new  VBox(20);
		vlayout.setAlignment(Pos.CENTER);
		vlayout.setFillWidth(true);
		
		// Title Label
		title = new Label("Search Publications");
		title.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
		
		// Password textbox
		searchInput = new TextField();
		searchInput.setPromptText("Enter publication title or part of title");
		searchInput.setFocusTraversable(false);
		searchInput.setMaxWidth(450);
		
		// Search Button
		searchButton = new Button("Search");
		searchButton.setFont(Font.font("Tahoma", FontWeight.NORMAL, 15));
		searchButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				SearchResultView searchRes = new SearchResultView();
				try {
					searchRes.start(searchStage);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		
		// Advanced Search Hyperlink
		advanceSearch = new Hyperlink();
		advanceSearch.setText("Advanced Search");
		advanceSearch.setFont(Font.font("Tahoma", FontWeight.NORMAL, 15));
		advanceSearch.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				AdvanceSearchView advance = new AdvanceSearchView();
				try {
					advance.start(searchStage);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		});
		
		// Horizontal layout for search button and hyperlink
		HBox hlayout = new HBox(70);
		hlayout.getChildren().addAll(searchButton,advanceSearch);
		hlayout.setAlignment(Pos.CENTER);
		 
		// Add all components to grid
		vlayout.getChildren().addAll(title, searchInput, hlayout);
		
		// Adding Scene
		BorderPane borderLayout = new BorderPane();
		borderLayout.setPadding(new Insets(30));
		borderLayout.setCenter(vlayout);;
		
		// Final Layout using Stack Pane for setting background color
		StackPane finalLayout = new StackPane();
		finalLayout.setStyle("-fx-background-color: DARKGRAY; -fx-padding: 10;");
		finalLayout.getChildren().addAll(borderLayout);
		
		Scene searchScene = new Scene(finalLayout, 800, 800);
		searchStage.setScene(searchScene);
		searchStage.show();
	}
	
	
}
