package main.java.interfaces;

import java.sql.SQLException;
import java.util.Set;

import main.java.entities.Author;

public interface IAuthUser {
	public boolean addAuthors(int userId, Set<Author> authorList)throws SQLException;

	public boolean updateAuthor(int userId, Author authorObj)throws SQLException;

	public boolean deleteAuthor(int userId, Author authorObj)throws SQLException;

	public Set<Author> getAuthors(int userId) throws SQLException;
}
