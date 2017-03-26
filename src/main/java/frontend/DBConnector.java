package main.java.frontend;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Logger;

/*
 * Creates a DB connection object
 */
public class DBConnector {
	
	/*org.mariadb.jdbc.Driver
	jdbc:mariadb://team12-msd.cylwolp3gguo.us-east-1.rds.amazonaws.com:3306
	username = "team12"
	password = "team12-cs5500"*/

	private static final String USERNAME = "root";
	private static final String PASSWORD = "mohit";
	private static final Logger LOGGER = Logger.getLogger(FrontEndParser.class.getName());

	private static Connection conn;
	
	/*
	 * Returns Connection object if connections is established else null
	 */
	public static Connection getConnection(){
		if(conn==null){

			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
				conn =DriverManager.getConnection("jdbc:mysql://localhost:3306/dblp-test",USERNAME,PASSWORD);
			} catch (SQLException e) {
				LOGGER.warning(e.getMessage());
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				LOGGER.warning(e.getMessage());
				e.printStackTrace();
			}
			if(conn!=null){
				System.out.println("Connected Established");
				return conn;
			} else{
				System.out.println("Connection is nulll");
				return null;
			}
		}
		return conn;

		
	}
	

	
}
