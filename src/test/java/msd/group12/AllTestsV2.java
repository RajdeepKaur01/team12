package test.java.msd.group12;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import test.java.msd.group12.unit.ArticleDAOTest;
import test.java.msd.group12.unit.AuthTest;
import test.java.msd.group12.unit.IFilterResultsTest;
import test.java.msd.group12.unit.IFindResearchersTest;
import test.java.msd.group12.unit.IFrontEndTest;
import test.java.msd.group12.unit.InProceedingsDAOTest;
import test.java.msd.group12.unit.ViewTest;

@RunWith(Suite.class)
@SuiteClasses({ ArticleDAOTest.class, InProceedingsDAOTest.class, IFilterResultsTest.class, IFindResearchersTest.class, IFrontEndTest.class, AuthTest.class,
		ViewTest.class })
public class AllTestsV2 {

}
