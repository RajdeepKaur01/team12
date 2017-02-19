/**
 * 
 */
package msd.group12;

import java.util.Date;
import java.util.List;

/**
 * @author sudeep
 *
 */
public interface IFindResearchers {
	
	public List<Author> findAuthorsByLocation (String address, int max);
	
	public List<Author> findAuthorsByAreaOfExpertise (String aresOfExpertise, int max);
	
	public List<Author> findAuthorsByPastExperience (String typeOfExperience, String numOfYears, int max);
	
	public List<Author> findAuthorsByNumberOfResearchPapers (int numOfResearchPaper, int max);
	
	public List<Author> findAuthorsByNumberOfResearchPapersAndDate (int numOfResearchPaper, int max, Date fromDate);
	
	public List<Author> findAuthorsByKeywordsInTitle (List<String> keywords, int max);
	
	public List<Author> findAuthorsByResearchPaperTitle (String title, int max);
	
	public List<Author> findAuthorsByAuthorName (String authorName, int max);
	
	public List<Author> findAuthorsByAlias (String alias, int max);
	
	public List<Author> findAuthorsByConference (String conferenceName, int max);
	
	public List<Author> findAuthorsByConference (String conferenceName, int numOfYears, int max);
	
	public List<Author> findAuthorsSimilarToProfile (Author author);
	
}
