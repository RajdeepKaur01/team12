package main.java.queryengine;

import java.sql.Connection;

import main.java.entities.Article;
import main.java.entities.Author;
import main.java.entities.Journal;
import main.java.entities.Proceedings;
import main.java.entities.InProceeding;
import main.java.queryengine.dao.DAO;

public interface DAOFactory {
	public Connection getConnection();
	public void closeConnection();
	
	public DAO<Author> getAuthorDAO();
	public DAO<Article> getArticleDAO();
	public DAO<Journal> getJournalDAO();
	public DAO<Proceedings> getProceedingsDAO();
	public DAO<InProceeding> getInProceedingsDAO();
}
