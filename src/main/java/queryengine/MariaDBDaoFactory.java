package main.java.queryengine;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.logging.Logger;

import main.java.entities.Author;

public class MariaDBDaoFactory implements DAOFactory {

	private static DAOFactory instance = null;
	private static AuthorDAO authorDaoInstance = null;
	private static final String DBUSERNAME = "root";
	private static final String DBPASSWORD = "mohit";
	
	public static final String DRIVER = "com.mysql.cj.jdbc.Driver";
	public static final String DBURL = "jdbc:mysql://localhost:3306/bibliography";
	
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
