package main.java.search;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import main.java.entities.Author;
import main.java.entities.Journal;
import main.java.entities.Proceedings;
import main.java.interfaces.IFindResearchers;
import main.java.queryengine.MariaDBDaoFactory;
import main.java.queryengine.dao.DAO;

public class FindResearcher implements IFindResearchers{

	private static final String TITLE = "title";
	private static final String KEY = "_key";
	private static final String JOURNAL = "journal";
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
		MariaDBDaoFactory mariaDb = new MariaDBDaoFactory();
		DAO<Proceedings> dao = mariaDb.getProceedingsDAO();
		DAO<Journal> journalDao = mariaDb.getJournalDAO();
		List<Author> authors = new ArrayList<Author>();
		String key = "";
		DAO<Author> authorDao  = mariaDb.getAuthorDAO();;
		try {
			List<Proceedings> proceedings = dao.findByAttribute(TITLE, title, max);
			List<Journal> journals = journalDao.findByAttribute(JOURNAL, title, max);
			for(Proceedings p: proceedings){
				key = p.getKey();
				authors.addAll(authorDao.findByAttribute(KEY, key, max));
			}
			for(Journal j: journals){
				key = j.getKey();
				authors.addAll(authorDao.findByAttribute(KEY, key, max));
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
		// TODO Auto-generated method stub
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

}
