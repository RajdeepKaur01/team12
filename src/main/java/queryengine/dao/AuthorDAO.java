package main.java.queryengine.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import main.java.entities.Author;
import main.java.queryengine.DAOFactory;
import main.java.queryengine.MariaDBDaoFactory;

public class AuthorDAO implements DAO<Author> {
	
	private static DAOFactory daoFactory = MariaDBDaoFactory.getInstance();
	private static final Connection connection = daoFactory.getConnection();
	private static final Map<String ,String> committeeAcronymMap = new HashMap<>();
	
	static {
		
		committeeAcronymMap.put("P", "Program Chair");
		committeeAcronymMap.put("G", "General Chair");
		committeeAcronymMap.put("C", "Conference Chair");
		committeeAcronymMap.put("E", "External Review Committee");
	}
	
	@Override
	public Author findById(int id) throws SQLException {
		PreparedStatement preparedStatement = connection.prepareStatement("select * from bibliography.author where ID = ?");
		preparedStatement.setInt(1, id);
		ResultSet resultSet = preparedStatement.executeQuery();
		return null;
	}

	@Override
	public List<Author> findByAttributes(Map<String, String> attributeNamesAndValues, int limit) throws SQLException {
		StringBuilder paramQueryString = new StringBuilder();
		
		attributeNamesAndValues.keySet().forEach((key) -> {
			paramQueryString.append(key).append(" = ").append(attributeNamesAndValues.get(key)).append(" and ");
		});
		
		int lastAndIndex = paramQueryString.lastIndexOf(" and ");
		paramQueryString.replace(lastAndIndex, paramQueryString.length(), "");
		Statement statement = connection.createStatement();
		List<Author> authors = new ArrayList<>();
		ResultSet resultSet = statement.executeQuery ("select * from bibliography.author where "+paramQueryString);
		while (resultSet.next()) { 
			Author author = populateAuthorData(resultSet);
			authors.add(author);
		}
		return coalesceSameNameData(authors);
	}

	@Override
	public List<Author> findByAttribute(String attributeName, Set<String> attributeValues, int limit) throws SQLException {
		StringBuilder sb = new StringBuilder();
		sb.append("select * from bibliography.author where ").append(attributeName)
		.append(" in ('");
		
		attributeValues.forEach((value) -> {
			sb.append(value).append("','");
		});
		sb.replace(sb.lastIndexOf(",'"), sb.length(), "").append(")");
		System.out.println(sb);
		PreparedStatement preparedStatement = 
				connection.prepareStatement(sb.toString());
		
		ResultSet resultSet = preparedStatement.executeQuery();
		System.out.println("query executed");
		System.out.println(resultSet.getFetchSize());
		List<Author> authors = new ArrayList<>();
		while (resultSet.next()) {
			Author author = populateAuthorData(resultSet);
			authors.add(author);
;		}
		return coalesceSameNameData(authors);
	}
	
	private Author populateAuthorData(ResultSet resultSet) throws SQLException {
		Author author = new Author ();
		author.setName(resultSet.getString(3));
		String conferenceName = resultSet.getString(4);
		if(conferenceName != null && !conferenceName.isEmpty()) {
			Map <String, Set<String>> map = new HashMap<>();
			int year = resultSet.getInt(5);
			String title = resultSet.getString(6);
			Set<String> list = new HashSet<>();
			list.add("Role:"+committeeAcronymMap.get(title) + ", Year:"+year);
			map.put(conferenceName, list);
			author.setCommitteeMemberInfo(map);
		}else{
			Map <String, Set<String>> map = new HashMap<>();
			author.setCommitteeMemberInfo(map);
		}
		return author;
	}
	
	private List<Author> coalesceSameNameData (List<Author> authors) throws SQLException {
		
		List<Author> dedupList = new ArrayList<>();
		Map<String, Author> dedupMap = new HashMap<>();
		authors.forEach((author) -> {
			if (dedupMap.containsKey(author.getName())) {
				
				Author entry = dedupList.get(dedupList.indexOf(author));
				
				Map <String, Set<String>> map1 = entry.getCommitteeMemberInfo();
				Map <String, Set<String>> map2 = author.getCommitteeMemberInfo();
				Map <String, Set<String>> map3 = new HashMap<>(map1);
				
				for (Map.Entry<String, Set<String>> e: map2.entrySet()) {
					map3.merge(e.getKey(), e.getValue(), (value1, value2) -> {
						value1.addAll(value2);
						return value1;
					});
				}
				dedupList.remove(entry);
				entry.setCommitteeMemberInfo(map3);
				dedupList.add (entry);
				
			} else {
				try {
					Statement statement = connection.createStatement();
					ResultSet rs = statement.executeQuery("select count(_key) from bibliography.author where name = '" + author.getName()+"'");
					rs.next();
					int count = rs.getInt(1);
					author.setNumberOfResearchPapers(count);
				}catch(SQLException e) { e.printStackTrace();}
				dedupList.add(author);
				dedupMap.put(author.getName(), author);
			}
		});
		
		return dedupList;
	}
	
	/* (non-Javadoc)
	 * @see main.java.queryengine.dao.DAO#join(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public List<Author> join(String tableName, String attributeName, String attributeValue) {
		// TODO Auto-generated method stub
		
		return null;
	}

	public static void main (String[] args) throws SQLException {
		AuthorDAO dao = new AuthorDAO();
		Set<String> names = new HashSet<>();
		names.add("Gert Smolka");
		names.add("Petra Ludewig");
		List<Author> authors = dao.findByAttribute("name", names, 100);
		System.out.println(authors);
		connection.close();
	}
}
