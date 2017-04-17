package main.java.interfaces;

/**
 * It represents the core functions that will be used by Front-end
 * to parse XML files and create entities in Database
 */

public interface IFrontEnd {
		public boolean initializeAndRunSAXParser(String inputFilePath);
		
		public boolean initializeAndRunCommitteeParser(String inputDirectory);
		
		public boolean insertRecordsInDatabase();
}
