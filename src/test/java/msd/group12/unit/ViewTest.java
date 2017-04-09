package test.java.msd.group12.unit;
import main.java.entities.Author;
import main.java.view.LoginView;
import static org.junit.Assert.*;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.testfx.api.FxRobot;
import org.testfx.api.FxToolkit;
import org.testfx.util.WaitForAsyncUtils;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
/**
 * Functional tests for login into the application. 
 * View Tests are ignored as there  is compatibility issues seen between TestFX and Maven.
 * It works as a single JUNIT test on local machine
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
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
    public void aLoginTest(){
    	
    	 fx.clickOn("#button");

    }
    
    // Test for search View
      @Test
       public void cSearchTest(){
       	
      	fx.clickOn("#advanceSearch");
     	fx.clickOn("#back");
    	//Search normal query by title
    	fx.clickOn("#searchInput").write("Access Control in Object-Oriented Database Systems");
    	fx.clickOn("#searchButton");
    	 
    	// Verify Results in Table
    	System.out.println("Start Testing");
   	   	TableView<Author> table = fx.lookup("#authorDetails").query();
   	 	assertEquals(3, table.getItems().size());
   	 	fx.clickOn("#authorDetails").clickOn("#authorDetails");
    	// filter by authorName
    	assertEquals("Author Name", ((ChoiceBox<String>) fx.lookup("#filterBox").query()).getSelectionModel().getSelectedItem());
    	fx.clickOn("#filterText").write("elisa");
    	fx.clickOn("#applyFilter");
    	TableView<Author> table1 = fx.lookup("#authorDetails").query();
    	assertEquals(1, table1.getItems().size());
    	fx.clickOn("#authorDetails");
    	fx.clickOn("Elisa Bertino").clickOn("Elisa Bertino");
    	//check details on new Page
    	Label label = fx.lookup("#authorName").query();
    	assertEquals("Elisa Bertino", label.getText());
    	assertEquals("ecoop", ((ChoiceBox<String>) fx.lookup("#confName").query()).getSelectionModel().getSelectedItem());
    	assertEquals("[Role:Program Chair, Year:2000]", ((Label) fx.lookup("#posHeld").query()).getText());
    	assertEquals("Elisa Bertino\n", ((TextArea) fx.lookup("#alias").query()).getText());
    	assertEquals("https://en.wikipedia.org/wiki/Elisa_Bertino", ((Label) fx.lookup("#url").query()).getText());
    	//check records in article and conference table
    	
    	table = fx.lookup("#journalTable").query();
    	assertEquals(0, table.getItems().size());
    	
    	table = fx.lookup("#proceedingTable").query();
    	assertEquals(0, table.getItems().size());
    	
    	// Test Back button 
    	fx.clickOn("#back");
    	table = fx.lookup("#authorDetails").query();
    	assertEquals(3, table.getItems().size());
    
    	fx.clickOn("#newSearch");
       }
       
       // Test for Advance Search - Position Held
     @Test
       public void dPositionHeldSearchTest(){
       	// go to advance search screen
       	fx.clickOn("#advanceSearch");
       	
       	Boolean result1 = ((ChoiceBox<String>) fx.lookup("#positionHeldText").query()).isDisabled();
       	assertEquals(false,result1);
       	
       	Boolean result2 = ((RadioButton) fx.lookup("#positionHeldCheck").query()).isSelected();
       	assertEquals(true,result2);
       	
       	fx.clickOn("#search");
       	TableView<Author> table1 = fx.lookup("#authorDetails").query();
        	assertEquals(83, table1.getItems().size());
        	
       	fx.clickOn("#newSearch");
       }
    
    // Test for Advance Search - confName
     @Test
    public void eConfNameSearchTest(){
    	commonAdvanceTest("conferenceName", "Conceptual Structures: From Information to Intelligence, 18th International Conference on Conceptual Structures, ICCS 2010, Kuching, Sarawak, Malaysia, July 26-30, 2010. Proceedings", 1046);
    }
    
    // Test for Advance Search - Acronym
      @Test
    public void fAcronymSearchTest(){
    	commonAdvanceTest("acronym", "ecoop", 1398);
    }
    
    // Test for Advance Search - Author Name
     @Test
    public void gAuthorNameTest(){
    	commonAdvanceTest("authorName", "Elisa Bertino", 1);
    }
    
 // Test for Advance Search - Year Of Publication
     @Test
    public void hYearOfPublicationTest(){
    	commonAdvanceTest("yop", "2017", 53155);
    }
    
    // Advance Search Test
    
    public void commonAdvanceTest(String check, String value, int res){
    	// go to advance search screen
    	fx.clickOn("#advanceSearch");
    	Boolean result = ((TextField) fx.lookup("#"+check+"Text").query()).isDisabled();
    	assertEquals(true,result);
    	
    	fx.clickOn("#"+check+"Check");
    	Boolean result2 = ((RadioButton) fx.lookup("#"+check+"Check").query()).isSelected();
    	assertEquals(true,result2);
    	
    	result = ((TextField) fx.lookup("#"+check+"Text").query()).isDisabled();
    	assertEquals(false,result);
    	
    	//Enter search by field
    	fx.clickOn("#"+check+"Text").write(value);
    	fx.clickOn("#search");
    	TableView<Author> table1 = fx.lookup("#authorDetails").query();
     	assertEquals(res, table1.getItems().size());
     	
    	fx.clickOn("#newSearch");
    }
}

