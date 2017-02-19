package entities;

public class User extends Person{
	
	/**
	 * @author 
	 * This class represents the User in the database
	 * User is either Program Committee Chair or Associate Editor
	 * 
	 */
	
	protected String username;
	protected String password;
	protected int userId;
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	
}
