/**
 * 
 */
package main.java.interfaces;

import java.util.Date;
import java.util.List;

import main.java.entities.*;
/**
 * Note: the last parameter 'max' in each function represents 
 * the max no. of results we want to retrieve from the database
 */
public interface IFindResearchers {
	
	
	public List<Author> findAuthorsByPositionHeld (String areaOfExpertise, int max);
		
	public List<Author> findAuthorsByNumberOfResearchPapers (int numOfResearchPaper, int max);
		
	public List<Author> findAuthorsByResearchPaperTitle (String title, int max);
	
	public List<Author> findAuthorsByAuthorName (String authorName, int max);
	
	public List<Author> findAuthorsByAlias (String alias, int max);
	
	public List<Author> findAuthorsByConferenceName (String conferenceName, int max);
	
	public List<Author> findAuthorsByConferenceAcronym (String conferenceAcronym, int max);
	
	public List<Author> findAuthorsByConference (String conferenceName, int numOfYears, int max);
	
	public List<Author> findAuthorsSimilarToProfile (Author author);
	
	
}
