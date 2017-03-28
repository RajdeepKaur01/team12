package test.java.msd.group12.unit;

import static org.junit.Assert.assertEquals;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import main.java.entities.Author;
import main.java.queryengine.MariaDBDaoFactory;
import test.java.msd.group12.TestObjectFactory;
import test.java.msd.group12.TestObjectFactory.IFindResearchersDummyTest;

public class IFindResearchersTest {

	IFindResearchersDummyTest iFindResearchersDummyObj = new TestObjectFactory().new IFindResearchersDummyTest();
	
	//This function serves as a test for the function to return a list of authors by their location.
	@Test
	public void testFindAuthorsByLocation() {
		//The expected result is a list of authors based on their location.
		assertEquals(true, iFindResearchersDummyObj.findAuthorsByLocation("", 10)!= null);
	}

	//This function serves as a test for the function to return a list of authors by their area of expertise.
	@Test
	public void testFindAuthorsByAreaOfExpertise() {
		
		//The expected result is a list of authors based on their area of expertise.
		assertEquals(true , iFindResearchersDummyObj.findAuthorsByAreaOfExpertise("", 10)!= null);
	}

	//This function serves as a test for the function to return a list of authors by their past type of
	//experience and the number of years.
	@Test
	public void testFindAuthorsByPastExperience() {
		
		//The expected result is a list of authors based on their past type of experience and the number of years.
		assertEquals(true , iFindResearchersDummyObj.findAuthorsByPastExperience("", "5", 10)!= null);
	}

	//This function serves as a test for the function to return a list of authors by the number of
	//research papers they published.
	@Test
	public void testFindAuthorsByNumberOfResearchPapers() {
		
		//The expected result is a list of authors based on the number of research papers they published.
		assertEquals(true, iFindResearchersDummyObj.findAuthorsByNumberOfResearchPapers(8, 10)!= null);
	}

	//This function serves as a test for the function to return a list of authors by the number of
	//research papers they published and the date on which they published.
	@Test
	public void testFindAuthorsByNumberOfResearchPapersAndDate() {

		//The expected result is a list of authors based on the number of research papers they published and the 
		//date on which they published.
		assertEquals(true , 
				iFindResearchersDummyObj.findAuthorsByNumberOfResearchPapersAndDate(6, 20, new Date(2009-04-04))!= null); 
	}

	//This function serves as a test for the function to return a list of authors by looking up
	//keywords in the titles of their research papers.
	@Test
	public void testFindAuthorsByKeywordsInTResearchPaperTitle() {
		
		//The expected result is a list of authors based on the keywords in the titles of their research papers.
		assertEquals(true , 
				iFindResearchersDummyObj.findAuthorsByKeywordsInTResearchPaperTitle(new ArrayList<String>(), 20)!= null);
	}

	//This function serves as a test for the function to return a list of authors by the title
	//of the research papers.
	@Test
	public void testFindAuthorsByResearchPaperTitle() {
		
		//The expected result is a list of authors based on the title of their research papers.
		assertEquals(true , iFindResearchersDummyObj.findAuthorsByResearchPaperTitle("", 10)!= null);
	}

	//This function serves as a test for the function to return a list of authors by their name.
	@Test
	public void testFindAuthorsByAuthorName() {
		//The expected result is a list of authors based on their name.
		Assert.assertNotNull(iFindResearchersDummyObj.findAuthorsByAuthorName("Gert Smolka", 10));
	}

	//This function serves as a test for the function to return a list of authors by their alias.
	@Test
	public void testFindAuthorsByAlias() {
		
		//The expected result is a list of authors based on their alias.
		assertEquals(true , iFindResearchersDummyObj.findAuthorsByAlias("", 20)!= null);
	}

	//This function serves as a test for the function to return a list of authors by the conferences
	//they participated in.
	@Test
	public void testFindAuthorsByConferenceStringInt() {
		
		//The expected result is a list of authors based on the conferences they participated in.
		assertEquals(true , iFindResearchersDummyObj.findAuthorsByConference("", 10)!= null);
	}

	//This function serves as a test for the function to return a list of authors by the conferences
	//they participated in and the number of years they did so.
	@Test
	public void testFindAuthorsByConferenceStringIntInt() {
		
		//The expected result is a list of authors based on the conferences they participated in 
		//and the number of years they did so.
		assertEquals(true , iFindResearchersDummyObj.findAuthorsByConference("", 8, 10)!= null);
	}

	//This function serves as a test for the function to return a list of authors similar to 
	//another profile.
	@Test
	public void testFindAuthorsSimilarToProfile() {
		
		//The expected result is a list of similar authors based on the specified profile.		
		assertEquals(null , iFindResearchersDummyObj.findAuthorsSimilarToProfile(new Author())!= null);
	}
	
	@AfterClass
	public static void tearDown() throws SQLException {
		MariaDBDaoFactory.getInstance().closeConnection();
	}

}
