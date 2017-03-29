package main.java.queryengine.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import main.java.entities.InProceeding;
import main.java.entities.Proceedings;
import main.java.queryengine.DAOFactory;
import main.java.queryengine.MariaDBDaoFactory;

public class InProceedingsDAO implements DAO<InProceeding>{

	private static String regex ="%";
	private static DAOFactory daoFactory = MariaDBDaoFactory.getInstance();
	private static final Connection connection = daoFactory.getConnection();
	@Override
	public InProceeding findById(int id) throws SQLException {
		PreparedStatement preparedStatement = connection.prepareStatement("select * from bibliography.inproceedings where ID = ?");
		preparedStatement.setInt(1, id);
		ResultSet resultSet = preparedStatement.executeQuery();
		InProceeding inproceeding = new InProceeding();
		while (resultSet.next()) {
			Proceedings proceedings = new Proceedings();
			proceedings.setTitle(resultSet.getString("title"));
			proceedings.setYear(resultSet.getInt("year"));
			inproceeding.setKey(resultSet.getString(2));
			inproceeding.setProceedings(proceedings);
			inproceeding.setBookTitle(resultSet.getString("booktitle"));
			break;
	}
		return inproceeding;
	}
	@Override
	public List<InProceeding> findByAttributes(Map<String, String> attributeNamesAndValues, int limit) {
			return null;
	}

	@Override
	public List<InProceeding> findByAttribute(String attributeName, String attributeValue, int limit)
			throws SQLException{
					
		PreparedStatement preparedStatement;
		if(attributeValue.equals("crossref")){
			regex="";
			preparedStatement = connection.prepareStatement("select * from bibliography.inproceedings where " + attributeName + " = ? LIMIT " + limit);
		}else{
			preparedStatement = connection.prepareStatement("select * from bibliography.inproceedings where " + attributeName + " LIKE ? LIMIT " + limit);
		}
		preparedStatement.setString(1, regex + attributeValue + regex);

			ResultSet resultSet = preparedStatement.executeQuery();
			List<InProceeding> list = new ArrayList<InProceeding>();
			while (resultSet.next()) {
				InProceeding inproceeding = new InProceeding();
				Proceedings proceedings = new Proceedings();
				proceedings.setTitle(resultSet.getString("title"));
				proceedings.setYear(resultSet.getInt("year"));
				inproceeding.setKey(resultSet.getString(2));
				inproceeding.setProceedings(proceedings);
				inproceeding.setBookTitle(resultSet.getString("booktitle"));
				list.add(inproceeding);
	
			}
			return list;	
	}
	
	
public static void main(String argp[]){
		
		InProceedingsDAO ob = new InProceedingsDAO();
		try {
			InProceeding bo = ob.findById(1);
			System.out.println(bo.getBookTitle());
			System.out.println(bo.getProceedings().getYear());
			System.out.println(bo.getProceedings().getTitle());
			List<InProceeding> bo2 = ob.findByAttribute("booktitle", "Advanced Database Systems", 10);
			for(InProceeding item : bo2){
				System.out.println(item.getBookTitle());
				System.out.println(item.getProceedings().getYear());
				System.out.println(item.getProceedings().getTitle());
			}
			System.out.println("!!!!!!!!!!!!!!!!!!!!");
			List<InProceeding> bo3 = ob.findByAttribute("crossref", "conf/er/2008", 10);
			for(InProceeding item : bo3){
				System.out.println(item.getBookTitle());
				System.out.println(item.getProceedings().getYear());
				System.out.println(item.getProceedings().getTitle());
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally{
			MariaDBDaoFactory.getInstance().closeConnection();
		}
		
		
	}

}

