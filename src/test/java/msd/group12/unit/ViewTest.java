package test.java.msd.group12.unit;
import main.java.entities.Author;
import main.java.view.LoginView;
import static org.junit.Assert.*;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.testfx.api.FxRobot;
import org.testfx.api.FxToolkit;
import org.testfx.util.WaitForAsyncUtils;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
/**
 * Functional tests for login into the application.
 */

public class ViewTest{

	// Initiate primary stage to start test
    public FxRobot fx = new FxRobot();
//System
    @BeforeClass
    public static void setup() throws Exception {
        FxToolkit.registerPrimaryStage();
        FxToolkit.setupStage((stage) -> {
            
            try {
				stage.setScene(new Scene(new LoginView().returnLoginPane(),800,800));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        });
        FxToolkit.showStage();
    }

    @AfterClass
    public static void cleanup() throws Exception {
        FxToolkit.hideStage();
    }

    // Test for login View
    @Test
    public void loginTest(){
    	 fx.clickOn("#username").write("admin");
    	 fx.clickOn("#password").write("admin");
    	 fx.clickOn("#register");

    }
    
    
    // Test for Register View
    @Test
    public void registerTest(){
		 fx.clickOn("#userName").write("Rajdeep");
		 fx.clickOn("#password").write("1234");
		 fx.clickOn("#confirmPassword").write("1234");
		 fx.clickOn("#registerButton");
		 Label test = (Label) fx.lookup("#message").query();
		 assertEquals("Successfully Registered", test.getText());
		 System.out.println(test.getText());
		 fx.clickOn("#redirectLogin");
		 WaitForAsyncUtils.waitForFxEvents();
    }
    
    // Test for search View
    @Test
    public void searchTest(){
    	fx.clickOn("#button");
    	fx.clickOn("#searchInput").write("frank");
    	fx.clickOn("#searchButton");
    	fx.clickOn("#newSearch");
    	fx.clickOn("#advanceSearch");
    }
    
    // Test for Advance Search
    @Test
    public void advanceSearchTest(){
    	Boolean result = ((TextField) fx.lookup("#pubTitle").query()).isDisabled();
    	assertEquals(true,result);
    	fx.clickOn("#publicationtitle");
    	Boolean result1 = ((TextField) fx.lookup("#pubTitle").query()).isDisabled();
    	assertEquals(false,result1);
    	fx.clickOn("#back");
    }
    
    
    // Test for searchResultView
    @SuppressWarnings("unchecked")
	@Test
    public void searchResultTest(){
    	fx.clickOn("#searchInput").write("frank");
    	fx.clickOn("#searchButton");
    	TableView<Author> table = fx.lookup("#authorDetailsTable").query();
    	assertEquals(3, table.getItems().size());
    	//fx.clickOn("#authorDetailsTable").clickOn("#authorDetailsTable");
    	fx.clickOn("#filterBox").clickOn("Age");
    	assertEquals("Age", ((ChoiceBox<String>) fx.lookup("#filterBox").query()).getSelectionModel().getSelectedItem());
    	fx.clickOn("#authorDetailsTable").clickOn("Science");
    	assertEquals(2, ((TableView<Author>) fx.lookup("#authorDetailsTable").query()).getSelectionModel().getSelectedIndex());
    	fx.clickOn("#authorDetailsTable").clickOn("Science").clickOn("Science");
    }
    
    @Test
    public void authorDetailsTest(){
    	
    }
}

