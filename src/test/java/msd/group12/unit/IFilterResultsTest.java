package test.java.msd.group12.unit;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.testfx.util.WaitForAsyncUtils;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.java.entities.Author;
import main.java.search.FindResearcher;
import test.java.msd.group12.TestObjectFactory;
import test.java.msd.group12.TestObjectFactory.IFilterDummyTest;

/*
 * This class is a JUnit test which was constructed to test the functions in the filter results interface.
 */
public class IFilterResultsTest {
	
	public IFilterDummyTest iFilterDummyObj;
	public List<Author> authors;
	public ObservableList<Author> data;
	
	@Before
	public void setUp() {
		iFilterDummyObj = new TestObjectFactory().new IFilterDummyTest();
		authors = new ArrayList<>(new FindResearcher().
				findAuthorsByResearchPaperTitle("Access Control in Object-Oriented Database Systems"));
		data = FXCollections.observableList(authors);
		WaitForAsyncUtils.sleep(60, TimeUnit.SECONDS);
	}
	
	@Test
	public void testFilterByName() {
		System.out.println("Authors: " + authors.size());
		authors.forEach(author -> System.out.println(author));
		//The expected output is a filtered list of authors based on author name.
		assertEquals(1, iFilterDummyObj.filterByName("elisa", data).size());
	}

	@Test
	public void testFilterByResearchPaper() {
		System.out.println("Authors: " + authors.size());
		authors.forEach(author -> System.out.println(author));
		//The expected output is a filtered list of authors based on their number of research paper.
		assertEquals(6 , iFilterDummyObj.filterByResearchPaper("1", data).size());
	}
	
	@Test
	public void testFilterByPastExperience() {	
		System.out.println("Authors: " + authors.size());
		authors.forEach(author -> System.out.println(author));
		//The expected output is a filtered list of authors based on their past experience.
		assertEquals(3 , iFilterDummyObj.filterByPastExperience("0", data).size());
	}

}
