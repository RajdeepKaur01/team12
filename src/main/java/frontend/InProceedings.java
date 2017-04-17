package main.java.frontend;

import java.util.ArrayList;

/*
 * Represents Bibliography inproceedings 
 */
public class InProceedings {

	private final String TITLE = "title";
	private final String BOOKTITLE = "booktitle";
	private final String YEAR = "year";
	private final String PAGES = "pages";
	private final String CROSSREF = "crossref";
	private final String AUTHOR = "author";
	public int ID = 2;
	public String key;
	public String mdate;

	public String title;
	public String booktitle;

	public int year;
	public String pages;

	public String crossref;
	public ArrayList<String> authors;

	public InProceedings() {
		this.authors = new ArrayList<>();
	}

	public String toString() {
		return "key: " + key + " mdate: " + mdate + " title: " + title + " booktitle: " + booktitle + " year: " + year;
	}

	/*
	 * populates inproceedings attributes matching against the eleType received
	 * in the arguments
	 */
	public void populateAttributes(String eleType, String eleValue) {
		switch (eleType) {
		case TITLE:
			this.title = eleValue;
			break;
		case BOOKTITLE:
			this.booktitle = eleValue;
			break;
		case AUTHOR:
			this.authors.add(eleValue.trim());
			break;
		case YEAR:
			this.year = eleValue.matches("[-+]?\\d*\\.?\\d+") ? Integer.parseInt(eleValue.trim()) : 0;
			break;
		case PAGES:
			this.pages = eleValue;
			break;
		case CROSSREF:
			this.crossref = eleValue;
			break;
		default:
			}
	}

}
