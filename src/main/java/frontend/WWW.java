package main.java.frontend;

import java.util.ArrayList;

public class WWW {
	
	private final String TITLE ="title";
	private final String PAGES = "pages";
	private final String CROSSREF = "crossref";
	private final String CITE = "cite";
	private final String AUTHOR = "author";
	
	public int ID =4;
	public String key="";
	public String title="";
	public String url="";

	public String cite="";
	public String crossref="";
	public ArrayList<String> authors;
	
	 public WWW(){
		this.authors = new ArrayList<>();
	}
	
	public String toString(){
		return "key: " + key + " title: " + title +
				" url: " + url + " cite: " + cite + " crossref: " + crossref;
	}
	
	public void populateAttributes(String eleType,String eleValue){
		switch(eleType){
		case TITLE:
			this.title=eleValue;
			break;
		case PAGES:
			this.url=eleValue;
			break;
		case AUTHOR:
			this.authors.add(eleValue);
		case CITE:
			this.cite=eleValue;
			break;
		case CROSSREF:
			this.crossref=eleValue;
			break;
		default:
			//System.out.println("no match found for childelement of proceedings");
			}
	}
}
