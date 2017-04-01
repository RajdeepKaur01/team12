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

public class ArticleDAO implements DAO<Article>{
	private static DAOFactory daoFactory = MariaDBDaoFactory.getInstance();
	private static final Connection connection = daoFactory.getConnection();
	private PreparedStatement preparedStatement;
	private String regex="%";

	@Override
	public Set<Article> findByAttributes(Map<String, String> attributeNamesAndValues, int limit) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<Article> findByAttribute(String attributeName, Set<String> attributeValue, int limit) throws SQLException {
		String value = "";
		
		for(String v: attributeValue) value = v;
		
		preparedStatement = connection.prepareStatement("select year,title from bibliography.journals where " + attributeName + " LIKE ? LIMIT " + limit);			
		preparedStatement.setString(1, regex + value + regex);
		ResultSet resultSet = preparedStatement.executeQuery();
		Set<Article> articles = new HashSet<>();
		while (resultSet.next()) {
			Article article = new Article();	
			article.setYear(resultSet.getInt(1));
			article.setTitle(resultSet.getString(2));
			articles.add(article);
		}
		return articles;
	}

	@Override
	public Set<Article> findByKeys(Set<String> keys) throws SQLException {
		Set<Article> articles = new HashSet<>();
		if(keys != null && !keys.isEmpty()){
			StringBuilder sb = new StringBuilder();
			sb.append("select year, title from bibliography.journals where _key").append(" in ('");

			keys.forEach((value) -> {
				sb.append(value).append("','");
			});
			sb.replace(sb.lastIndexOf(",'"), sb.length(), "").append(")");
			System.out.println("Query String is: " + sb);
			PreparedStatement preparedStatement = connection.prepareStatement(sb.toString());

			ResultSet resultSet = preparedStatement.executeQuery();
			Article article;
			while (resultSet.next()) {
				article = new Article();
				article.setYear(resultSet.getInt(1));
				article.setTitle(resultSet.getString(2));
				articles.add(article);
			}				
		}
		return articles;
	}
	
	public static void main(String argp[]){
		
		ArticleDAO ob = new ArticleDAO();
		try {
			Set<String> set = new HashSet<String>();
			set.add("Parallel Integer Sorting and Simulation Amongst CRCW Models");
			Set<Article> bo2 = ob.findByAttribute("title", set, 100);
			for(Article item : bo2){
				System.out.println(item.getTitle());
				System.out.println(item.getYear());
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally{
			MariaDBDaoFactory.getInstance().closeConnection();
		}
				
	}

}
