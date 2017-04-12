/**
 * 
 */
package main.java.entities;

import java.net.URL;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * This class represents an Author of research paper(s)
 * in the database
 */
public class Author extends Person{
	
	private Map<String, Set<String>> committeeMemberInfo;
	private int numberOfResearchPapers;
	private int pastExperienceYrs;
	private AuthorInfo authorInfo;
	private String note;
	//The list of research papers - both journal articles and conference articles
	private Set<InProceeding> inProceedings = new HashSet<>();
	private Set<Article> articles = new HashSet<>();
	private Set<String> paperKeys = new HashSet<>();
	
	/**
	 * @return the paperKeys
	 */
	public Set<String> getPaperKeys() {
		return paperKeys;
	}
	/**
	 * @param paperKeys the paperKeys to set
	 */
	public void setPaperKeys(Set<String> paperKeys) {
		this.paperKeys = paperKeys;
	}
	
	public void addToPaperSet(String key) {
		this.paperKeys.add(key);
	}
	
	public Map<String, Set<String>> getCommitteeMemberInfo() {
		return committeeMemberInfo;
	}
	public void setCommitteeMemberInfo(Map<String, Set<String>> committeeMemberInfo) {
		this.committeeMemberInfo = committeeMemberInfo;
	}
	public AuthorInfo getAuthorInfo() {
		return authorInfo;
	}
	public void setAuthorInfo(AuthorInfo authorInfo) {
		this.authorInfo = authorInfo;
	}


	//The list of research papers - both journal articles and conference articles
	private Set<ResearchPaper> researchPapers = new HashSet<>();

	public Set<ResearchPaper> getResearchPapers() {
		return researchPapers;
	}
	public void setResearchPapers(Set<ResearchPaper> researchPapers) {
		this.researchPapers.addAll(researchPapers);
	}
	
	/**
	 * @return the numberOfResearchPapers
	 */
	public int getNumberOfResearchPapers() {
		return numberOfResearchPapers;
	}
	
	/**
	 * @return the inProceedings
	 */
	public Set<InProceeding> getInProceedings() {
		return inProceedings;
	}
	/**
	 * @param inProceedings the inProceedings to set
	 */
	public void setInProceedings(Set<InProceeding> inProceedings) {
		this.inProceedings = inProceedings;
	}
	/**
	 * @return the articles
	 */
	public Set<Article> getArticles() {
		return articles;
	}
	/**
	 * @param articles the articles to set
	 */
	public void setArticles(Set<Article> articles) {
		this.articles = articles;
	}
	/**
	 * @param numberOfResearchPapers the numberOfResearchPapers to set
	 */
	public void setNumberOfResearchPapers(int numberOfResearchPapers) {
		this.numberOfResearchPapers = numberOfResearchPapers;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Author")
				.append(", committeeMemberInfo=").append(committeeMemberInfo).append(", numberOfResearchPapers=")
				.append(numberOfResearchPapers).append(", inProceedings=").append(inProceedings).append(", articles=")
				.append(articles).append(", paperKeys=").append(paperKeys).append("]");
		return builder.toString();
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return this.getName().hashCode();
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (! (obj instanceof Author)) return false;
		Author other = (Author) obj;
		return (this.getName().equals(other.getName()));
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public int getPastExperienceYrs() {
		return pastExperienceYrs;
	}
	public void setPastExperienceYrs(int pastExperienceYrs) {
		this.pastExperienceYrs = pastExperienceYrs;
	}
	
}
