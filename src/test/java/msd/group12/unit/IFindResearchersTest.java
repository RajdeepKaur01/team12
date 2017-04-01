package test.java.msd.group12.unit;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import main.java.queryengine.DAOFactory;
import main.java.queryengine.MariaDBDaoFactory;
import test.java.msd.group12.TestObjectFactory;
import test.java.msd.group12.TestObjectFactory.IFindResearchersDummyTest;

public class IFindResearchersTest {

	public static final DAOFactory daoFactory = MariaDBDaoFactory.getInstance();
	IFindResearchersDummyTest iFindResearchersDummyObj = new TestObjectFactory().new IFindResearchersDummyTest();
	
	@Test
	public void testFindAuthorsByAreaOfExpertise() {
		
		assertEquals(true , iFindResearchersDummyObj.findAuthorsByPositionHeld("G", 10)!= null);
	}
	@Test
	public void testFindAuthorsByAuthorName() {
		
		assertEquals(true , iFindResearchersDummyObj.findAuthorsByAuthorName("Gert Smolka", 10)!= null);
	}

	@Test
	public void testFindAuthorsInfoByAuthorName() {
		
		assertEquals(true, iFindResearchersDummyObj.findAuthorsInfoByAuthorName("Fu-Chiang Tsui", 10)!= null);
	}

	@Test
	public void testfindAuthorsByResearchPaperTitle() {
		
		assertEquals(true , iFindResearchersDummyObj.findAuthorsByResearchPaperTitle("Access Control in Object-Oriented Database Systems", 7)!= null);
	}

	@Test
	public void testFindAuthorsByYearOfPublication() {
		assertEquals(true , iFindResearchersDummyObj.findAuthorsByYearOfPublication(2008, 1)!= null);
	}

	@Test
	public void testFindAuthorsByConferenceName() {
		
		assertEquals(true , iFindResearchersDummyObj.findAuthorsByConferenceName("Conceptual Modeling - ER 2008",10)!= null);
	}


	@Test
	public void testFindAuthorsByConferenceStringInt() {
		
		assertEquals(true , iFindResearchersDummyObj.findAuthorsByConferenceAcronym("ER", 10)!= null);
	}

}
