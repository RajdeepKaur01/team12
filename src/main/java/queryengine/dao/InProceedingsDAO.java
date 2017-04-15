package main.java.queryengine.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import main.java.entities.Article;
import main.java.entities.InProceeding;
import main.java.entities.Proceedings;
import main.java.queryengine.DAOFactory;
import main.java.queryengine.MariaDBDaoFactory;

public class InProceedingsDAO implements DAO<InProceeding> {

	private static final String CROSSREF = "crossref";
	private static final String YEAR = "year";
	private String regex = "%";
	private static DAOFactory daoFactory = MariaDBDaoFactory.getInstance();
	private static final Connection connection = daoFactory.getConnection();

	@Override
	public Set<InProceeding> findByAttribute(String attributeName, Set<String> attributeValue)
			throws SQLException {

		String value = "";

		for (String v : attributeValue) {
			value = v;
		}
		PreparedStatement preparedStatement;
		if (attributeName.equals(CROSSREF)||attributeName.equals(YEAR)) {
			regex = "";
			preparedStatement = connection
					.prepareStatement("select * from bibliography.inproceedings where " + attributeName + " = ?");
		} else {
			preparedStatement = connection
					.prepareStatement("select * from bibliography.inproceedings where " + attributeName + " LIKE ?");
		}
		preparedStatement.setString(1, regex + value + regex);

		ResultSet resultSet = preparedStatement.executeQuery();
		Set<InProceeding> set = new HashSet<>();
		while (resultSet.next()) {
			InProceeding inProceeding = new InProceeding();
			Proceedings proceedings = new Proceedings();
			proceedings.setTitle(resultSet.getString("title"));
			proceedings.setYear(resultSet.getInt("year"));
			inProceeding.setKey(resultSet.getString(2));
			inProceeding.setProceedings(proceedings);
			inProceeding.setBookTitle(resultSet.getString("booktitle"));
			set.add(inProceeding);
		}
		return set;
	}
	
	@Override
	public Set<InProceeding> findByKeys(Set<String> keys) throws SQLException {
		Set<InProceeding> inproceedings = new HashSet<>();
		if(keys.size()>0){
			StringBuilder sb = new StringBuilder();
			sb.append("select year, title from bibliography.inproceedings where _key").append(" in ('");
			
			keys.forEach((value) -> {
				sb.append(value).append("','");
			});
			sb.replace(sb.lastIndexOf(",'"), sb.length(), "").append(")");
			PreparedStatement preparedStatement = connection.prepareStatement(sb.toString());

			ResultSet resultSet = preparedStatement.executeQuery();
			InProceeding inproceeding;
			while (resultSet.next()) {
				inproceeding = new InProceeding();
				inproceeding.setYear(resultSet.getInt(1));
				inproceeding.setTitle(resultSet.getString(2));
				inproceedings.add(inproceeding);
			}
			
		}
		return inproceedings;
	}
}
