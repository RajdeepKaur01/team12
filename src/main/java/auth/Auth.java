package main.java.auth;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import main.java.entities.User;
import main.java.interfaces.IAuth;
import main.java.queryengine.dao.UserDAO;

public class Auth implements IAuth{
	
	User userObj;

	@Override
	public User login(String username, String password) {
		UserDAO userAuthObj = new UserDAO();
		HashMap<String,String> attributeNamesAndValues = new HashMap<>();
		attributeNamesAndValues.put("username", password);
		attributeNamesAndValues.put("password", password);
		try {
			Set<User> userSet= userAuthObj.findByAttributes(attributeNamesAndValues);

			if(userSet.size()==0){
				userObj=null;
			}
			else{
				userObj = userSet.iterator().next();
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return userObj;
	}

}
