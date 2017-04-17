package main.java.entities;

import java.net.URL;

public class AuthorInfo {

	private String[] aliases;
	private URL homePageURL;
	public String[] getAliases() {
		return aliases;
	}
	public void setAliases(String[] aliases) {
		this.aliases = aliases;
	}
	public URL getHomePageURL() {
		return homePageURL;
	}
	public void setHomePageURL(URL homePageURL) {
		this.homePageURL = homePageURL;
	}
}
