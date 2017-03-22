package main.java.entities;

/**
 * This represents a research paper that was submitted 
 * in a proceedings of research conference
 *
 */
public class Inproceeding extends ResearchPaper{
	private String bookTitle;
	
	//The proceedings in which the research paper was published
	private Proceedings proceedings;
	
	public String getBookTitle() {
		return bookTitle;
	}
	public void setBookTitle(String bookTitle) {
		this.bookTitle = bookTitle;
	}
	public Proceedings getProceedings() {
		return proceedings;
	}
	public void setProceedings(Proceedings proceedings) {
		this.proceedings = proceedings;
	}
}
