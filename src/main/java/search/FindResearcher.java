package main.java.search;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.math.NumberUtils;

import main.java.entities.Author;
import main.java.entities.AuthorInfo;
import main.java.entities.InProceeding;
import main.java.entities.Journal;
import main.java.entities.Proceedings;
import main.java.entities.ResearchPaper;
import main.java.interfaces.IFindResearchers;
import main.java.queryengine.DAOFactory;
import main.java.queryengine.MariaDBDaoFactory;
import main.java.queryengine.dao.DAO;

public class FindResearcher implements IFindResearchers {

	//Constants used throughout the class
	private static final String NAME = "name";
	private static final String TITLE = "title";
	private static final String YEAR = "year";
	private static final int LOWERYEAR = 1800;
	
	//Variables used for querying the database	
	private static DAOFactory daoFactory;
	private static DAO<Author> authorDAO;
	private static DAO<AuthorInfo> authorInfoDAO;
	private static DAO<InProceeding> inProceedingsDAO;
	private static DAO<Proceedings> proceedingsDAO;
	private static DAO<Journal> journalDAO;
	
	//Initialize class-level variables
	static {
		daoFactory = MariaDBDaoFactory.getInstance();
		authorDAO = daoFactory.getAuthorDAO();
		authorInfoDAO = daoFactory.getAuthorInfoDAO();
		inProceedingsDAO = daoFactory.getInProceedingsDAO();
		proceedingsDAO = daoFactory.getProceedingsDAO();
		journalDAO = daoFactory.getJournalDAO();
	}

