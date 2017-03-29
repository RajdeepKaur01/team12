package test.java.msd.group12.unit;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/* Test suite for the user interface; where the interfaces to find authors and filter 
 * results are run together.
 */
@RunWith(Suite.class)
@SuiteClasses({ IAuthTest.class, IFilterResultsTest.class, IFindResearchersTest.class })
public class UserInterfaceTestSuite {

}
