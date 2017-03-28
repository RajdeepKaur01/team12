package main.java.queryengine;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Logger;

import main.java.entities.Author;
import main.java.queryengine.dao.AuthorDAO;
import main.java.queryengine.dao.DAO;

public class MariaDBDaoFactory implements DAOFactory {

	private static DAOFactory instance = null;
	private static AuthorDAO authorDaoInstance = null;

	private static final String DBUSERNAME = "team12";// "team12" "root";
	private static final String DBPASSWORD = "team12-cs5500";// "team12-cs5500" "";
	private static final String DBSERVER = "team12-msd.cylwolp3gguo.us-east-1.rds.amazonaws.com";// "localhost" "team12-msd.cylwolp3gguo.us-east-1.rds.amazonaws.com";

	public static final String DRIVER = "org.mariadb.jdbc.Driver";
	public static final String DBURL = "jdbc:mariadb://" + DBSERVER + ":3306/";
	private static Connection connection = null;

	protected MariaDBDaoFactory() {
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

}
