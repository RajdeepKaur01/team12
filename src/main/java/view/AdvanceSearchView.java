package main.java.view;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import main.java.entities.Author;
import main.java.queryengine.dao.AuthorDAO;
import main.java.search.FindResearcher;

public class AdvanceSearchView extends Application implements EventHandler<ActionEvent> {

	private Stage advanceStage;
	private RadioButton positionHeldCheck;
	private RadioButton authorNameCheck;
	private RadioButton yopCheck;
	private RadioButton conferenceNameCheck;
	private RadioButton acronymCheck;
	private ChoiceBox<String> positionHeldText;
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
		gridLayout.setVgap(10);
		gridLayout.setHgap(15);
		
		// Elements in Grids
		positionHeldCheck = new RadioButton("Position Held");
		positionHeldCheck.setId("positionHeldCheck");
		positionHeldCheck.setToggleGroup(group);
		positionHeldCheck.setSelected(true);
		positionHeldCheck.setFont(Font.font(FONTSTYLE, FontWeight.NORMAL, 15));
		GridPane.setConstraints(positionHeldCheck, 0, 3);
		
		authorNameCheck = new RadioButton("Author Name:");
		authorNameCheck.setId("authorNameCheck");
		authorNameCheck.setToggleGroup(group);
		authorNameCheck.setFont(Font.font(FONTSTYLE, FontWeight.NORMAL, 15));
		GridPane.setConstraints(authorNameCheck, 0, 4);
		
		yopCheck = new RadioButton("Year of Publication:");
		yopCheck.setId("yopCheck");
		yopCheck.setToggleGroup(group);
		yopCheck.setFont(Font.font(FONTSTYLE, FontWeight.NORMAL, 15));
		GridPane.setConstraints(yopCheck, 0, 5);
		
		conferenceNameCheck = new RadioButton("Name of Conference:");
		conferenceNameCheck.setId("conferenceNameCheck");
		conferenceNameCheck.setToggleGroup(group);
		conferenceNameCheck.setFont(Font.font(FONTSTYLE, FontWeight.NORMAL, 15));
		GridPane.setConstraints(conferenceNameCheck, 0, 6);
		
		acronymCheck = new RadioButton("Conference Acronym:");
		acronymCheck.setId("acronymCheck");
		acronymCheck.setToggleGroup(group);
		acronymCheck.setFont(Font.font(FONTSTYLE, FontWeight.NORMAL, 15));
		GridPane.setConstraints(acronymCheck, 0, 7);
		
		positionHeldText = new ChoiceBox<String>();
		positionHeldText.setId("positionHeldText");
		positionHeldText.setItems(FXCollections.observableArrayList("General Chair", "Program Chair", "Conference Chair", "External Review Committee"));
		positionHeldText.getSelectionModel().selectFirst();
		GridPane.setConstraints(positionHeldText, 1, 3);
		
		authorNameText = new TextField();
		authorNameText.setId("authorNameText");
		GridPane.setConstraints(authorNameText, 1, 4);
		
		yopText = new TextField();
		yopText.setId("yopText");
		GridPane.setConstraints(yopText, 1, 5);
		
		conferenceNameText = new TextField();
		conferenceNameText.setId("conferenceNameText");
		GridPane.setConstraints(conferenceNameText, 1, 6);
		
		acronymText = new TextField();
		acronymText.setId("acronymText");
		GridPane.setConstraints(acronymText, 1, 7);
		
		// Disable textboxes
		authorNameText.setDisable(true);
		yopText.setDisable(true);
		conferenceNameText.setDisable(true);
		acronymText.setDisable(true);
		
		// Enable TextBoxes
		positionHeldCheck.setOnAction(this);
		authorNameCheck.setOnAction(this);
		yopCheck.setOnAction(this);
		conferenceNameCheck.setOnAction(this);
		acronymCheck.setOnAction(this);
		
		// Search Button
		search = new Button("Search");
		search.setId("search");
		search.setFont(Font.font(FONTSTYLE, FontWeight.NORMAL, 15));
		GridPane.setConstraints(search, 1, 11);
		
