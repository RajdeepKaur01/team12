package main.java.interfaces;

/**
 * It represents the core functions that will be used by Front-end
 * to parse XML files and create entities in Database
 */

public interface IFrontEnd {
		public boolean initializeAndRunParser(String inputFilePath);
		
		public boolean setUpDBConnection();

		public boolean createDBInsertStatements();

		public boolean commitRecords();
		
}
