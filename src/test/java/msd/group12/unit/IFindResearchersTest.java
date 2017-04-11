package test.java.msd.group12.unit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import main.java.entities.Author;
import main.java.search.FindResearcher;
import test.java.msd.group12.TestObjectFactory;
import test.java.msd.group12.TestObjectFactory.IFindResearchersDummyTest;

public class IFindResearchersTest {

	public static IFindResearchersDummyTest iFindResearchersDummyObj;
	public static List<Author> authors;
	
	@BeforeClass
	public static void setUp() {
		iFindResearchersDummyObj = new TestObjectFactory().new IFindResearchersDummyTest();
		//TODO: COMMENTED OUT UNTIL FIX
	/*	authors = new ArrayList<>(new FindResearcher()
				.findAuthorsByResearchPaperTitle("Access Control in Object-Oriented Database Systems"));
		System.out.println("authors list");
		System.out.println(authors);*/
	
	}
	
	@Test
	public void testfindAuthorsByResearchPaperTitle() {
		//TODO: COMMENTED OUT UNTIL FIX
	/*	System.out.println("test 1"+new FindResearcher().findAuthorsByResearchPaperTitle("Access Control in Object-Oriented Database"));
		System.out.println("test 2"+iFindResearchersDummyObj.findAuthorsByResearchPaperTitle("Access Control in Object-Oriented Database"));
		assertFalse(iFindResearchersDummyObj.findAuthorsByResearchPaperTitle("Constraint Programming").isEmpty());
		*/
	    assertEquals(true , iFindResearchersDummyObj.findAuthorsByResearchPaperTitle("   ").isEmpty());
	    assertEquals(true , iFindResearchersDummyObj.findAuthorsByResearchPaperTitle("123456").isEmpty());
	    assertEquals(true , iFindResearchersDummyObj.findAuthorsByResearchPaperTitle(null).isEmpty());
	}
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
		assertFalse(iFindResearchersDummyObj.findAuthorsByAuthorName("Gert Smolka").isEmpty());
	}
	
	@Test
	public void testFindAuthorsInfoByAuthorName() {
		assertTrue(iFindResearchersDummyObj.findAuthorsInfoByAuthorName(null).isEmpty());
		assertTrue(iFindResearchersDummyObj.findAuthorsInfoByAuthorName("4").isEmpty());
		assertTrue(iFindResearchersDummyObj.findAuthorsInfoByAuthorName("").isEmpty());
		assertFalse(iFindResearchersDummyObj.findAuthorsInfoByAuthorName("Fu-Chiang Tsui").isEmpty());
		assertFalse(iFindResearchersDummyObj.findAuthorsInfoByAuthorName("Fu-Chiang Tsui").isEmpty());
	}


	@Test
	public void testFindAuthorsByYearOfPublication() {
		assertEquals(false , iFindResearchersDummyObj.findAuthorsByYearOfPublication(2017).isEmpty());
		assertEquals(false , iFindResearchersDummyObj.findAuthorsByYearOfPublication(2017).isEmpty());
		assertEquals(true , iFindResearchersDummyObj.findAuthorsByYearOfPublication(0).isEmpty());
		assertEquals(true , iFindResearchersDummyObj.findAuthorsByYearOfPublication(-1800).isEmpty());
		assertEquals(true , iFindResearchersDummyObj.findAuthorsByYearOfPublication(99999).isEmpty());
	}

	@Test
	public void testFindAuthorsByConferenceName() {
		
		assertEquals(true, iFindResearchersDummyObj.findAuthorsByConferenceName("Conceptual Modeling - ER 2008")!= null);
		assertEquals(true, iFindResearchersDummyObj.findAuthorsByConferenceName("415").size() == 0);
		assertEquals(true, iFindResearchersDummyObj.findAuthorsByConferenceName("").size() == 0);
		
	}

	@Test
	public void testFindAuthorsByConferenceAcronym() {
		
		assertEquals(false , iFindResearchersDummyObj.findAuthorsByConferenceAcronym("ER").isEmpty());
		assertEquals(true , iFindResearchersDummyObj.findAuthorsByConferenceAcronym("RRR").isEmpty());
	    assertEquals(true , iFindResearchersDummyObj.findAuthorsByConferenceAcronym("   ").isEmpty());
	    assertEquals(true , iFindResearchersDummyObj.findAuthorsByConferenceAcronym("123456").isEmpty());
	    assertEquals(true , iFindResearchersDummyObj.findAuthorsByConferenceAcronym(null).isEmpty());
	}
}
