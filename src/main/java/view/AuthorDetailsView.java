package main.java.view;
import main.java.entities.*;
import main.java.search.FindResearcher;

import java.awt.GridBagConstraints;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.text.TabableView;


import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.Tooltip;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;

public class AuthorDetailsView extends Application implements EventHandler<ActionEvent>{
	private Stage authorDetailsStage;
	private Button back, logout;
	private int userID;
	private GridPane authorGrid;
	private Author selectedAuthor;
	private TableView<ResearchPaper> researchPapers;
	Button similarAuthor;
	static final String FONTSTYLE = "Arial";
	ChoiceBox<String> confName;
	Label confYear, posHeld; 
	String prevPage;
	private ObservableList<Author> masterData;
	SearchResultView searchRes;
	
	@SuppressWarnings("unchecked")
	public void start(Stage primaryStage, int userID, String prevPage) throws Exception {
		searchRes = new SearchResultView();
		this.userID = userID;
		this.prevPage = prevPage;
		authorDetailsStage = primaryStage;
		authorDetailsStage.setTitle("Author Details");
		FindResearcher find = new FindResearcher();
		
		// Layout for Login page
		authorGrid = new GridPane();
		authorGrid.setPadding(new Insets(10, 10, 10, 10));
		authorGrid.setAlignment(Pos.CENTER);
		authorGrid.setVgap(15);
		authorGrid.setHgap(70);
		
		Label authorName = new Label(selectedAuthor.getName());
		authorName.setId("authorName");
		//authorName.setFont(Font.font(FONTSTYLE, FontWeight.EXTRA_BOLD, 20));
		authorName.setAlignment(Pos.TOP_LEFT);
		authorName.setStyle("-fx-font: 20px Arial;"+
				"-fx-text-fill: #0076a3;"+
				"-fx-opacity: 0.6;");
		
		// Position held by Author Label
		Label confNameLabel = new Label("Conference Name:");
		confNameLabel.setFont(Font.font(FONTSTYLE, FontWeight.NORMAL, 15));
		GridPane.setConstraints(confNameLabel, 0, 2);
		
		// get data for ChoiceBox
		ObservableList<String> conf ;
		List<String> confnamelist = new ArrayList<String>();
		for(String name: selectedAuthor.getCommitteeMemberInfo().keySet()){
			confnamelist.add(name);
		}
		conf = FXCollections.observableArrayList(confnamelist);
		confName = new ChoiceBox<>();
		confName.setId("confName");
		confName.setItems(conf);
		confName.setPrefHeight(30);
		confName.setPrefWidth(150);
		confName.getSelectionModel().selectFirst();
		GridPane.setConstraints(confName, 1, 2);
		
		confName.setOnAction(this);
		
		// PositionHeld Label
		Label posHeldLabel = new Label("Position Held & Year:");
		posHeldLabel.setFont(Font.font(FONTSTYLE, FontWeight.NORMAL, 15));
		GridPane.setConstraints(posHeldLabel, 0, 3,1,1,HPos.LEFT,VPos.TOP);
		
		posHeld = new Label();
		posHeld.setId("posHeld");
		posHeld.setFont(Font.font(FONTSTYLE, FontWeight.NORMAL, 15));
		if(selectedAuthor.getCommitteeMemberInfo().size() != 0){
			StringBuilder str = new StringBuilder(selectedAuthor.getCommitteeMemberInfo().get(confName.getSelectionModel().getSelectedItem()).toString());
			str.delete(0, 6);
			str.delete(str.length()-10, str.length()-5);
			str.delete(str.length()-1, str.length());
			posHeld.setText(str.toString());
		}
			
		GridPane.setConstraints(posHeld, 1, 3);
		
		// Get Value for Alias and Url
		Author authorDet = new FindResearcher().getAuthorInfo(selectedAuthor);
		
		// Alias Label
		Label aliasLabel = new Label("Alias:");
		aliasLabel.setFont(Font.font(FONTSTYLE, FontWeight.NORMAL, 15));
		GridPane.setConstraints(aliasLabel, 0, 0);
		
		if(authorDet.getAuthorInfo().getAliases().length <= 1){
			Label al = new Label();
			al.setText("None");
			al.setFont(Font.font(FONTSTYLE, FontWeight.NORMAL, 15));
			GridPane.setConstraints(al, 1, 0);
			authorGrid.getChildren().add(al);
		}
		else{
			TextArea alias = new TextArea(convertToString(authorDet.getAuthorInfo().getAliases()));
			alias.setId("alias");
			alias.setEditable(false);
			alias.setMaxHeight(60);
			alias.setMaxWidth(150);
			alias.setWrapText(true);
			alias.setFont(Font.font(FONTSTYLE, FontWeight.NORMAL, 15));
			GridPane.setConstraints(alias, 1, 0);
			authorGrid.getChildren().add(alias);
		}
		// Homepage URL Label
		Label urlLabel = new Label("HomePage URL:");
		urlLabel.setFont(Font.font(FONTSTYLE, FontWeight.NORMAL, 15));
		GridPane.setConstraints(urlLabel, 0, 1);
		
		Label url = new Label();
		url.setWrapText(true);
		url.setId("url");
		url.setFont(Font.font(FONTSTYLE, FontWeight.NORMAL, 15));
		
		Hyperlink hyp = new Hyperlink();
		hyp.setId("hyp");
		hyp.setWrapText(true);
		
		if(authorDet.getAuthorInfo().getHomePageURL() == null){
			url.setText("No URL");
			GridPane.setConstraints(url, 1, 1);
			authorGrid.getChildren().add(url);
		}
		else{
			hyp.setTooltip(new Tooltip(authorDet.getAuthorInfo().getHomePageURL().toString()));
			hyp.setText(authorDet.getAuthorInfo().getHomePageURL().toString());
			hyp.setWrapText(true);
			hyp.setTooltip(new Tooltip(authorDet.getAuthorInfo().getHomePageURL().toString()));
			//authorGrid.getChildren().remove(url);
			authorGrid.getChildren().add(hyp);
			GridPane.setConstraints(hyp, 1, 1);
		}
		//	url.setText(authorDet.getAuthorInfo().getHomePageURL().toString());
			
		
		
		
		// List label
		Text text1 = new Text("Research Papers & Articles:");
		text1.setFont(Font.font(FONTSTYLE, FontWeight.NORMAL, 15));
		
		// Research Paper Table
		researchPapers = new TableView<>();
		researchPapers.setPlaceholder(new Label("Zero Research Papers or Journals Published"));
		researchPapers.setId("journalTable");
		researchPapers.setPrefHeight(370);
		researchPapers.setMaxWidth(600);
		researchPapers.setPrefWidth(600);
		researchPapers.setMinWidth(600);
		researchPapers.setFocusTraversable(false);
		//Columns : Title , Year, Type
		TableColumn<ResearchPaper, String> articleNameCol = new TableColumn<ResearchPaper, String>("Title");
		articleNameCol.setPrefWidth(300);
		articleNameCol.setMinWidth(300);
		TableColumn<ResearchPaper, Integer> articleYearCol = new TableColumn<ResearchPaper, Integer>("Published in Year");
		articleYearCol.setPrefWidth(150);
		articleYearCol.setMinWidth(150);
		TableColumn<ResearchPaper, String>  type = new TableColumn<ResearchPaper, String>("Type of Paper");
		type.setPrefWidth(150);
		type.setMinWidth(150);
		
		//Add Columns
	
		researchPapers.getColumns().addAll(articleNameCol, articleYearCol, type);
		
		//Wrapping column text
		articleNameCol.setCellFactory (col -> {
		    TableCell<ResearchPaper, String> cell = new TableCell<ResearchPaper, String>() {
		        @Override
		        public void updateItem(String item, boolean empty) {
		            super.updateItem(item, empty);
		            if (item != null) {
		                   Text text = new Text(item);
		                   text.setStyle(" -fx-opacity: 1;" +
		                                 " -fx-font-family: \"verdena\";" +
		                                 " -fx-font-size: 10pt;" +
		                                 " -fx-fill: #1398c8;" +   
		                                 " -fx-text-wrap: true;" +
		                                 " -fx-padding: 5px 30px 5px 5px;" +
		                                 " -fx-text-alignment:left;");
		                   text.setWrappingWidth(col.getPrefWidth() - 35);
		                   this.setPrefHeight(text.getLayoutBounds().getHeight()+10);
		                   this.setGraphic(text);
		            }
		        }
		    };
		    return cell;
		});
		
		
		
		// Map Columns to attributes of class
		articleNameCol.setCellValueFactory(
                new PropertyValueFactory<ResearchPaper, String>("title"));
		articleYearCol.setCellValueFactory(
                new PropertyValueFactory<ResearchPaper, Integer>("year"));
		type.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ResearchPaper,String>, ObservableValue<String>>() {
			
			@Override
			public ObservableValue<String> call(CellDataFeatures<ResearchPaper, String> p) {
				if(p.getValue().getClass().isAssignableFrom(InProceeding.class))
					return new SimpleStringProperty("Article");
				return new SimpleStringProperty("Research Paper");
				
				
			}
		});
		
