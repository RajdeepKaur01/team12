package main.java.queryengine.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import main.java.entities.Inproceeding;
import main.java.entities.Proceedings;
import main.java.queryengine.DAOFactory;
import main.java.queryengine.MariaDBDaoFactory;

public class InproceedingsDAO implements DAO<Inproceeding>{

	
	private static DAOFactory daoFactory = MariaDBDaoFactory.getInstance();
	private static final Connection connection = daoFactory.getConnection();
	@Override
	public Inproceeding findById(int id) throws SQLException {
		PreparedStatement preparedStatement = connection.prepareStatement("select * from bibliography.inproceedings where ID = ?");
		preparedStatement.setInt(1, id);
		ResultSet resultSet = preparedStatement.executeQuery();
		Inproceeding inproceeding = new Inproceeding();
		while (resultSet.next()) {
			Proceedings proceedings = new Proceedings();
			proceedings.setTitle(resultSet.getString("title"));
			proceedings.setYear(resultSet.getInt("year"));
			inproceeding.setProceedings(proceedings);
			inproceeding.setBookTitle(resultSet.getString("booktitle"));
			break;
	}
		return inproceeding;
	}
	@Override
	public List<Inproceeding> findByAttributes(Map<String, String> attributeNamesAndValues, int limit) {
			return null;
	}

	@Override
	public List<Inproceeding> findByAttribute(String attributeName, String attributeValue, int limit)
			throws SQLException{
		if(attributeName.equals("_key"))
			attributeName = "crossref";
		
			PreparedStatement preparedStatement = connection.prepareStatement("select * from bibliography.inproceedings where " + attributeName + " = ? LIMIT " + limit);
			preparedStatement.setString(1, attributeValue);
			ResultSet resultSet = preparedStatement.executeQuery();
			List<Inproceeding> list = new ArrayList<Inproceeding>();
			while (resultSet.next()) {
				Inproceeding inproceeding = new Inproceeding();
				Proceedings proceedings = new Proceedings();
				proceedings.setTitle(resultSet.getString("title"));
				proceedings.setYear(resultSet.getInt("year"));
				inproceeding.setProceedings(proceedings);
				inproceeding.setBookTitle(resultSet.getString("booktitle"));
				list.add(inproceeding);
	
			}
			return list;	
	}
	
	
public static void main(String argp[]){
		
		InproceedingsDAO ob = new InproceedingsDAO();
		try {
			Inproceeding bo = ob.findById(1);
			System.out.println(bo.getBookTitle());
			System.out.println(bo.getProceedings().getYear());
			System.out.println(bo.getProceedings().getTitle());
			List<Inproceeding> bo2 = ob.findByAttribute("booktitle", "Advanced Database Systems", 10);
			for(Inproceeding item : bo2){
				System.out.println(item.getBookTitle());
				System.out.println(item.getProceedings().getYear());
				System.out.println(item.getProceedings().getTitle());
			}
			System.out.println("!!!!!!!!!!!!!!!!!!!!");
			List<Inproceeding> bo3 = ob.findByAttribute("_key", "journals/lncs/1991-546", 10);
			for(Inproceeding item : bo3){
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

