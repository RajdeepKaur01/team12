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

	// This function serves as test for the function to initialize a dblp sax
	// parser and parse dblp xml
	@Test
	public void testInitializeAndRunSAXParser() {

		// The expected output is true if the input file path is initialized and
		// parsed correctly.
		assertTrue(null, iFrontEndTestDummyObj.initializeAndRunSAXParser(""));

	}

	// This function serves as test for the function to initialize a committee
	// parser and parse committee directory
	@Test
	public void testInitializeAndRunCommitteeParser() {

		assertTrue(null, iFrontEndTestDummyObj.initializeAndRunCommitteParser(""));
	}

	// This function serves as test to commit records found by CommitteeParser and SaxParser
	@Test
	public void testInsertRecordsInDatabase() {

		assertTrue(null, iFrontEndTestDummyObj.insertRecordsInDatabase());
	}

}
