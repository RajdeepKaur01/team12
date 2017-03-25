package main.java.queryengine;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface DAO <T>{
	public T findById (int id) throws SQLException;
	public List<T> findByAttributes (Map<String, String> attributeNamesAndValues);
	public List<T> findByAttribute (String attirubteName, String attributeValue, int limit) throws SQLException;
	public boolean insert (T entity);
}
