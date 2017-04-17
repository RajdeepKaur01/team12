package main.java.queryengine.dao;

import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import main.java.entities.AuthorInfo;
import main.java.queryengine.DAOFactory;
import main.java.queryengine.MariaDBDaoFactory;

public class AuthorInfoDAO implements DAO<AuthorInfo> {

	//Constants
	private static final String NAME = "name";
	private static final String URLTYPE = "urltype";
	private static final String HOMEPAGE = "Home Page";
	
	//Static members to store DB factory instance and DB connection instance
	private static DAOFactory daoFactory = MariaDBDaoFactory.getInstance();
	private static final Connection connection = daoFactory.getConnection();
	
	//Reuse this prepared statement throughout the class
	private PreparedStatement preparedStatement;
	
	private String regex = "";

	/**
	 * Find author alias and home page URL using the attribute provided
	 * Most common attribute is name of the author
	 */
	@Override
	public Set<AuthorInfo> findByAttribute(String attributeName, Set<String> attributeValues) throws SQLException {
		String value = "";
		for (String v : attributeValues)
			value = v;
		value = "'" + value + "'";
		
		//If searching by auhtor name, query is "like <name>"
		if (attributeName.equals(NAME)) {
			int c = 0;
			//Create the query
			preparedStatement = connection
					.prepareStatement("select url,authors from bibliography.authorinfo where find_in_set(" + value
							+ ", authors) AND " + URLTYPE + " LIKE ?");
			preparedStatement.setString(1, regex + HOMEPAGE + regex);
			//Execute query
			ResultSet resultSet = preparedStatement.executeQuery();
			Set<AuthorInfo> authorsInfoSet = new HashSet<>();
			
			//Iterate over the results
			while (resultSet.next()) {
				AuthorInfo authorInfo = new AuthorInfo();
				//Retrieve all alises and add to the object
				authorInfo.setAliases(resultSet.getString(2).split(","));
				try {
					//If the value is an empty string
					if (resultSet.getString(1).isEmpty()) {
						authorInfo.setHomePageURL(null);
					} else {
						//Found a home page URL, set it in the obejct
						authorInfo.setHomePageURL(new URL(resultSet.getString(1)));
					}
				} catch (MalformedURLException e) {
					e.printStackTrace();
				}
				authorsInfoSet.add(authorInfo);
			}
			return authorsInfoSet;
		}
		//No other attribute to query by, return null
		return null;

	}

}
