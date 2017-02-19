package entities;

import java.util.List;

/**
 * @author 
 * This class represents the Research paper(s) in the database
 * Research paper is a paper published in conference or journal
 * Research paper may have one or more authors
 * 
 */

public class ResearchPaper {

	private int key;
	private List<Author> authors;
	private String title;
	private int year;
	private String content;
	private int pages;
	private int month;
	private Person editor;
	// Note will hold miscellaneous information related to research paper
	private Note note;
	
	public int getKey() {
		return key;
	}
	public void setKey(int key) {
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
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getPages() {
		return pages;
	}
	public void setPages(int pages) {
		this.pages = pages;
	}
	public int getMonth() {
		return month;
	}
	public void setMonth(int month) {
		this.month = month;
	}
	public Person getEditor() {
		return editor;
	}
	public void setEditor(Person editor) {
		this.editor = editor;
	}
	public Note getNote() {
		return note;
	}
	public void setNote(Note note) {
		this.note = note;
	}
	
	
}
