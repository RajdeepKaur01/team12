package main.java.view;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.util.Callback;
import main.java.auth.Auth;
import main.java.auth.AuthUser;
import main.java.entities.Author;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.paint.Color;
import javafx.scene.control.ListView;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.text.Text;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import java.util.List;
import java.util.ArrayList;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

public class SelectedAuthors extends Application implements EventHandler<ActionEvent> {
    private TableView<Author> selectedAuth;
    private ObservableList<Author> mdata;
    private TextArea desctxt;
    private Text actionstatus;
    private Stage selectedStage;
    TableColumn<Author, String> authorNameCol;
	TableColumn<Author, Integer> researchPaperCol, pastExpCol;
	private ObservableList<Author> masterData;
	private Button logout, search, savebtn, delbtn;
	private int userID;

    public static void main(String [] args) {
        Application.launch(args);
    }

    public void start(Stage primaryStage, int userID) {
    	this.userID = userID;
    	selectedStage = primaryStage;
        selectedStage.setTitle("My Program Committee");
        
     // gridPane layout
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(15);
        grid.setVgap(20);
        grid.setPadding(new Insets(25, 25, 25, 25));
		
        // Table Data
        
        
     // Selected Author Details Table and Columns
     		selectedAuth = new TableView<Author>();
     		selectedAuth.setPlaceholder(new Label("Programme Committee List is Empty"));
     		selectedAuth.setId("authorDetails");
     		selectedAuth.setMaxHeight(300);
     		selectedAuth.setPrefWidth(600);
     		pastExpCol = new TableColumn<Author, Integer>("Past Experience \n (in years)");
     		pastExpCol.setPrefWidth(150);
     		pastExpCol.setMinWidth(150);
     		pastExpCol.setResizable(false);
     		authorNameCol = new TableColumn<Author, String>("Author Name");
     		authorNameCol.setPrefWidth(200);
     		authorNameCol.setMinWidth(200);
     		researchPaperCol = new TableColumn<Author, Integer> ("Number of \n Research Papers");
     		researchPaperCol.setPrefWidth(150);
     		researchPaperCol.setMinWidth(150);
     		
     	// Multiple Selection in Table
     		//selectedAuth.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    	//	authorDetails.getSelectionModel().setCellSelectionEnabled(true);
    		
    		selectedAuth.setFocusTraversable(true);
     		selectedAuth.getColumns().addAll(authorNameCol, pastExpCol, researchPaperCol);
     		mdata = FXCollections.observableArrayList(new AuthUser().getAuthors(userID));
    		setDataInTable(mdata);
    		
    		
    		// select row to navigate to author details
    		selectedAuth.setOnMousePressed(new EventHandler<MouseEvent>() {
    		    @Override 
    		    public void handle(MouseEvent event) {
    		        if (event.isPrimaryButtonDown() && event.getClickCount() == 1) {
    		        	if(selectedAuth.getSelectionModel().getSelectedItem()==null){
    		        		
    		        		desctxt.clear();
    		        		desctxt.setPromptText("Enter Notes (optional).");
    		        	}
    		        	else{
    		        		desctxt.setText(selectedAuth.getSelectionModel().getSelectedItem().getNote());
    		        	}
    		        }
    		    }
    		});
    		

        // todo desc text area in a scrollpane
        desctxt = new TextArea();
        desctxt.setPromptText("Enter Notes (optional).");
        desctxt.setWrapText(true);
        desctxt.setFocusTraversable(false);
        ScrollPane sp = new ScrollPane();
        sp.setContent(desctxt);
        sp.setFitToWidth(true);
        sp.setFitToHeight(true);
        sp.setPrefHeight(300);
        sp.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        sp.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
		
        // todo hbox (label + text fld), scrollpane - in a vbox 
        VBox vbox = new VBox();
        vbox.setSpacing(10);
        vbox.getChildren().addAll(sp);

        grid.add(vbox, 2, 1); // col = 2, row = 1
		grid.add(selectedAuth, 1, 1);
        // new and delete buttons
        delbtn = new Button("Delete");
        delbtn.setPrefHeight(30);
        delbtn.setPrefWidth(100);
        delbtn.setStyle("-fx-background-radius: 30, 30, 29, 28;"+
        		"-fx-padding: 3px 10px 3px 10px;"+
        		"-fx-background-color: linear-gradient(lightblue, white );");
        HBox hbox2 = new HBox(10);
        hbox2.getChildren().addAll(delbtn);
        grid.add(hbox2, 1, 2); // col = 1, row = 2
        delbtn.setOnAction(this);

        // save button to the right anchor pane and grid
        savebtn = new Button("Save");
        savebtn.setStyle("-fx-background-radius: 30, 30, 29, 28;"+
        		"-fx-padding: 3px 10px 3px 10px;"+
        		"-fx-background-color: linear-gradient(lightblue, white );");
        savebtn.setOnAction(this);
        savebtn.setPrefHeight(30);
        savebtn.setPrefWidth(100);
        AnchorPane anchor = new AnchorPane();
        AnchorPane.setRightAnchor(savebtn, 0.0);
        anchor.getChildren().add(savebtn);		
        grid.add(anchor, 2, 2); // col = 2, row = 2
        

      //Logout Button
      		logout = new Button("Logout");
      		logout.setId("add");
      		logout.setStyle("-fx-background-radius: 30, 30, 29, 28;"+
      		"-fx-padding: 3px 10px 3px 10px;"+
      		"-fx-background-color: linear-gradient(lightblue, white );");
      		logout.setAlignment(Pos.CENTER);
      	//	logout.setFont(Font.font(FONTSTYLE, FontWeight.NORMAL, 15));
      		logout.setOnAction(this);
      		
      		//Selected Authors Button
      		search = new Button("Search");
      		search.setId("add");
      		search.setStyle("-fx-background-radius: 30, 30, 29, 28;"+
      		"-fx-padding: 3px 10px 3px 10px;"+
      		"-fx-background-color: linear-gradient(lightblue, white );");
      		search.setAlignment(Pos.CENTER);
     // 		search.setFont(Font.font(FONTSTYLE, FontWeight.NORMAL, 15));
      		search.setOnAction(this);
      		
      		// HBox for logout and selected author button
      		HBox hlogout = new HBox(20);
      		hlogout.getChildren().addAll(search, logout);
      		hlogout.setAlignment(Pos.TOP_RIGHT);
      		
  		Label welcome = new Label("My Program Committee ");
		//authorName.setFont(Font.font(FONTSTYLE, FontWeight.EXTRA_BOLD, 20));
		welcome.setAlignment(Pos.TOP_LEFT);
		welcome.setStyle("-fx-font: 20px Arial;"+
				"-fx-text-fill: #0076a3;"+
				"-fx-opacity: 0.6;");
        
        // action message (status) text
        actionstatus = new Text();
        actionstatus.setFill(Color.FIREBRICK);
        actionstatus.setText("");	
        grid.add(actionstatus, 1, 3); // col = 1, row = 3
      
        VBox vCenter = new VBox(15);
        vCenter.getChildren().addAll(welcome, grid);
        vCenter.setAlignment(Pos.TOP_CENTER);
        
        BorderPane layout = new BorderPane();
        layout.setCenter(vCenter);
        layout.setTop(hlogout);
        
        
        // scene
        Scene scene = new Scene(layout, 1000, 650); 
        selectedStage.setScene(scene);
        selectedStage.show();
        layout.setStyle("-fx-background-color:  linear-gradient(lightblue, lightblue);"+
     	       " -fx-border-color: white;"+
     	       " -fx-border-radius: 20;"+
     	       "-fx-padding: 10 10 10 10;"+
     	        "-fx-background-radius: 20;");
        
    }

