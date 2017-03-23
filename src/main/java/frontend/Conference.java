package main.java.frontend;

import java.util.ArrayList;
import java.util.List;

public class Conference {
	
	private static final  String EMPTY ="empty";
	private static final String TITLE ="title";
	private static final String BOOKTITLE = "booktitle";
	private static final String YEAR ="year";
	private static final String ISBN = "isbn";
	private static final String VOLUME = "volume";
	private static final String SERIES = "series";
	private static final String PUBLISHER = "publisher";
	private static final String EDITOR = "editor";
	
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
	public List<String> editors;
	
	public Conference() {
		
		this.editors = new ArrayList<>();
	}
	
	
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
		case EDITOR:
			this.editors.add(eleValue);
			break;
		default:
			return;
			}
	}

}
