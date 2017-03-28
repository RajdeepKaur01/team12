package test.java.msd.group12;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import main.java.entities.Author;
import main.java.entities.Person;
import main.java.entities.User;
import main.java.interfaces.IAuth;
import main.java.interfaces.IFilterResults;
import main.java.interfaces.IFindResearchers;
import main.java.interfaces.IFrontEnd;
import main.java.queryengine.DAOFactory;
import main.java.queryengine.MariaDBDaoFactory;
import main.java.queryengine.dao.AuthorDAO;

/*
 * This class contains a dummy implementation class of all interfaces. These dummy classes 
 * are used in the classes created for unit testing.
 */

public class TestObjectFactory {
	
	public static final DAOFactory daoFactory = MariaDBDaoFactory.getInstance();
	
//Dummy class implementation of the IAuth class ; which is the user interface
public class IAuthDummyTest implements IAuth{

	//A user is the intended user of the application. On registration a new object of type user is created.
	public User register(Person person, String username, String password) {
		return null;
	}
	
	//If a user is already registered and tries to login, the login function returns true.
	public boolean login(String username, String password) {
		return false;
	}

	//If a user successfully resets their new password from the old, the resetPassword function returns true.
	public boolean resetPassword(String username, String newPassword, String oldPassword) {
		return false;
	}

	//The username is retrieved using the e-mail address of the user. 
	public String retrieveUsername(String emailAddress) {
		return null;
	}
	
}

//Dummy class implementation of IFilterResults, which defines various methods by which results are filtered.
public class IFilterResultsDummyTest implements IFilterResults{

	//This function returns a filters a list of authors based on the location being entered by the user.
	public List<Author> filterByLocation(String address, List<Author> authors) {
		return null;
	}

	//This function generates a filtered list of authors based on the age entered by the user.
	public List<Author> filterByAge(int age, List<Author> authors) {
		return null;
	}

	//This function generates a filtered list of authors based on the gender entered by the user.
	public List<Author> filterByGender(String gender, List<Author> authors) {
		return null;
	}

	//This function generates a filtered list of authors based on the number of years they have already served 
	// as a committee member.
	public List<Author> filterByYearsOnCommittee(int yearsAsCommitteeMem, List<Author> authors) {
		return null;
	}

	//This function generates a filtered list of authors based on their area of expertise entered by the user.
	public List<Author> filterByAreaOfExpertise(String areaOfExpertise, List<Author> authors) {
		return null;
	}
	
}

//Dummy class implementation of IFindResearchers, which defines various methods by which results are displayed.
public class IFindResearchersDummyTest implements IFindResearchers{

	///Max in all functions below defines the number of maximum results.
	
	//This function returns a list of authors based on their address.  
	public List<Author> findAuthorsByLocation(String address, int max) {
		return null;
	}

	//This function returns a list of authors based on their area of expertise.
	public List<Author> findAuthorsByAreaOfExpertise(String areaOfExpertise, int max) {
		return null;
	}

	//This function returns a list of authors based on the type of experience they had and the number of years
	//they served in a particular committee/conference.
	public List<Author> findAuthorsByPastExperience(String typeOfExperience, String numOfYears, int max) {
		return null;
	}

	//This function returns a list of authors based on the number of research papers they published previously.
	public List<Author> findAuthorsByNumberOfResearchPapers(int numOfResearchPaper, int max) {
		return null;
	}

	//This function returns a list of authors based on the number of research papers they published
	//and the date when they published.
	public List<Author> findAuthorsByNumberOfResearchPapersAndDate(int numOfResearchPaper, int max, Date fromDate) {
		return null;
	}

	//This function returns a list of authors based on keywords in their research paper titles.
	public List<Author> findAuthorsByKeywordsInTResearchPaperTitle(List<String> keywords, int max) {
		return null;
	}

	//This function returns a list of authors based on their reserach paper titles.
	public List<Author> findAuthorsByResearchPaperTitle(String title, int max) {
		return null;
	}

	//This function returns a list of authors based on their name as specified.
	public List<Author> findAuthorsByAuthorName(String authorName, int max) {
		List<Author> authors = null;
		try {
			authors =  daoFactory.getAuthorDAO().findByAttribute("name", authorName, 10);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return authors;
	}

	//This function returns a list of authors based on their alias if any.
	public List<Author> findAuthorsByAlias(String alias, int max) {
		return null;
	}

	//This function returns a list of authors based on the conferences they took part in.
	public List<Author> findAuthorsByConference(String conferenceName, int max) {
		return null;
	}

	//This function returns a list of authors based on the conferences they were part of and the number of years
	//they participated.
	public List<Author> findAuthorsByConference(String conferenceName, int numOfYears, int max) {
		return null;
	}

	//This function returns a list of authors similar to some other author specified by the user. 
	public List<Author> findAuthorsSimilarToProfile(Author author) {
		return null;
	}
	
}

//Dummy class implementation of the IFrontEnd class, which implements the front end of this application.
public class IFrontEndDummyTest implements IFrontEnd{

	//This function returns true if the input file path was initialized and parsed successfully.
	public boolean initializeAndRunParser(String inputFilePath) {
		return false;
	}

	//This function returns true if the DB connection was setup successfully.
	public boolean setUpDBConnection() {
		return false;
	}

	//This function returns true if the given insert query is created successfully on the database.
	public boolean createDBInsertStatements() {
		return false;
	}

	//This function returns true if the records are committed successfully on the database.
	public boolean commitRecords() {
		return false;
	}
	
}

}
