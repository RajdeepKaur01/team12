package interfaces;

import entities.Person;
import entities.User;

public interface IAuth {
	public User register (Person person, String username, String password);
	
	public boolean login (String username, String password);
	
	public boolean resetPassword (String username, String newPassword, String oldPassword);
	
	public String retrieveUsername (String emailAddress);
}