		search.setOnAction(this);
		
		// button to get Back to Normal search 
		backToNormalSearch = new Button("Back");
		backToNormalSearch.setId("back");
		backToNormalSearch.setFont(Font.font(FONTSTYLE, FontWeight.NORMAL, 15));
		GridPane.setConstraints(backToNormalSearch, 2, 11);
		
		backToNormalSearch.setOnAction(this);
		
		// Add components to gridLayout
		gridLayout.getChildren().addAll(backToNormalSearch, positionHeldCheck, authorNameCheck, yopCheck, conferenceNameCheck, acronymCheck, positionHeldText, authorNameText, yopText, conferenceNameText, acronymText, search);
		
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
            
        Scene scene = new Scene(finalLayout, 1000, 800);
        advanceStage.setTitle("Advanced Search");
        advanceStage.setScene(scene);
        advanceStage.show();
		
	}

	@Override
	public void handle(ActionEvent event) {
		positionHeldText.setDisable(!positionHeldCheck.isSelected());
		authorNameText.setDisable(!(authorNameCheck.isSelected()));
		acronymText.setDisable(!acronymCheck.isSelected());
		yopText.setDisable(!yopCheck.isSelected());
		conferenceNameText.setDisable(!conferenceNameCheck.isSelected());
		
		try{
			// backto NormalSearch Button Action
			if(event.getSource() == backToNormalSearch){
				SearchView redirectToSearch = new SearchView();
				redirectToSearch.start(advanceStage);
			}
			
			// Search Button Action
			if(event.getSource() == search){
				SearchResultView sv = new SearchResultView();
				List<Author> authors = new ArrayList<>();
				ObservableList<Author> data;
				FindResearcher find = new FindResearcher();
			
				// Check if search TextBox are not empty
				if(!positionHeldCheck.isSelected() && !(!conferenceNameText.getText().isEmpty() || !acronymText.getText().isEmpty() || !yopText.getText().isEmpty() || !authorNameText.getText().isEmpty()))
					{
						generateAlert("Enter value to search!");
						return;
					}
				// Get Result for search by Conference Name
				
				if(conferenceNameCheck.isSelected()){
						authors = new ArrayList<>(new FindResearcher().
								findAuthorsByConferenceName(conferenceNameText.getText()));		

				}
				
				//Get Result for search by author name
				if(authorNameCheck.isSelected()){
						Set<String> query = new HashSet<String>();
						Set<Author> result;
						query.add(authorNameText.getText());

						authors = new ArrayList<>(find.findAuthorsByAuthorName(authorNameText.getText()));

				}
				
				// Get Result for search by acronym
				if(acronymCheck.isSelected()){
						authors = new ArrayList<>(find.
								findAuthorsByConferenceAcronym(acronymText.getText()));
					}
				
				//Get Result for Position Held
				if(positionHeldCheck.isSelected()){
					authors = new ArrayList<>(find.
							findAuthorsByPositionHeld(positionHeldText.getSelectionModel().getSelectedItem().substring(0, 1)));

				}
				
				// Get Result for Year of Publication
				if(yopCheck.isSelected()){
					if(yopText.getText().matches("[0-9]+") && (Integer.parseInt(yopText.getText()) >=1800) && (Integer.parseInt(yopText.getText()) <= Calendar.getInstance().get(Calendar.YEAR))){
						authors = new ArrayList<>(find.
								findAuthorsByYearOfPublication(Integer.parseInt(yopText.getText())));
					}
					else{
						generateAlert("Year of publication should be in year format!");
						return;
					}

				}
				
				data = FXCollections.observableList(authors);
				sv.start(advanceStage,data);
			}
		}
		catch (Exception e) {
			System.out.println(e);
		}
	}

	// Generate Error Alert
	private void generateAlert(String string) {
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle("Warning Dialog");
		alert.setContentText(string);
		alert.show();
		
	}
			
}



