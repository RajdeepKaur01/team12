package main.java.view;
import main.java.entities.*;
import main.java.search.FindResearcher;

import java.awt.GridBagConstraints;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.text.TabableView;


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
import javafx.scene.control.cell.PropertyValueFactory;
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

public class AuthorDetailsView extends Application implements EventHandler<ActionEvent>{
	private Stage authorDetailsStage;
	private Button similarProfileButton, back;
	private GridPane authorGrid;
	private Author selectedAuthor;
	private TableView<Article> journalTable;
	private TableView<InProceeding> proceedingTable;
	static final String FONTSTYLE = "Tahoma";
	ChoiceBox<String> confName;
	Label confYear, posHeld;
	private ObservableList<Author> masterData;

	public static void main (String args) {
		launch(args);
	}
	@SuppressWarnings("unchecked")
	@Override
	public void start(Stage primaryStage) throws Exception {
		authorDetailsStage = primaryStage;
		authorDetailsStage.setTitle("Author Details");
		FindResearcher find = new FindResearcher();
		
		// Layout for Login page
		authorGrid = new GridPane();
		authorGrid.setPadding(new Insets(10, 10, 10, 10));
		authorGrid.setAlignment(Pos.CENTER);
		authorGrid.setVgap(15);
		authorGrid.setHgap(20);
		authorGrid.setPrefHeight(50);
		
		// Author Name Label
		Label authorNameLabel = new Label("Author Name:");
		authorNameLabel.setFont(Font.font(FONTSTYLE, FontWeight.NORMAL, 15));
		authorNameLabel.setAlignment(Pos.TOP_LEFT);
		GridPane.setConstraints(authorNameLabel, 0, 0);
		
		Label authorName = new Label(selectedAuthor.getName());
		authorName.setId("authorName");
		authorName.setFont(Font.font(FONTSTYLE, FontWeight.NORMAL, 15));
		authorName.setAlignment(Pos.TOP_LEFT);
		GridPane.setConstraints(authorName, 1, 0);
		
		// Position held by Author Label
		Label confNameLabel = new Label("Conference Name:");
		confNameLabel.setFont(Font.font(FONTSTYLE, FontWeight.NORMAL, 15));
		GridPane.setConstraints(confNameLabel, 0, 3);
		
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
		confName.getSelectionModel().selectFirst();
		GridPane.setConstraints(confName, 1, 3);
		
		confName.setOnAction(this);
		
		// PositionHeld Label
		Label posHeldLabel = new Label("Position Held & Year:");
		posHeldLabel.setFont(Font.font(FONTSTYLE, FontWeight.NORMAL, 15));
		GridPane.setConstraints(posHeldLabel, 0, 4);
		
		posHeld = new Label();
		posHeld.setId("posHeld");
		posHeld.setFont(Font.font(FONTSTYLE, FontWeight.NORMAL, 15));
		if(selectedAuthor.getCommitteeMemberInfo().size() != 0)
			posHeld.setText(selectedAuthor.getCommitteeMemberInfo().get(confName.getSelectionModel().getSelectedItem()).toString());
		GridPane.setConstraints(posHeld, 1, 4);
		
		// Get Value for Alias and Url
		Author authorDet = new FindResearcher().getAuthorInfo(selectedAuthor);
		
		// Alias Label
		Label aliasLabel = new Label("Alias:");
		aliasLabel.setFont(Font.font(FONTSTYLE, FontWeight.NORMAL, 15));
		GridPane.setConstraints(aliasLabel, 0, 1);
		
		Label alias = new Label(convertToString(authorDet.getAuthorInfo().getAliases()));
		alias.setId("alias");
		alias.setWrapText(true);
		alias.setFont(Font.font(FONTSTYLE, FontWeight.NORMAL, 15));
		GridPane.setConstraints(alias, 1, 1);
		
		// Homepage URL Label
		Label urlLabel = new Label("HomePage URL:");
		urlLabel.setFont(Font.font(FONTSTYLE, FontWeight.NORMAL, 15));
		GridPane.setConstraints(urlLabel, 0, 2);
		
		//System.out.println(authorDet.getAuthorInfo().getHomePageURL().toString());
		
		Label url = new Label();
		if(authorDet.getAuthorInfo().getHomePageURL() == null){
			url.setText("No URL");
		}
		else
			url.setText(authorDet.getAuthorInfo().getHomePageURL().toString());
		url.setId("url");
		url.setFont(Font.font(FONTSTYLE, FontWeight.NORMAL, 15));
		GridPane.setConstraints(url, 1, 2);
		
		// Search Similar profile
		similarProfileButton = new Button("Search Similar Profiles");
		similarProfileButton.setFont(Font.font(FONTSTYLE, FontWeight.NORMAL, 15));
		GridPane.setConstraints(similarProfileButton, 1, 7);
		
		similarProfileButton.setOnAction(this);
		
		// Back Button
		back = new Button("Return to Search Results");
		back.setId("back");
		back.setFont(Font.font(FONTSTYLE, FontWeight.NORMAL, 15));
		GridPane.setConstraints(similarProfileButton, 2, 7);
		back.setFocusTraversable(true);
		
		back.setOnAction(this);
		
		// List label
		Text text1 = new Text("List of Author's Publications");
		text1.setFont(Font.font(FONTSTYLE, FontWeight.BOLD, 15));
		GridPane.setConstraints(text1, 0, 6);
		
		// Journal Table
		journalTable = new TableView<>();
		journalTable.setId("journalTable");
		journalTable.setPrefHeight(400);
		journalTable.setPrefWidth(500);
		journalTable.setFocusTraversable(false);
		//Columns : Journal table : Article , Year
		TableColumn<Article, String> articleNameCol = new TableColumn<Article, String>("Article Name");
		articleNameCol.setPrefWidth(300);
		TableColumn<Article, Integer> articleYearCol = new TableColumn<Article, Integer>("Year");
		articleYearCol.setPrefWidth(100);
		//Add Columns
		journalTable.getColumns().addAll(articleNameCol, articleYearCol);
		
		// Map Columns to attributes of class
		articleNameCol.setCellValueFactory(
                new PropertyValueFactory<Article, String>("title"));
		articleYearCol.setCellValueFactory(
                new PropertyValueFactory<Article, Integer>("year"));
		
		// Add data to table
		Author tAuthor = find.getResearchPapers(selectedAuthor);
		journalTable.setItems(FXCollections.observableArrayList(tAuthor.getArticles()));
		
		
		// Proceedings Table
		proceedingTable = new TableView<>();
		proceedingTable.setId("proceedingTable");
		proceedingTable.setPrefHeight(400);
		proceedingTable.setPrefWidth(500);
		proceedingTable.setFocusTraversable(false);
		//Columns : proceeding table : Publication , Year
		TableColumn<InProceeding, String> publicationNameCol = new TableColumn<InProceeding, String>("Publication Name");
		publicationNameCol.setPrefWidth(300);
		TableColumn<InProceeding, Integer> publicationYearCol = new TableColumn<InProceeding, Integer>("Year");
		publicationYearCol.setPrefWidth(100);
		//Add Columns
		proceedingTable.getColumns().addAll(publicationNameCol, publicationYearCol);
		
		// Map Columns to attributes of class
		publicationNameCol.setCellValueFactory(
                new PropertyValueFactory<InProceeding, String>("title"));
		publicationYearCol.setCellValueFactory(
                new PropertyValueFactory<InProceeding, Integer>("year"));
		
		// Sample test data 
		proceedingTable.setItems(FXCollections.observableArrayList(tAuthor.getInProceedings()));
		
		//add all elements to grid
		authorGrid.getChildren().addAll(authorNameLabel, authorName, alias, aliasLabel, url, urlLabel, text1);
		if(selectedAuthor.getCommitteeMemberInfo().size() != 0){
			authorGrid.getChildren().addAll(posHeldLabel, posHeld, confNameLabel, confName);
		}
		
		//HBox
		HBox horizontallayout = new HBox(20);
		horizontallayout.getChildren().addAll(journalTable, proceedingTable);
		horizontallayout.setAlignment(Pos.CENTER);
		horizontallayout.setPrefWidth(800);
		horizontallayout.setPrefHeight(400);
		
		// HBox for buttons
		HBox buttonlayout = new HBox(20);
		buttonlayout.getChildren().addAll(similarProfileButton, back);
		buttonlayout.setAlignment(Pos.CENTER);
		
		
		// VBox
		VBox verticalLayout = new VBox(10);
		verticalLayout.getChildren().addAll(authorGrid, horizontallayout, buttonlayout);
		verticalLayout.setAlignment(Pos.CENTER);
		
		// Final Layout using Stack Pane for setting background color
		FlowPane finalLayout = new FlowPane();
		finalLayout.getChildren().addAll(verticalLayout);
		finalLayout.setAlignment(Pos.CENTER);
		finalLayout.setStyle("-fx-background-color: DARKGRAY; -fx-padding: 10;");
		
		
		// Login Scene
		
		Scene authorDetailsScene = new Scene(finalLayout, 1000, 800);
		authorDetailsStage.setScene(authorDetailsScene);
		authorDetailsStage.show();
	}

	// Convert Alias list in proper format
	public String convertToString(String[] aliases) {
		String result = "";
		for(String s:aliases){
			result = result + s + "\n";
			
		}
		System.out.println(result);
		return result;
		
	}
	@Override
	public void handle(ActionEvent event) {
		
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
		
		else if(event.getSource() == back){
			System.out.println("back");
		SearchResultView searchRes = new SearchResultView();
		try {
			searchRes.start(authorDetailsStage, masterData);
		} catch (Exception e) {
			System.out.println(e);
		}
		}
	}

	public void sendAuthorDetails(Author selectedItem, ObservableList<Author> masterData) {
		selectedAuthor = selectedItem;
		this.masterData = masterData;
	}
	
	
}
