package test.java.msd.group12.unit;

import static org.junit.Assert.assertEquals;


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
		Assert.assertTrue(iFindResearchersDummyObj.findAuthorsByPositionHeld(null).size() == 0);
		Assert.assertTrue(iFindResearchersDummyObj.findAuthorsByPositionHeld("Y").size() == 0);
		Assert.assertTrue(iFindResearchersDummyObj.findAuthorsByPositionHeld("").size() == 0);
		Assert.assertTrue(iFindResearchersDummyObj.findAuthorsByPositionHeld("G").size() > 0);
	}
	@Test
	public void testFindAuthorsByAuthorName() {
		Assert.assertTrue(iFindResearchersDummyObj.findAuthorsByAuthorName(null).size() == 0);
		Assert.assertTrue(iFindResearchersDummyObj.findAuthorsByAuthorName("4").size() == 0);
		Assert.assertTrue(iFindResearchersDummyObj.findAuthorsByAuthorName("").size() == 0);
		Assert.assertTrue(iFindResearchersDummyObj.findAuthorsByAuthorName("Gert Smolka").size() > 0);
	}

	@Test
	public void testFindAuthorsInfoByAuthorName() {
		
		assertEquals(true, iFindResearchersDummyObj.findAuthorsInfoByAuthorName("Fu-Chiang Tsui")!= null);
	}

	@Test
	public void testfindAuthorsByResearchPaperTitle() {
		assertEquals(true , iFindResearchersDummyObj.findAuthorsByResearchPaperTitle("Access Control in Object-Oriented Database Systems")!= null);
	}

	@Test
	public void testFindAuthorsByYearOfPublication() {
		assertEquals(true , iFindResearchersDummyObj.findAuthorsByYearOfPublication(2008)!= null);
	}

	@Test
	public void testFindAuthorsByConferenceName() {
		
		assertEquals(true, iFindResearchersDummyObj.findAuthorsByConferenceName("Conceptual Modeling - ER 2008")!= null);
		assertEquals(true, iFindResearchersDummyObj.findAuthorsByConferenceName("415").size() == 0);
		assertEquals(true, iFindResearchersDummyObj.findAuthorsByConferenceName("").size() == 0);
		
	}

	@Test
	public void testFindAuthorsByConferenceStringInt() {
		
		assertEquals(true , iFindResearchersDummyObj.findAuthorsByConferenceAcronym("ER")!= null);
	}

}
