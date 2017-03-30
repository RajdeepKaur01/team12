package main.java.view;
import main.java.entities.*;

import java.awt.GridBagConstraints;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.text.TabableView;

import org.hamcrest.generator.HamcrestFactoryWriter;

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
	private Button similarProfileButton;
	private GridPane authorGrid;
	private Author selectedAuthor;
	private TableView<Article> journalTable;
	private TableView<Proceedings> proceedingTable;
	static final String FONTSTYLE = "Tahoma";
	ChoiceBox<String> confName;
	Label confYear, posHeld;

	public static void main (String args) {
		launch(args);
	}
	@SuppressWarnings("unchecked")
	@Override
	public void start(Stage primaryStage) throws Exception {
		authorDetailsStage = primaryStage;
		authorDetailsStage.setTitle("Author Details");
		
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
		
		Label authorName = new Label("ABC");
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
		confName.setItems(conf);
		confName.getSelectionModel().selectFirst();
		GridPane.setConstraints(confName, 1, 3);
		
		confName.setOnAction(this);
		
		// PositionHeld Label
		Label posHeldLabel = new Label("Position Held & Year:");
		posHeldLabel.setFont(Font.font(FONTSTYLE, FontWeight.NORMAL, 15));
		GridPane.setConstraints(posHeldLabel, 0, 4);
		
		posHeld = new Label();
		posHeld.setFont(Font.font(FONTSTYLE, FontWeight.NORMAL, 15));
		if(selectedAuthor.getCommitteeMemberInfo().size() != 0)
			posHeld.setText(selectedAuthor.getCommitteeMemberInfo().get(confName.getSelectionModel().getSelectedItem()).toString());
		GridPane.setConstraints(posHeld, 1, 4);
		
		// Alias Label
		Label aliasLabel = new Label("Alias:");
		aliasLabel.setFont(Font.font(FONTSTYLE, FontWeight.NORMAL, 15));
		GridPane.setConstraints(aliasLabel, 0, 1);
		
		//Label alias = new Label(""+selectedAuthor.getAlias());
		Label alias = new Label("Xyz");
		alias.setFont(Font.font(FONTSTYLE, FontWeight.NORMAL, 15));
		GridPane.setConstraints(alias, 1, 1);
		
		// Homepage URL Label
		Label urlLabel = new Label("HomePage URL:");
		urlLabel.setFont(Font.font(FONTSTYLE, FontWeight.NORMAL, 15));
		GridPane.setConstraints(urlLabel, 0, 2);
		
		Label url = new Label("www.abc.com");
		url.setFont(Font.font(FONTSTYLE, FontWeight.NORMAL, 15));
		GridPane.setConstraints(url, 1, 2);
		
		// Search Similar profile
		similarProfileButton = new Button("Search Similar Profiles");
		similarProfileButton.setFont(Font.font(FONTSTYLE, FontWeight.NORMAL, 15));
		GridPane.setConstraints(similarProfileButton, 1, 7);
		
		similarProfileButton.setOnAction(this);
		
		// List label
		Text text1 = new Text("List of Author's Publications");
		text1.setFont(Font.font(FONTSTYLE, FontWeight.BOLD, 15));
		GridPane.setConstraints(text1, 0, 6);
		
		// Journal Table
		journalTable = new TableView<>();
		journalTable.setId("journalTable");
		journalTable.setPrefHeight(400);
		journalTable.setPrefWidth(500);
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
		
		// Sample test data 
		List<Article> la = new ArrayList<Article>();
		Article a = new Article();
		a.setTitle("Article 1");
		a.setYear(2006);
		la.add(a);
		Article b = new Article();
		b.setTitle("Article 2");
		b.setYear(2016);
		la.add(b);
		journalTable.setItems(FXCollections.observableArrayList(la));
		
		
		// Proceedings Table
		proceedingTable = new TableView<>();
		proceedingTable.setId("proceedingTable");
		proceedingTable.setPrefHeight(400);
		proceedingTable.setPrefWidth(500);
		//Columns : proceeding table : Publication , Year
		TableColumn<Proceedings, String> publicationNameCol = new TableColumn<Proceedings, String>("Publication Name");
		publicationNameCol.setPrefWidth(300);
		TableColumn<Proceedings, Integer> publicationYearCol = new TableColumn<Proceedings, Integer>("Year");
		publicationYearCol.setPrefWidth(100);
		//Add Columns
		proceedingTable.getColumns().addAll(publicationNameCol, publicationYearCol);
		
		// Map Columns to attributes of class
		publicationNameCol.setCellValueFactory(
                new PropertyValueFactory<Proceedings, String>("title"));
		publicationYearCol.setCellValueFactory(
                new PropertyValueFactory<Proceedings, Integer>("year"));
		
		// Sample test data 
		List<Proceedings> lp = new ArrayList<Proceedings>();
		Proceedings p = new Proceedings();
		p.setTitle("Proceeding 1");
		p.setYear(2003);
		lp.add(p);
		proceedingTable.setItems(FXCollections.observableArrayList(lp));
		
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
		
		// VBox
		VBox verticalLayout = new VBox(10);
		verticalLayout.getChildren().addAll(authorGrid, horizontallayout, similarProfileButton);
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

	@Override
	public void handle(ActionEvent event) {
		if(event.getSource() == confName){
			Set<String> value = selectedAuthor.getCommitteeMemberInfo().get(confName.getSelectionModel().getSelectedItem());
			String display="";
			for(String s: value){
				display = display + s + "\n";
			}
			posHeld.setText(display);
			
			
			
		}
		else{
		SearchResultView searchRes = new SearchResultView();
		try {
			searchRes.start(authorDetailsStage);
		} catch (Exception e) {
			Logger logger = Logger.getLogger("logger");
			logger.log(Level.FINE, "Search Result  Stage not found", e);
		}
		}
	}

	public void sendAuthorDetails(Author selectedItem) {
		selectedAuthor = selectedItem;
		
	}
	
	
}
