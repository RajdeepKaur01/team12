package test.java.msd.group12;

import java.sql.SQLException;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javafx.collections.ObservableList;
import main.java.auth.Auth;
import main.java.auth.AuthUser;
import main.java.entities.Author;
import main.java.entities.Person;
import main.java.entities.User;
import main.java.frontend.FrontEndParser;
import main.java.interfaces.IFilterResults;
import main.java.interfaces.IFindResearchers;
import main.java.interfaces.IFrontEnd;
import main.java.queryengine.DAOFactory;
import main.java.queryengine.MariaDBDaoFactory;
import main.java.queryengine.dao.AuthorDAO;
import main.java.search.FilterSearch;
import main.java.search.FindResearcher;

/*
 * This class contains a dummy implementation class of all interfaces. These dummy classes 
 * are used in the classes created for unit testing.
 */

public class TestObjectFactory {
	public class IFrontEndDummyTest extends FrontEndParser {

		public IFrontEndDummyTest(boolean testindicator) {
			super(testindicator);
		}
	}

	public class IFindResearchersDummyTest extends FindResearcher {

		public IFindResearchersDummyTest() {
		}

	}

	public class IFilterDummyTest extends FilterSearch {

		public IFilterDummyTest() {
		}

	}

	public class AuthDummyTest extends Auth {

		public AuthDummyTest() {
		}
	}

	public class AuthUserDummyTest extends AuthUser {

		public AuthUserDummyTest() {
		}
	}

}
