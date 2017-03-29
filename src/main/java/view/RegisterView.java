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
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.*;

public class RegisterView extends Application implements EventHandler<ActionEvent> {

	private Stage registerStage = new Stage();
	private Button registerButton;
	private Hyperlink redirectLogin;
	private Label message;
	static final String FONTSTYLE = "Tahoma";
	
	@Override
	public void start(Stage stage) throws Exception {
		registerStage = stage;
		registerStage.setTitle("Register New User");
		
		// Layout for Register page
		GridPane registerGrid = new GridPane();
		registerGrid.setPadding(new Insets(10, 10, 10, 10));
		registerGrid.setAlignment(Pos.CENTER);
		registerGrid.setVgap(8);
		registerGrid.setHgap(10);
		
		// UserName Label
		Label userName = new Label("Username:");
		userName.setFont(Font.font(FONTSTYLE, FontWeight.NORMAL, 15));
		GridPane.setConstraints(userName, 0, 0);
		
		// UserName textbox
		TextField userNameInput = new TextField();
		userNameInput.setId("userName");
		userNameInput.setPromptText("Enter username here");
		GridPane.setConstraints(userNameInput, 1, 0);
		
		// Password Label
		Label password = new Label("Password:");
		password.setFont(Font.font(FONTSTYLE, FontWeight.NORMAL, 15));
		GridPane.setConstraints(password, 0, 2);
		
		// Password textbox
		PasswordField passwordInput = new PasswordField();
		passwordInput.setId("password");
		passwordInput.setPromptText("Enter password here");
		GridPane.setConstraints(passwordInput, 1, 2);
		
		// Password Label
		Label confirmPassword = new Label("Confirm Password:");
		confirmPassword.setFont(Font.font(FONTSTYLE, FontWeight.NORMAL, 15));
		GridPane.setConstraints(confirmPassword, 0, 3);
		
		// Password textbox
		PasswordField confirmPasswordInput = new PasswordField();
		confirmPasswordInput.setId("confirmPassword");
		confirmPasswordInput.setPromptText("Enter password here");
		GridPane.setConstraints(confirmPasswordInput, 1, 3);
		
		// Register Button
		registerButton = new Button("Register");
		registerButton.setId("registerButton");
		registerButton.setFont(Font.font(FONTSTYLE, FontWeight.NORMAL, 15));
		GridPane.setConstraints(registerButton, 1, 5);
		
		registerButton.setOnAction(this);
		
		// message label
		message = new Label();
		message.setId("message");
		message.setFont(Font.font(FONTSTYLE, FontWeight.NORMAL, 15));
		GridPane.setConstraints(message, 1, 8);
		
		// HyperLink to Login Page
		redirectLogin = new Hyperlink();
		redirectLogin.setId("redirectLogin");
		redirectLogin.setText("Log in Again");
		redirectLogin.setFont(Font.font(FONTSTYLE, FontWeight.NORMAL, 15));
		GridPane.setConstraints(redirectLogin, 1, 6);
		
		redirectLogin.setOnAction(this);
		 
		// Add all components to grid
		registerGrid.getChildren().addAll(redirectLogin, registerButton, passwordInput, password, userName, userNameInput, confirmPasswordInput, confirmPassword, message);
		
		// Final Layout using Stack Pane for setting background color
		StackPane finalLayout = new StackPane();
		finalLayout.setStyle("-fx-background-color: DARKGRAY; -fx-padding: 10;");
		finalLayout.getChildren().addAll(registerGrid);
		
		// Login Scene
		Scene registerScene = new Scene(finalLayout, 800, 800);
		registerStage.setScene(registerScene);
		registerStage.show();
	}
	@Override
	public void handle(ActionEvent event) {
		if(event.getSource() == registerButton){
			message.setText("Successfully Registered");
		}
		if(event.getSource() == redirectLogin){
			LoginView login = new LoginView();
			try {
				login.start(registerStage);
			} catch (Exception e) {
				Logger logger = Logger.getLogger("logger");
				logger.log(Level.FINE, "Login Stage not found", e);
			}
		}
	}
	
	
}
