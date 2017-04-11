package main.java.auth;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import main.java.entities.Author;
import main.java.interfaces.IAuthUser;
import main.java.entities.User;
import main.java.queryengine.DAOFactory;
import main.java.queryengine.MariaDBDaoFactory;
import main.java.queryengine.dao.DAO;
import main.java.queryengine.dao.UserDAO;

public class AuthUser implements IAuthUser{
	
	private static final String USERID = "user_id";
	private static DAOFactory daoFactory;
	private static UserDAO userDAO;

	static{
		daoFactory = MariaDBDaoFactory.getInstance();
		userDAO = (UserDAO)daoFactory.getUserDAO();
	}



	@Override
	public boolean addAuthors(int UserId, Set<Author> authorList) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean updateAuthors(int UserId, Author authorObj) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean deleteAuthors(int UserId, Author authorObj) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public Set<Author> getAuthors(int userId) {
		Set<String> values = new HashSet<>();
		values.add(Integer.toString(userId));
		Set<Author> authors = new HashSet<>();
		try {
			authors = userDAO.findAuthorsById(USERID, values);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return authors;
	}
	
}
