/**
 * 
 */
package main.java.interfaces;

import java.util.Set;

import main.java.entities.*;

/**
 * Note: the last parameter 'max' in each function represents the max no. of
 * results we want to retrieve from the database
 */
public interface IFindResearchers {
	
	/**
	 * Find authors who have held a position in the past on one/many committees
	 * Position is one of:
	 *** G - General Chair
	 *** P - Program Chair
	 *** C - Committee Chair
	 */
	public Set<Author> findAuthorsByPositionHeld(String areaOfExpertise);

	/**
	 * Find authors given a research paper title
	 */
	public Set<Author> findAuthorsByResearchPaperTitle(String title);

	/**
	 * Find author aliases and other miscellaneous info by using author name
	 */
	public Set<AuthorInfo> findAuthorsInfoByAuthorName(String authorName);

	/**
	 * Find authors matching a given name
	 */
	public Set<Author> findAuthorsByAuthorName(String authorName);

	/**
	 * Find authors who have published at least one paper in the given Conference
	 */
	public Set<Author> findAuthorsByConferenceName(String conferenceName);

	/**
	 * Find authors who have served in a conference, given its acronym
	 * e.g. OOPSLA, ECOOP etc.
	 */
	public Set<Author> findAuthorsByConferenceAcronym(String conferenceAcronym);

	/**
	 * Find all authors who have published at least one research paper
	 * in the given year
	 */
	public Set<Author> findAuthorsByYearOfPublication(int yearOfPublication);

	/**
	 * Find authors who have a profile similar to the given author. 
	 * Similarity is defined as follows:
	 * ** Held a position in the same conferene as the author
	 * ** # of research papers is at least equal to # of research papers of this author
	 */
	public Set<Author> findAuthorsWithSimilarProfile(Author author);

	/**
	 * Find all the research papers and journal publications for the given author
	 */
	public Author getResearchPapers(Author author);

	/**
	 * Return author meta info, such as alias, home page URL ets given the Author object
	 */
	public Author getAuthorInfo(Author author);

}
