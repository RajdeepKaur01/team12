package main.java.entities;

import java.util.List;

/**
 * This class describes the proceeding details of a conference. 
 * It contains a list of InProceedings, which specifies papers in a conference.
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
	private String organization;
	private List<InProceeding> inProceedings;
	private String conferenceName;
	private String confAcronym;
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public List<String> getEditors() {
		return editors;
	}
	public void setEditors(List<String> editors) {
		this.editors = editors;
	}
	public String getVolume() {
		return volume;
	}
	public void setVolume(String volume) {
		this.volume = volume;
	}
	public String getSeries() {
		return series;
	}
	public void setSeries(String series) {
		this.series = series;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public int getMonth() {
		return month;
	}
	public void setMonth(int month) {
		this.month = month;
	}
	public String getPublisher() {
		return publisher;
	}
	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}
	public String getOrganization() {
		return organization;
	}
	public void setOrganization(String organization) {
		this.organization = organization;
	}
	public List<InProceeding> getInproceedings() {
		return inProceedings;
	}
	public void setInProceedings(List<InProceeding> inProceedings) {
		this.inProceedings = inProceedings;
	}
	public String getConferenceName() {
		return conferenceName;
	}
	public void setConferenceName(String conferenceName) {
		this.conferenceName = conferenceName;
	}
	public String getConfAcronym() {
		return confAcronym;
	}
	public void setConfAcronym(String confAcronym) {
		this.confAcronym = confAcronym;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	
}
