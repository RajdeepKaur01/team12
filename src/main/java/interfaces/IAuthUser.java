package main.java.interfaces;
import java.util.Set;

import main.java.entities.Author;

public interface IAuthUser {
	public boolean addAuthors (int userId, Set<Author> authorList);
	public boolean updateAuthor (int userId, Author authorObj);
	public boolean deleteAuthor (int userId, Author authorObj);
	public Set<Author> getAuthors(int userId);
}
