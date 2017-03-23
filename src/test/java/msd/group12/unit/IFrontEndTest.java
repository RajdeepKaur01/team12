package test.java.msd.group12.unit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.sql.PreparedStatement;

import org.junit.Test;

import test.java.msd.group12.TestObjectFactory;
import test.java.msd.group12.TestObjectFactory.IFrontEndDummyTest;

/* 
 * This class is a JUnit test which was constructed to test the functions in the IFrontEnd interface.  
 */

public class IFrontEndTest {
	
	PreparedStatement pStatement;
	IFrontEndDummyTest iFrontEndTestDummyObj = new TestObjectFactory().new IFrontEndDummyTest();

	//This function serves as a test for the function to initialize a parser and run it in the front end.
	@Test
	public void testInitializeAndRunParser(){
		
		//The expected output is true if the input file path is initialized and parsed correctly.
		assertTrue(null , iFrontEndTestDummyObj.initializeAndRunParser(""));
		
	}
	
		
	//This function serves as a test for the function to setup a connection with the database.
	@Test
	public void testSetupDBConnection(){
		
		//The expected output is true is the connection is set up successfully.
		assertEquals(null , iFrontEndTestDummyObj.setUpDBConnection());
	}
	
	//This function serves as a test for the function to write an insert query to the database.
	@Test
	public void testCreateDBInsertStatements(){
			
			//The expected output is true if the query to the database is executed correctly.
			assertTrue(null , iFrontEndTestDummyObj.createDBInsertStatements());  
			
		}

	//This function serves as a test for the function to commit records to the database.
	@Test
	public void testCommitRecords(){
				
			//The expected output is true if the records are committed correctly.
			assertTrue(null , iFrontEndTestDummyObj.commitRecords());  
				
		}

}
