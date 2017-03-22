package main.java.view;
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

	private Stage loginStage;
	private Label password, userName;
	private TextField userNameInput;
	private PasswordField passwordInput;
	private Button registerButton, loginButton;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		// TODO Auto-generated method stub
		loginStage = primaryStage;
		loginStage.setTitle("Login Page");
		
		// Layout for Login page
		GridPane loginGrid = new GridPane();
		loginGrid.setPadding(new Insets(10, 10, 10, 10));
		loginGrid.setAlignment(Pos.CENTER);
		loginGrid.setVgap(8);
		loginGrid.setHgap(10);
		
		// UserName Label
		userName = new Label("Username:");
		userName.setFont(Font.font("Tahoma", FontWeight.NORMAL, 15));
		GridPane.setConstraints(userName, 0, 0);
		
		// UserName textbox
		userNameInput = new TextField();
		userNameInput.setPromptText("Enter username here");
		GridPane.setConstraints(userNameInput, 1, 0);
		
		// Password Label
		password = new Label("Password:");
		password.setFont(Font.font("Tahoma", FontWeight.NORMAL, 15));
		GridPane.setConstraints(password, 0, 2);
		
		// Password textbox
		passwordInput = new PasswordField();
		passwordInput.setPromptText("Enter password here");
		GridPane.setConstraints(passwordInput, 1, 2);
		
		// Login Button
		loginButton = new Button("Login");
		loginButton.setFont(Font.font("Tahoma", FontWeight.NORMAL, 15));
		GridPane.setConstraints(loginButton, 1, 4);
		
		loginButton.setOnAction(this);
		
		// Register Button
		registerButton = new Button("New User?");
		registerButton.setFont(Font.font("Tahoma", FontWeight.NORMAL, 15));
		GridPane.setConstraints(registerButton, 1, 5);
		
		registerButton.setOnAction(this);
		 
		// Add all components to grid
		loginGrid.getChildren().addAll(loginButton, registerButton, passwordInput, password, userName, userNameInput);
		
		// Final Layout using Stack Pane for setting background color
		StackPane finalLayout = new StackPane();
		finalLayout.setStyle("-fx-background-color: DARKGRAY; -fx-padding: 10;");
		finalLayout.getChildren().addAll(loginGrid);
		
		// Login Scene
		Scene loginScene = new Scene(finalLayout, 800, 800);
		loginStage.setScene(loginScene);
		loginStage.show();
	}
	@Override
	public void handle(ActionEvent event) {
		// TODO Auto-generated method stub
		// Redirect to Register Page
		if(event.getSource() == registerButton){
			RegisterView redirectToRegister = new RegisterView();
			try {
				redirectToRegister.start(loginStage);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		//Redirect to Search Page
		if(event.getSource() == loginButton){
			SearchView redirectToSearch = new SearchView();
			try {
				redirectToSearch.start(loginStage);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	
}
