package test.java.msd.group12.unit;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.java.entities.Author;
import main.java.queryengine.DAOFactory;
import main.java.queryengine.MariaDBDaoFactory;
import main.java.search.FindResearcher;
import test.java.msd.group12.TestObjectFactory;
import test.java.msd.group12.TestObjectFactory.IFilterDummyTest;

/*
 * This class is a JUnit test which was constructed to test the functions in the filter results interface.
 */
public class IFilterResultsTest {
 
	IFilterDummyTest iFilterDummyObj = new TestObjectFactory().new IFilterDummyTest();
	List<Author> authors = new ArrayList<>(new FindResearcher().
			findAuthorsByResearchPaperTitle("Access Control in Object-Oriented Database Systems"));
	ObservableList<Author> data = FXCollections.observableList(authors);
	
	//This function serves as a test for the function to return a filtered list of authors based on author name.
	@Test
	public void testFilterByName() {
		
		//The expected output is a filtered list of authors based on author name.
		assertEquals(1, iFilterDummyObj.filterByName("elisa", data).size());
	}

	//This function serves as a test for the function to return a filtered list of authors based on their number of research paper.
	@Test
	public void testFilterByResearchPaper() {
		
		//The expected output is a filtered list of authors based on their number of research paper.
		assertEquals(3 , iFilterDummyObj.filterByResearchPaper("1", data).size());
	}

	//This function serves as a test for the function to return a filtered list of authors based on their past experience.
	@Test
	public void testFilterByPastExperience() {
		
		//The expected output is a filtered list of authors based on their past experience.
		assertEquals(2 , iFilterDummyObj.filterByPastExperience("0", data).size());
	}

}
