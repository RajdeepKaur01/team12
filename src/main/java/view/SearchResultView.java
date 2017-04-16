package main.java.view;

import main.java.auth.AuthUser;
import org.apache.poi.hssf.*;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import main.java.entities.*;
import main.java.search.FilterSearch;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TablePosition;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
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
	TableColumn<Author, String> authorNameCol;
	TableColumn<Author, Integer> researchPaperCol, pastExpCol;
	private Button addButton, logout, viewSelectedList; 
	private int userID;
	private String style = "-fx-background-radius: 30, 30, 29, 28;"+
			"-fx-padding: 3px 10px 3px 10px;"+
			"-fx-background-color: linear-gradient(white, white );";
	static final String FONTSTYLE = "Arial";
	Set<Author> selectedAuthors = new HashSet<Author>();
	Set<TablePosition> selectedCells = new HashSet<TablePosition>();
	
	
	@Override
	public void start(Stage stage) {
	}
	@SuppressWarnings({ "unchecked", "static-access" })
	public void start(Stage stage, ObservableList<Author> data, int userID) throws Exception {
		this.userID = userID;
		
		// set masterData
			masterData = data;
		
		// Create stage
		searchResultStage = stage;
		searchResultStage.setTitle("Search Publication Results");
		
		// Author Details Table and Columns
		authorDetails = new TableView<Author>();
		authorDetails.setId("authorDetails");
		authorDetails.setMaxHeight(400);
		authorDetails.setMaxWidth(700);
		pastExpCol = new TableColumn<Author, Integer>("Past Experience \n (in years)");
		pastExpCol.setPrefWidth(200);
		pastExpCol.setMinWidth(200);
		pastExpCol.setResizable(false);
		authorNameCol = new TableColumn<Author, String>("Author Name");
		authorNameCol.setPrefWidth(300);
		authorNameCol.setMinWidth(300);
		researchPaperCol = new TableColumn<Author, Integer> ("Number of \n Research Papers");
		researchPaperCol.setPrefWidth(200);
		researchPaperCol.setMinWidth(200);
		
		
	/*TableColumn select = new TableColumn("Select");
        select.setPrefWidth(50);
        select.setMaxWidth(50);
       
      select.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Author, CheckBox>, ObservableValue<CheckBox>>() {

            @Override
            public ObservableValue<CheckBox> call(
                    TableColumn.CellDataFeatures<Author, CheckBox> arg0) {
                Author user = arg0.getValue();
                CheckBox checkBox = new CheckBox();
               checkBox.selectedProperty().setValue(user.getIsSelected());
               checkBox.selectedProperty().addListener((ov, old_val, new_val) -> {
                   user.setIsSelected(new_val);
                   if(user.getIsSelected()){
                	   selectedAuthors.add(user);
                   }
                   else if(!user.getIsSelected() && selectedAuthors.contains(user)){
                	   selectedAuthors.remove(user);
                   }
               });
                return new SimpleObjectProperty<CheckBox>(checkBox);

            }

        });*/
		
		// Multiple Selection in Table
		authorDetails.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
	//	authorDetails.getSelectionModel().setCellSelectionEnabled(true);
		
		
		authorDetails.getColumns().addAll(authorNameCol, pastExpCol, researchPaperCol);
		
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
						view.start(searchResultStage, userID, "SearchResultView");
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
		filterChoice.setStyle(style);
		filterChoice.setItems(FXCollections.observableArrayList("Author Name", "No of Research Papers","Past Experience"));
		filterChoice.getSelectionModel().selectFirst();
		filterText = new TextField();
		filterText.setId("filterText");
		filterText.setPromptText("Enter text to filter by");
		
		applyFilter = new Button("Apply");
		applyFilter.setId("applyFilter");
		applyFilter.setStyle(style);
		applyFilter.setPrefHeight(30);
		applyFilter.setPrefWidth(100);
		applyFilter.setFont(Font.font(FONTSTYLE, FontWeight.NORMAL, 15));
		
		removeFilter = new Button("Remove");
		removeFilter.setStyle(style);
		removeFilter.setPrefHeight(30);
		removeFilter.setPrefWidth(100);
		removeFilter.setFont(Font.font(FONTSTYLE, FontWeight.NORMAL, 15));
		
		newSearch = new Button("New Search");
		newSearch.setId("newSearch");
		newSearch.setStyle(style);
		newSearch.setPrefHeight(30);
		newSearch.setPrefWidth(130);
		newSearch.setFont(Font.font(FONTSTYLE, FontWeight.NORMAL, 15));
		
		applyFilter.setOnAction(this);
		newSearch.setOnAction(this);
		removeFilter.setOnAction(this);
		
		/// Horizontal layout for filter
		HBox filterLayout = new HBox(15);
		filterLayout.getChildren().addAll(filter, filterChoice, filterText, applyFilter, removeFilter, newSearch);
		filterLayout.setAlignment(Pos.CENTER);
		
		// Add text label
		Label addText = new Label();
		addText.setFont(Font.font(FONTSTYLE, FontWeight.NORMAL, 15));
		addText.setText(" Select authors and Click ADD ");
		
		
		// Add Author
		addButton = new Button("ADD");
		addButton.setId("add");
		addButton.setStyle("-fx-background-radius: 30, 30, 29, 28;"+
		"-fx-padding: 3px 10px 3px 10px;"+
		"-fx-background-color: linear-gradient(lightblue, lightblue );");
		addButton.setPrefHeight(30);
		addButton.setPrefWidth(100);
		addButton.setAlignment(Pos.CENTER);
		addButton.setFont(Font.font(FONTSTYLE, FontWeight.NORMAL, 15));
		addButton.setOnAction(this);
		
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
		viewSelectedList = new Button("View Selected Authors");
		viewSelectedList.setId("add");
		viewSelectedList.setStyle("-fx-background-radius: 30, 30, 29, 28;"+
		"-fx-padding: 3px 10px 3px 10px;"+
		"-fx-background-color: linear-gradient(lightblue, white );");
		viewSelectedList.setAlignment(Pos.CENTER);
		viewSelectedList.setFont(Font.font(FONTSTYLE, FontWeight.NORMAL, 15));
		viewSelectedList.setOnAction(this);
		
		// HBox for logout and selected author button
		HBox hlogout = new HBox(20);
		hlogout.getChildren().addAll(viewSelectedList, logout);
		hlogout.setAlignment(Pos.TOP_RIGHT);
		
		/*HBox hSelected = new HBox();
		hSelected.getChildren().add(viewSelectedList);
		hlogout.setAlignment(Pos.TOP_LEFT);
		
		HBox combine = new HBox();
		combine.getChildren().addAll(hlogout,hSelected);*/
		
		VBox vcom = new VBox(20);
		vcom.getChildren().addAll(hlogout, filterLayout);
		
		// Hbox for add Button
		HBox h1 = new HBox();
		h1.getChildren().addAll(addText, addButton);
		HBox h2 = new HBox();
		h2.getChildren().addAll(addButton);
		
		VBox addV = new VBox(20);
		addV.getChildren().addAll(h1, h2);
		
		HBox addlayout = new HBox(80);
		addlayout.setAlignment(Pos.CENTER);
		addlayout.getChildren().add(addV);
		
		// Layout for page
	    BorderPane layout = new BorderPane();
		//layout.setPadding(new Insets(20, 20, 20, 20));
	 //   layout.setPadding(new Insets(20));
		layout.setCenter(authorDetails);
		layout.setTop(vcom);
		layout.setBottom(addlayout);
		
		layout.setMargin(filterLayout, new Insets(10));
		
		// Final Layout using Stack Pane for setting background color
		StackPane finalLayout = new StackPane();
	//	finalLayout.setStyle("-fx-background-color: WHITESMOKE ; -fx-padding: 10;");
		finalLayout.setStyle("-fx-background-color:  linear-gradient(lightblue, white);"+
			       " -fx-border-color: white;"+
			       " -fx-border-radius: 20;"+
			       "-fx-padding: 10 10 10 10;"+
			        "-fx-background-radius: 20;");
		finalLayout.getChildren().addAll(layout);
		
		// Scene
		Scene resultScene = new Scene(finalLayout, 1000, 650);
		searchResultStage.setScene(resultScene);
		searchResultStage.show();
		// Handle Key Events
        resultScene.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				if(event.getCode()==KeyCode.ENTER)
					{
					//handleSearchEvent();
					}
				if(event.getCode()==KeyCode.BACK_SPACE)
					handleBackKeyEvent();
				
			}
		});
		
	}

	@Override
	public void handle(ActionEvent event) {
		try{
		if(event.getSource() == newSearch){
			handleBackKeyEvent();
		}
		
		if(event.getSource() == addButton){
			selectedAuthors = new HashSet<Author>(authorDetails.getSelectionModel().getSelectedItems());
			for(Author a: selectedAuthors){
				System.out.println(a.getName());
			}
			HSSFWorkbook workbook = new HSSFWorkbook();
	        HSSFSheet spreadsheet = workbook.createSheet("sample");
			HSSFRow row  =null;
			System.out.println(authorDetails.getItems().size());
			System.out.println(authorDetails.getColumns().size());
			row= spreadsheet.createRow(0); 
			row.createCell(0).setCellValue("Author Name");
			row.createCell(1).setCellValue("Past Experience");
			row.createCell(2).setCellValue("Number of Research Papers");
		    for(int i=1;i<=authorDetails.getItems().size();i++){
		         row= spreadsheet.createRow(i);          
		         for(int j=0; j< authorDetails.getColumns().size();j++){
		        	 if(authorDetails.getColumns().get(j).getCellData(i)==null)
		        		 break;
		             row.createCell(j).setCellValue(authorDetails.getColumns().get(j).getCellData(i).toString());
		         }
		    }
		    FileOutputStream out = new FileOutputStream("example.xls");
	        workbook.write(out);
	        out.close();
	        System.out.println("Data is wrtten Successfully");
		  if(new AuthUser().addAuthors(userID, selectedAuthors)){
			  Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Selected Author/Authors Added");
				String s = "Click on View my Programmee Committee to see the list";
				alert.setContentText(s);
				alert.showAndWait();
		  } else{
			  Alert alert = new Alert(AlertType.ERROR);
			  alert.setTitle("Selected Author/Authors Not Added");
				String s = "Author/Authors Already Exists";
				alert.setContentText(s);
				alert.showAndWait();
		  }
			//new AuthUser().addAuthors(userID, selectedAuthors);
			//generateAlert("Selected Authors saved to List"); 
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
		
		if(event.getSource() == logout){
			
				new LoginView().start(searchResultStage);
			 
		}
		
		if(event.getSource() == viewSelectedList){
			new SelectedAuthors().start(searchResultStage, userID);
		}
		}
		catch (Exception e) {
			
			e.printStackTrace();
		}
		
		
	}

	// Generate Error Alert
	
	public void generateAlert(String string) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Warning Dialog");
		alert.setContentText(string);
		alert.show();
		
	}
	
	//Return to previous screen
	private void handleBackKeyEvent() {
		SearchView redirectToSearch = new SearchView();
		try {
			redirectToSearch.start(searchResultStage, userID);
		} catch (Exception e) {
			Logger logger = Logger.getLogger("logger");
			logger.log(Level.FINE, "Search Stage not found", e);
		}
		
	} 
	
	// Set Data in Author Details table

	private void setDataInTable(ObservableList<Author> data) {
		System.out.println("Enter Search");
		
		//Set Column Value
		authorNameCol.setCellValueFactory(
                new PropertyValueFactory<Author, String>("name"));
		
		researchPaperCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Author,Integer>, ObservableValue<Integer>>() {
			
			@Override
			public ObservableValue<Integer> call(CellDataFeatures<Author, Integer> p) {
				
				if(p.getValue().getPaperKeys().size() == 0)
					return new SimpleIntegerProperty(0).asObject();
				
				return new SimpleIntegerProperty(p.getValue().getPaperKeys().size()).asObject();
				
				
			}
		});
		
		pastExpCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Author,Integer>, ObservableValue<Integer>>() {
			
			@Override
			public ObservableValue<Integer> call(CellDataFeatures<Author, Integer> p) {
				
				if(p.getValue().getCommitteeMemberInfo().size() == 0)
					return new SimpleIntegerProperty(0).asObject();
				
				return new SimpleIntegerProperty(p.getValue().getCommitteeMemberInfo().size()).asObject();
				
				
			}
		});
		authorDetails.setItems(data);
	}

}
