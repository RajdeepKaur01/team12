package main.java.queryengine.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import main.java.entities.InProceeding;
import main.java.entities.Proceedings;
import main.java.queryengine.DAOFactory;
import main.java.queryengine.MariaDBDaoFactory;

// Data access object for the Proceedings class
public class ProceedingsDAO implements DAO<Proceedings> {
	
	private static String regex ="%";
	private static DAOFactory daoFactory = MariaDBDaoFactory.getInstance();
	private static DAO<InProceeding> inproceedingDAO = daoFactory.getInProceedingsDAO();
	private static final Connection connection = daoFactory.getConnection();
	private PreparedStatement preparedStatement;
	
	/* The findByAttribute function searches for an entity based on the attribute name, its value and the limit.
	 Limit defines the number of results being queried in the function.
	 The result set will be populated using the column no.s indicated from the table specified. */
	@Override
	public Set<Proceedings> findByAttribute(String attributeName, Set<String> attributeValue) throws SQLException {
		String childAttributeName="crossref";
		String value = "";
		for (String v: attributeValue) value = v;
		if(attributeName.equals("year") || attributeName.equals("_key")){
			regex="";
			preparedStatement = connection.prepareStatement("select * from bibliography.proceedings where " + attributeName + " = ?");
		}else{
			preparedStatement = connection.prepareStatement("select * from bibliography.proceedings where " + attributeName + " LIKE ?");
		}
		preparedStatement.setString(1, regex + value + regex);
		
		ResultSet resultSetProceedings = preparedStatement.executeQuery();
		Set<Proceedings> proceedingsSet = new HashSet<>();
			
		while (resultSetProceedings.next()) {
			Proceedings proceedings = new Proceedings();
			proceedings.setKey(resultSetProceedings.getString(2));
			proceedings.setTitle(resultSetProceedings.getString(4));
			proceedings.setEditors(Arrays.asList(resultSetProceedings.getString(10).split("\\s*,\\s*")));
			proceedings.setYear(resultSetProceedings.getInt(6));
			proceedings.setVolume(resultSetProceedings.getString(7));
			proceedings.setSeries(resultSetProceedings.getString(8));
			proceedings.setPublisher(resultSetProceedings.getString(9));
			proceedings.setConferenceName(resultSetProceedings.getString(5));
			Set<String> inProceedingCrossRefs = new HashSet<>();
			inProceedingCrossRefs.add(proceedings.getKey());
			Set<InProceeding> inProceedings = inproceedingDAO.findByAttribute(childAttributeName, inProceedingCrossRefs);
			proceedings.setInProceedings(inProceedings);
			proceedingsSet.add(proceedings);
		}
		return proceedingsSet;
	}
	
	@Override
	public Set<Proceedings> findByKeys(Set<String> keys) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}
}