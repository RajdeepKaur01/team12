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
	public boolean addAuthors(int userId, Set<Author> authorList) {
		boolean isSuccessful = false;

		try {
			isSuccessful = userDAO.insertAuthorsbyId(userId, authorList);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return isSuccessful;
	}

	@Override
	public boolean updateAuthor(int userId, Author authorObj) {
		boolean isSuccessful = false;
		try {
			isSuccessful = userDAO.updateAuthorNote(userId, authorObj);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return isSuccessful;
	}

	@Override
	public boolean deleteAuthor(int userId, Author authorObj) {
		boolean isSuccessful = false;
		try {
			isSuccessful = userDAO.deleteAttribute(userId, authorObj);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return isSuccessful;
	}

	@Override
	public Set<Author> getAuthors(int userId) {
		Set<String> values = new HashSet<>();
		Set<Author> authors = new HashSet<>();
		values.add(Integer.toString(userId));

		try {
			authors = userDAO.findAuthorsById(USERID, values);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return authors;

	}

}
