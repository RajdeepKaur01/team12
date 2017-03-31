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
import main.java.entities.AuthorInfo;
import main.java.queryengine.DAOFactory;
import main.java.queryengine.MariaDBDaoFactory;

public class AuthorInfoDAO implements DAO<AuthorInfo> {

	private static final String NAME = "name";
	private static final String URLTYPE = "urltype";
	private static final String HOMEPAGE = "Home Page";
	private static DAOFactory daoFactory = MariaDBDaoFactory.getInstance();
	private static final Connection connection = daoFactory.getConnection();
	private PreparedStatement preparedStatement;
	private String regex = "";

	@Override
	public Set<AuthorInfo> findByAttributes(Map<String, String> attributeNamesAndValues, int limit) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<AuthorInfo> findByAttribute(String attributeName, Set<String> attributeValues, int limit)
			throws SQLException {
		String value = "";
		for (String v : attributeValues)
			value = v;
		value = "'" + value + "'";
		if (attributeName.equals(NAME)) {
			int c = 0;
			preparedStatement = connection
					.prepareStatement("select url,authors from bibliography.authorinfo where find_in_set(" + value
							+ ", authors) AND " + URLTYPE + " LIKE ? LIMIT " + limit);
			preparedStatement.setString(1, regex + HOMEPAGE + regex);
			ResultSet resultSet = preparedStatement.executeQuery();
			Set<AuthorInfo> authorsInfoSet = new HashSet<>();
			while (resultSet.next()) {
				AuthorInfo authorInfo = new AuthorInfo();
/*				// TODO HASHCODE ERROR FIX
				authorInfo.setName("S" + c++);*/
				authorInfo.setAliases(resultSet.getString(2).split(","));
				try {
					if (resultSet.getString(1).length() < 1) {
						authorInfo.setHomePageURL(null);
					} else {
						authorInfo.setHomePageURL(new URL(resultSet.getString(1)));
					}
				} catch (MalformedURLException e) {
					e.printStackTrace();
				}
				authorsInfoSet.add(authorInfo);
			}
			return authorsInfoSet;
		}
		return null;

	}

	@Override
	public Set<AuthorInfo> findByKeys(Set<String> keys) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

}
