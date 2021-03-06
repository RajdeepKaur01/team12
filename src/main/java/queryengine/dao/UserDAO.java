package main.java.queryengine.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import main.java.entities.Author;
import main.java.entities.User;
import main.java.queryengine.DAOFactory;
import main.java.queryengine.MariaDBDaoFactory;;

public class UserDAO implements DAO<User> {

	private static DAOFactory daoFactory = MariaDBDaoFactory.getInstance();
	private static final Connection connection = daoFactory.getConnection();
	private static PreparedStatement preparedStatement;

	// find users in user table by user-name,password
	@Override
	public Set<User> findByAttributes(Map<String, String> attributeNamesAndValues) throws SQLException {
		StringBuilder query = new StringBuilder("select * from bibliography.user where ");
		Set<User> userSet = new HashSet<>();
		int c = 0;
		for (Map.Entry<String, String> entry : attributeNamesAndValues.entrySet()) {
			if (c == 0) {
				query.append(entry.getKey() + " = '" + entry.getValue() + "'");
			} else {
				query.append(" and " + entry.getKey() + " = '" + entry.getValue() + "'");
			}
			c++;
		}
		preparedStatement = connection.prepareStatement(query.toString());
		ResultSet resultSetUser = preparedStatement.executeQuery();
		while (resultSetUser.next()) {
			User userObj = new User();
			userObj.setUserId(resultSetUser.getInt(1));
			userObj.setName(resultSetUser.getString(2) + " " + resultSetUser.getString(3));
			userObj.setUsername(resultSetUser.getString(4));
			userObj.setPassword(resultSetUser.getString(5));
			userSet.add(userObj);
		}
		return userSet;
	}

	@Override
	public Set<User> findByKeys(Set<String> keys) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	// find set of authors for user ID
	public Set<Author> findAuthorsById(String attributeName, Set<String> attributeValues) throws SQLException {
		Set<Author> authors = new HashSet<>();
		attributeValues.forEach((value) -> {
			try {
				preparedStatement = connection
						.prepareStatement("select * from bibliography.usercommittee where " + attributeName + " = ?");
				preparedStatement.setString(1, value);
				ResultSet resultSet = preparedStatement.executeQuery();
				while (resultSet.next()) {
					Author author = new Author();
					Set<String> setPaperKeys = new HashSet<>(Arrays.asList(resultSet.getString(3).split(",")));
					author.setPaperKeys(setPaperKeys);
					author.setName(resultSet.getString(4));
					author.setNote(resultSet.getString(5));
					author.setPastExperienceYrs(resultSet.getInt(6));
					author.setNumberOfResearchPapers(resultSet.getInt(7));
					authors.add(author);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		});
		return authors;
	}

	// finds set of authors for a user ID
	public Set<String> findAuthorByKeys(Set<String> keys, int id) throws SQLException {
		Set<String> namesExisting = new HashSet<>();
		if (keys != null && !keys.isEmpty()) {
			StringBuilder sb = new StringBuilder();
			sb.append("select author_name from bibliography.usercommittee where user_id =" + "'" + id + "'"
					+ " and author_name").append(" in ('");
			keys.forEach((value) -> {
				sb.append(value).append("','");
			});
			sb.replace(sb.lastIndexOf(",'"), sb.length(), "").append(")").append(" ORDER BY author_name");
			preparedStatement = connection.prepareStatement(sb.toString());
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				String name = resultSet.getString(1);
				namesExisting.add(name);
			}
		}
		return namesExisting;
	}

	//// insert set of authors for user ID
	public boolean insertAuthorsbyId(int Id, Set<Author> attributeValues) throws SQLException {
		Set<String> names = new HashSet<>();

		for (Author author : attributeValues) {
			names.add(author.getName());
		}

		Set<String> authorsExisting = findAuthorByKeys(names, Id);
		connection.setAutoCommit(false);
		preparedStatement = connection.prepareStatement(
				"insert into bibliography.usercommittee(user_id,author_key,author_name,note_text,past_experience,paper_count) values (?,?,?,?,?,?)");

		for (Author author : attributeValues) {

			if (!authorsExisting.contains(author.getName())) {
				String keys = StringUtils.join(author.getPaperKeys(), ',');
				preparedStatement.setInt(1, Id);
				preparedStatement.setString(2, keys);
				preparedStatement.setString(3, author.getName());
				preparedStatement.setString(4, author.getNote());
				if (author.getCommitteeMemberInfo() != null) {
					preparedStatement.setInt(5, author.getCommitteeMemberInfo().size());
				} else {
					preparedStatement.setInt(5, 0);
				}
				if (author.getResearchPapers() != null) {
					preparedStatement.setInt(6, author.getResearchPapers().size());
				} else {
					preparedStatement.setInt(6, 0);
				}
				preparedStatement.addBatch();
			}
		}
		int result = preparedStatement.executeBatch().length;
		connection.commit();
		connection.setAutoCommit(true);
		return result > 0;
	}

	public boolean deleteAttribute(int ID, Author attributeValue) throws SQLException {
		preparedStatement = connection
				.prepareStatement("delete from bibliography.usercommittee where user_id=? and author_name=?");
		preparedStatement.setInt(1, ID);
		preparedStatement.setString(2, attributeValue.getName());
		return preparedStatement.executeUpdate() > 0;
	}

	public boolean updateAuthorNote(int ID, Author author) throws SQLException {
		String authorName = author.getName();
		preparedStatement = connection.prepareStatement(
				"update bibliography.usercommittee set note_text=? where user_id=? and author_name=?");

		preparedStatement.setString(1, author.getNote());
		preparedStatement.setInt(2, ID);
		preparedStatement.setString(3, authorName);

		return preparedStatement.executeUpdate() > 0;
	}

	@Override
	public Set<User> findByAttribute(String attirubteName, Set<String> attributeValues) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

}