		// Add data to table
		Author tAuthor = find.getResearchPapers(selectedAuthor);
		Set<ResearchPaper> completeList = new HashSet<ResearchPaper>();
		completeList.addAll(tAuthor.getArticles());
		completeList.addAll(tAuthor.getInProceedings());
		researchPapers.setItems(FXCollections.observableArrayList(completeList));
		researchPapers.
        setStyle("-fx-selection-bar: lightblue; ");
		
		// Similar Profile Button
		similarAuthor = new Button("View Similar Author");
		similarAuthor.setId("similarAuthor");
		similarAuthor.setFont(Font.font(FONTSTYLE, FontWeight.NORMAL, 15));
		similarAuthor.setFocusTraversable(true);
		similarAuthor.setStyle("-fx-background-radius: 30, 30, 29, 28;"+
		  		"-fx-padding: 3px 10px 3px 10px;"+
		  		"-fx-background-color: linear-gradient(lightblue, white );");
		similarAuthor.setDisable(true);
		similarAuthor.setOnAction(this);
		
		//add all elements to grid
		authorGrid.getChildren().addAll(aliasLabel, urlLabel, text1);
		if(selectedAuthor.getCommitteeMemberInfo().size() != 0){
			authorGrid.getChildren().addAll(posHeldLabel, posHeld, confNameLabel, confName);
			similarAuthor.setDisable(false);
		}
		
