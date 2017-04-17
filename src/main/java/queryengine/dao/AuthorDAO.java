package main.java.queryengine.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import main.java.entities.Article;
import main.java.entities.Author;
import main.java.entities.InProceeding;
import main.java.queryengine.DAOFactory;
import main.java.queryengine.MariaDBDaoFactory;

public class AuthorDAO implements DAO<Author> {

	//Class level variables to hold DB factory instance and connection instance
	private static DAOFactory daoFactory = MariaDBDaoFactory.getInstance();
	private static final Connection connection = daoFactory.getConnection();
	
	//Map that stores mapping between position acronym and position name
	private static final Map<String, String> committeeAcronymMap = new HashMap<>();

	//References to conference DAO and journal DAO
	private static final DAO<InProceeding> inproceedingDAO;
	private static final DAO<Article> articleDao;
	
	//Static member Initialization
	static {
		inproceedingDAO = daoFactory.getInProceedingsDAO();
		articleDao = daoFactory.getArticleDAO();

		committeeAcronymMap.put("P", "Program Chair");
		committeeAcronymMap.put("G", "General Chair");
		committeeAcronymMap.put("C", "Conference Chair");
		committeeAcronymMap.put("E", "External Review Committee");
	}

	@Override
	/**
	 * Find author record(s) having the given attribute(s)
	 * E.g. Name, Title, Conference Name etc.
	 */
	public Set<Author> findByAttribute(String attributeName, Set<String> attributeValues) throws SQLException {
		StringBuilder sb = new StringBuilder();
		//Build the query string using the attribute name and values provided
		//Order by naem to identify same author with multiple research paper publications
		sb.append("select * from bibliography.author where ").append(attributeName).append(" LIKE '%");
		attributeValues.forEach((value) -> {
			sb.append(value).append("%','%");
		});
		sb.replace(sb.lastIndexOf(",'%"), sb.length(), "").append(" ORDER BY name");
		
		//Prepared Statement to execute query
		PreparedStatement preparedStatement = connection.prepareStatement(sb.toString());

		//Execute query and fetch results
		ResultSet resultSet = preparedStatement.executeQuery();
		Set<Author> authorSet = new HashSet<>();
		String name = "";
		Author author = null;
		
		//Iterate over the results and create Author object(s)
		while (resultSet.next()) {
			String localName = resultSet.getString(3);
			
			//Name is same, increase count of research paper and add research paper to the author object
			if (localName.equals(name)) {
				author.addToPaperSet(resultSet.getString(2));
				continue;
			} else {
				//Difference author found, create new object
				author = new Author();
				author.addToPaperSet(resultSet.getString(2));
				name = localName;
				authorSet.add(populateAuthorData(author, resultSet));
			}
		}

		return authorSet;
	}

	//Populate an author object from the raw SQL data
	private Author populateAuthorData(Author author, ResultSet resultSet) throws SQLException {
		author.setName(resultSet.getString(3));
		String conferenceName = resultSet.getString(4);
		Map<String, Set<String>> map = new HashMap<>();
		author.setCommitteeMemberInfo(map);
		
		//Check if author has held a position with a conference and add the info to the object
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

	/*
	 * Join author table with conference papers table and journal publications table
	 * To find all the papers published by the given author
	 */
	@Override
	public Author join(Author author) throws SQLException {
		Set<String> articleKeys = new HashSet<>(), confKeys = new HashSet<>();

		//Segregate journal publications and conference publications
		author.getPaperKeys().forEach((key) -> {
			if (key.contains("journals")) {
				articleKeys.add(key);
			} else {
				confKeys.add(key);
			}
		});
		
		//Find conference papers and journal publications, store in the author object
		author.setInProceedings(inproceedingDAO.findByKeys(confKeys));
		author.setArticles(articleDao.findByKeys(articleKeys));

		return author;
	}

	/**
	 * Find author by research paper keys
	 */
	@Override
	public Set<Author> findByKeys(Set<String> keys) throws SQLException {
		Set<Author> authorSet = new HashSet<>();
		//If set of keys is not empty
		if (keys != null && !keys.isEmpty()) {
			StringBuilder sb = new StringBuilder();
			//Build the query string
			sb.append("select * from bibliography.author where _key").append(" in ('");
			keys.forEach((value) -> {
				sb.append(value).append("','");
			});
			sb.replace(sb.lastIndexOf(",'"), sb.length(), "").append(")").append(" ORDER BY name");
			PreparedStatement preparedStatement = connection.prepareStatement(sb.toString());

			//Execute query
			ResultSet resultSet = preparedStatement.executeQuery();

			String name = "";
			Author author = new Author();

			//Retrieve data and populate set of author objects
			while (resultSet.next()) {
				String localName = resultSet.getString(3);
				if (localName.equals(name)) {
					author.addToPaperSet(resultSet.getString(2));
					continue;
				} else {
					author = new Author();
					author.addToPaperSet(resultSet.getString(2));
					name = localName;
					authorSet.add(populateAuthorData(author, resultSet));
				}
			}
		}
		return authorSet;
	}

	/**
	 * Find authors who have profiles similar to the given profile
	 */
	@Override
	public Set<Author> findAuthorsWithSimilarProfile(Author author) throws SQLException {
		
		//Check conference name and count of research papers as similarity criteria
		StringBuilder query = new StringBuilder(
				"SELECT * FROM bibliography.author where conferenceName is not null and conferenceName in (");
		author.getCommitteeMemberInfo().keySet().forEach(confName -> query.append("'").append(confName).append("', "));
		query.replace(query.lastIndexOf(","), query.length(), "").append(") group by name having count(name) >=?");

		PreparedStatement preparedStatement = connection.prepareStatement(query.toString());
		int count = preparedStatement.getParameterMetaData().getParameterCount();
		preparedStatement.setInt(1, author.getPaperKeys().size());
		ResultSet resultSet = preparedStatement.executeQuery();
		Set<Author> authorSet = new HashSet<>();

		//Execute query and create author objects
		while (resultSet.next()) {
			author = new Author();
			author.addToPaperSet(resultSet.getString(2));
			authorSet.add(populateAuthorData(author, resultSet));
		}

		return authorSet;
	}
}