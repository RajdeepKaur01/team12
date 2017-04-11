package main.java.view;

import main.java.entities.*;
import main.java.search.FilterSearch;
import main.java.search.FindResearcher;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.management.relation.RelationServiceNotRegisteredException;

import com.sun.javafx.scene.control.skin.TableHeaderRow;

import javafx.application.Application;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.util.Callback;

public class SearchResultView extends Application implements EventHandler<ActionEvent> {

	private Stage searchResultStage;
	private TableView<Author> authorDetails;
	private Label filter;
	private TextField filterText;
	private ChoiceBox<String> filterChoice;
	private Button applyFilter, removeFilter;
	private Button newSearch;
	private ObservableList<Author> masterData;
	private ObservableList<Author> filterData;
	TableColumn<Author, String> authorNameCol, pastExpCol, researchPaperCol;
	TableColumn<Author, Integer> confYearCol;
	static final String FONTSTYLE = "Arial";
	List<Author> selectedAuthors = new ArrayList<Author>();
	
	@SuppressWarnings({ "unchecked", "static-access" })
	@Override
	public void start(Stage stage) {
	}

	public void start(Stage stage, ObservableList<Author> data) throws Exception {
		
		// set masterData
			masterData = data;
		
		// Create stage
		searchResultStage = stage;
		searchResultStage.setTitle("Search Publication Results");
		
		// Author Details Table and Columns
		authorDetails = new TableView<Author>();
		authorDetails.setId("authorDetails");
		authorDetails.setMaxHeight(500);
		authorDetails.setMaxWidth(700);
		pastExpCol = new TableColumn<Author, String>("Past Experience \n (in years)");
		pastExpCol.setPrefWidth(200);
		pastExpCol.setResizable(false);
		authorNameCol = new TableColumn<Author, String>("Author Name");
		authorNameCol.setPrefWidth(300);
		researchPaperCol = new TableColumn<Author, String> ("Number of \n Research Papers");
		researchPaperCol.setPrefWidth(150);
		
		TableColumn<Author, CheckBox> select = new TableColumn("Select");
        select.setPrefWidth(50);
        select.setMaxWidth(50);
        select.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Author, CheckBox>, ObservableValue<CheckBox>>() {

            @Override
            public ObservableValue<CheckBox> call(
                    TableColumn.CellDataFeatures<Author, CheckBox> arg0) {
                Author user = arg0.getValue();
                CheckBox checkBox = new CheckBox();
               // checkBox.selectedProperty().setValue(checkBox.isSelected());
                checkBox.selectedProperty().addListener(new ChangeListener<Boolean>() {
                    public void changed(ObservableValue<? extends Boolean> ov,
                            Boolean old_val, Boolean new_val) {
                    	
                    	if(new_val && old_val!=new_val){
                    		selectedAuthors.add(user);
                    	}
                    	else if(!new_val && selectedAuthors.contains(user))
                    		selectedAuthors.remove(user);
                    	else{
                    		
                    	}
                    

                    }
                });

                return new SimpleObjectProperty<CheckBox>(checkBox);

            }

        });
		
		
		authorDetails.getColumns().addAll(select, authorNameCol, pastExpCol, researchPaperCol);
		
		setDataInTable(data);
		
		//authorDetails.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
	
