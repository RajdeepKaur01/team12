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

	private static DAOFactory daoFactory = MariaDBDaoFactory.getInstance();
	private static final Connection connection = daoFactory.getConnection();
	
	@Override
	public Journal findById(int id) throws SQLException {
		PreparedStatement preparedStatement = connection.prepareStatement("select * from bibliography.journals where ID = ?");
		preparedStatement.setInt(1, id);
		ResultSet resultSet = preparedStatement.executeQuery();
		List<Article> articles = new ArrayList<>();
		Journal journal = new Journal();
		while (resultSet.next()) {		
			journal.setName(resultSet.getString(8));
			journal.setVolume(resultSet.getString(5));
			ArticleDAO article = new ArticleDAO();
			Set<String> set = new HashSet<String>();
			set.add(Integer.toString(resultSet.getInt(1)));
			articles= article.findByAttribute("_key", set, 100);
			journal.setArticles(articles);
		}

		return journal;
	}

	@Override
	public List<Journal> findByAttributes(Map<String, String> attributeNamesAndValues, int limit) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Journal> findByAttribute(String attributeName, Set<String>attributeValue, int limit) throws SQLException {
		List<Journal> journals = new ArrayList<>();
		attributeValue.forEach((value) -> {
			PreparedStatement preparedStatement;
			try {
				preparedStatement = connection.prepareStatement("select * from bibliography.journals where " + attributeName + " LIKE ? LIMIT " + limit);
				preparedStatement.setString(1, "%" + value + "%");
				ResultSet resultSet = preparedStatement.executeQuery();
				while (resultSet.next()) {
					Journal journal = new Journal();
					journal.setName(resultSet.getString(8));
					journal.setVolume(resultSet.getString(5));
					ArticleDAO article = new ArticleDAO();
					List<Article> articles = new ArrayList<>();
					Set<String> set = new HashSet<String>();
					set.add(Integer.toString(resultSet.getInt(1)));
					articles= article.findByAttribute("_key", set, limit);
					journal.setArticles(articles);
					journals.add(journal);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		return journals;
	}
	public static void main(String argp[]){
		
		JournalDAO ob = new JournalDAO();
		try {
			Journal bo = ob.findById(1);
			System.out.println(bo.getName());
			System.out.println(bo.getVolume());
			Set<String> set = new HashSet<String>();
			set.add("Acta");
			List<Journal> bo2 = ob.findByAttribute("journal", set, 4);
			for(Journal item : bo2){
				System.out.println(item.getName());
				System.out.println(item.getVolume());
				for(Article item2: item.getArticles()){
					System.out.println(item2.getTitle());
					System.out.println(item2.getYear());
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			MariaDBDaoFactory.getInstance().closeConnection();
		}
		
	}

}
