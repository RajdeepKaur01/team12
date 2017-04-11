package main.java.auth;

import java.util.List;
import java.util.Set;

import main.java.entities.Author;
import main.java.interfaces.IAuthUser;

public class AuthUser implements IAuthUser{


	@Override
	public boolean addAuthors(int UserId, List<Author> authorList) {
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
	public Set<Author> getAuthors(int UserId) {
		// TODO Auto-generated method stub
		return null;
	}
}
