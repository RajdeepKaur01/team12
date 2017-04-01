package test.java.msd.group12.unit;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Test;

import main.java.queryengine.DAOFactory;
import main.java.queryengine.MariaDBDaoFactory;
import test.java.msd.group12.TestObjectFactory;
import test.java.msd.group12.TestObjectFactory.IFindResearchersDummyTest;

public class IFindResearchersTest {

	public static final DAOFactory daoFactory = MariaDBDaoFactory.getInstance();
	IFindResearchersDummyTest iFindResearchersDummyObj = new TestObjectFactory().new IFindResearchersDummyTest();
	
	@Test
	public void testFindAuthorsByPositionHeld() {
		assertTrue(iFindResearchersDummyObj.findAuthorsByPositionHeld(null).isEmpty());
		assertTrue(iFindResearchersDummyObj.findAuthorsByPositionHeld("Y").isEmpty());
		assertTrue(iFindResearchersDummyObj.findAuthorsByPositionHeld("").isEmpty());
		assertFalse(iFindResearchersDummyObj.findAuthorsByPositionHeld("G").isEmpty());
	}
	@Test
	public void testFindAuthorsByAuthorName() {
		assertTrue(iFindResearchersDummyObj.findAuthorsByAuthorName(null).isEmpty());
		assertTrue(iFindResearchersDummyObj.findAuthorsByAuthorName("4").isEmpty());
		assertTrue(iFindResearchersDummyObj.findAuthorsByAuthorName("").isEmpty());
		assertFalse(iFindResearchersDummyObj.findAuthorsByAuthorName("Gert Smolka").isEmpty());
	}
	
	@Test
	public void testFindAuthorsInfoByAuthorName() {
		assertTrue(iFindResearchersDummyObj.findAuthorsInfoByAuthorName(null).isEmpty());
		assertTrue(iFindResearchersDummyObj.findAuthorsInfoByAuthorName("4").isEmpty());
		assertTrue(iFindResearchersDummyObj.findAuthorsInfoByAuthorName("").isEmpty());
		assertFalse(iFindResearchersDummyObj.findAuthorsInfoByAuthorName("Fu-Chiang Tsui").isEmpty());
	}

	@Test
	public void testfindAuthorsByResearchPaperTitle() {
	    assertEquals(true , iFindResearchersDummyObj.findAuthorsByResearchPaperTitle("Access Control in Object-Oriented Database Systems").size() == 0);
	    assertEquals(false , iFindResearchersDummyObj.findAuthorsByResearchPaperTitle("   ").size() == 0);
	    assertEquals(false , iFindResearchersDummyObj.findAuthorsByResearchPaperTitle("123456").size() == 0);
	    assertEquals(false , iFindResearchersDummyObj.findAuthorsByResearchPaperTitle(null).size() == 0);
	}

	@Test
	public void testFindAuthorsByYearOfPublication() {
		assertEquals(true , iFindResearchersDummyObj.findAuthorsByYearOfPublication(2017).size() == 0);
		assertEquals(false , iFindResearchersDummyObj.findAuthorsByYearOfPublication(0).size() == 0);
		assertEquals(false , iFindResearchersDummyObj.findAuthorsByYearOfPublication(-1800).size() == 0);
		assertEquals(false , iFindResearchersDummyObj.findAuthorsByYearOfPublication(99999).size() == 0);
	}

	@Test
	public void testFindAuthorsByConferenceName() {
		
		assertEquals(true , iFindResearchersDummyObj.findAuthorsByConferenceName("Conceptual Modeling - ER 2008")!= null);
	}

	@Test
	public void testFindAuthorsByConferenceAcronym() {
		
		assertEquals(true , iFindResearchersDummyObj.findAuthorsByConferenceAcronym("ER").size() == 0);
		assertEquals(false , iFindResearchersDummyObj.findAuthorsByConferenceAcronym("RRR").size() == 0);
		assertEquals(false , iFindResearchersDummyObj.findAuthorsByConferenceAcronym("").size() == 0);
	    assertEquals(false , iFindResearchersDummyObj.findAuthorsByConferenceAcronym("   ").size() == 0);
	    assertEquals(false , iFindResearchersDummyObj.findAuthorsByConferenceAcronym("123456").size() == 0);
	    assertEquals(false , iFindResearchersDummyObj.findAuthorsByConferenceAcronym(null).size() == 0);
	}
}
