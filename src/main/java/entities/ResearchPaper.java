package main.java.entities;

import java.util.List;

/**
 * This class represents the Research paper(s) in the database
 * Research paper is a paper published in conference or journal
 * Research paper may have one or more authors
 */

public class ResearchPaper {
	private String key;
	//Author(s) of the research paper
	private List<Author> authors;
	private String title;
	private int year;
	
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public List<Author> getAuthor() {
		return authors;
	}
	public void setAuthor(List<Author> authors) {
		this.authors = authors;
	}
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
}
