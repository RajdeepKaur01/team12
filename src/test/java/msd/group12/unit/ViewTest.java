package test.java.msd.group12.unit;
import main.java.entities.Author;
import main.java.view.LoginView;
import static org.junit.Assert.*;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import static javafx.scene.input.KeyCode.TAB;

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
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;
import javafx.scene.input.KeyCode;
/**
 * Functional tests for login into the application. 
 * View Tests are ignored as there  is compatibility issues seen between TestFX and Maven.
 * It works as a single JUNIT test on local machine
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ViewTest{

	// Initiate primary stage to start test
    public static FxRobot fx = new FxRobot();
//System
   @Ignore @BeforeClass
    public static void setup() throws Exception {
        FxToolkit.registerPrimaryStage();
        FxToolkit.setupStage((stage) -> {
            
            try {
				stage.setScene(new LoginView().createLoginPane());
				
				
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
    public void aLoginTest() throws TimeoutException{
    	 FxToolkit.setupStage((stage) -> {
             
             try {
 				stage.setScene(new LoginView().createLoginPane());
 				
 				
 			} catch (Exception e) {
 				// TODO Auto-generated catch block
 				e.printStackTrace();
 			}
         });
         FxToolkit.showStage();

         
         fx.clickOn("#btnLogin");
     	 fx.clickOn("OK");
 		 assertNull((Button) fx.lookup("#searchButton").query());
         
         fx.clickOn("#username").write("a");
         fx.clickOn("#password").write("m");
         fx.clickOn("#btnLogin");
     	 fx.clickOn("OK");
 		 assertNull((Button) fx.lookup("#searchButton").query());

 		fx.clickOn("#username").type(KeyCode.BACK_SPACE);
    	fx.clickOn("#username").write("mohit");
    	fx.clickOn("#password").type(KeyCode.BACK_SPACE);
    	fx.clickOn("#btnLogin");
    	fx.clickOn("OK");
		assertNull((Button) fx.lookup("#searchButton").query());
		

    	fx.clickOn("#username").push(TAB);

		fx.clickOn("#password").write("mohit");
		fx.clickOn("#btnLogin");
		assertNotNull((Button) fx.lookup("#searchButton").query());

    } 
    

    // Test for search View
    @Test
      public void cSearchTest(){
      	
    fx.clickOn("#advanceSearch");
     	//WaitForAsyncUtils.sleep(60, TimeUnit.SECONDS);
    fx.clickOn("#back");
   	//Search normal query by title
   	fx.clickOn("#searchInput").write("Access Control in Object-Oriented Database Systems");
   	fx.clickOn("#searchButton");
   	//WaitForAsyncUtils.sleep(60, TimeUnit.SECONDS);
   	 
   	// Verify Results in Table
  	   	TableView<Author> table = fx.lookup("#authorDetails").query();
  	   	assertTrue(table.getItems().size()>0);
  
  	//fx.clickOn("#authorDetails").clickOn("#authorDetails");
   	// filter by authorName
   	assertEquals("Author Name", ((ChoiceBox<String>) fx.lookup("#filterBox").query()).getSelectionModel().getSelectedItem());
   	fx.clickOn("#filterText").write("elisa");
   	fx.clickOn("#applyFilter");
   	TableView<Author> table1 = fx.lookup("#authorDetails").query();
   	assertNotNull(table1.getItems().size());
   	fx.clickOn("#authorDetails");
   	fx.clickOn("1").clickOn("1");
   	//check details on new Page
   	Label label = fx.lookup("#authorName").query();
   	assertEquals("Elisa Bertino", label.getText());
   	assertEquals("ecoop", ((ChoiceBox<String>) fx.lookup("#confName").query()).getSelectionModel().getSelectedItem());
   	assertEquals("Program Chair, 2000", ((Label) fx.lookup("#posHeld").query()).getText());
 
   	//check records in article and conference table
   	
   	table = fx.lookup("#journalTable").query();
   	assertTrue(table.getItems().size()==0);
 
    }
      
    
    @Test
    public void dSimilarAuthorTest(){
    	
    	fx.clickOn("#similarAuthor");
    	TableView<Author> table = fx.lookup("#authorDetails").query();
       	assertTrue(table.getItems().size()>0);
    	fx.clickOn("1").clickOn("1");

       	// Test Back button 
       	fx.clickOn("#back");
       	table = fx.lookup("#authorDetails").query();
       	assertTrue(table.getItems().size()>0);
       
       	fx.clickOn("#newSearch");
    	
    }
    
      // Test for Advance Search - Position Held
    @Test
      public void ePositionHeldSearchTest(){
      	// go to advance search screen
      	fx.clickOn("#advanceSearch");
      	
      	Boolean result1 = ((ChoiceBox<String>) fx.lookup("#positionHeldText").query()).isDisabled();
      	assertEquals(false,result1);
      	
      	Boolean result2 = ((RadioButton) fx.lookup("#positionHeldCheck").query()).isSelected();
      	assertEquals(true,result2);
      	
      	fx.clickOn("#search");
      	TableView<Author> table1 = fx.lookup("#authorDetails").query();
      	assertTrue( table1.getItems().size()>0);
       	
      	fx.clickOn("#newSearch");
      }
   
   // Test for Advance Search - confName
      @Test
   public void fConfNameSearchTest(){
   	commonAdvanceTest("conferenceName", "Conceptual Structures: From Information to Intelligence, 18th International Conference on Conceptual Structures, ICCS 2010, Kuching, Sarawak, Malaysia, July 26-30, 2010. Proceedings", 1046);
   }
   
   // Test for Advance Search - Acronym
    @Test
   public void gAcronymSearchTest(){
   	 
   	 fx.clickOn("#advanceSearch");
    	Boolean result = ((ChoiceBox<String>) fx.lookup("#acronymText").query()).isDisabled();
    	assertEquals(true,result);
    	
    	fx.clickOn("#acronymCheck");
       	Boolean result2 = ((RadioButton) fx.lookup("#acronymCheck").query()).isSelected();
       	assertEquals(true,result2);
       	
       	fx.clickOn("#search");
       	TableView<Author> table1 = fx.lookup("#authorDetails").query();
       	assertTrue( table1.getItems().size()>0);
        	
       	fx.clickOn("#newSearch");
   }
   
   // Test for Advance Search - Author Name
   @Test
   public void hAuthorNameTest(){
   	commonAdvanceTest("authorName", "Elisa Bertino", 1);
   }
   
// Test for Advance Search - Year Of Publication
   @Test
   public void iYearOfPublicationTest(){
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
   	
   	//Enter search by fiel
   	fx.clickOn("#"+check+"Text").write(value);
   	fx.clickOn("#search");
   	TableView<Author> table1 = fx.lookup("#authorDetails").query();
    	assertTrue(table1.getItems().size()>0);
    	
   	fx.clickOn("#newSearch");
   }
   
   
    @Test
    public void jaddAuthors(){
    	fx.clickOn("#advanceSearch");
    	fx.clickOn("#authorNameCheck");
    	fx.clickOn("#authorNameText").write("Andy Evans");
    	fx.clickOn("#search");
    	fx.clickOn("0");
    	fx.clickOn("#addButton");
    	fx.clickOn("OK");
    	fx.clickOn("#viewSelectedList");
    	fx.clickOn("Author Name");
    	fx.clickOn("Andy Evans");
    	fx.clickOn("#desctxt").write("select this!");
    	fx.clickOn("#savebtn");
    	fx.clickOn("OK");
    	assertTrue(((TextArea) fx.lookup("#desctxt").query()).getText().length()>0);
    	fx.clickOn("#delbtn");
    	fx.clickOn("OK");
    	assertNull((TextField) fx.lookup("Andy Evans").query());
    	
    	
    }
    
    @Test
    public void kExportTest(){
    	fx.clickOn("#exportbtn");
    	fx.type(KeyCode.ENTER);
    	assertNotNull((TextField) fx.lookup("#search").query());
    	fx.clickOn("#search");
    }
    
   
}

