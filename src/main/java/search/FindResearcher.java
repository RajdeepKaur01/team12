package main.java.search;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import main.java.entities.Article;
import main.java.entities.Author;
import main.java.entities.InProceeding;
import main.java.entities.Journal;
import main.java.entities.Proceedings;
import main.java.interfaces.IFindResearchers;
import main.java.queryengine.DAOFactory;
import main.java.queryengine.MariaDBDaoFactory;
import main.java.queryengine.dao.AuthorDAO;
import main.java.queryengine.dao.DAO;
import main.java.queryengine.dao.InProceedingsDAO;
import main.java.queryengine.dao.JournalDAO;
import main.java.queryengine.dao.ProceedingsDAO;

public class FindResearcher implements IFindResearchers{

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
	public List<Author> findAuthorsByLocation(String address, int max) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Author> findAuthorsByAreaOfExpertise(String areaOfExpertise, int max) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Author> findAuthorsByPastExperience(String typeOfExperience, String numOfYears, int max) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Author> findAuthorsByNumberOfResearchPapers(int numOfResearchPaper, int max) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Author> findAuthorsByNumberOfResearchPapersAndDate(int numOfResearchPaper, int max, Date fromDate) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Author> findAuthorsByKeywordsInTResearchPaperTitle(List<String> keywords, int max) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Author> findAuthorsByResearchPaperTitle(String title, int max) {
		List<Author> authors = new ArrayList<Author>();
		
		try {
			List<InProceeding> inproceedings = inProceedingsDAO.findByAttribute(TITLE, title, max);
			List<Journal> journals = journalDAO.findByAttribute(JOURNAL, title, max);
			
			for(InProceeding p: inproceedings){
				System.out.println("inproceeding Enter");
				authors.addAll(authorDAO.findByAttribute(KEY, p.getKey(), max));
			}
			
			for(Journal j: journals){
				System.out.println("journal Enter");
				authors.addAll(authorDAO.findByAttribute(KEY, j.getKey(), max));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return authors;
	}

	@Override
	public List<Author> findAuthorsByAuthorName(String authorName, int max) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Author> findAuthorsByAlias(String alias, int max) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Author> findAuthorsByConference(String conferenceName, int max) {
		
		return null;
	}

	@Override
	public List<Author> findAuthorsByConference(String conferenceName, int numOfYears, int max) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Author> findAuthorsSimilarToProfile(Author author) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public static void main(String argp[]){
		List<Author> ob =new FindResearcher().findAuthorsByResearchPaperTitle("Access Control in Object-Oriented Database Systems", 7);
		for(Author el: ob){
			System.out.println(el.getName());
			System.out.println(el.getNumberOfResearchPapers());
			Map<String, Set<String>> map2 =el.getCommitteeMemberInfo();
			if(map2!=null){
				for (Map.Entry<String, Set<String>> e: map2.entrySet()) {
					System.out.println("key is"+e.getKey());
					System.out.println("valeu is "+e.getValue());
				}
			}
		}
	}

}
