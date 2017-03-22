package main.java.entities;

/**
 * Note is common across many entities
 * It represents any kind of miscellaneous, additional information that 
 * is associated with an entity
 */
public class Note {
	
	protected String unicode;
	protected String affiliation;
	
	public String getUnicode() {
		return unicode;
	}
	public void setUnicode(String unicode) {
		this.unicode = unicode;
	}
	public String getAffiliation() {
		return affiliation;
	}
	public void setAffiliation(String affiliation) {
		this.affiliation = affiliation;
	}
}
