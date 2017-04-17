package test.java.msd.group12.unit;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.JFXPanel;
import main.java.entities.Author;
import main.java.search.FindResearcher;
import test.java.msd.group12.TestObjectFactory;
import test.java.msd.group12.TestObjectFactory.IFilterDummyTest;

import javafx.application.Platform;

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
 
		assertEquals(1, iFilterDummyObj.filterByName("elisa", data).size());
	}

	@Test
	public void testFilterByResearchPaper() {
		assertEquals(3, iFilterDummyObj.filterByResearchPaper("1", data).size());
	}

	@Test
	public void testInvalidResearchPaperFilter() {
		Platform.runLater(new Runnable() {
			
			@Override
			public void run() {
				assertEquals(authors.size(), iFilterDummyObj.filterByResearchPaper("a", data).size());
			}
		});
	}

	@Test
	public void testInvalidPastExperienceFilter() {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				assertEquals(authors.size(), iFilterDummyObj.filterByPastExperience("a", data).size());	
			}
		});
	}

	@Test
	public void testFilterByPastExperience() {
		assertEquals(2, iFilterDummyObj.filterByPastExperience("0", data).size());
	}

}
