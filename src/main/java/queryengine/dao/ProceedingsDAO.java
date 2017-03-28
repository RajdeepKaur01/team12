package main.java.queryengine.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import main.java.entities.Proceedings;
import main.java.queryengine.DAOFactory;
import main.java.queryengine.MariaDBDaoFactory;

public class ProceedingsDAO implements DAO<Proceedings> {
	
	private static DAOFactory daoFactory = MariaDBDaoFactory.getInstance();
	private static final Connection connection = daoFactory.getConnection();
	
	@Override
	public Proceedings findById(int id) throws SQLException {
		PreparedStatement preparedStatement = connection.prepareStatement("select * from bibliography.proceedings where ID = ?");
		preparedStatement.setInt(1, id);
		
		ResultSet resultSetProceedings = preparedStatement.executeQuery();
		Proceedings proceedings = new Proceedings();
		proceedings.setTitle(resultSetProceedings.getString(4));
		proceedings.setYear(resultSetProceedings.getInt(6));
		proceedings.setEditors(Arrays.asList(resultSetProceedings.getString(10).split("\\s*,\\s*")));		
		proceedings.setVolume(Integer.parseInt(resultSetProceedings.getString(7)));
		proceedings.setSeries(resultSetProceedings.getString(8));
		proceedings.setPublisher(resultSetProceedings.getString(9));
		proceedings.setConferenceName(resultSetProceedings.getString(5));
		
		return proceedings;
	}

	@Override
	public List<Proceedings> findByAttributes(Map<String, String> attributeNamesAndValues, int limit) {
		return null;
	}

	@Override
	public List<Proceedings> findByAttribute(String attributeName, String attributeValue, int limit) throws SQLException {
		
		PreparedStatement preparedStatement = connection.prepareStatement("select * from bibliography.proceedings where " + attributeName + " = ? LIMIT " + limit);
		preparedStatement.setString(1, attributeValue);
		ResultSet resultSetProceedings = preparedStatement.executeQuery();
		List<Proceedings> proceedingsList = new ArrayList<>();
		while (resultSetProceedings.next()) {
			Proceedings proceedings = new Proceedings();
			
			proceedings.setTitle(resultSetProceedings.getString(4));
			proceedings.setYear(resultSetProceedings.getInt(6));
			proceedings.setEditors(Arrays.asList(resultSetProceedings.getString(10).split("\\s*,\\s*")));
			proceedings.setVolume(Integer.parseInt(resultSetProceedings.getString(7)));
			proceedings.setSeries(resultSetProceedings.getString(8));
			proceedings.setPublisher(resultSetProceedings.getString(9));
			proceedings.setConferenceName(resultSetProceedings.getString(5));
			
			proceedingsList.add(proceedings);
		}
		return proceedingsList;
	}
}
