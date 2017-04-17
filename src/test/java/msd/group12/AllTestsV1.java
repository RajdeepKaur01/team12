package test.java.msd.group12;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import test.java.msd.group12.unit.AuthTest;
import test.java.msd.group12.unit.IFindResearchersTest;
import test.java.msd.group12.unit.IFrontEndTest;

@RunWith(Suite.class)
@SuiteClasses({ IFindResearchersTest.class, IFrontEndTest.class, AuthTest.class })
public class AllTestsV1 {

}
