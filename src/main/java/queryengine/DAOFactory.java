package main.java.queryengine;

import java.sql.Connection;

import main.java.entities.Author;
import main.java.queryengine.dao.DAO;

public interface DAOFactory {
	public Connection getConnection();
	public void closeConnection();
	public DAO<Author> getAuthorDAO();
}