 // Set Data in Author Details table

 	private void setDataInTable(ObservableList<Author> data) {
 		
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
 		pastExpCol.setCellValueFactory(
                new PropertyValueFactory<Author, Integer>("pastExperienceYrs"));
 		
 	/*	pastExpCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Author,String>, ObservableValue<String>>() {
 			@Override
 			public ObservableValue<String> call(CellDataFeatures<Author, String> p) {
 			//
 				if(p.getValue().getCommitteeMemberInfo()==null || p.getValue().getCommitteeMemberInfo().size() == 0)
 					return new SimpleStringProperty("No Experience");
 				
 				return new SimpleStringProperty("" + p.getValue().getCommitteeMemberInfo().size());
 				
 				
 			}
 		});*/
 		
 		selectedAuth.setItems(data);
 	}
    
	@Override
	public void start(Stage primaryStage) throws Exception {
		start(primaryStage, userID);
	}

	@Override
	public void handle(ActionEvent event) {
		AuthUser auser = new AuthUser();
		try{
			if(event.getSource() == logout){
				new LoginView().start(selectedStage);
			}
			if(event.getSource() == search){
				new SearchView().start(selectedStage, userID);
			}
			if(event.getSource() == delbtn){
				if(selectedAuth.getSelectionModel().getSelectedItem()==null)
					generateAlert("No Author selected to delete!!");
				else{
					auser.deleteAuthor(userID, selectedAuth.getSelectionModel().getSelectedItem());
					setDataInTable(FXCollections.observableArrayList(auser.getAuthors(userID)));
					generateAlert("Author Deleted!!");
				}
			}
			if(event.getSource() == savebtn){
				Author selected = selectedAuth.getSelectionModel().getSelectedItem();
				if(desctxt.getText().length()>0 && desctxt.getText().replaceAll(" ", "").length()==0){
					generateAlert("Enter description to save!!");
				}
				else if(selected == null)
					generateAlert("Select Author to save description!!");
				else{
					selected.setNote(desctxt.getText());
					auser.updateAuthor(userID, selected);
					generateAlert("Note saved for "+ selected.getName());
				}
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	// Generate Error Alert
	public void generateAlert(String string) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Message Dialog");
			alert.setContentText(string);
			alert.show();
		}

}