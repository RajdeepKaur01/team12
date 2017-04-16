package main.java.interfaces;

import main.java.entities.User;

public interface IAuth {

	public User login(String username, String password);
}
