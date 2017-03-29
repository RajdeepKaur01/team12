package main.java.application;

import javafx.application.Application;
import javafx.stage.Stage;
import main.java.view.LoginView;
import main.java.view.SearchView;

public class MainApp extends Application {
	
	public static void main(String[] args) {

		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {

		SearchView login = new SearchView();
		login.start(primaryStage);
	}

}
