package main.java.search;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import main.java.entities.Article;
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

	private static final String NAME = "name";
	private static final String TITLE = "title";
	private static final String YEAR = "year";
	private static DAOFactory daoFactory;
	private static DAO<Author> authorDAO;
	private static DAO<AuthorInfo> authorInfoDAO;
	private static DAO<InProceeding> inProceedingsDAO;
	private static DAO<Proceedings> proceedingsDAO;
	private static DAO<Journal> journalDAO;
	static {
		daoFactory = MariaDBDaoFactory.getInstance();
		authorDAO = daoFactory.getAuthorDAO();
		authorInfoDAO = daoFactory.getAuthorInfoDAO();
		daoFactory.getArticleDAO();
		inProceedingsDAO = daoFactory.getInProceedingsDAO();
		proceedingsDAO = daoFactory.getProceedingsDAO();
		journalDAO = daoFactory.getJournalDAO();
	}

	@Override
	public Set<Author> findAuthorsByResearchPaperTitle(String title) {
		Set<Author> authors = new HashSet<>();
		{
			Set<String> titles = new HashSet<String>();
			titles.add(title);
			Set<String> confKeys = new HashSet<>(), journalKeys = new HashSet<>();
			try {
				Set<InProceeding> inproceedings = inProceedingsDAO.findByAttribute(TITLE, titles);
				inproceedings.forEach((v) -> confKeys.add(v.getKey()));
				authors.addAll(authorDAO.findByKeys(confKeys));
				Set<Journal> journals = journalDAO.findByAttribute(TITLE, titles);
				journals.forEach((v) -> journalKeys.add(v.getKey()));
				authors.addAll(authorDAO.findByKeys(journalKeys));
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return authors;
	}

	@Override
	public Set<Author> findAuthorsByAuthorName(String authorName) {
		Set<String> names = new HashSet<String>();
		names.add(authorName);
		Set<Author> authors = new HashSet<Author>();
		try {
			authors = authorDAO.findByAttribute(NAME, names);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return authors;
	}

	@Override
	public Set<AuthorInfo> findAuthorsInfoByAuthorName(String authorName) {
		Set<String> authorNameAttributeValues = new HashSet<String>();
		authorNameAttributeValues.add(authorName);
		Set<AuthorInfo> authorsInfo = new HashSet<AuthorInfo>();
		try {
			authorsInfo = authorInfoDAO.findByAttribute(NAME, authorNameAttributeValues);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return authorsInfo;
	}

	@Override
	public Set<Author> findAuthorsByPositionHeld(String areaOfExpertise) {
		Set<String> titles = new HashSet<String>();
		titles.add(areaOfExpertise);
		Set<Author> authors = new HashSet<Author>();
		try {
			authors = authorDAO.findByAttribute(TITLE, titles);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return authors;
	}

	@Override
	public Set<Author> findAuthorsByConferenceName(String conferenceName) {
		Set<String> names = new HashSet<>();
		Set<String> authorKeys = new HashSet<>();
		Set<Author> authors = new HashSet<>();
		names.add(conferenceName);
		try {
			Set<Proceedings> proceedings = proceedingsDAO.findByAttribute(TITLE, names);
			Set<ResearchPaper> inProceedingSet = new HashSet<>();
			proceedings.forEach((proceeding) -> inProceedingSet.addAll(proceeding.getInproceedings()));
			inProceedingSet.forEach((inProceeding) -> authorKeys.add(inProceeding.getKey()));
			authors = authorDAO.findByKeys(authorKeys);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return authors;
	}

	@Override
	public Set<Author> findAuthorsByConferenceAcronym(String conferenceAcronym) {
		Set<String> acronyms = new HashSet<>();
		Set<String> authorKeys = new HashSet<>();
		Set<Author> authors = new HashSet<>();
		acronyms.add(conferenceAcronym);
		try {
			Set<InProceeding> inProceedingSet = inProceedingsDAO.findByAttribute("booktitle", acronyms);
			inProceedingSet.forEach((inProceeding) -> authorKeys.add(inProceeding.getKey()));
			authors = authorDAO.findByKeys(authorKeys);
			// authors.forEach((author) ->
			// author.setResearchPapers(inProceedingSet));

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return authors;
	}

	@Override
	public Set<Author> findAuthorsByYearOfPublication(int yearOfPublication) {
		Set<String> years = new HashSet<String>();
		years.add(Integer.toString(yearOfPublication));
		Set<Author> authors = new HashSet<>();
		Set<String> authorKeys = new HashSet<>();
		try {
			Set<InProceeding> inProceedings = inProceedingsDAO.findByAttribute(YEAR, years);
			Set<Journal> journals = journalDAO.findByAttribute(YEAR, years);
			inProceedings.forEach((inproceeding) -> authorKeys.add(inproceeding.getKey()));
			journals.forEach((journal) -> authorKeys.add(journal.getKey()));
			authors = authorDAO.findByKeys(authorKeys);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return authors;
	}

	@Override
	public Author getResearchPapers(Author author) {

		try {
			author = authorDAO.join(author);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return author;
	}

	@Override
	public Author getAuthorInfo(Author author) {
		Set<AuthorInfo> authorInfoSet = findAuthorsInfoByAuthorName(author.getName());
		AuthorInfo authorInfoObj = authorInfoSet.iterator().next();
		author.setAuthorInfo(authorInfoObj);
		return author;
	}
}
