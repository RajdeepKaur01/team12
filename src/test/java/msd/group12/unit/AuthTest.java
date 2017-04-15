package test.java.msd.group12.unit;

import static org.junit.Assert.*;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import main.java.entities.Author;
import test.java.msd.group12.TestObjectFactory;
import test.java.msd.group12.TestObjectFactory.AuthDummyTest;
import test.java.msd.group12.TestObjectFactory.AuthUserDummyTest;

public class AuthTest {
	
	public static AuthDummyTest authDummyTestObj;
	public static AuthUserDummyTest authUserDummyTestObj;
	public static Set<Author> authors;
	private static Set<String> paperKeys;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		authDummyTestObj = new TestObjectFactory().new AuthDummyTest();
		authUserDummyTestObj = new TestObjectFactory().new AuthUserDummyTest();
		authors = new HashSet<Author>();
		Author author1 = new Author();
		author1.setName("Test User");
		author1.setNumberOfResearchPapers(1);
		paperKeys.add("conf/seke/RuheB99a");
		paperKeys.add("journals/ki/BomariusBL92");
		paperKeys.add("conf/icse/BasiliBF07");
		author1.setPaperKeys(paperKeys);
		//author1.setCommitteeMemberInfo(committeeMemberInfo);
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
		assertTrue(authDummyTestObj.login("mohit", "mohit0")==null);
		assertTrue(authDummyTestObj.login("mohit", "mohit")!=null);
		//authUserDummyTestObj.addAuthors("1", authorList)
	}

}
