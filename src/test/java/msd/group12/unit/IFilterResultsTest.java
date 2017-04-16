package test.java.msd.group12.unit;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.testfx.util.WaitForAsyncUtils;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.JFXPanel;
import main.java.entities.Author;
import main.java.search.FindResearcher;
import test.java.msd.group12.TestObjectFactory;
import test.java.msd.group12.TestObjectFactory.IFilterDummyTest;
import test.java.msd.group12.TestObjectFactory.IFindResearchersDummyTest;

/*
 * This class is a JUnit test which was constructed to test the functions in the filter results interface.
 */
public class IFilterResultsTest {
	
	public static IFilterDummyTest iFilterDummyObj;
	public static List<Author> authors;
	public static ObservableList<Author> data;
	
	@BeforeClass
	public static void setUp() {
		new JFXPanel(); 
		iFilterDummyObj = new TestObjectFactory().new IFilterDummyTest();
		authors = new ArrayList<>(new FindResearcher()
				.findAuthorsByResearchPaperTitle("Access Control in Object-Oriented Database Systems"));
		data = FXCollections.observableList(authors);
	}
	
	@Test 
	public void testFilterByName() {
		//The expected output is a filtered list of authors based on author name.
		assertEquals(1, iFilterDummyObj.filterByName("elisa", data).size());
	}

	@Ignore @Test
	public void testFilterByResearchPaper() {
		//The expected output is a filtered list of authors based on their number of research paper.
		assertEquals(3 , iFilterDummyObj.filterByResearchPaper("1", data).size());
	}
	
	@Ignore @Test
	public void testFilterByPastExperience() {	
		//The expected output is a filtered list of authors based on their past experience.
		assertEquals(2 , iFilterDummyObj.filterByPastExperience("0", data).size());
	}

}
