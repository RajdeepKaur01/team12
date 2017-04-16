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

	public Set<Author> findAuthorsByPositionHeld(String areaOfExpertise);

	public Set<Author> findAuthorsByResearchPaperTitle(String title);

	public Set<AuthorInfo> findAuthorsInfoByAuthorName(String authorName);

	public Set<Author> findAuthorsByAuthorName(String authorName);

	public Set<Author> findAuthorsByConferenceName(String conferenceName);

	public Set<Author> findAuthorsByConferenceAcronym(String conferenceAcronym);

	public Set<Author> findAuthorsByYearOfPublication(int yearOfPublication);

	public Set<Author> findAuthorsWithSimilarProfile(Author author);

	public Author getResearchPapers(Author author);

	public Author getAuthorInfo(Author author);

}
