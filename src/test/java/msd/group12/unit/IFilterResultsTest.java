package test.java.msd.group12.unit;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Test;

import main.java.entities.Author;
import test.java.msd.group12.TestObjectFactory;
import test.java.msd.group12.TestObjectFactory.IFilterResultsDummyTest;

/*
 * This class is a JUnit test which was constructed to test the functions in the filter results interface.
 */
public class IFilterResultsTest {
 
	IFilterResultsDummyTest iFilterResultsDummyObj = new TestObjectFactory().new IFilterResultsDummyTest();
	
	//This function serves as a test for the function to return a filtered list of authors based on location.
	@Test
	public void testFilterByLocation() {
		
		//The expected output is a filtered list of authors based on their location.
		assertEquals(true, iFilterResultsDummyObj.filterByLocation("" , new ArrayList<Author>())!= null);
	}

	//This function serves as a test for the function to return a filtered list of authors based on their age.
	@Test
	public void testFilterByAge() {
		
		//The expected output is a filtered list of authors based on their age.
		assertEquals(true , iFilterResultsDummyObj.filterByAge(35, new ArrayList<Author>())!= null);
	}

	//This function serves as a test for the function to return a filtered list of authors based on their gender.
	@Test
	public void testFilterByGender() {
		
		//The expected output is a filtered list of authors based on their gender.
		assertEquals(true , iFilterResultsDummyObj.filterByGender("Female", new ArrayList<Author>())!= null);
	}

	//This function serves as a test for the function to return a filtered list of authors based on the 
	//number of years they served on a particular committee.
	@Test
	public void testFilterByYearsOnCommittee() {
		
		//The expected output is a filtered list of authors based on the number of years spent on a committee.
		assertEquals(true , iFilterResultsDummyObj.filterByYearsOnCommittee(5, new ArrayList<Author>())!= null);
	}

	//This function serves as a test for the function to return a filtered list of authors based on
	//their area of expertise.
	@Test
	public void testFilterByAreaOfExpertise() {
		
		//The expected output is a filtered list of authors based on their area of expertise.
		assertEquals(true , iFilterResultsDummyObj.filterByAreaOfExpertise("", new ArrayList<Author>())!= null);
	}

}
