package test.java.msd.group12.unit;

import static org.junit.Assert.*;

import java.sql.PreparedStatement;

import org.junit.Test;

import test.java.msd.group12.TestObjectFactory;
import test.java.msd.group12.TestObjectFactory.IFrontEndDummyTest;

/* 
 * This class is a JUnit test which was constructed to test the functions in the IFrontEnd interface.  
 */

public class IFrontEndTest {

	PreparedStatement pStatement;
	IFrontEndDummyTest iFrontEndTestDummyObj = new TestObjectFactory().new IFrontEndDummyTest(true);

	// This function serves as test for the function to initialize a dblp sax
	// parser and parse dblp xml
	@Test
	public void testInitializeAndRunSAXParser() {

		// The expected output is true if the input file path is initialized and
		// parsed correctly.
		assertFalse(iFrontEndTestDummyObj.initializeAndRunSAXParser(""));
		assertFalse(iFrontEndTestDummyObj
				.initializeAndRunSAXParser("src/test/java/msd/group12/unit/xml-tests/dblp-test1.xml"));
		assertTrue(iFrontEndTestDummyObj
				.initializeAndRunSAXParser("src/test/java/msd/group12/unit/xml-tests/dblp-test2.xml"));
		assertTrue(iFrontEndTestDummyObj
				.initializeAndRunSAXParser("src/test/java/msd/group12/unit/xml-tests/dblp-01.xml"));
	}

	// This function serves as test for the function to initialize a committee
	// parser and parse committee directory
	@Test
	public void testInitializeAndRunCommitteeParser() {

		assertFalse(iFrontEndTestDummyObj.initializeAndRunCommitteeParser(""));
		assertFalse(iFrontEndTestDummyObj.initializeAndRunCommitteeParser(
				"src/test/java/msd/group12/unit/xml-tests/committee-tests/oopsla1996-pc.xml"));
		assertFalse(iFrontEndTestDummyObj
				.initializeAndRunCommitteeParser("src/test/java/msd/group12/unit/xml-tests/committee-tests"));
		assertTrue(iFrontEndTestDummyObj.initializeAndRunCommitteeParser("committee-tests/committees"));
	}

	// This function serves as test to commit records found by CommitteeParser
	// and SaxParser
	@Test
	public void testInsertRecordsInDatabase() {

		assertTrue(null, iFrontEndTestDummyObj.insertRecordsInDatabase());
	}

}
