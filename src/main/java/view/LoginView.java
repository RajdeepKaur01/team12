package main.java.view;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.plaf.basic.BasicSplitPaneUI.KeyboardDownRightHandler;

import javafx.application.*;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Reflection;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.*;
import main.java.auth.Auth;
import main.java.entities.User;
import main.java.interfaces.IAuth;

public class LoginView extends Application implements EventHandler<ActionEvent> {

	static final String FONTSTYLE = "Times New Roman";
	final ProgressIndicator progressIndicator = new ProgressIndicator();
	final TextField txtUserName = new TextField();
	final PasswordField txtPassword = new PasswordField();
	final Label lblMessage = new Label();

	Stage loginStage = new Stage();
	Scene loginScene;
	StackPane finalLayout;

	Label userNameLabel = new Label("Username");
	Label passwordLabel = new Label("Password");
	Button btnLogin = new Button("Login");
	Auth authObject = new Auth();

	@Override
	public void start(Stage primaryStage) throws Exception {
		
		loginStage = primaryStage;
		loginStage.setTitle("Login Page");

	
		
		/*txtUserName.focusedProperty().addListener((observable, oldValue, newValue) -> {
		    if(!newValue && txtUserName.getText().length()==0) { // we only care about loosing focus
		   			Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("UserName left bLank");
					String s = "Please type in your username correctly";
					alert.setContentText(s);
					alert.showAndWait();
		            
		    }
		});
		
		txtPassword.focusedProperty().addListener((observable, oldValue, newValue) -> {
		    if(!newValue && txtUserName.getText().length()==0){
		   			Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Password left blank");
					String s = "Please type in your password correctly";
					alert.setContentText(s);
					alert.showAndWait();
		    }
		});*/
		loginStage.setScene(createLoginPane());
		loginStage.setResizable(false);
		loginStage.show();
	}
	

	public Scene createLoginPane() throws Exception {

		BorderPane borderPaneObj = new BorderPane();
		borderPaneObj.setPadding(new Insets(10, 50, 50, 50));

		// Adding HBox
		HBox hBoxObj = new HBox();
		hBoxObj.setPadding(new Insets(20, 20, 20, 30));

		progressIndicator.setMaxWidth(100);
		progressIndicator.setMaxHeight(100);
		progressIndicator.setVisible(false);
		txtUserName.setId("username");
		txtPassword.setId("password");
		// Layout for Login page
		GridPane gridPane = new GridPane();
		gridPane.setAlignment(Pos.CENTER);
		gridPane.setPadding(new Insets(20, 20, 20, 20));
		gridPane.setHgap(5);
		gridPane.setVgap(5);

		gridPane.add(userNameLabel, 0, 0);
		gridPane.add(txtUserName, 1, 0);
		gridPane.add(passwordLabel, 0, 1);
		gridPane.add(txtPassword, 1, 1);
		gridPane.add(btnLogin, 1, 2);
		gridPane.add(lblMessage, 1, 3);

		Reflection r = new Reflection();
		r.setFraction(0.7f);
		gridPane.setEffect(r);

		DropShadow dropShadow = new DropShadow();
		dropShadow.setOffsetX(5);
		dropShadow.setOffsetY(5);

		Text text = new Text("Welcome");
		text.setFont(Font.font(FONTSTYLE, FontWeight.BOLD, 28));

		// Adding text to HBox
		hBoxObj.getChildren().add(text);
		hBoxObj.setAlignment(Pos.CENTER);
		// Add ID's to Nodes
		borderPaneObj.setId("bp");
		gridPane.setId("root");
		btnLogin.setId("btnLogin");
		text.setId("text");

		btnLogin.setOnAction(this);

		borderPaneObj.setTop(hBoxObj);
		borderPaneObj.setCenter(gridPane);
		borderPaneObj.getChildren().addAll(progressIndicator);
		// Login Scene
		loginScene = new Scene(borderPaneObj, 1000, 650);
		
		loginScene.getStylesheets().add("login.css");

		loginScene.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				if (event.getCode() == KeyCode.ENTER) {  
		            btnLogin.fire();
		            event.consume(); // <-- stops passing the event to next node
		        }
				
			} 
		});
		
		return loginScene;
		
		
	}

	@Override
	public void handle(ActionEvent event) {
		System.out.println("reached");

		// Redirect to Search Page
		if (event.getSource() == btnLogin) {
			
		if ((txtUserName.getText().length()==0) && (txtPassword.getText().length()==0) ){
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Authentication Failed");
			String s = "UserName and Password required";
			alert.setContentText(s);
			alert.showAndWait();
		}
		else 
			{
			User user = authObject.login(txtUserName.getText(), txtPassword.getText());
				if (user != null) {
				
					SearchView redirectToSearch = new SearchView();
					try {
						redirectToSearch.start(loginStage, user.getUserId());
					} catch (Exception e) {
						Logger logger = Logger.getLogger("logger");
						logger.log(Level.FINE, "Login Stage not found", e);
					}
				} else {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Authentication Failed");
					String s = "Incorrect username or passowrd";
					alert.setContentText(s);
					alert.showAndWait();
	
				}
			}
		}

	}

	/*public static void main(String[] args) {

		launch(args);
	}
*/
}
