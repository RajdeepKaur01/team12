package entities;

import java.util.List;

/**
 * This class describes the proceeding details of a conference. 
 * It contains a list of Inproceedings, which specifies papers in a conference.
 **/
public class Proceedings {	
	private String title;
	private int year;
	private String editor;
	private int volume;
	private String series;
	private String address;
	private int month;
	private String publisher;
	private String organization;
	private List<Inproceeding> inproceedings;
	
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
	public String getEditor() {
		return editor;
	}
	public void setEditor(String editor) {
		this.editor = editor;
	}
	public int getVolume() {
		return volume;
	}
	public void setVolume(int volume) {
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
	public List<Inproceeding> getInproceedings() {
		return inproceedings;
	}
	public void setInproceedings(List<Inproceeding> inproceedings) {
		this.inproceedings = inproceedings;
	}
	
}
