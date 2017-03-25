package main.java.queryengine;

public interface DAO <T>{
	public T findById (String id);
	public T findByAttribute (String attributeName, String attributeValue);
	public boolean insert (T entity);
}
