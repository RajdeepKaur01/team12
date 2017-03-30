package main.java.search;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import main.java.entities.Article;
import main.java.entities.Author;
import main.java.entities.InProceeding;
import main.java.entities.Journal;
import main.java.entities.Proceedings;
import main.java.entities.ResearchPaper;
import main.java.interfaces.IFindResearchers;
import main.java.queryengine.DAOFactory;
import main.java.queryengine.MariaDBDaoFactory;
import main.java.queryengine.dao.AuthorDAO;
import main.java.queryengine.dao.DAO;
import main.java.queryengine.dao.InProceedingsDAO;
import main.java.queryengine.dao.JournalDAO;
import main.java.queryengine.dao.ProceedingsDAO;

public class FindResearcher implements IFindResearchers {

	private static final String TITLE = "title";
	private static final String KEY = "_key";
	private static final String JOURNAL = "journal";

	private static DAOFactory daoFactory;
	private static DAO<Author> authorDAO;
	private static DAO<InProceeding> inProceedingsDAO;
	private static DAO<Proceedings> proceedingsDAO;
	private static DAO<Journal> journalDAO;
	private static DAO<Article> articleDao;

	static {
		daoFactory = MariaDBDaoFactory.getInstance();
		authorDAO = daoFactory.getAuthorDAO();
		articleDao = daoFactory.getArticleDAO();
		inProceedingsDAO = daoFactory.getInProceedingsDAO();
		proceedingsDAO = daoFactory.getProceedingsDAO();
		journalDAO = daoFactory.getJournalDAO();
	}

	@Override
	public Set<Author> findAuthorsByNumberOfResearchPapers(int numOfResearchPaper, int max) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<Author> findAuthorsByResearchPaperTitle(String title, int max) {

		Set<String> titles = new HashSet<String>();
		titles.add(title);
		Set<Author> authors = new HashSet<>();
		Set<String> keys = new HashSet<>();
		try {
			Set<InProceeding> inproceedings = inProceedingsDAO.findByAttribute(TITLE, titles, max);
			Set<Journal> journals = journalDAO.findByAttribute(TITLE, titles, max);

			inproceedings.forEach((v) -> keys.add(v.getKey()));
			journals.forEach((v) -> keys.add(v.getKey()));
			authors = authorDAO.findByAttribute("_key", keys, 1000);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return authors;
	}

	@Override
	public Set<Author> findAuthorsByAuthorName(String authorName, int max) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<Author> findAuthorsByAlias(String alias, int max) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<Author> findAuthorsSimilarToProfile(Author author) {
		// TODO Auto-generated method stub
		return null;
	}

	public static void main(String argp[]) {
//		Set<Author> ob = new FindResearcher()
//				.findAuthorsByConferenceName("Conceptual Structures: From Information to Intelligence, 18th International Conference on Conceptual Structures, ICCS 2010, Kuching, Sarawak, Malaysia, July 26-30, 2010. Proceedings", 1000);
//		
//		ob.forEach((auth) -> System.out.println(auth.getName()));
		
		Set<Author> ob = new FindResearcher()
				.findAuthorsByConferenceAcronym("ECOOP", 1000);
		
		ob.forEach((auth) -> System.out.println(auth.getName()));
	}

	@Override
	public Set<Author> findAuthorsByPositionHeld(String areaOfExpertise, int max) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<Author> findAuthorsByConferenceName(String conferenceName, int max) {
		Set<String> names = new HashSet<>();
		Set<String> authorKeys = new HashSet<>();
		Set<Author> authors = new HashSet<>();
		names.add(conferenceName);
		try {
			Set<Proceedings> proceedings = proceedingsDAO.findByAttribute(TITLE, names, 1000);
			Set<ResearchPaper> inProceedingSet = new HashSet<>();
			proceedings.forEach((proceeding) -> inProceedingSet.addAll(proceeding.getInproceedings()));
			inProceedingSet.forEach((inProceeding) -> authorKeys.add(inProceeding.getKey()));
			authors = authorDAO.findByAttribute(KEY, authorKeys, 1000);
			authors.forEach((author) -> author.setResearchPapers(inProceedingSet));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return authors;
	}

	@Override
	public Set<Author> findAuthorsByConferenceAcronym(String conferenceAcronym, int max) {
		Set<String> acronyms = new HashSet<>();
		Set<String> authorKeys = new HashSet<>();
		Set<Author> authors = new HashSet<>();
		acronyms.add(conferenceAcronym);
		try {
			Set<InProceeding> inProceedingSet = 
					inProceedingsDAO.findByAttribute("booktitle", acronyms, 2000);
			inProceedingSet.forEach((inProceeding) -> authorKeys.add(inProceeding.getKey()));
			authors = authorDAO.findByAttribute(KEY, authorKeys, 1000);
			//authors.forEach((author) -> author.setResearchPapers(inProceedingSet));
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return authors;
	}

	@Override
	public Set<Author> findAuthorsByConference(String conferenceName, int numOfYears, int max) {
		// TODO Auto-generated method stub
		return null;
	}
}
