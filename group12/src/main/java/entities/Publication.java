package entities;

import java.util.Date;
import java.util.List;
/*
 * This class represents a type of Publication that can be of type conference or journal
 */
public class Publication {
	protected String name;
	protected Date dateOfPublication;
	private String title;
	//The list of notes
	protected List<Note> notes;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getDateOfPublication() {
		return dateOfPublication;
	}
	public void setDateOfPublication(Date dateOfPublication) {
		this.dateOfPublication = dateOfPublication;
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
