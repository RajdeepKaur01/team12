package main.java.queryengine;

import java.sql.Connection;

import main.java.entities.Author;

public interface DAOFactory {
	public Connection getConnection();
	public DAO<Author> getAuthorDAO();
}
