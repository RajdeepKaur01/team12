package main.java.interfaces;

import java.util.Set;

import main.java.entities.Author;
import main.java.entities.AuthorInfo;
import main.java.entities.InProceeding;
import main.java.entities.Journal;
import main.java.entities.Proceedings;
import main.java.queryengine.DAOFactory;
import main.java.queryengine.MariaDBDaoFactory;
import main.java.queryengine.dao.DAO;

public interface IAuthUser {
	
	
	public boolean addAuthors (int UserId,Set<Author> authorList);
	public boolean updateAuthors (int UserId, Author authorObj);
	public boolean deleteAuthors(int UserId, Author authorObj);
	public Set<Author> getAuthors(int UserId);
}
