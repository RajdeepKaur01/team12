package entities;

import java.util.List;

/*
 * This class represents a journal where a work was published in.
 * It is type of Publication.
 */

public class Journal extends Publication{
	protected String volume;
	//The list of articles published in journal
	protected List<Article> articles;

	public List<Article> getArticles() {
		return articles;
	}

	public void setArticles(List<Article> articles) {
		this.articles = articles;
	}

	public String getVolume() {
		return volume;
	}

	public void setVolume(String volume) {
		this.volume = volume;
	}
}
