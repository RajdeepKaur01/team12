package main.java.interfaces;

import java.util.List;
import java.util.Set;

import main.java.entities.Author;

public interface IAuthUser {

	public boolean addAuthors (int UserId,List<Author> authorList);
	public boolean updateAuthors (int UserId, Author authorObj);
	public boolean deleteAuthors(int UserId, Author authorObj);
	public Set<Author> getAuthors(int UserId);
}
