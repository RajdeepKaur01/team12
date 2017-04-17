package main.java.auth;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import main.java.entities.Author;
import main.java.interfaces.IAuthUser;
import main.java.queryengine.DAOFactory;
import main.java.queryengine.MariaDBDaoFactory;
import main.java.queryengine.dao.UserDAO;

public class AuthUser implements IAuthUser {

	private static final String USERID = "user_id";
	private static DAOFactory daoFactory;
	private static UserDAO userDAO;

	static {
		daoFactory = MariaDBDaoFactory.getInstance();
		userDAO = (UserDAO) daoFactory.getUserDAO();
	}

	@Override
	public boolean addAuthors(int userId, Set<Author> authorList) throws SQLException {
		boolean isSuccessful = false;

			isSuccessful = userDAO.insertAuthorsbyId(userId, authorList);
		
		return isSuccessful;
	}

	@Override
	public boolean updateAuthor(int userId, Author authorObj) throws SQLException {
		boolean isSuccessful = false;
			isSuccessful = userDAO.updateAuthorNote(userId, authorObj);
	
		return isSuccessful;
	}

	@Override
	public boolean deleteAuthor(int userId, Author authorObj) throws SQLException {
		boolean isSuccessful = false;
			isSuccessful = userDAO.deleteAttribute(userId, authorObj);
		
		return isSuccessful;
	}

	@Override
	public Set<Author> getAuthors(int userId) throws SQLException {
		Set<String> values = new HashSet<>();
		Set<Author> authors = new HashSet<>();
		values.add(Integer.toString(userId));
			authors = userDAO.findAuthorsById(USERID, values);
	

		return authors;

	}

}
