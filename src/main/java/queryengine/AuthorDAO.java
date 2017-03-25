package main.java.queryengine;

import main.java.entities.Author;

public class AuthorDAO implements DAO<Author> {
	
	private static DAOFactory daoFactory = MariaDBDaoFactory.getInstance();
	
	@Override
	public Author findById(String id) {
		return null;
	}

	@Override
	public Author findByAttribute(String attributeName, String attributeValue) {
		
		return null;
	}

	@Override
	public boolean insert(Author entity) {
		
		return false;
	}

}
