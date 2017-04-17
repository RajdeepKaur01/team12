package test.java.msd.group12.unit;

import org.junit.runners.MethodSorters;
import static org.junit.Assert.*;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import main.java.entities.Author;
import main.java.entities.User;
import test.java.msd.group12.TestObjectFactory;
import test.java.msd.group12.TestObjectFactory.AuthDummyTest;
import test.java.msd.group12.TestObjectFactory.AuthUserDummyTest;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AuthTest {
	private static Set<String> paperKeys;
	private static Author author1;

	@Rule
	public final ExpectedException exception = ExpectedException.none();
	public static AuthDummyTest authDummyTestObj;
	public static AuthUserDummyTest authUserDummyTestObj;
	public static Set<Author> authors1;
	public static Set<Author> authors2;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		authDummyTestObj = new TestObjectFactory().new AuthDummyTest();
		authUserDummyTestObj = new TestObjectFactory().new AuthUserDummyTest();
		authors1 = new HashSet<Author>();
		authors2 = new HashSet<Author>();
		paperKeys = new HashSet<>();
		author1 = new Author();
		author1.setName("Test User");
		author1.setNumberOfResearchPapers(1);
		paperKeys.add("conf/test1/12345");
		paperKeys.add("journals/test2/12345");
		paperKeys.add("conf/test3/12345");
		author1.setPaperKeys(paperKeys);
		Map<String, Set<String>> testCommitteeObject = new HashMap<>();
		Set<String> committeeInfo = new HashSet<String>();
		committeeInfo.add("Role:" + "Program Chair" + ", Year:" + 1989);
		testCommitteeObject.put("ecoop", committeeInfo);
		author1.setCommitteeMemberInfo(testCommitteeObject);
		authors1.add(author1);
		authors2.add(author1);
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testLogin() {
		User userObj =authDummyTestObj.login("mohit", "mohit");
		assertTrue(authDummyTestObj.login("mohit", "mohit0") == null);
		assertTrue(userObj != null);
		String output = "mohit gupta";
		assertTrue(userObj.getName().equals(output));
		assertTrue(userObj.getUserId()==3);
		assertTrue(userObj.getUsername().equals("mohit"));
		assertTrue(userObj.getPassword().equals("mohit"));
	}

	@Test
	public void testAddAuthorA() throws SQLException {
		assertTrue(authUserDummyTestObj.addAuthors(1, authors1));
		assertFalse(authUserDummyTestObj.addAuthors(1, authors2));
		author1.setCommitteeMemberInfo(null);
		assertTrue(authUserDummyTestObj.addAuthors(2, authors1));
	}

	@Test
	public void testFindAuthorsById() throws SQLException {
		assertTrue(authUserDummyTestObj.getAuthors(1).size() >= 1);
		assertFalse(authUserDummyTestObj.getAuthors(12).size() > 1);

	}

	@Test
	public void testUpdateMyAuthor() throws SQLException {
		author1.setNote("test author updated");
		assertTrue(authUserDummyTestObj.updateAuthor(1, author1));
		assertFalse(authUserDummyTestObj.updateAuthor(23, author1));
	}

	@Test
	public void testXDeleteMyAuthor() throws SQLException {
		assertTrue(authUserDummyTestObj.deleteAuthor(1, author1));
		assertTrue(authUserDummyTestObj.deleteAuthor(2, author1));
		assertFalse(authUserDummyTestObj.deleteAuthor(3, author1));
		assertFalse(authUserDummyTestObj.deleteAuthor(23, author1));
	}

}
