package main.java.queryengine.dao;

import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import main.java.entities.Article;
import main.java.entities.Author;
import main.java.queryengine.DAOFactory;
import main.java.queryengine.MariaDBDaoFactory;

public class AuthorInfoDAO implements DAO<Author> {

	private static final String NAME = "name";
	private static final String URLTYPE = "urltype";
	private static final String HOMEPAGE = "Home Page";
	private static DAOFactory daoFactory = MariaDBDaoFactory.getInstance();
	private static final Connection connection = daoFactory.getConnection();
	private PreparedStatement preparedStatement;
	private String regex = "";

	@Override
	public Author findById(int id) throws SQLException {
		PreparedStatement preparedStatement = connection
				.prepareStatement("select * from bibliography.authorinfo where ID = ?");
		preparedStatement.setInt(1, id);
		ResultSet resultSet = preparedStatement.executeQuery();
		return null;
	}

	@Override
	public Set<Author> findByAttributes(Map<String, String> attributeNamesAndValues, int limit) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<Author> findByAttribute(String attributeName, Set<String> attributeValues, int limit)
			throws SQLException {
		String value = "";
		for (String v : attributeValues)
			value = v;
		value = "'" + value + "'";
		if (attributeName.equals(NAME)) {
			int c=0;
			preparedStatement = connection
					.prepareStatement("select url,authors from bibliography.authorinfo where find_in_set(" + value
							+ ", authors) AND " + URLTYPE + " LIKE ? LIMIT " + limit);
			preparedStatement.setString(1, regex + HOMEPAGE + regex);
			ResultSet resultSet = preparedStatement.executeQuery();
			Set<Author> authorsInfoSet = new HashSet<>();
			System.out.println("size" + resultSet.getFetchSize());
			while (resultSet.next()) {
				System.out.println(resultSet.getString(2).split(",")[0]);
				System.out.println(resultSet.getString(1));
				Author author = new Author();
				//TODO HASHCODE ERROR FIX
				author.setName("S" + c++);
				author.setAliases(resultSet.getString(2).split(","));
				try {
					if(resultSet.getString(1).length()<1){
						author.setHomePageURL(null);
					} else{
						author.setHomePageURL(new URL(resultSet.getString(1)));
					}
				} catch (MalformedURLException e) {
					e.printStackTrace();
				}
				authorsInfoSet.add(author);
			}
			System.out.println("status" + authorsInfoSet.isEmpty());
			return authorsInfoSet;
		}
		return null;

	}

}
