package main.java.queryengine.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import main.java.entities.InProceeding;
import main.java.entities.Proceedings;
import main.java.entities.User;
import main.java.queryengine.DAOFactory;
import main.java.queryengine.MariaDBDaoFactory;;

public class UserDAO implements DAO<User>{

	private static DAOFactory daoFactory = MariaDBDaoFactory.getInstance();
	private static final Connection connection = daoFactory.getConnection();
	private PreparedStatement preparedStatement;
	
	@Override
	public Set<User> findByAttributes(Map<String, String> attributeNamesAndValues) throws SQLException {
		StringBuilder query = new StringBuilder("select * from bibliography.user where ");
		Set<User> userSet = new HashSet<>();
		int c=0;
		for (Map.Entry<String, String> entry : attributeNamesAndValues.entrySet()) {
			if(c==0){
				query.append(entry.getKey()+" = '"+entry.getValue()+"'");
			} else{
				query.append(" and "+entry.getKey()+" = '"+entry.getValue()+"'");
			}
			c++;
		}
		preparedStatement = connection.prepareStatement(query.toString());
		ResultSet resultSetUser = preparedStatement.executeQuery();
		while (resultSetUser.next()) {
			User userObj = new User();
			userObj.setUserId(resultSetUser.getInt(1));
			userObj.setName(resultSetUser.getString(2) + " "+ resultSetUser.getString(3));
			userObj.setUsername	(resultSetUser.getString(4));
			userObj.setPassword(resultSetUser.getString(5));
			userObj.setEmailAdress(resultSetUser.getString(7));
			userSet.add(userObj);
		}
		return userSet;
	}

	@Override
	public Set<User> findByAttribute(String attirubteName, Set<String> attributeValues) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<User> findByKeys(Set<String> keys) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}
	
	public static void main(String argp[]){
		/*UserDAO b = new UserDAO();
		Map<String, String> data = new HashMap<String,String>();
		data.put("username", "franktip");
		data.put("password", "franktip");
		try {
			
			for(User a:b.findByAttributes(data)){
				System.out.println(a.getPassword());
				System.out.println(a.getUsername());
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
	}

}
