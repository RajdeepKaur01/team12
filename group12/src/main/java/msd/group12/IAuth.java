package msd.group12;

public interface IAuth {

	public User register (Person person, String username, String password);
	public boolean login (String username, String password);
	public boolean resetPassword (String username, String newPassword, String oldPassword);
	public String retrieveUsername (String emailAddress);
}
