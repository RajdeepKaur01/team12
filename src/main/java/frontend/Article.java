package main.java.frontend;

import java.util.ArrayList;

public class Article {
	
	private final static String TITLE ="title";
	private final static String YEAR ="year";
	private final static String PAGES = "pages";
	private final static String CROSSREF = "crossref";
	private final static String VOLUME = "volume";
	private final static String JOURNAL = "journal";
	private final static String AUTHOR = "author";
	
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
