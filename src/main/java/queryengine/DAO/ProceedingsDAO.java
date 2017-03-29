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
	private static final Connection connection = daoFactory.getConnection();
	private PreparedStatement preparedStatement;
	
	@Override
	/* The findByID function searches for an entity based on its ID.
	The result set will be populated using the column no.s indicated from the table specified. */
	public Proceedings findById(int id) throws SQLException {
		preparedStatement = connection.prepareStatement("select * from bibliography.proceedings where ID = ?");
		preparedStatement.setInt(1, id);
		
		ResultSet resultSetProceedings = preparedStatement.executeQuery();
		Proceedings proceedings = new Proceedings();
		List<InProceeding> inProceedings = new ArrayList<>();
		
		while (resultSetProceedings.next()) {
		proceedings.setKey(resultSetProceedings.getString(2));
		proceedings.setTitle(resultSetProceedings.getString(4));
		proceedings.setYear(resultSetProceedings.getInt(6));
		proceedings.setEditors(Arrays.asList(resultSetProceedings.getString(10).split("\\s*,\\s*")));		
		proceedings.setVolume(resultSetProceedings.getString(7));
		proceedings.setSeries(resultSetProceedings.getString(8));
		proceedings.setPublisher(resultSetProceedings.getString(9));
		proceedings.setConfAcronym(resultSetProceedings.getString(5));

		Set<String> set = new HashSet<>();
		InProceedingsDAO inproc = new InProceedingsDAO();
		set.add(resultSetProceedings.getString(2));
		inProceedings = inproc.findByAttribute("crossref",set , 10);
		proceedings.setInProceedings(inProceedings);
		
		}
		return proceedings;
	}

	@Override
	public List<Proceedings> findByAttributes(Map<String, String> attributeNamesAndValues, int limit) {
		return null;
	}

	/* The findByAttribute function searches for an entity based on the attribute name, its value and the limit.
	 Limit defines the number of results being queried in the function.
	 The result set will be populated using the column no.s indicated from the table specified. */
	@Override
	public List<Proceedings> findByAttribute(String attributeName, Set<String> attributeValue, int limit) throws SQLException {
		String childAttributeName="_key";
		String value = "";
		
		for (String v: attributeValue) value = v;
		
		if(attributeName.equals("year") || attributeName.equals("_key")){
			regex="";
			preparedStatement = connection.prepareStatement("select * from bibliography.proceedings where " + attributeName + " = ? LIMIT " + limit);
		}else{
			preparedStatement = connection.prepareStatement("select * from bibliography.proceedings where " + attributeName + " LIKE ? LIMIT " + limit);
		}
		preparedStatement.setString(1, regex + value + regex);
		
		ResultSet resultSetProceedings = preparedStatement.executeQuery();
		List<Proceedings> proceedingsList = new ArrayList<>();
		
		
		while (resultSetProceedings.next()) {
			Proceedings proceedings = new Proceedings();
			proceedings.setKey(resultSetProceedings.getString(2));
			proceedings.setTitle(resultSetProceedings.getString(4));
			proceedings.setEditors(Arrays.asList(resultSetProceedings.getString(10).split("\\s*,\\s*")));
			proceedings.setVolume(resultSetProceedings.getString(7));
			proceedings.setSeries(resultSetProceedings.getString(8));
			proceedings.setPublisher(resultSetProceedings.getString(9));
			proceedings.setConferenceName(resultSetProceedings.getString(5));
			
			InProceedingsDAO inproc = new InProceedingsDAO();
			if(attributeName.equals("_key")){
				childAttributeName="crossref";
			}
			List<InProceeding> inProceedings = new ArrayList<>();
			inProceedings = inproc.findByAttribute(childAttributeName, attributeValue, 10);
			proceedings.setInProceedings(inProceedings);
			proceedingsList.add(proceedings);
		}
		return proceedingsList;
	}
	
	//main method added to test the above
	public static void main(String argp[]){
		
	   /* ProceedingsDAO obj = new ProceedingsDAO();
		try {
			Proceedings pro = obj.findById(1);
			//System.out.println(pro.getTitle());
			//System.out.println(pro.getVolume());
			Set<String> set = new HashSet<>();
			set.add("conf/er/2008");
			List<Proceedings> proList = obj.findByAttribute("_key", set , 5);
			for(Proceedings item : proList){
				System.out.println("proceeding:"+item.getTitle());
				for(InProceeding item2 : item.getInproceedings()){
					System.out.println("IP title:"+item2.getBookTitle());
				}
				}
		} 
		catch (SQLException e) {
		// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		finally {
			MariaDBDaoFactory.getInstance().closeConnection();
		} */

}
}
