package entities;

import java.util.List;

/**
 * This represents a conference in the database
 * Conference has one/more proceedings, which are 
 * a collection of technical papers presented at a professional association meeting
 */
public class Conference extends Publication {
	protected String acronym;
	
	private List<Proceedings> proceedings; 
	
	public String getAcronym() {
		return acronym;
	}
	public void setAcronym(String acronym) {
		this.acronym = acronym;
	}
	public List<Proceedings> getInproceedings() {
		return proceedings;
	}
	public void setInproceedings(List<Proceedings> proceedings) {
		this.proceedings = proceedings;
	}
	
}
