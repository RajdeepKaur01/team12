package test.java.msd.group12.unit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import main.java.entities.Person;
import main.java.entities.User;
import test.java.msd.group12.TestObjectFactory;
import test.java.msd.group12.TestObjectFactory.IAuthDummyTest;

/* 
 * This class is a JUnit test which was constructed to test the functions in the IAuth interface.  
 */
public class IAuthTest {

	Person testObj = new Person();
	IAuthDummyTest iAuthDummyObj = new TestObjectFactory().new IAuthDummyTest();
	
	//This function is a test for the register function.  
	@Test
	public void testRegister() {
		
		//The expected output is a new object of the User class.
		assertEquals(new User(), iAuthDummyObj.register(testObj , "" , ""));
	}

	//This function serves as a test for the login function.  
	@Test
	public void testLogin() {
		
		//The expected output is true if a valid user logs in successfully.
		assertTrue(iAuthDummyObj.login("", ""));
	}

	//This function serves as a test to check if the password has been reset successfully.
	@Test
	public void testResetPassword() {
		
		//The expected output is true is the password has been reset correctly.
		assertTrue(iAuthDummyObj.resetPassword("", "" , ""));
	}

	//This function serves as a test to check if the username has been retrieved correctly.
	@Test
	public void testRetrieveUsername() {
		
		//The output expected is the right username based on the given e-mail address.
		assertEquals("", iAuthDummyObj.retrieveUsername(""));
	}

}
