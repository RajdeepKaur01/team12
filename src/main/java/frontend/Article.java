package main.java.frontend;

import java.util.ArrayList;

/*
 * Represents a bibliography Article (dblp)
 */
public class Article {
	
	private static final String TITLE ="title";
	private static final String YEAR ="year";
	private static final String PAGES = "pages";
	private static final String CROSSREF = "crossref";
	private static final String VOLUME = "volume";
	private static final String JOURNAL = "journal";
	private static final String AUTHOR = "author";
	
	public int ID =3;
	public String key="";
	public String mdate="";
	
	public String title="";
	public String pages="";
	public int year=0;
	public String volume="";
	
	public String journal="";
	public String crossref="";
	public ArrayList<String> authors;
	
	 public Article(){
		this.authors = new ArrayList<>();
	}
	
	public String toString(){
		return "key: " + key + " mdate: " + mdate +
				" title: " + title + " journal: " + journal + " year: " + year;
	}
	/*
	 * populates article attributes matching against the eleType received in the arguments
	 */
	public void populateAttributes(String eleType,String eleValue){
		switch(eleType){
		case TITLE:
			this.title=eleValue;
			break;
		case PAGES:
			this.pages=eleValue;
			break;
		case AUTHOR:
			this.authors.add(eleValue);
			break;
		case YEAR:
			this.year=eleValue.matches("[-+]?\\d*\\.?\\d+")? Integer.parseInt(eleValue.trim()):0;
			break;
		case VOLUME:
			this.volume=eleValue;
			break;
		case CROSSREF:
			this.crossref=eleValue;
			break;
		case JOURNAL:
			this.journal=eleValue;
			break;
		default:
			return;
			}
	}

}
