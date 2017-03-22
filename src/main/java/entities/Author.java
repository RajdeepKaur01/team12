/**
 * 
 */
package main.java.entities;

import java.net.URL;
import java.util.List;

/**
 * This class represents an Author of research paper(s)
 * in the database
 */
public class Author extends Person{
	
	private String[] aliases;
	private URL homePageURL;
	private int yearsAsCommitteeMemember;
	private String areaOfExpertise;
	//The list of research papers - both journal articles and conference articles
	private List<ResearchPaper> researchPapers;
	
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
	public int getyearsAsCommitteeMemember() {
		return yearsAsCommitteeMemember;
	}
	public void setyearsAsCommitteeMemember(int yearsAsCommitteeMemember) {
		this.yearsAsCommitteeMemember = yearsAsCommitteeMemember;
	}
	public String getAreaOfExpertise() {
		return areaOfExpertise;
	}
	public void setAreaOfExpertise(String areaOfExpertise) {
		this.areaOfExpertise = areaOfExpertise;
	}
	public List<ResearchPaper> getResearchPapers() {
		return researchPapers;
	}
	public void setResearchPapers(List<ResearchPaper> researchPapers) {
		this.researchPapers = researchPapers;
	}
}
