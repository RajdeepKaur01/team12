package main.java.interfaces;

import java.util.List;

import main.java.entities.Author;

public interface IAuthUser {

	public boolean addAuthors (int UserId,List<Author> authorList);
	public boolean updateAuthors (int UserId, Author authorObj);
	public boolean deleteAuthors(int UserId, Author authorObj);
	public boolean getAuthors(int UserId);
}
