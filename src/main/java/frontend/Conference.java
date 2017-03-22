package main.java.frontend;

import java.util.ArrayList;

public class Conference {
	
	private static final  String EMPTY ="empty";
	private static final String TITLE ="title";
	private static final String BOOKTITLE = "booktitle";
	private static final String YEAR ="year";
	private static final String ISBN = "isbn";
	private static final String VOLUME = "volume";
	private static final String SERIES = "series";
	private static final String PUBLISHER = "publisher";
	private static final  String PAGES = "pages";
	
	public int ID =1;
	public String key="";
	public String mdate="";
	
	public String title="";
	public String booktitle="";
	
	public int year=0;
	public String isbn="";
	public String volume="";
	public String series="";
	
	public String publisher="";
	public Conference() {
	}
	public ArrayList<String> editors;
	
	public String toString(){
		return "key: " + key + " mdate: " + mdate +
				" title: " + title + " booktitle: " + booktitle + " year: " + year;
	}
	public void populateAttributes(String eleType,String eleValue){
		switch(eleType){
		case TITLE:
			this.title=eleValue;
			break;
		case BOOKTITLE:
			this.booktitle=eleValue;
			break;
		case YEAR:
			this.year=Integer.parseInt(eleValue);
			break;
		case ISBN:
			this.isbn=eleValue;
			break;
		case VOLUME:
			this.volume=eleValue;
			break;
		case SERIES:
			this.series=eleValue;
			break;
		case PUBLISHER:
			this.publisher=eleValue;
			break;
		default:
			//System.out.println("no match found for childelement of proceedings");
			}
	}

}
