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
	public Set<Journal> findByAttributes(Map<String, String> attributeNamesAndValues, int limit) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<Journal> findByAttribute(String attributeName, Set<String>attributeValue, int limit) throws SQLException {
		Set<Journal> journals = new HashSet<>();
		attributeValue.forEach((value) -> {
			PreparedStatement preparedStatement;
			try {
				if(attributeName.equals(YEAR)){
					regex="";
					preparedStatement = connection.prepareStatement("select * from bibliography.journals where " + attributeName + " = ? LIMIT " + limit);
				} else{
					preparedStatement = connection.prepareStatement("select * from bibliography.journals where " + attributeName + " LIKE ? LIMIT " + limit);
				}
				preparedStatement.setString(1, regex + value + regex);
				ResultSet resultSet = preparedStatement.executeQuery();
				while (resultSet.next()) {
					Journal journal = new Journal();
					journal.setKey(resultSet.getString(2));
					journal.setYear(resultSet.getInt(6));
					journal.setName(resultSet.getString(8));
					journal.setVolume(resultSet.getString(5));

					//TODO: DECIDE IF WE NEED IT
					/*ArticleDAO article = new ArticleDAO();
					Set<Article> articles = new ArrayList<>();
					Set<String> set = new HashSet<String>();
					set.add(Integer.toString(resultSet.getInt(1)));
					articles= article.findByAttribute("_key", set, limit);
					journal.setArticles(articles);*/
					journals.add(journal);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		});
		return journals;
	}
	public static void main(String argp[]){
		
		JournalDAO ob = new JournalDAO();
		try {
			Set<String> set = new HashSet<String>();
			set.add("Acta");
			System.out.println("Test 2 Search by Attribute Journal");
			Set<Journal> bo2 = ob.findByAttribute("journal", set, 4);
			for(Journal item : bo2){
				System.out.println(item.getName());
				System.out.println(item.getVolume());
			}
			System.out.println("Test 3 Search by Year ");
			Set<String> set2 = new HashSet<String>();
			set2.add("2001");
			Set<Journal> bo3 = ob.findByAttribute("year", set2, 4);
			for(Journal item : bo3){
				System.out.println(item.getKey());
				System.out.println(item.getYear());
				System.out.println(item.getName());
				System.out.println(item.getVolume());
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			MariaDBDaoFactory.getInstance().closeConnection();
		}
		
	}

	@Override
	public Set<Journal> findByKeys(Set<String> keys) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

}
