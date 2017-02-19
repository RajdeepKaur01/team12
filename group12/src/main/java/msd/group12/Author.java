/**
 * 
 */
package msd.group12;

import java.net.URL;

/**
 * @author sudeep
 * This class represents an Author of research paper(s)
 * in the database
 *
 */
public class Author {
	
	private String name;
	private String emailAddress;
	private String address;
	private String[] aliases;
	private URL homePageURL;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmailAddress() {
		return emailAddress;
	}
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
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
