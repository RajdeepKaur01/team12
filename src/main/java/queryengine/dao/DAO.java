package main.java.queryengine.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface DAO <T>{
	public Set<T> findByAttributes (Map<String, String> attributeNamesAndValues) throws SQLException;
	public Set<T> findByAttribute (String attirubteName, Set<String> attributeValues) throws SQLException;
	default public T join (T entity) throws SQLException {return null;}
	public Set<T> findByKeys (Set<String> keys) throws SQLException;
	default public Set<T> findAuthorsWithSimilarProfile (T t) throws SQLException {return null;}
}
