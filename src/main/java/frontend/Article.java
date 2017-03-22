package main.java.frontend;

import java.util.ArrayList;

public class Article {
	
	private final String TITLE ="title";
	private final String YEAR ="year";
	private final String PAGES = "pages";
	private final String CROSSREF = "crossref";
	private final String VOLUME = "volume";
	private final String JOURNAL = "journal";
	private final String AUTHOR = "author";
	
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
		case YEAR:
			this.year=(eleValue.matches("[-+]?\\d*\\.?\\d+")? Integer.parseInt(eleValue.trim()):0);
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
			//System.out.println("no match found for childelement of proceedings");
			}
	}

}
