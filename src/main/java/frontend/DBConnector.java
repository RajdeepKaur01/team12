package main.java.frontend;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnector {
	
	private static String username = "root";
	private static String password = "mohit";
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
