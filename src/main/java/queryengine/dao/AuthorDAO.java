package main.java.queryengine.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import main.java.entities.Author;
import main.java.queryengine.DAOFactory;
import main.java.queryengine.MariaDBDaoFactory;

public class AuthorDAO implements DAO<Author> {

	private static DAOFactory daoFactory = MariaDBDaoFactory.getInstance();
	private static final Connection connection = daoFactory.getConnection();
	private static final Map<String, String> committeeAcronymMap = new HashMap<>();

	static {

		committeeAcronymMap.put("P", "Program Chair");
		committeeAcronymMap.put("G", "General Chair");
		committeeAcronymMap.put("C", "Conference Chair");
		committeeAcronymMap.put("E", "External Review Committee");
	}

	@Override
	public Author findById(int id) throws SQLException {
		PreparedStatement preparedStatement = connection
				.prepareStatement("select * from bibliography.author where ID = ?");
		preparedStatement.setInt(1, id);
		ResultSet resultSet = preparedStatement.executeQuery();
		return null;
	}

	@Override
	public Set<Author> findByAttributes(Map<String, String> attributeNamesAndValues, int limit) throws SQLException {
		StringBuilder paramQueryString = new StringBuilder();

		attributeNamesAndValues.keySet().forEach((key) -> {
			paramQueryString.append(key).append(" = ").append(attributeNamesAndValues.get(key)).append(" and ");
		});

		int lastAndIndex = paramQueryString.lastIndexOf(" and ");
		paramQueryString.replace(lastAndIndex, paramQueryString.length(), "");
		Statement statement = connection.createStatement();
		Set<Author> authors = new HashSet<>();
		ResultSet resultSet = statement.executeQuery("select * from bibliography.author where " + paramQueryString);
		String name = "";
		while (resultSet.next()) {
			Author author = new Author();
			String localName = resultSet.getString(3);
			
			if (localName.equals(name)) {
				continue;
			}else {
				name = localName;
				author.setName(name);
				authors.add(populateAuthorData(author, resultSet));
			}
		}
		return authors;
	}

	@Override
	public Set<Author> findByAttribute(String attributeName, Set<String> attributeValues, int limit)
			throws SQLException {
		StringBuilder sb = new StringBuilder();
		sb.append("select * from bibliography.author where ").append(attributeName).append(" in ('");

		attributeValues.forEach((value) -> {
			sb.append(value).append("','");
		});
		sb.replace(sb.lastIndexOf(",'"), sb.length(), "").append(")").append(" ORDER BY name");
		PreparedStatement preparedStatement = connection.prepareStatement(sb.toString());

		ResultSet resultSet = preparedStatement.executeQuery();
		Set<Author> authorSet = new HashSet<>();
		String name = "";
		while (resultSet.next()) {
			Author author = new Author();
			String localName = resultSet.getString(3);
			
			if (localName.equals(name)) {
				continue;
			}else {
				name = localName;
				author.setName(name);
				authorSet.add(populateAuthorData(author, resultSet));
			}
		}
		
		return authorSet;
	}

	private Author populateAuthorData(Author author, ResultSet resultSet) throws SQLException {
		author.setName(resultSet.getString(3));
		String conferenceName = resultSet.getString(4);
		Map<String, Set<String>> map = new HashMap<>();
		author.setCommitteeMemberInfo(map);
		if (conferenceName != null && !conferenceName.isEmpty()) {
			int year = resultSet.getInt(5);
			String title = resultSet.getString(6);
			Set<String> set = new HashSet<>();
			set.add("Role:" + committeeAcronymMap.get(title) + ", Year:" + year);
			map.put(conferenceName, set);
			author.setCommitteeMemberInfo(map);
		} else {
			author.setCommitteeMemberInfo(map);
		}
		return author;
	}

	public static void main(String[] args) throws SQLException {
		AuthorDAO dao = new AuthorDAO();
		Set<String> names = new HashSet<>();
		names.add("Gert Smolka");
		names.add("Petra Ludewig");
		Set<Author> authors = dao.findByAttribute("name", names, 100);
		System.out.println(authors);
		connection.close();
	}
}