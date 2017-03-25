package main.java.queryengine;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import main.java.entities.Author;

public class AuthorDAO implements DAO<Author> {
	
	private static DAOFactory daoFactory = MariaDBDaoFactory.getInstance();
	private static final Connection connection = daoFactory.getConnection();
	
	@Override
	public Author findById(int id) throws SQLException {
		PreparedStatement preparedStatement = connection.prepareStatement("select * from bibliography.author where ID = ?");
		preparedStatement.setInt(1, id);
		ResultSet resultSet = preparedStatement.executeQuery();
		return null;
	}

	@Override
	public List<Author> findByAttributes(Map<String, String> attributeNamesAndValues, int limit) {
		return null;
	}

	@Override
	public List<Author> findByAttribute(String attributeName, String attributeValue, int limit) throws SQLException {
		PreparedStatement preparedStatement = connection.prepareStatement("select * from bibliography.author where " + attributeName + " = ? LIMIT " + limit);
		preparedStatement.setString(1, attributeValue);
		ResultSet resultSet = preparedStatement.executeQuery();
		List<Author> authors = new ArrayList<>();
		while (resultSet.next()) {
			Author author = new Author();
			author.setName(resultSet.getString(3));
			System.out.println("Name is: " + author.getName());
			authors.add(author);
;		}
		return authors;
	}
}
