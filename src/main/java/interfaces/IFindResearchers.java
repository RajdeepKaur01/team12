/**
 * 
 */
package main.java.interfaces;

import java.util.Set;

import main.java.entities.*;
/**
 * Note: the last parameter 'max' in each function represents 
 * the max no. of results we want to retrieve from the database
 */
public interface IFindResearchers {
	
	
	public Set<Author> findAuthorsByPositionHeld (String areaOfExpertise, int max);
		
	public Set<Author> findAuthorsByNumberOfResearchPapers (int numOfResearchPaper, int max);
		
	public Set<Author> findAuthorsByResearchPaperTitle (String title, int max);
	
	public Set<Author> findAuthorsByAuthorName (String authorName, int max);
	
	public Set<Author> findAuthorsByAlias (String alias, int max);
	
	public Set<Author> findAuthorsByConferenceName (String conferenceName, int max);
	
	public Set<Author> findAuthorsByConferenceAcronym (String conferenceAcronym, int max);
	
	public Set<Author> findAuthorsByConference (String conferenceName, int numOfYears, int max);
	
	public Set<Author> findAuthorsSimilarToProfile (Author author);
	
	public Author getResearchPapers(Author author);
	
	public Author getAuthorInfo(Author author);
	
	
}
