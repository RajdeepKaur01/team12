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
	
	private String[] aliases;
	private URL homePageURL;
	private Map<String, Set<String>> committeeMemberInfo;
	private int numberOfResearchPapers;
	
	public Map<String, Set<String>> getCommitteeMemberInfo() {
		return committeeMemberInfo;
	}
	public void setCommitteeMemberInfo(Map<String, Set<String>> committeeMemberInfo) {
		this.committeeMemberInfo = committeeMemberInfo;
	}
	//The list of research papers - both journal articles and conference articles
	private Set<ResearchPaper> researchPapers = new HashSet<>();
	public String[] getAliases() {
		return aliases;
	}
	public void setAliases(String[] aliases) {
		this.aliases = aliases;
	}
	public URL getHomePageURL() {
		return homePageURL;
	}
	public void setHomePageURL(URL homePageURL) {
		this.homePageURL = homePageURL;
	}
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
		builder.append("Author [aliases=").append(Arrays.toString(aliases)).append(", homePageURL=").append(homePageURL)
				.append(", committeeMemberInfo=").append(committeeMemberInfo).append(", numberOfResearchPapers=")
				.append(numberOfResearchPapers).append(", researchPapers=").append(researchPapers).append(", name=")
				.append(name).append("]");
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
	
	
	
}