		// select row to navigate to author details
		authorDetails.setOnMousePressed(new EventHandler<MouseEvent>() {
		    @Override 
		    public void handle(MouseEvent event) {
		        if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
		        	AuthorDetailsView view = new AuthorDetailsView();
		        	view.sendAuthorDetails(authorDetails.getSelectionModel().getSelectedItem(), masterData);
		        	try {
						view.start(searchResultStage);
					} catch (Exception e) {

						System.out.println(e.getMessage());
						System.out.println(e.toString());
						e.printStackTrace();
					}                 
		        }
		    }
		});
		
		
		// Table Filter
		filter = new Label("Filter By");
		filter.setFont(Font.font(FONTSTYLE, FontWeight.NORMAL, 15));
		filterChoice = new ChoiceBox<String>();
		filterChoice.setId("filterBox");
		filterChoice.setItems(FXCollections.observableArrayList("Author Name", "No of Research Papers","Past Experience"));
		filterChoice.getSelectionModel().selectFirst();
		filterText = new TextField();
		filterText.setId("filterText");
		filterText.setPromptText("Enter text to filter by");
		
		applyFilter = new Button("Apply Filter");
		applyFilter.setId("applyFilter");
		applyFilter.setFont(Font.font(FONTSTYLE, FontWeight.NORMAL, 15));
		
		removeFilter = new Button("Remove Filter");
		removeFilter.setFont(Font.font(FONTSTYLE, FontWeight.NORMAL, 15));
		
		newSearch = new Button("New Search");
		newSearch.setId("newSearch");
		newSearch.setFont(Font.font(FONTSTYLE, FontWeight.NORMAL, 15));
		applyFilter.setOnAction(this);
		newSearch.setOnAction(this);
		removeFilter.setOnAction(this);
		
		/// Horizontal layout for filter
		HBox filterLayout = new HBox(15);
		filterLayout.getChildren().addAll(filter, filterChoice, filterText, applyFilter, removeFilter, newSearch);
		filterLayout.setAlignment(Pos.CENTER);
		
		//
		
		// Layout for page
	    BorderPane layout = new BorderPane();
		layout.setPadding(new Insets(20, 20, 20, 20));
		layout.setCenter(authorDetails);
		layout.setTop(filterLayout);
		layout.setMargin(filterLayout, new Insets(10));
		
		// Final Layout using Stack Pane for setting background color
		StackPane finalLayout = new StackPane();
		finalLayout.setStyle("-fx-background-color: WHITESMOKE ; -fx-padding: 10;");
		finalLayout.getChildren().addAll(layout);
		
		// Scene
		Scene resultScene = new Scene(finalLayout, 1000, 700);
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
		
		// Apply Filter Action
		if(event.getSource() == applyFilter){
			List<Author> filterauth = new ArrayList<Author>();
			String filterby = filterChoice.getSelectionModel().getSelectedItem();
			String filterVal = filterText.getText();
			FilterSearch filterClass = new FilterSearch();
			
			if(filterVal.isEmpty()){
				generateAlert("Enter Value to Filter");
				filterauth.addAll(masterData);
			}
			else if("Author Name".equals(filterby)){
				 filterauth.addAll(filterClass.filterByName(filterVal, masterData));
			}
			else if("No of Research Papers".equals(filterby)){
				filterauth.addAll(filterClass.filterByResearchPaper(filterVal, masterData));
			}
			else{
				filterauth.addAll(filterClass.filterByPastExperience(filterVal, masterData));
			}
			
			filterData = FXCollections.observableList(filterauth);
			setDataInTable(filterData);
			
		}
		
		//Remove Filter Action
		
		if(event.getSource() == removeFilter){
			setDataInTable(masterData);
		}
		
	}

	// Generate Error Alert
	
	public void generateAlert(String string) {
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle("Warning Dialog");
		alert.setContentText(string);
		alert.show();
		
	}
	
	
	// Set Data in Author Details table

	private void setDataInTable(ObservableList<Author> data) {
		System.out.println("Enter Search");
		
		//Set Column Value
		authorNameCol.setCellValueFactory(
                new PropertyValueFactory<Author, String>("name"));
		
		researchPaperCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Author,String>, ObservableValue<String>>() {
			
			@Override
			public ObservableValue<String> call(CellDataFeatures<Author, String> p) {
				
				if(p.getValue().getPaperKeys().size() == 0)
					return new SimpleStringProperty("0");
				
				return new SimpleStringProperty("" + p.getValue().getPaperKeys().size());
				
				
			}
		});
		
		pastExpCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Author,String>, ObservableValue<String>>() {
			
			@Override
			public ObservableValue<String> call(CellDataFeatures<Author, String> p) {
				
				if(p.getValue().getCommitteeMemberInfo().size() == 0)
					return new SimpleStringProperty("No Experience");
				
				return new SimpleStringProperty("" + p.getValue().getCommitteeMemberInfo().size());
				
				
			}
		});
		authorDetails.setItems(data);
	}

}