		// Add Grid to HBox
		HBox hGrid = new HBox(20);
		hGrid.getChildren().addAll(authorGrid);
		hGrid.setAlignment(Pos.CENTER);
		
		
		// VBox
		VBox verticalLayout = new VBox(20);
		verticalLayout.getChildren().addAll( authorName,similarAuthor, hGrid, text1, researchPapers, new Label());
		verticalLayout.setAlignment(Pos.CENTER);
		verticalLayout.setLayoutX(100);
		
		//Logout Button
  		logout = new Button("Logout");
  		logout.setId("add");
  		logout.setStyle("-fx-background-radius: 30, 30, 29, 28;"+
  		"-fx-padding: 3px 10px 3px 10px;"+
  		"-fx-background-color: linear-gradient(lightblue, white );");
  		logout.setAlignment(Pos.CENTER);
  	//	logout.setFont(Font.font(FONTSTYLE, FontWeight.NORMAL, 15));
  		logout.setOnAction(this);
  		
  		// Back Button
		back = new Button("Return");
		back.setId("back");
		back.setFont(Font.font(FONTSTYLE, FontWeight.NORMAL, 15));
		back.setFocusTraversable(true);
		back.setStyle("-fx-background-radius: 30, 30, 29, 28;"+
		  		"-fx-padding: 3px 10px 3px 10px;"+
		  		"-fx-background-color: linear-gradient(lightblue, white );");
		back.setOnAction(this);
		
  		
  		// HBox for logout and selected author button
  		HBox hlogout = new HBox(20);
  		hlogout.getChildren().addAll(back, logout);
  		hlogout.setAlignment(Pos.TOP_RIGHT);
  		
		
		// Border Pane
		BorderPane bp = new BorderPane();
		bp.setCenter(verticalLayout);
		bp.setTop(hlogout);
		//bp.setLayoutX(100);
		bp.setStyle("-fx-background-color:  linear-gradient(lightblue, white);"+
			       " -fx-border-color: white;"+
			       " -fx-border-radius: 20;"+
			       "-fx-padding: 10 10 10 10;"+
			        "-fx-background-radius: 20;");
		
		
		//Add Scroll pane
		ScrollPane sp = new ScrollPane();
		sp.setContent(bp);
		sp.setFocusTraversable(true);
		sp.setVbarPolicy(ScrollBarPolicy.ALWAYS);
		sp.setHbarPolicy(ScrollBarPolicy.AS_NEEDED);
		sp.setFitToWidth(true);
		sp.setFitToHeight(true);
		sp.setStyle("-fx-background-color:  linear-gradient(lightblue, white);"+
			       " -fx-border-color: white;"+
			       " -fx-border-radius: 20;"+
			       "-fx-padding: 10 10 10 10;"+
			        "-fx-background-radius: 20;");
		
		// Scene
	
		Scene authorDetailsScene = new Scene(bp, 1000, 650);
		authorDetailsStage.setScene(authorDetailsScene);
		authorDetailsStage.show();
		
		// Handle Key Event
		authorDetailsScene.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				if(event.getCode()==KeyCode.BACK_SPACE)
					handleBackEvent();
				
			}
		});
		
	}

	// Convert Alias list in proper format
	public String convertToString(String[] aliases) {
		String result = "";
		for(String s:aliases){
			result = result + s + "\n";
			
		}
		return result;
		
	}
	@Override
	public void handle(ActionEvent event) {
		try{
		// Handle Action for Conference Name Drop Down
		if(event.getSource() == confName){
			Set<String> value = selectedAuthor.getCommitteeMemberInfo().get(confName.getSelectionModel().getSelectedItem());
			String display="";
			for(String s: value){
				display = display + s + "\n";
			}
			posHeld.setText(display);	
		}
		
		// Handle action of back button
		
		if(event.getSource() == back){
			
			handleBackEvent();

		}
		
		if(event.getSource() == logout){
			new LoginView().start(authorDetailsStage);
		}
		
		if(event.getSource() == similarAuthor){
			Set<Author> simAuth = new FindResearcher().findAuthorsWithSimilarProfile(selectedAuthor);
			ObservableList<Author> simList = FXCollections.observableArrayList(simAuth);
			searchRes.setResultLbl(simAuth.size(), "Similar Author", selectedAuthor.getName());
			searchRes.start(authorDetailsStage, simList, userID);
		}
		
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}

	// Takes you back to previous screen
	private void handleBackEvent() {
	
		try {
			searchRes.setPrevScreen("search");
			searchRes.setResultLbl(prevPage);
			searchRes.start(authorDetailsStage, masterData, userID);

			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	public void sendAuthorDetails(Author selectedItem, ObservableList<Author> masterData) {
		selectedAuthor = selectedItem;
		this.masterData = masterData;
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		
		
	}
}
