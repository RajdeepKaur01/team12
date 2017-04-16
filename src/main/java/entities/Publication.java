package main.java.entities;

/*
 * This class represents a type of Publication
 * Conference and Journal both are types of Publication
 */
public class Publication {
	protected String name;
	protected int year;

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

}
