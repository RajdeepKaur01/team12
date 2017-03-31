package main.java.queryengine.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface DAO <T>{
	public T findById (int id) throws SQLException;
	public Set<T> findByAttributes (Map<String, String> attributeNamesAndValues, int limit) throws SQLException;
	public Set<T> findByAttribute (String attirubteName, Set<String> attributeValues, int limit) throws SQLException;
	default public Set<T> join (T entity) throws SQLException {return null;}
}
