package main.java.queryengine;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import main.java.entities.Article;
import main.java.entities.Author;

public class ArticleDAO implements DAO<Article>{
	private static DAOFactory daoFactory = MariaDBDaoFactory.getInstance();
	private static final Connection connection = daoFactory.getConnection();
	private PreparedStatement preparedStatement;
	
	@Override
	public Article findById(int id) throws SQLException {
		preparedStatement = connection.prepareStatement("select * from bibliography.journals where ID = ?");
		preparedStatement.setInt(1, id);
		ResultSet resultSet = preparedStatement.executeQuery();
		List<Author> authors = new ArrayList<>();
		Article article = new Article();
		while (resultSet.next()) {		
			article.setYear(resultSet.getInt(6));
			article.setTitle(resultSet.getString(4));
			//AuthorDAO author = new AuthorDAO();
			//authors= author.findByAttribute("_key",Integer.toString(resultSet.getInt(1)) , 100);
			//article.setAuthor(authors);
		}

		return article;
	}

	@Override
	public List<Article> findByAttributes(Map<String, String> attributeNamesAndValues, int limit) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Article> findByAttribute(String attributeName, String attributeValue, int limit) throws SQLException {
		if(attributeValue.equals("year")){
			preparedStatement = connection.prepareStatement("select year,title from bibliography.journals where " + attributeName + " = ? LIMIT " + limit);
		} else{
			preparedStatement = connection.prepareStatement("select year,title from bibliography.journals where " + attributeName + " LIKE ? LIMIT " + limit);			
		}
		preparedStatement.setString(1, "%" + attributeValue + "%");
		ResultSet resultSet = preparedStatement.executeQuery();
		List<Article> articles = new ArrayList<>();
		while (resultSet.next()) {
			Article article = new Article();
			article.setYear(resultSet.getInt(1));
			article.setTitle(resultSet.getString(2));
			//AuthorDAO author = new AuthorDAO();
			//List<Author> authors = new ArrayList<>();
			//authors= author.findByAttribute("_key",Integer.toString(resultSet.getInt(1)) , 100);
			//article.setAuthor(authors);
			articles.add(article);
		}
		return articles;
	}
	
	public static void main(String argp[]){
		
		ArticleDAO ob = new ArticleDAO();
		try {
			Article bo = ob.findById(1);
			System.out.println(bo.getTitle());
			System.out.println(bo.getYear());
			List<Article> bo2 = ob.findByAttribute("title", "Parallel Integer Sorting and Simulation Amongst CRCW Models", 100);
			for(Article item : bo2){
				System.out.println(item.getTitle());
				System.out.println(item.getYear());
			}
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
