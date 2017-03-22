package interfaces;

import java.sql.PreparedStatement;

/**
 * It represents the core functions that will be used by Front-end
 * to parse XML files and create entities in Database
 */

public interface IFrontEnd {
	public String  readFile(String filepath);
	
	public void parseFile(String inputfile);
	
	public void writeToDatabase(String entityType, PreparedStatement statement);
}
