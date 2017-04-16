package main.java.queryengine;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Logger;
import main.java.entities.Article;
import main.java.entities.Author;
import main.java.entities.Journal;
import main.java.entities.Proceedings;
import main.java.entities.User;
import main.java.entities.AuthorInfo;
import main.java.entities.InProceeding;
import main.java.queryengine.dao.*;

public class MariaDBDaoFactory implements DAOFactory {

	private static DAOFactory instance = null;
	private static UserDAO userDaoInstance = null;
	private static AuthorDAO authorDaoInstance = null;
	private static ArticleDAO articleDaoInstance = null;
	private static JournalDAO journalDaoInstance = null;
	private static ProceedingsDAO proceedingsDAOInstance = null;
	private static InProceedingsDAO inproceedingsDAOInstance = null;

	private static final String DBUSERNAME;

	private static final String DBPASSWORD;
	private static final String DBSERVER;
	private static final String DBDRIVER;
	private static final String DBURL;
	private static final String DBPROTOCOL;
	private static final String DBPORT;
	private static Connection connection = null;
	
	private static final Properties properties;
	
	static {
		properties = new Properties();
		try {
			properties.load(new FileInputStream("src/main/java/queryengine/db.properties"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		DBUSERNAME = properties.getProperty("USER");
		DBPASSWORD = properties.getProperty("PASSWORD");
		DBSERVER = properties.getProperty("SERVER");
		DBDRIVER = properties.getProperty("DRIVER");
		DBPORT = properties.getProperty("PORT");
		DBPROTOCOL = properties.getProperty("PROTOCOL");
		
		DBURL = DBPROTOCOL + DBSERVER + ":" + DBPORT;
	}

	public MariaDBDaoFactory() {
	}

	@Override
	public DAO<Author> getAuthorDAO() {
		return getAuthorDaoInstance();
	}

	synchronized public Connection getConnection() {
		try {
			if (connection == null || connection.isClosed()) {
				Class.forName(DBDRIVER);
				connection = DriverManager.getConnection(DBURL, DBUSERNAME, DBPASSWORD);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return connection;
	}
	
	@Override
	synchronized public void closeConnection() {
		try {
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static DAOFactory getInstance() {
		if (instance == null) {
			instance = new MariaDBDaoFactory();
		}
		return instance;
	}
	
	private static DAO<User> getUserDaoInstance() {
		if (userDaoInstance == null) {
			userDaoInstance = new UserDAO();
		}
		return userDaoInstance;
	}

	private static DAO<Author> getAuthorDaoInstance() {
		if (authorDaoInstance == null) {
			authorDaoInstance = new AuthorDAO();
		}
		return authorDaoInstance;
	}
	
	private static DAO<AuthorInfo> getAuthorInfoDaoInstance() {
		AuthorInfoDAO dao = new AuthorInfoDAO();
		return dao;
	}
	
	private static DAO<Article> getArticleDaoInstance() {
		if (articleDaoInstance == null) {
			articleDaoInstance = new ArticleDAO();
		}
		return articleDaoInstance;
	}

	private static DAO<Journal> getJournalDaoInstance() {
		if (journalDaoInstance == null) {
			journalDaoInstance = new JournalDAO();
		}
		return journalDaoInstance;
	}
	
	private static DAO<Proceedings> getProceedingsDaoInstance() {
		if (proceedingsDAOInstance == null) {
			proceedingsDAOInstance = new ProceedingsDAO();
		}
		return proceedingsDAOInstance;
	}
	
	private static DAO<InProceeding> getInProceedingsDaoInstance() {
		if (inproceedingsDAOInstance == null) {
			inproceedingsDAOInstance = new InProceedingsDAO();
		}
		return inproceedingsDAOInstance;
	}
	
	@Override
	public DAO<Article> getArticleDAO() {
		return getArticleDaoInstance();
	}

	@Override
	public DAO<Journal> getJournalDAO() {
		return getJournalDaoInstance();
	}
	
	@Override
	public DAO<User> getUserDAO() {
		return getUserDaoInstance();
	}
	
	@Override
	public DAO<AuthorInfo> getAuthorInfoDAO() {
		return getAuthorInfoDaoInstance();
	}

	@Override
	public DAO<Proceedings> getProceedingsDAO() {
		return getProceedingsDaoInstance();
	}
	
	@Override
	public DAO<InProceeding> getInProceedingsDAO() {
		return getInProceedingsDaoInstance();
	}

	public static ProceedingsDAO getProceedingsDAOInstance() {
		return proceedingsDAOInstance;
	}
	
}
