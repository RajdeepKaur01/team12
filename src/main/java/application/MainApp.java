package main.java.application;

import javafx.application.Application;
import javafx.stage.Stage;
import main.java.view.LoginView;

public class MainApp extends Application {

	public static void main(String[] args) {

		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {

		LoginView login = new LoginView();
		login.start(primaryStage);
	}

}
