package main.java.queryengine.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import main.java.entities.Inproceeding;
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
		List<Inproceeding> inproceedings = new ArrayList<>();
		
		while (resultSetProceedings.next()) {
		proceedings.setTitle(resultSetProceedings.getString(4));
		proceedings.setYear(resultSetProceedings.getInt(6));
		proceedings.setEditors(Arrays.asList(resultSetProceedings.getString(10).split("\\s*,\\s*")));		
		proceedings.setVolume(resultSetProceedings.getString(7));
		proceedings.setSeries(resultSetProceedings.getString(8));
		proceedings.setPublisher(resultSetProceedings.getString(9));
		proceedings.setConfAcronym(resultSetProceedings.getString(5));
		
		InproceedingsDAO inproc = new InproceedingsDAO();
		inproceedings = inproc.findByAttribute("crossref",resultSetProceedings.getString(2) , 10);
		proceedings.setInproceedings(inproceedings);
		
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
	public List<Proceedings> findByAttribute(String attributeName, String attributeValue, int limit) throws SQLException {
		String childAttributeName="_key";
		if(attributeName.equals("year") || attributeName.equals("_key")){
			regex="";
			preparedStatement = connection.prepareStatement("select * from bibliography.proceedings where " + attributeName + " = ? LIMIT " + limit);
		}else{
			preparedStatement = connection.prepareStatement("select * from bibliography.proceedings where " + attributeName + " LIKE ? LIMIT " + limit);
		}
		preparedStatement.setString(1, regex + attributeValue + regex);
		
		ResultSet resultSetProceedings = preparedStatement.executeQuery();
		List<Proceedings> proceedingsList = new ArrayList<>();
		
		
		while (resultSetProceedings.next()) {
			Proceedings proceedings = new Proceedings();
			proceedings.setTitle(resultSetProceedings.getString(4));
			proceedings.setEditors(Arrays.asList(resultSetProceedings.getString(10).split("\\s*,\\s*")));
			proceedings.setVolume(resultSetProceedings.getString(7));
			proceedings.setSeries(resultSetProceedings.getString(8));
			proceedings.setPublisher(resultSetProceedings.getString(9));
			proceedings.setConferenceName(resultSetProceedings.getString(5));
			
			InproceedingsDAO inproc = new InproceedingsDAO();
			if(attributeName.equals("_key")){
				childAttributeName="crossref";
			}
			List<Inproceeding> inproceedings = new ArrayList<>();
			inproceedings = inproc.findByAttribute(childAttributeName, attributeValue, 10);
			proceedings.setInproceedings(inproceedings);
			proceedingsList.add(proceedings);
		}
		return proceedingsList;
	}
	
	//main method added to test the above
	public static void main(String argp[]){
		
		ProceedingsDAO obj = new ProceedingsDAO();
		try {
			Proceedings pro = obj.findById(1);
			//System.out.println(pro.getTitle());
			//System.out.println(pro.getVolume());
			List<Proceedings> proList = obj.findByAttribute("_key", "conf/er/2008", 5);
			for(Proceedings item : proList){
				System.out.println("proceeding:"+item.getTitle());
				for(Inproceeding item2 : item.getInproceedings()){
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
		}

}
}
