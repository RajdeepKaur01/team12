package main.java.queryengine;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
		while(resultSet.next()) {
			System.out.println(resultSet.getString(2));
		}
		return null;
	}
	
	@Override
	public boolean insert(Author entity) {
		return false;
	}

	@Override
	public List<Author> findByAttributes(Map<String, String> attributeNamesAndValues) {
		return null;
	}

	@Override
	public List<Author> findByAttribute(String attirubteName, String attributeValue) {
		// TODO Auto-generated method stub
		return null;
	}

}
