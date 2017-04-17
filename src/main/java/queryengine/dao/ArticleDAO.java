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

public class ArticleDAO implements DAO<Article> {
	
	//Static members to store DB factory instance and DB connection instance
	private static DAOFactory daoFactory = MariaDBDaoFactory.getInstance();
	private static final Connection connection = daoFactory.getConnection();
	
	//Reuse this prepared statement throughout the class
	private PreparedStatement preparedStatement;
	
	private String regex = "%";

	/**
	 * Find journal articles by a given attribute
	 * E.g. title, year, pages
	 */
	@Override 
	public Set<Article> findByAttribute(String attributeName, Set<String> attributeValue) throws SQLException {
		String value = "";

		for (String v : attributeValue)
			value = v;

		//Create query string
		preparedStatement = connection
				.prepareStatement("select year,title from bibliography.journals where " + attributeName + " LIKE ?");
		preparedStatement.setString(1, regex + value + regex);
		
		//Execute query
		ResultSet resultSet = preparedStatement.executeQuery();
		Set<Article> articles = new HashSet<>();
		
		//Iterate over the result set and create Article objects
		while (resultSet.next()) {
			Article article = new Article();
			article.setYear(resultSet.getInt(1));
			article.setTitle(resultSet.getString(2));
			articles.add(article);
		}
		return articles;
	}

	/**
	 * Find journal articles by using the given set of keys
	 */
	@Override
	public Set<Article> findByKeys(Set<String> keys) throws SQLException {
		Set<Article> articles = new HashSet<>();
		
		//If valid set
		if (keys != null && !keys.isEmpty()) {
			
			//Build the query from the key values
			StringBuilder sb = new StringBuilder();
			sb.append("select year, title from bibliography.journals where _key").append(" in ('");

			keys.forEach((value) -> {
				sb.append(value).append("','");
			});
			sb.replace(sb.lastIndexOf(",'"), sb.length(), "").append(")");
			PreparedStatement preparedStatement = connection.prepareStatement(sb.toString());

			//Execute query
			ResultSet resultSet = preparedStatement.executeQuery();
			Article article;
			
			//Iterate over the result set and create Article objects
			while (resultSet.next()) {
				article = new Article();
				article.setYear(resultSet.getInt(1));
				article.setTitle(resultSet.getString(2));
				articles.add(article);
			}
		}
		return articles;

	}
}
