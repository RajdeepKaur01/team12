package main.java.entities;

import java.util.List;
import java.util.Set;

/**
 * This class describes the proceeding details of a conference. It contains a
 * list of InProceedings, which specifies papers in a conference.
 **/
public class Proceedings {
	private String key;
	private String title;
	private int year;
	private List<String> editors;
	private String volume;
	private String series;
	private String address;
	private int month;
	private String publisher;
	private Set<InProceeding> inProceedings;
	private String conferenceName;
	private String confAcronym;
	public void setTitle(String title) {
		this.title = title;
	}


	public void setYear(int year) {
		this.year = year;
	}

	public void setEditors(List<String> editors) {
		this.editors = editors;
	}

	public void setVolume(String volume) {
		this.volume = volume;
	}


	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public Set<InProceeding> getInproceedings() {
		return inProceedings;
	}

	public void setInProceedings(Set<InProceeding> inProceedings) {
		this.inProceedings = inProceedings;
	}

	public void setConferenceName(String conferenceName) {
		this.conferenceName = conferenceName;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public void setSeries(String s) {
		this.series = s;
	}


}
