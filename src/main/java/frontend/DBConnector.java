package main.java.frontend;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnector {
	
	/*org.mariadb.jdbc.Driver
	jdbc:mariadb://team12-msd.cylwolp3gguo.us-east-1.rds.amazonaws.com:3306
	username = "team12"
	password = "team12-cs5500"*/

	private static final String username = "root";
	private static final String password = "mohit";
	static Connection conn;
	
	public static Connection getConnection(){
		try{
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn =DriverManager.getConnection("jdbc:mysql://localhost:3306/dblp-test",username,password);
			if(conn!=null){
				System.out.println("Connected Established");
				return conn;
			} else{
				System.out.println("Connection is nulll");
				return null;
			}
		}
		catch(Exception e){
			System.out.println(e);
			return null;
		}
		
	}
	

	
}
