package main.java.frontend;

import java.util.ArrayList;

public class AuthorInfo {

	private static final String TITLE = "title";
	private static final String URL = "url";
	private static final String CROSSREF = "crossref";
	private static final String CITE = "cite";
	private static final String AUTHOR = "author";

	public int ID = 4;
	public String key = "";
	public String title = "";
	public String url = "";
	public String crossref = "";
	public ArrayList<String> authors;

	public AuthorInfo() {
		this.authors = new ArrayList<>();
	}

	public String toString() {
		return "key: " + key + " authors: " + authors + " title: " + title + " url: " + url + " crossref: " + crossref;
	}

	public void populateAttributes(String eleType, String eleValue) {
		switch (eleType) {
		case TITLE:
			this.title = eleValue;
			break;
		case URL:
			this.url = eleValue;
			break;
		case CROSSREF:
			this.crossref = eleValue;
			break;
		case AUTHOR:
			this.authors.add(eleValue);
			break;
		default:
			// System.out.println("no match found for childelement of
			// proceedings");
		}
	}
}