	@Override
	/**
	 * Find authors given a research paper title
	 */
	public Set<Author> findAuthorsByResearchPaperTitle(String title) {
		//Create an empty set
		Set<Author> authors = new HashSet<>();
		
		//Check if input title is a valid string, if not, return empty set
		if (checkIfValidString(title)) {
			Set<String> titles = new HashSet<String>();
			titles.add(title);
			Set<String> confKeys = new HashSet<>(), journalKeys = new HashSet<>();
			try {
				//Retrieve all the conference papers matching the title, if any
				Set<InProceeding> inproceedings = inProceedingsDAO.findByAttribute(TITLE, titles);
				//Store keys of conference papers to query the author table by later
				inproceedings.forEach((v) -> confKeys.add(v.getKey()));
				//Find authors who have the conference matching keys
				authors.addAll(authorDAO.findByKeys(confKeys));
				//Retrieve all the journal papers matching the title, if any
				Set<Journal> journals = journalDAO.findByAttribute(TITLE, titles);
				//Store keys of journal papers to query the author table by later
				journals.forEach((v) -> journalKeys.add(v.getKey()));
				//Find authors who have the journal matching keys
				Set<Author> authorFromJournals = authorDAO.findByKeys(journalKeys);
				if (authorFromJournals != null)
					authors.addAll(authorDAO.findByKeys(journalKeys));
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return authors;
	}

	@Override
	/**
	 * Find authors matching a given name
	 */
	public Set<Author> findAuthorsByAuthorName(String authorName) {
		Set<String> names = new HashSet<String>();
		names.add(authorName);
		Set<Author> authors = new HashSet<Author>();
		//Check if input name is a valid string, if not, return empty set
		if (checkIfValidString(authorName)) {
			try {
				//Find author with the given names
				authors = authorDAO.findByAttribute(NAME, names);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return authors;
	}

	@Override
	/**
	 * Find author aliases and other miscellaneous info by using author name
	 */
	public Set<AuthorInfo> findAuthorsInfoByAuthorName(String authorName) {
		Set<String> authorNameAttributeValues = new HashSet<String>();
		authorNameAttributeValues.add(authorName);
		Set<AuthorInfo> authorsInfo = new HashSet<AuthorInfo>();
		//Check if input name is a valid string, if not, return empty set
		if (checkIfValidString(authorName)) {
			try {
				//Find aliases, homepage url etc for the input name
				authorsInfo = authorInfoDAO.findByAttribute(NAME, authorNameAttributeValues);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return authorsInfo;
	}

	@Override
	/**
	 * Find authors who have held a position in the past on one/many committees
	 * Position is one of:
	 *** G - General Chair
	 *** P - Program Chair
	 *** C - Committee Chair
	 */
	public Set<Author> findAuthorsByPositionHeld(String position) {
		Set<Author> authors = new HashSet<Author>();
		//Check if the position string is valid
		if (checkIfValidString(position)) {
			Set<String> titles = new HashSet<String>();
			titles.add(position);
			try {
				//Find Author meta-info
				authors = authorDAO.findByAttribute(TITLE, titles);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return authors;
	}

	@Override
	/**
	 * Find authors who have published at least one paper in the given Conference
	 */
	public Set<Author> findAuthorsByConferenceName(String conferenceName) {
		Set<String> names = new HashSet<>();
		Set<String> authorKeys = new HashSet<>();
		Set<Author> authors = new HashSet<>();
		names.add(conferenceName);
		//Check if conference name is a valid string
		if (checkIfValidString(conferenceName)) {
			try {
				//Find all conference papers by using the conference name
				Set<Proceedings> proceedings = proceedingsDAO.findByAttribute(TITLE, names);
				Set<ResearchPaper> inProceedingSet = new HashSet<>();
				//Iterate through list of research papers for that conference and get their keys
				proceedings.forEach((proceeding) -> inProceedingSet.addAll(proceeding.getInproceedings()));
				inProceedingSet.forEach((inProceeding) -> authorKeys.add(inProceeding.getKey()));
				//Find author matching the set of research paper keys
				authors = authorDAO.findByKeys(authorKeys);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return authors;
	}

	@Override
	/**
	 * Find authors who have served in a conference, given its acronym
	 * e.g. OOPSLA, ECOOP etc.
	 */
	public Set<Author> findAuthorsByConferenceAcronym(String conferenceAcronym) {
		Set<String> acronyms = new HashSet<>();
		Set<String> authorKeys = new HashSet<>();
		Set<Author> authors = new HashSet<>();
		//Check if the conference acronym is a valid string
		if (checkIfValidString(conferenceAcronym)) {
			acronyms.add(conferenceAcronym);
			try {
				//Find all research papers published in the conference
				Set<InProceeding> inProceedingSet = inProceedingsDAO.findByAttribute("booktitle", acronyms);
				inProceedingSet.forEach((inProceeding) -> authorKeys.add(inProceeding.getKey()));
				//Find all authors matching the research paper keys
				authors = authorDAO.findByKeys(authorKeys);

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return authors;
	}

	@Override
	/**
	 * Find all authors who have published at least one research paper
	 * in the given year
	 */
	public Set<Author> findAuthorsByYearOfPublication(int yearOfPublication) {
		Set<Author> authors = new HashSet<>();
		//Check if year of publication is a valid year
		if (validateYear(yearOfPublication) && yearOfPublication != 0) {
			Set<String> years = new HashSet<String>();
			years.add(Integer.toString(yearOfPublication));
			Set<String> authorKeys = new HashSet<>();
			try {
				//Find all conference papers and journal publications for the given year
				Set<InProceeding> inProceedings = inProceedingsDAO.findByAttribute(YEAR, years);
				Set<Journal> journals = journalDAO.findByAttribute(YEAR, years);
				
				//Find all authors who have published papers matching the keys
				inProceedings.forEach((inproceeding) -> authorKeys.add(inproceeding.getKey()));
				journals.forEach((journal) -> authorKeys.add(journal.getKey()));
				authors = authorDAO.findByKeys(authorKeys);

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return authors;
	}

	@Override
	/**
	 * Find authors who have a profile similar to the given author. 
	 * Similarity is defined as follows:
	 * ** Held a position in the same conferene as the author
	 * ** # of research papers is at least equal to # of research papers of this author
	 */
	public Set<Author> findAuthorsWithSimilarProfile(Author author) {
		Set<Author> authors = new HashSet<>();
		try {
			authors = authorDAO.findAuthorsWithSimilarProfile(author);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return authors;
	}

	@Override
	/**
	 * Find all the research papers and journal publications for the given author
	 */
	public Author getResearchPapers(Author author) {
		if (author != null) {
			try {
				author = authorDAO.join(author);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return author;
	}

	@Override
	/**
	 * Return author meta info, such as alias, home page URL ets given the Author object
	 */
	public Author getAuthorInfo(Author author) {
		if (author != null) {
			Set<AuthorInfo> authorInfoSet = findAuthorsInfoByAuthorName(author.getName());
			AuthorInfo authorInfoObj = authorInfoSet.iterator().next();
			author.setAuthorInfo(authorInfoObj);
		}
		return author;
	}

	//Function to check if the input string is a valid string
	private static boolean checkIfValidString(String str) {
		return str != null && !str.isEmpty() && !NumberUtils.isNumber(str);
	}

	//Function to check if the given number is a valid year
	private static boolean validateYear(int year) {
		int inputYear = year;
		int currentYear = Calendar.getInstance().get(Calendar.YEAR);
		return (inputYear >= LOWERYEAR && inputYear <= currentYear);
	}


}
