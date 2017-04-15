package main.java.queryengine.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import main.java.entities.Article;
import main.java.entities.Author;
import main.java.entities.Journal;
import main.java.queryengine.DAOFactory;
import main.java.queryengine.MariaDBDaoFactory;

public class JournalDAO implements DAO<Journal> {
	
	private static final String YEAR="year";
	private static DAOFactory daoFactory = MariaDBDaoFactory.getInstance();
	private static final Connection connection = daoFactory.getConnection();
	private String regex = "%";

	@Override
	public Set<Journal> findByAttribute(String attributeName, Set<String>attributeValue) throws SQLException {
		Set<Journal> journals = new HashSet<>();
		attributeValue.forEach((value) -> {
			PreparedStatement preparedStatement;
			try {
				if(attributeName.equals(YEAR)){
					regex="";
					preparedStatement = connection.prepareStatement("select * from bibliography.journals where " + attributeName + " = ?");
				} else{
					preparedStatement = connection.prepareStatement("select * from bibliography.journals where " + attributeName + " LIKE ?");
				}
				preparedStatement.setString(1, regex + value + regex);
				ResultSet resultSet = preparedStatement.executeQuery();
				while (resultSet.next()) {
					Journal journal = new Journal();
					journal.setKey(resultSet.getString(2));
					journal.setYear(resultSet.getInt(6));
					journal.setName(resultSet.getString(8));
					journal.setVolume(resultSet.getString(5));
					journals.add(journal);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		});
		return journals;
	}

	@Override
	public Set<Journal> findByKeys(Set<String> keys) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

}
