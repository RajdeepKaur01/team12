package main.java.view;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.application.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.*;

public class LoginView extends Application implements EventHandler<ActionEvent> {

	Stage loginStage = new Stage();
	Label password; 
	Label userName;
	Label errorMessage;
	TextField userNameInput;
	PasswordField passwordInput;
	Button registerButton;
	Button loginButton;
	Scene loginScene;
	StackPane finalLayout;
	static final String FONTSTYLE = "Tahoma";
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		loginStage = primaryStage;
		loginStage.setTitle("Login Page");
		
		// Login Scene
		loginScene = new Scene(returnLoginPane(), 800, 800);
		loginStage.setScene(loginScene);
		loginStage.show();
	}
	
	public StackPane returnLoginPane() throws Exception{
		// Layout for Login page
		GridPane loginGrid = new GridPane();
		loginGrid.setPadding(new Insets(10, 10, 10, 10));
		loginGrid.setAlignment(Pos.CENTER);
		loginGrid.setVgap(8);
		loginGrid.setHgap(10);
		
		// UserName Label
		userName = new Label("Username:");
		userName.setId("users");
		userName.setFont(Font.font(FONTSTYLE, FontWeight.NORMAL, 15));
		GridPane.setConstraints(userName, 0, 0);
		
		// UserName textbox
		userNameInput = new TextField();
		userNameInput.setId("username");
		userNameInput.setPromptText("Enter username here");
		GridPane.setConstraints(userNameInput, 1, 0);
		
		// Password Label
		password = new Label("Password:");
		password.setFont(Font.font(FONTSTYLE, FontWeight.NORMAL, 15));
		GridPane.setConstraints(password, 0, 2);
		
		// Password textbox
		passwordInput = new PasswordField();
		passwordInput.setId("password");
		passwordInput.setPromptText("Enter password here");
		GridPane.setConstraints(passwordInput, 1, 2);
		
		// Login Button
		loginButton = new Button("Login");
		loginButton.setId("button");
		loginButton.setFont(Font.font(FONTSTYLE, FontWeight.NORMAL, 15));
		GridPane.setConstraints(loginButton, 1, 4);
		
		loginButton.setOnAction(this);
		
		// Register Button
		registerButton = new Button("New User?");
		registerButton.setId("register");
		registerButton.setFont(Font.font(FONTSTYLE, FontWeight.NORMAL, 15));
		GridPane.setConstraints(registerButton, 1, 5);
		
		registerButton.setOnAction(this);
		
		// Error Message Label in case of invalid Login
		errorMessage = new Label();
		errorMessage.setId("errorMessage");
		errorMessage.setFont(Font.font(FONTSTYLE, FontWeight.NORMAL, 15));
		GridPane.setConstraints(errorMessage, 1, 6);
		 
		// Add all components to grid
		loginGrid.getChildren().addAll(loginButton, registerButton, passwordInput, password, userName, userNameInput, errorMessage);
		
		// Final Layout using Stack Pane for setting background color
		//Color.
		finalLayout = new StackPane();
		finalLayout.setStyle("-fx-background-color: DARKGRAY ; -fx-padding: 10;");
		finalLayout.getChildren().addAll(loginGrid);
		return finalLayout;
	
	}
	@Override
	public void handle(ActionEvent event){
		// Redirect to Register Page
		if(event.getSource() == registerButton){
			RegisterView redirectToRegister = new RegisterView();
			try {
				redirectToRegister.start(loginStage);
			} catch (Exception e) {
				Logger logger = Logger.getLogger("logger");
				logger.log(Level.FINE, "Register Stage not found", e);
			}
		}
		
		//Redirect to Search Page
		if(event.getSource() == loginButton){
			SearchView redirectToSearch = new SearchView();
			try {
				redirectToSearch.start(loginStage);
			} catch (Exception e) {
				Logger logger = Logger.getLogger("logger");
				logger.log(Level.FINE, "Login Stage not found", e);
			}
		}
		
	}
	
	
}
