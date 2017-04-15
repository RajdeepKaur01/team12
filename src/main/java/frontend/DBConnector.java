package main.java.frontend;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Logger;

/*
 * Creates a DB connection object
 */
public class DBConnector {
	
	/*
	AWS Configuration
	org.mariadb.jdbc.Driver
	jdbc:mariadb://team12-msd.cylwolp3gguo.us-east-1.rds.amazonaws.com:3306/
	username = "team12"
	password = "team12-cs5500"
	Local Configuration
	com.mysql.cj.jdbc.Driver
	jdbc:mysql://localhost:3306/dblp-test
	USERNAME="root"
	PASSWORD="XXXX"*/
/*
	private static final String DBUSERNAME = "team12";
	private static final String DBPASSWORD = "team12-cs5500";
	
	public static final String DRIVER = "org.mariadb.jdbc.Driver";
	public static final String DBURL = "jdbc:mariadb://team12-msd.cylwolp3gguo.us-east-1.rds.amazonaws.com:3306/";
	
	private static final Logger LOGGER = Logger.getLogger(FrontEndParser.class.getName());

	private static Connection conn;
	
	
	 * Returns Connection object if connections is established else null
	 
	public static Connection getConnection(){
		if(conn==null){

			try {
			Class.forName(DRIVER);
			conn = DriverManager.getConnection(DBURL, DBUSERNAME, DBPASSWORD);
			} catch (SQLException e) {
				LOGGER.warning(e.getMessage());
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				LOGGER.warning(e.getMessage());
				e.printStackTrace();
			}
			if(conn!=null){
				LOGGER.info("Connected Established");
				return conn;
			} else{
				LOGGER.info("Connection is nulll");
				return null;
			}
		}
		return conn;

		
	}*/
	

	
}
