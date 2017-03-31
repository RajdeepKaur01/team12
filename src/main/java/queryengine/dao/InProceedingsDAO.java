package main.java.queryengine.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import main.java.entities.InProceeding;
import main.java.entities.Proceedings;
import main.java.queryengine.DAOFactory;
import main.java.queryengine.MariaDBDaoFactory;

public class InProceedingsDAO implements DAO<InProceeding> {

	private static final String CROSSREF = "crossref";
	private static final String YEAR = "year";
	private String regex = "%";
	private static DAOFactory daoFactory = MariaDBDaoFactory.getInstance();
	private static final Connection connection = daoFactory.getConnection();

	@Override
	public Set<InProceeding> findByAttributes(Map<String, String> attributeNamesAndValues, int limit) {
		return null;
	}

	@Override
	public Set<InProceeding> findByAttribute(String attributeName, Set<String> attributeValue, int limit)
			throws SQLException {

		String value = "";

		for (String v : attributeValue) {
			value = v;
		}
		PreparedStatement preparedStatement;
		if (attributeName.equals(CROSSREF)||attributeName.equals(YEAR)) {
			regex = "";
			preparedStatement = connection
					.prepareStatement("select * from bibliography.inproceedings where " + attributeName + " = ? LIMIT " + limit);
		} else {
			preparedStatement = connection
					.prepareStatement("select * from bibliography.inproceedings where " + attributeName + " LIKE ? LIMIT " + limit);
		}
		preparedStatement.setString(1, regex + value + regex);

		ResultSet resultSet = preparedStatement.executeQuery();
		Set<InProceeding> set = new HashSet<>();
		while (resultSet.next()) {
			InProceeding inProceeding = new InProceeding();
			Proceedings proceedings = new Proceedings();
			proceedings.setTitle(resultSet.getString("title"));
			proceedings.setYear(resultSet.getInt("year"));
			inProceeding.setKey(resultSet.getString(2));
			inProceeding.setProceedings(proceedings);
			inProceeding.setBookTitle(resultSet.getString("booktitle"));
			set.add(inProceeding);
		}
		return set;
	}

	public static void main(String argp[]) {
		/*
		 * InProceedingsDAO ob = new InProceedingsDAO(); try { InProceeding bo =
		 * ob.findById(1); System.out.println(bo.getBookTitle());
		 * System.out.println(bo.getProceedings().getYear());
		 * System.out.println(bo.getProceedings().getTitle()); Set<String> set =
		 * new HashSet<String>(); set.add("Advanced Database Systems");
		 * List<InProceeding> bo2 = ob.findByAttribute("booktitle", set , 10);
		 * for(InProceeding item : bo2){
		 * System.out.println(item.getBookTitle());
		 * System.out.println(item.getProceedings().getYear());
		 * System.out.println(item.getProceedings().getTitle()); }
		 * System.out.println("!!!!!!!!!!!!!!!!!!!!"); Set<String> set2 = new
		 * HashSet<String>(); set2.add("conf/er/2008"); List<InProceeding> bo3 =
		 * ob.findByAttribute("crossref", set2 , 10); for(InProceeding item :
		 * bo3){ System.out.println(item.getBookTitle());
		 * System.out.println(item.getProceedings().getYear());
		 * System.out.println(item.getProceedings().getTitle()); } } catch
		 * (SQLException e) { e.printStackTrace(); } finally{
		 * MariaDBDaoFactory.getInstance().closeConnection(); }
		 */
	}

	@Override
	public Set<InProceeding> findByKeys(Set<String> keys) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}
}
