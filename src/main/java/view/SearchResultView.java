package main.java.view;

import main.java.entities.*;

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
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class SearchResultView extends Application implements EventHandler<ActionEvent> {

	private Stage searchResultStage;
	private TableView<Author> authorDetails;
	private Label filter;
	private TextField filterText;
	private ChoiceBox<String> filterChoice;
	private Button applyFilter;
	private Button newSearch;
	private ObservableList<Author> data;
	static final String FONTSTYLE = "Tahoma";
	
	@SuppressWarnings({ "unchecked", "static-access" })
	@Override
	public void start(Stage stage) throws Exception {
		// Create stage
		searchResultStage = stage;
		searchResultStage.setTitle("Search Publication Results");
		
		// Author Details Table and Columns
		authorDetails = new TableView<Author>();
		authorDetails.setId("authorDetailsTable");
		TableColumn<Author, String>  authorNameCol = new TableColumn<Author, String>("Author Name");
		authorNameCol.setPrefWidth(100);
		TableColumn<Author, String> locationCol = new TableColumn<Author, String>("Location");
		locationCol.setPrefWidth(100);
		TableColumn<Author, Integer> ageCol = new TableColumn<Author, Integer> ("Age");
		ageCol.setPrefWidth(100);
		TableColumn<Author, String> genderCol = new TableColumn<Author, String> ("Gender");
		genderCol.setPrefWidth(100);
		TableColumn<Author, Integer> yearsAsCMCol = new TableColumn<Author, Integer> ("Years As Committee Member");
		yearsAsCMCol.setPrefWidth(100);
		TableColumn<Author, String> areaOfExpertiseCol = new TableColumn<Author, String> ("Area of Expertise");
		areaOfExpertiseCol.setPrefWidth(100);
		TableColumn<Author, String> researchPaperCol = new TableColumn<Author, String> ("Research Papers");
		researchPaperCol.setPrefWidth(200);
		
		authorDetails.getColumns().addAll(authorNameCol, locationCol, ageCol, genderCol, yearsAsCMCol, areaOfExpertiseCol, researchPaperCol);
		
		// add data
	/*	data = FXCollections.observableArrayList();
		data.add(new Author("Technology",32));
		data.add(new Author("ABC\nABC",33));
		data.add(new Author("Science",34));
		
		areaOfExpertiseCol.setCellValueFactory(
                new PropertyValueFactory<Author, String>("areaOfExpertise"));
		ageCol.setCellValueFactory(
                new PropertyValueFactory<Author, Integer>("age"));
		yearsAsCMCol.setCellValueFactory(
                new PropertyValueFactory<Author, Integer>("yearsAsCommitteeMemember"));
		authorNameCol.setCellValueFactory(
                new PropertyValueFactory<Author, String>("name"));
		*/
		authorDetails.setItems(data);
		
		// select row to navigate to author details
		authorDetails.setOnMousePressed(new EventHandler<MouseEvent>() {
		    @Override 
		    public void handle(MouseEvent event) {
		        if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
		        	AuthorDetailsView view = new AuthorDetailsView();
		        	view.sendAuthorDetails(authorDetails.getSelectionModel().getSelectedItem());
		        	try {
						view.start(searchResultStage);
					} catch (Exception e) {
						Logger logger = Logger.getLogger("logger");
						logger.log(Level.FINE, "Author Details Stage not found", e);
					}                 
		        }
		    }
		});
		
		// Table Filter
		filter = new Label("Filter By");
		filter.setFont(Font.font(FONTSTYLE, FontWeight.NORMAL, 15));
		filterChoice = new ChoiceBox<String>();
		filterChoice.setId("filterBox");
		filterChoice.setItems(FXCollections.observableArrayList("Location", "Age", "Gender", "Years As Committee Member", "Area Of Expertise"));
		filterChoice.getSelectionModel().selectFirst();
		filterText = new TextField();
		filterText.setPromptText("Enter text to filter by");
		
		applyFilter = new Button("Apply Filter");
		applyFilter.setFont(Font.font(FONTSTYLE, FontWeight.NORMAL, 15));
		
		newSearch = new Button("New Search");
		newSearch.setId("newSearch");
		newSearch.setFont(Font.font(FONTSTYLE, FontWeight.NORMAL, 15));
		applyFilter.setOnAction(this);
		newSearch.setOnAction(this);
		
		/// Horizontal layout for filter
		HBox filterLayout = new HBox(10);
		filterLayout.getChildren().addAll(filter, filterChoice, filterText, applyFilter, newSearch);
		filterLayout.setAlignment(Pos.CENTER);
		
		// Layout for page
	    BorderPane layout = new BorderPane();
		layout.setPadding(new Insets(10, 10, 10, 10));
		layout.setCenter(authorDetails);
		layout.setTop(filterLayout);
		layout.setMargin(filterLayout, new Insets(10));
		
		// Final Layout using Stack Pane for setting background color
		StackPane finalLayout = new StackPane();
		finalLayout.setStyle("-fx-background-color: DARKGRAY; -fx-padding: 10;");
		finalLayout.getChildren().addAll(layout);
		
		// Scene
		Scene resultScene = new Scene(finalLayout, 800, 800);
		searchResultStage.setScene(resultScene);
		searchResultStage.show();
	}

	@Override
	public void handle(ActionEvent event) {
		if(event.getSource() == newSearch){
			SearchView redirectToSearch = new SearchView();
			try {
				redirectToSearch.start(searchResultStage);
			} catch (Exception e) {
				Logger logger = Logger.getLogger("logger");
				logger.log(Level.FINE, "Search Stage not found", e);
			}
		}
		if(event.getSource() == applyFilter){
			
		}
	}

}
