package main.java.view;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
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
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
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
	private ChoiceBox<String> acronymText;
	private Button search, logout, selectBtn;
	private Button backToNormalSearch;
	private int userID;
	static final String FONTSTYLE = "Arial";
	final ToggleGroup group = new ToggleGroup();
		
	
	public void start(Stage stage, int userID) throws Exception {
		this.userID = userID;
		
		advanceStage = stage;
		advanceStage.setTitle("Advanced Search");
		
		// Grid Layout
		GridPane gridLayout = new GridPane();
		gridLayout.setPadding(new Insets(10, 10, 10, 10));
		gridLayout.setAlignment(Pos.CENTER);
		gridLayout.setVgap(30);
		gridLayout.setHgap(100);
		
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
		GridPane.setConstraints(authorNameCheck, 0, 6);
		
		yopCheck = new RadioButton("Year of Publication:");
		yopCheck.setId("yopCheck");
		yopCheck.setToggleGroup(group);
		yopCheck.setFont(Font.font(FONTSTYLE, FontWeight.NORMAL, 15));
		GridPane.setConstraints(yopCheck, 0, 7);
		
		conferenceNameCheck = new RadioButton("Name of Conference:");
		conferenceNameCheck.setId("conferenceNameCheck");
		conferenceNameCheck.setToggleGroup(group);
		conferenceNameCheck.setFont(Font.font(FONTSTYLE, FontWeight.NORMAL, 15));
		GridPane.setConstraints(conferenceNameCheck, 0, 5);
		
		acronymCheck = new RadioButton("Conference Acronym:");
		acronymCheck.setId("acronymCheck");
		acronymCheck.setToggleGroup(group);
		acronymCheck.setFont(Font.font(FONTSTYLE, FontWeight.NORMAL, 15));
		GridPane.setConstraints(acronymCheck, 0, 4);
		
		positionHeldText = new ChoiceBox<String>();
		positionHeldText.setId("positionHeldText");
		positionHeldText.setItems(FXCollections.observableArrayList("General Chair", "Program Chair", "Conference Chair", "External Review Committee"));
		positionHeldText.getSelectionModel().selectFirst();
		positionHeldText.setPrefHeight(30);
		positionHeldText.setStyle(  "-fx-background-radius: 30, 30, 29, 28;"+
				"-fx-padding: 3px 10px 3px 10px;"+
				"-fx-background-color: linear-gradient(white, white );");
		GridPane.setConstraints(positionHeldText, 1, 3);
		
		authorNameText = new TextField();
		authorNameText.setId("authorNameText");
		authorNameText.setPrefHeight(30);
		authorNameText.setPromptText("Enter Author Name");
		GridPane.setConstraints(authorNameText, 1, 6);
		
		yopText = new TextField();
		yopText.setPrefHeight(30);
		yopText.setId("yopText");
		yopText.setPromptText("Enter Year of Publication");
		GridPane.setConstraints(yopText, 1, 7);
		
		conferenceNameText = new TextField();
		conferenceNameText.setPrefHeight(30);
		conferenceNameText.setId("conferenceNameText");
		conferenceNameText.setPromptText("Enter Conference Name");
		GridPane.setConstraints(conferenceNameText, 1, 5);
		
		acronymText = new ChoiceBox<String>();
		acronymText.setPrefHeight(30);
		acronymText.setId("acronymText");
		acronymText.setItems(FXCollections.observableArrayList("ECOOP", "POPL", "ESOP", "PPOPP", "OOPSLA", "PLDI", "ICSE", "ASE", "ICFP", "ISMM", "ISSTA", "FSE"));
		acronymText.getSelectionModel().selectFirst();
		acronymText.setPrefHeight(30);
		acronymText.setPrefWidth(200);
		positionHeldText.setPrefWidth(200);
		acronymText.setStyle(  "-fx-background-radius: 30, 30, 29, 28;"+
				"-fx-padding: 3px 10px 3px 10px;"+
				"-fx-background-color: linear-gradient(white, white );");
		GridPane.setConstraints(acronymText, 1, 4);
		
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
		search.setPrefHeight(30);
		search.setPrefWidth(150);
		search.setStyle(  "-fx-background-radius: 30, 30, 29, 28;"+
				"-fx-padding: 3px 10px 3px 10px;"+
				"-fx-background-color: linear-gradient(white, white );");
		//GridPane.setConstraints(search, 1, 11);
		
		search.setOnAction(this);
		
		// button to get Back to Normal search 
		backToNormalSearch = new Button("Back");
		backToNormalSearch.setId("back");
		backToNormalSearch.setFont(Font.font(FONTSTYLE, FontWeight.NORMAL, 15));
		backToNormalSearch.setPrefHeight(30);
		backToNormalSearch.setPrefWidth(150);
		backToNormalSearch.setStyle(  "-fx-background-radius: 30, 30, 29, 28;"+
				"-fx-padding: 3px 10px 3px 10px;"+
				"-fx-background-color: linear-gradient(white, white );");
	//	GridPane.setConstraints(backToNormalSearch, 1, 11);
		
		backToNormalSearch.setOnAction(this);
		
		//HBOX
		HBox buttons = new HBox(50);
		buttons.getChildren().addAll(search, backToNormalSearch);
		buttons.setAlignment(Pos.CENTER);
		//GridPane.setConstraints(buttons, 1, 9);
		
		// Add components to gridLayout
		gridLayout.getChildren().addAll(positionHeldCheck, authorNameCheck, yopCheck, conferenceNameCheck, acronymCheck, positionHeldText, authorNameText, yopText, conferenceNameText, acronymText);
		
		//Logout Button
		logout = new Button("Logout");
		logout.setId("add");
		logout.setStyle("-fx-background-radius: 30, 30, 29, 28;"+
		"-fx-padding: 3px 10px 3px 10px;"+
		"-fx-background-color: linear-gradient(lightblue, white );");
		logout.setAlignment(Pos.CENTER);
		logout.setFont(Font.font(FONTSTYLE, FontWeight.NORMAL, 15));
		logout.setOnAction(this);
		
		//Selected Authors Button
		selectBtn = new Button("My Program Committee");
		selectBtn.setId("add");
		selectBtn.setStyle("-fx-background-radius: 30, 30, 29, 28;"+
		"-fx-padding: 3px 10px 3px 10px;"+
		"-fx-background-color: linear-gradient(lightblue, white );");
		selectBtn.setAlignment(Pos.CENTER);
		selectBtn.setFont(Font.font(FONTSTYLE, FontWeight.NORMAL, 15));
		selectBtn.setOnAction(this);
		
		// HBox for logout and selected author button
		HBox hlogout = new HBox(20);
		hlogout.getChildren().addAll(selectBtn, logout);
		hlogout.setAlignment(Pos.TOP_RIGHT);
				
		
		// Create Scene
		VBox root = new VBox(30);
       // root.setHgap(20);
        root.setAlignment(Pos.TOP_CENTER);
        root.setPadding(new Insets(50, 10, 10, 10));
        root.getChildren().addAll(gridLayout, buttons);
        
        // Final Layout using Stack Pane for setting background color
 		BorderPane finalLayout = new BorderPane();
 		finalLayout.setStyle("-fx-background-color:  linear-gradient(lightblue, white);"+
 		       " -fx-border-color: white;"+
 		       " -fx-border-radius: 20;"+
 		       "-fx-padding: 10 10 10 10;"+
 		        "-fx-background-radius: 20;");
     	finalLayout.setCenter(root);
     	finalLayout.setTop(hlogout);
            
        Scene scene = new Scene(finalLayout, 1000, 650);
        advanceStage.setTitle("Advanced Search");
        advanceStage.setScene(scene);
        advanceStage.show();
        
        // Handle Key Events
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				if(event.getCode()==KeyCode.ENTER)
					handleSearchEvent();
				if(event.getCode()==KeyCode.BACK_SPACE)
					handleBackKeyEvent();
				
			}
		});
		
	}

	@Override
	public void handle(ActionEvent event) {
		positionHeldText.setDisable(!positionHeldCheck.isSelected());
		authorNameText.setDisable(!(authorNameCheck.isSelected()));
		acronymText.setDisable(!acronymCheck.isSelected());
		yopText.setDisable(!yopCheck.isSelected());
		conferenceNameText.setDisable(!conferenceNameCheck.isSelected());
		
		if(positionHeldCheck.isSelected()){
			conferenceNameText.clear();
			yopText.clear();
			authorNameText.clear();
		}
		else if(conferenceNameCheck.isSelected()){
			authorNameText.clear();
			yopText.clear();
		}
		else if(authorNameCheck.isSelected()){
			conferenceNameText.clear();
			yopText.clear();
		}
		else if(acronymCheck.isSelected()){
			conferenceNameText.clear();
			authorNameText.clear();
			yopText.clear();
		}
		else{
			conferenceNameText.clear();
			authorNameText.clear();
		}
		
		try{
			// backto NormalSearch Button Action
			if(event.getSource() == backToNormalSearch){
				handleBackKeyEvent();
			}
			
			// Search Button Action
			if(event.getSource() == search){
				handleSearchEvent();
			}
			if(event.getSource() == logout){
				new LoginView().start(advanceStage);
		}
		
		if(event.getSource() == selectBtn){
			new SelectedAuthors().start(advanceStage, userID);
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
	
	// This methods handles Search
	private void handleBackKeyEvent() {
		SearchView redirectToSearch = new SearchView();
		try {
			redirectToSearch.start(advanceStage, userID);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	// This method handles Back button and backspace key event
	private void handleSearchEvent() {
		
		SearchResultView sv = new SearchResultView();
		List<Author> authors = new ArrayList<>();
		ObservableList<Author> data;
		FindResearcher find = new FindResearcher();
	
		// Check if search TextBox are not empty
		if(!positionHeldCheck.isSelected() && !acronymCheck.isSelected() && !(!conferenceNameText.getText().isEmpty() || !yopText.getText().isEmpty() || !authorNameText.getText().isEmpty()))
			{
				generateAlert("Enter value to search!");
				return;
			}
		// Get Result for search by Conference Name
		
		if(conferenceNameCheck.isSelected()){
			if(!StringUtils.isAlphanumeric(conferenceNameText.getText())){
				authors = new ArrayList<>(new FindResearcher().
						findAuthorsByConferenceName(conferenceNameText.getText()));	

				sv.setResultLbl(authors.size(), "Conference Name", conferenceNameText.getText());


			}
			else{
				generateAlert("please enter valid conference name");
				return;
		}

		}
		
		//Get Result for search by author name
		if(authorNameCheck.isSelected()){

			if(!StringUtils.isAlphanumeric(authorNameText.getText())){

				Set<String> query = new HashSet<String>();
				Set<Author> result;
				query.add(authorNameText.getText());

				authors = new ArrayList<>(find.findAuthorsByAuthorName(authorNameText.getText()));

				sv.setResultLbl(authors.size(), "Author Name", authorNameText.getText());

			} else{
				generateAlert("please enter valid author name");
				return;
			}
				

}
		
		
		// Get Result for search by acronym
		if(acronymCheck.isSelected() ){
			
				authors = new ArrayList<>(find.

						findAuthorsByConferenceAcronym(acronymText.getSelectionModel().getSelectedItem()));
				sv.setResultLbl(authors.size(), "Conference Acronym", acronymText.getSelectionModel().getSelectedItem());

			}
				

			
		
		//Get Result for Position Held
		if(positionHeldCheck.isSelected()){
			conferenceNameText.clear();
			authors = new ArrayList<>(find.
					findAuthorsByPositionHeld(positionHeldText.getSelectionModel().getSelectedItem().substring(0, 1)));
			sv.setResultLbl(authors.size(), "Postion Held", positionHeldText.getSelectionModel().getSelectedItem());

		}
		
		// Get Result for Year of Publication
		if(yopCheck.isSelected()){
			if(yopText.getText().matches("[0-9]+") && yopText.getText().length()<5 && (Integer.parseInt(yopText.getText()) >=1800) && (Integer.parseInt(yopText.getText()) <= Calendar.getInstance().get(Calendar.YEAR))){
				authors = new ArrayList<>(find.
						findAuthorsByYearOfPublication(Integer.parseInt(yopText.getText())));
				sv.setResultLbl(authors.size(), "Year of Publication", yopText.getText());
				System.out.println(authors.size());
			}
			else{
				generateAlert("Year of publication should be in year format!");
				return;
			}

		}
		
		data = FXCollections.observableList(authors);
		try {
			sv.start(advanceStage,data, userID);
		} catch (Exception e) {
			e.printStackTrace();
		}
	
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		
	}
			
}



