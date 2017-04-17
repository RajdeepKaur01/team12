package test.java.msd.group12.unit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import main.java.entities.Article;
import main.java.entities.Author;
import main.java.entities.AuthorInfo;
import main.java.entities.InProceeding;
import main.java.entities.ResearchPaper;
import main.java.queryengine.DAOFactory;
import main.java.queryengine.MariaDBDaoFactory;
import main.java.search.FindResearcher;
import test.java.msd.group12.TestObjectFactory;
import test.java.msd.group12.TestObjectFactory.IFindResearchersDummyTest;

public class IFindResearchersTest {

	public static IFindResearchersDummyTest iFindResearchersDummyObj;
	public static List<Author> authors;
	public static ResearchPaper testRP= new ResearchPaper();

	@BeforeClass
	public static void setUp() {
		iFindResearchersDummyObj = new TestObjectFactory().new IFindResearchersDummyTest();
		// TODO: COMMENTED OUT UNTIL FIX
		authors = new ArrayList<>(new FindResearcher()
				.findAuthorsByResearchPaperTitle("Access Control in Object-Oriented Database Systems"));
	}

	@Test
	public void testfindAuthorsByResearchPaperTitle() {
		assertFalse(authors.isEmpty());
		assertFalse(iFindResearchersDummyObj.findAuthorsByResearchPaperTitle("Pattern Matching in Trees and Nets.")
				.isEmpty());
		assertFalse(iFindResearchersDummyObj
				.findAuthorsByResearchPaperTitle("Incremental Vocabulary Extensions in Text Understanding Systems.")
				.isEmpty());
		assertTrue(iFindResearchersDummyObj.findAuthorsByResearchPaperTitle("   ").isEmpty());
		assertTrue(iFindResearchersDummyObj.findAuthorsByResearchPaperTitle("123456").isEmpty());
		assertTrue(iFindResearchersDummyObj.findAuthorsByResearchPaperTitle(null).isEmpty());
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
		
		Set<Author> authorSet = iFindResearchersDummyObj.findAuthorsByAuthorName("Gert Smolka");
	    Author author1 = authorSet.iterator().next();
	    
	    Set<ResearchPaper> testResearchPapers = new HashSet<>();
	    testResearchPapers.add(testRP);
	    author1.setResearchPapers(testResearchPapers);
	    assertTrue(author1.getAuthorInfo()==null);
	    assertTrue(author1.getNumberOfResearchPapers()==0);
	    assertTrue(author1.getInProceedings().size()==0);
	    assertTrue(author1.getArticles().size()==0);
	    assertTrue(author1.getPastExperienceYrs()==0);
	    assertTrue(author1.equals(author1));
	    assertFalse(author1.equals(testResearchPapers));
	    assertFalse(authorSet.isEmpty());
	    
	    Author author1Updated= iFindResearchersDummyObj.getResearchPapers(author1);
	    Set<InProceeding> inproceedingsSet = author1Updated.getInProceedings();
	    InProceeding inproceedingsOb1 = inproceedingsSet.iterator().next();
	    Set<Article> articlesSet = author1Updated.getArticles();
	    Article article1 = articlesSet.iterator().next();
	    assertTrue(inproceedingsOb1.getBookTitle()==null);
	    assertTrue(inproceedingsOb1.getProceedings()==null);
	    assertTrue(article1.getTitle().length()>0);
		assertTrue(iFindResearchersDummyObj.findAuthorsByAuthorName(null).isEmpty());
		assertTrue(iFindResearchersDummyObj.findAuthorsByAuthorName("4").isEmpty());
		assertTrue(iFindResearchersDummyObj.findAuthorsByAuthorName("").isEmpty());
	}

	@Test
	public void testFindAuthorsInfoByAuthorName() {
		
		Set<AuthorInfo> authorInfoSet = iFindResearchersDummyObj.findAuthorsInfoByAuthorName("Fu-Chiang Tsui");
		AuthorInfo authorInfo = authorInfoSet.iterator().next();
		assertFalse(authorInfoSet.isEmpty());
		assertTrue(authorInfo.getAliases().length>=0);
		assertTrue(authorInfo.getHomePageURL()==null);
		assertTrue(iFindResearchersDummyObj.findAuthorsInfoByAuthorName(null).isEmpty());
		assertTrue(iFindResearchersDummyObj.findAuthorsInfoByAuthorName("4").isEmpty());
		assertTrue(iFindResearchersDummyObj.findAuthorsInfoByAuthorName("").isEmpty());
		
	}

	@Test
	public void testFindAuthorsByYearOfPublication() {
		assertEquals(false, iFindResearchersDummyObj.findAuthorsByYearOfPublication(2017).isEmpty());
		assertEquals(true, iFindResearchersDummyObj.findAuthorsByYearOfPublication(0).isEmpty());
		assertEquals(true, iFindResearchersDummyObj.findAuthorsByYearOfPublication(-1800).isEmpty());
		assertEquals(true, iFindResearchersDummyObj.findAuthorsByYearOfPublication(99999).isEmpty());
	}

	@Test
	public void testFindAuthorsByConferenceName() {

		Set<Author> authorSet = iFindResearchersDummyObj.findAuthorsByConferenceName("Conceptual Modeling - ER 2008");
	    Author author1 = authorSet.iterator().next();
		assertEquals(true,authorSet != null);
		assertEquals(true, iFindResearchersDummyObj.findAuthorsByConferenceName("415").size() == 0);
		assertEquals(true, iFindResearchersDummyObj.findAuthorsByConferenceName("").size() == 0);

	}

	@Test
	public void testFindAuthorsByConferenceAcronym() {

		assertEquals(false, iFindResearchersDummyObj.findAuthorsByConferenceAcronym("ER").isEmpty());
		assertEquals(true, iFindResearchersDummyObj.findAuthorsByConferenceAcronym("RRR").isEmpty());
		assertEquals(true, iFindResearchersDummyObj.findAuthorsByConferenceAcronym("   ").isEmpty());
		assertEquals(true, iFindResearchersDummyObj.findAuthorsByConferenceAcronym("123456").isEmpty());
		assertEquals(true, iFindResearchersDummyObj.findAuthorsByConferenceAcronym(null).isEmpty());
	}

	@Test
	public void testGetAuthorInfo() {
		Author authorObj = new Author();
		authorObj.setName("Gert Smolka");
		assertEquals(false, iFindResearchersDummyObj.getAuthorInfo(authorObj).getName().isEmpty());
	}

	@Test
	public void testGetResearchPapers() {
		Author authorObj = new Author();
		authorObj.setName("Gert Smolka");
		assertFalse(iFindResearchersDummyObj.getResearchPapers(authorObj).toString().isEmpty());
		authorObj.setName("Hans Ulrich Simon");
		assertFalse(iFindResearchersDummyObj.getResearchPapers(authorObj).toString().isEmpty());
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
	@Test
	public void testResearchPaper(){
		
		testRP.setAuthor(authors);
		testRP.setTitle("test");
		testRP.setYear(1989);
		assertTrue(testRP.getAuthor()!=null);
		assertTrue(testRP.getTitle()!=null);
		assertTrue(testRP.getYear()!=0);
	} 
	
	
}
