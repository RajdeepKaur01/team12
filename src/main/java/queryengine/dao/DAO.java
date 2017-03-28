package main.java.queryengine.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface DAO <T>{
	public T findById (int id) throws SQLException;
	public List<T> findByAttributes (Map<String, String> attributeNamesAndValues, int limit) throws SQLException;
	public List<T> findByAttribute (String attirubteName, String attributeValue, int limit) throws SQLException;
	default public List<T> join (String tableName, String attributeName, String attributeValue) {return null;}
}
