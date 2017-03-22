package testsuite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import unit.IAuthTest;
import unit.IFilterResultsTest;
import unit.IFindResearchersTest;

@RunWith(Suite.class)
@SuiteClasses({ IAuthTest.class, IFilterResultsTest.class, IFindResearchersTest.class })
public class UserInterfaceSuite {

}
