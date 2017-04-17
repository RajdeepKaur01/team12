package main.java.entities;

import java.util.Date;
import java.util.List;
/*
 * This class represents a type of Publication
 * Conference and Journal both are types of Publication
 */
public class Publication {
	protected String name;
	protected int year;
	private String title;
	//The list of notes contains miscellaneous information
	protected List<Note> notes;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public List<Note> getNotes() {
		return notes;
	}
	public void setNotes(List<Note> notes) {
		this.notes = notes;
	}
	
	
}
