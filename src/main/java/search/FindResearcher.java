package main.java.search;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import main.java.entities.Article;
import main.java.entities.Author;
import main.java.entities.AuthorInfo;
import main.java.entities.InProceeding;
import main.java.entities.Journal;
import main.java.entities.Proceedings;
import main.java.entities.ResearchPaper;
import main.java.frontend.FrontEndParser;
import main.java.interfaces.IFindResearchers;
import main.java.queryengine.DAOFactory;
import main.java.queryengine.MariaDBDaoFactory;
import main.java.queryengine.dao.AuthorInfoDAO;
import main.java.queryengine.dao.AuthorDAO;
import main.java.queryengine.dao.DAO;

public class FindResearcher implements IFindResearchers {

	private static final String NAME = "name";
	private static final String TITLE = "title";
	private static final String KEY = "_key";
	private static final String JOURNAL = "journal";
	private static final String YEAR = "year";
	private static final int LIMIT = 10;

	private static DAOFactory daoFactory;
	private static DAO<Author> authorDAO;
	private static DAO<AuthorInfo> authorInfoDAO;
	private static DAO<InProceeding> inProceedingsDAO;
	private static DAO<Proceedings> proceedingsDAO;
	private static DAO<Journal> journalDAO;
	private static DAO<Article> articleDao;
	
	private static final ExecutorService service = Executors.newCachedThreadPool();

	static {
		daoFactory = MariaDBDaoFactory.getInstance();
		authorDAO = daoFactory.getAuthorDAO();
		authorInfoDAO = daoFactory.getAuthorInfoDAO();
		articleDao = daoFactory.getArticleDAO();
		inProceedingsDAO = daoFactory.getInProceedingsDAO();
		proceedingsDAO = daoFactory.getProceedingsDAO();
		journalDAO = daoFactory.getJournalDAO();
	}


	@Override
	public Set<Author> findAuthorsByResearchPaperTitle(String title, int max) {
		Future<Set<Author>> future1, future2;
		Set<Author> authors = new HashSet<>();
		try {
			Set<String> titles = new HashSet<String>();
			titles.add(title);
			Set<String> keys = new HashSet<>();
			Callable<Set<Author>> inProceedingCallable = () -> {
				Set<InProceeding> inproceedings = inProceedingsDAO.findByAttribute(TITLE, titles, max);
				inproceedings.forEach((v) -> keys.add(v.getKey()));
				return authorDAO.findByKeys(keys);
			};
			future1 = service.submit(inProceedingCallable);
			Callable<Set<Author>> journalCallable = () -> {
				Set<Journal> journals = journalDAO.findByAttribute(TITLE, titles, max);
				journals.forEach((v) -> keys.add(v.getKey()));
				return authorDAO.findByKeys(keys);
			};
			future2 = service.submit(journalCallable);
			
			authors.addAll(future1.get());
			authors.addAll(future2.get());

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return authors;
	}
	
	
	@Override
	public Set<Author> findAuthorsByAuthorName(String authorName, int max) {
		Set<String> names = new HashSet<String>();
		names.add(authorName);
		Set<Author> authors = new HashSet<Author>();
		try {
			authors = authorDAO.findByAttribute(NAME, names, max);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return authors;
	}

	@Override
	public Set<AuthorInfo> findAuthorsInfoByAuthorName(String authorName, int max) {
		Set<String> authorNameAttributeValues = new HashSet<String>();
		authorNameAttributeValues.add(authorName);
		Set<AuthorInfo> authorsInfo = new HashSet<AuthorInfo>();
		try {
			authorsInfo = authorInfoDAO.findByAttribute(NAME, authorNameAttributeValues, max);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return authorsInfo;
	}
	

	@Override
	public Set<Author> findAuthorsByPositionHeld(String areaOfExpertise, int max) {
		Set<String> titles = new HashSet<String>();
		titles.add(areaOfExpertise);
		Set<Author> authors = new HashSet<Author>();
		try {
			authors = authorDAO.findByAttribute(TITLE, titles, max);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return authors;
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
			authors = authorDAO.findByKeys(authorKeys);
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
			Set<InProceeding> inProceedingSet = inProceedingsDAO.findByAttribute("booktitle", acronyms, 2000);
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
	public Set<Author> findAuthorsByYearOfPublication(int yearOfPublication, int max) {
		Set<String> years = new HashSet<String>();
		years.add(Integer.toString(yearOfPublication));
		Set<Author> authors = new HashSet<>();
		Set<String> authorKeys = new HashSet<>();
		try {
			Set<InProceeding> inProceedings = inProceedingsDAO.findByAttribute(YEAR, years, max);
			Set<Journal> journals = journalDAO.findByAttribute(YEAR, years, max);
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
		Set<AuthorInfo> authorInfoSet  = findAuthorsInfoByAuthorName(author.getName(), LIMIT);
		AuthorInfo authorInfoObj = authorInfoSet.iterator().next();
		author.setAuthorInfo(authorInfoObj);
		return author;
	}
	
	public static void main(String argp[]) { 
		
		FindResearcher ob = new FindResearcher();
		  //System.out.println(ob.findAuthorsByPositionHeld("G", 10)!= null);
		  //System.out.println(ob.findAuthorsByAuthorName("Gert Smolka", 10)!= null);
		 //System.out.println(ob.findAuthorsInfoByAuthorName("Fu-Chiang Tsui", 10)!= null);
		  //System.out.println(ob.findAuthorsByResearchPaperTitle("Access Control in Object-Oriented Database Systems", 7)!= null);
		  //System.out.println(ob.findAuthorsByYearOfPublication(2008, 1)!= null);
		  //System.out.println(ob.findAuthorsByConferenceName("Conceptual Modeling - ER 2008",10)!= null);
		 // System.out.println(ob.findAuthorsByConferenceAcronym("ER", 10)!= null);
		 
	}
}
