package main.java.queryengine;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Logger;
import main.java.entities.Article;
import main.java.entities.Author;
import main.java.entities.Journal;
import main.java.entities.Proceedings;
import main.java.entities.AuthorInfo;
import main.java.entities.InProceeding;
import main.java.queryengine.dao.ArticleDAO;
import main.java.queryengine.dao.AuthorInfoDAO;
import main.java.queryengine.dao.AuthorDAO;
import main.java.queryengine.dao.DAO;
import main.java.queryengine.dao.InProceedingsDAO;
import main.java.queryengine.dao.JournalDAO;
import main.java.queryengine.dao.ProceedingsDAO;

public class MariaDBDaoFactory implements DAOFactory {

	private static DAOFactory instance = null;
	private static AuthorInfoDAO authorInfoDaoInstance = null;
	private static AuthorDAO authorDaoInstance = null;
	private static ArticleDAO articleDaoInstance = null;
	private static JournalDAO journalDaoInstance = null;
	private static ProceedingsDAO proceedingsDAOInstance = null;
	private static InProceedingsDAO inproceedingsDAOInstance = null;

	static final String DBUSERNAME = "root";// "team12" "root";
	private static final String DBPASSWORD = "mohit";// "team12-cs5500" "";
	private static final String DBSERVER = "localhost"; //"team12-msd.cylwolp3gguo.us-east-1.rds.amazonaws.com";


	public static final String DRIVER = "com.mysql.cj.jdbc.Driver";
			
	public static final String DBURL = "jdbc:mysql://localhost:3306/bibliography";
	private static Connection connection = null;

	public MariaDBDaoFactory() {
	}

	@Override
	public DAO<Author> getAuthorDAO() {
		return getAuthorDaoInstance();
	}

	synchronized public Connection getConnection() {
		try {
			if (connection == null || connection.isClosed()) {
				Class.forName(DRIVER);
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

	public static void setProceedingsDAOInstance(ProceedingsDAO proceedingsDAOInstance) {
		MariaDBDaoFactory.proceedingsDAOInstance = proceedingsDAOInstance;
	}


	
}
