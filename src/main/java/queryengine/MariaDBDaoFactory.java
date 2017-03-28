package main.java.queryengine;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.logging.Logger;

import main.java.entities.Author;
import main.java.entities.Proceedings;

public class MariaDBDaoFactory implements DAOFactory {

	private static DAOFactory instance = null;
	private static AuthorDAO authorDaoInstance = null;
	private static final String DBUSERNAME = "team12";
	private static final String DBPASSWORD = "team12-cs5500";
	
	public static final String DRIVER = "org.mariadb.jdbc.Driver";
	public static final String DBURL = "jdbc:mariadb://team12-msd.cylwolp3gguo.us-east-1.rds.amazonaws.com:3306/";
	
	protected MariaDBDaoFactory() { }
	
	@Override
	public DAO<Author> getAuthorDAO() {
		return getAuthorDaoInstance();
	}
	
	public Connection getConnection() {
		Connection connection = null;
		try {
			Class.forName(DRIVER);
			connection = DriverManager.getConnection(DBURL, DBUSERNAME, DBPASSWORD);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return connection;
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

	
}
