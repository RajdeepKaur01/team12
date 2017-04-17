package main.java.entities;

import java.util.List;
import java.util.Set;

/*
 * This class represents a journal where a work was published in.
 * It is a type of Publication.
 */

public class Journal extends Publication{
	protected String key;
	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	protected String volume;
	//The list of articles published in journal
	protected Set<Article> articles;

	public Set<Article> getArticles() {
		return articles;
	}

	public void setArticles(Set<Article> articles) {
		this.articles = articles;
	}

	public String getVolume() {
		return volume;
	}

	public void setVolume(String volume) {
		this.volume = volume;
	}
}
