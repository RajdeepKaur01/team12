package entities;

/*
 * This class represents a journal article.
 */
public class Article {
	
	//The journal which the article is part of
	protected Journal journal;
	protected int rating;
	
	public Journal getJournal() {
		return journal;
	}

	public void setJournal(Journal journal) {
		this.journal = journal;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

}
