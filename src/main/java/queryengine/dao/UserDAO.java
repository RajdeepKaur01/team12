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

public class UserDAO implements DAO<User>{

	private static DAOFactory daoFactory = MariaDBDaoFactory.getInstance();
	private static final Connection connection = daoFactory.getConnection();
	private PreparedStatement preparedStatement;
	

	public Set<Author> findAuthorById(String attributeName, Set<String> attributeValues) throws SQLException {
		Set<Author> authors = new HashSet<>();
		attributeValues.forEach((value) -> {
			PreparedStatement preparedStatement;
			try {
					preparedStatement = connection.prepareStatement("select * from bibliography.usercommittee where " + attributeName + " = ?");
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

	public boolean insertAuthorsbyId(String Id, Set<Author> attributeValues) throws SQLException {
		int userID = Integer.parseInt(Id);
		Set<String> names = new HashSet<>();
		for(Author author: attributeValues){
			names.add(author.getName());
		}
		Set<Author> authors = findAuthorByKeys(names);

		if(authors.size()>0){
			preparedStatement = connection.prepareStatement(
					"insert into usercommittee(user_id,author_key,author_name,note_text,past_experience,paper_count) values (?,?,?,?,?,?)");
			for(Author author: attributeValues){
				String keys = StringUtils.join(author.getPaperKeys(), ',');
				preparedStatement.setInt(1, userID);
				preparedStatement.setString(2, keys);
				preparedStatement.setString(3, author.getName());
				preparedStatement.setString(4, author.getNote());
				preparedStatement.setInt(6, author.getCommitteeMemberInfo().size());
				preparedStatement.setInt(7, author.getResearchPapers().size());
				preparedStatement.executeQuery();
			}
			return true;
		}
		return false;
		}
	public boolean deleteAttribute(String ID, Author attributeValue) throws SQLException {
		
		return false;
	}

	public Set<Author> findAuthorByKeys(Set<String> keys) throws SQLException {
		Set<Author> authorSet = new HashSet<>();
		if (keys != null && !keys.isEmpty()) {
			StringBuilder sb = new StringBuilder();
			sb.append("select * from bibliography.usercommittee where author_name").append(" in ('");
			keys.forEach((value) -> {
				sb.append(value).append("','");
			});
			sb.replace(sb.lastIndexOf(",'"), sb.length(), "").append(")").append(" ORDER BY name");
			PreparedStatement preparedStatement = connection.prepareStatement(sb.toString());

			ResultSet resultSet = preparedStatement.executeQuery();

			String name = "";
			Author author = new Author();
			while (resultSet.next()) {
					author = new Author();
				  
				}
			}
		return authorSet;
	}
	
	@Override
	public Set<User> findByAttributes(Map<String, String> attributeNamesAndValues) throws SQLException {
		StringBuilder query = new StringBuilder("select * from bibliography.user where ");
		Set<User> userSet = new HashSet<>();
		int c=0;
		for (Map.Entry<String, String> entry : attributeNamesAndValues.entrySet()) {
			if(c==0){
				query.append(entry.getKey()+" = '"+entry.getValue()+"'");
			} else{
				query.append(" and "+entry.getKey()+" = '"+entry.getValue()+"'");
			}
			c++;
		}
		preparedStatement = connection.prepareStatement(query.toString());
		ResultSet resultSetUser = preparedStatement.executeQuery();
		while (resultSetUser.next()) {
			User userObj = new User();
			userObj.setUserId(resultSetUser.getInt(1));
			userObj.setName(resultSetUser.getString(2) + " "+ resultSetUser.getString(3));
			userObj.setUsername	(resultSetUser.getString(4));
			userObj.setPassword(resultSetUser.getString(5));
			userObj.setEmailAdress(resultSetUser.getString(7));
			userSet.add(userObj);
		}
		return userSet;
	}
	
	@Override
	public Set<User> findByKeys(Set<String> keys) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}
	
	public static void main(String argp[]){
		/*UserDAO b = new UserDAO();
		Map<String, String> data = new HashMap<String,String>();
		data.put("username", "franktip");
		data.put("password", "franktip");
		try {
			
			for(User a:b.findByAttributes(data)){
				System.out.println(a.getPassword());
				System.out.println(a.getUsername());
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
		UserDAO b = new UserDAO();
		//b.findAuthorByKeys(keys)
	}

	@Override
	public Set<User> findByAttribute(String attirubteName, Set<String> attributeValues) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

}
