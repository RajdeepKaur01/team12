package test.java.msd.group12.unit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
		authors = new ArrayList<>(new FindResearcher()
				.findAuthorsByResearchPaperTitle("Access Control in Object-Oriented Database Systems"));
	
	}
	
	@Test
	public void testfindAuthorsByResearchPaperTitle() {
		assertFalse(authors.isEmpty());
		assertFalse(iFindResearchersDummyObj.findAuthorsByResearchPaperTitle("Incremental Vocabulary Extensions in Text Understanding Systems.").isEmpty());
	    assertTrue (iFindResearchersDummyObj.findAuthorsByResearchPaperTitle("   ").isEmpty());
	    assertTrue (iFindResearchersDummyObj.findAuthorsByResearchPaperTitle("123456").isEmpty());
	    assertTrue (iFindResearchersDummyObj.findAuthorsByResearchPaperTitle(null).isEmpty());
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
	}
	
	@Test
	public void testFindAuthorsInfoByAuthorName() {
		assertTrue(iFindResearchersDummyObj.findAuthorsInfoByAuthorName(null).isEmpty());
		assertTrue(iFindResearchersDummyObj.findAuthorsInfoByAuthorName("4").isEmpty());
		assertTrue(iFindResearchersDummyObj.findAuthorsInfoByAuthorName("").isEmpty());
		assertFalse(iFindResearchersDummyObj.findAuthorsInfoByAuthorName("Fu-Chiang Tsui").isEmpty());
	}


	@Test
	public void testFindAuthorsByYearOfPublication() {
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
	
	@Test
	public void testGetAuthorInfo(){
		Author authorObj = new Author();
		authorObj.setName("Gert Smolka");
		assertEquals(false, iFindResearchersDummyObj.getAuthorInfo(authorObj).getName().isEmpty());
	}

	@Test
	public void testGetResearchPapers(){
		Author authorObj = new Author();
		authorObj.setName("Gert Smolka");
		assertEquals(false, iFindResearchersDummyObj.getResearchPapers(authorObj).toString().isEmpty());
		
	}
	
	@Test
	public void testFindAuthorsWithSimilarProfile() {
		Author author = new Author();
		Set<String> paperKeys = new HashSet<>();
		paperKeys.add("1");
		paperKeys.add("2");
		paperKeys.add("3");
		paperKeys.add("1");
		author.setPaperKeys(paperKeys);
		Map<String, Set<String>> map = new HashMap<>();
		map.put("OOPSLA", null);
		map.put("ECOOP", null);
		author.setCommitteeMemberInfo(map);
		
		assertTrue(iFindResearchersDummyObj.findAuthorsWithSimilarProfile(author).size() > 0);
	}
}
