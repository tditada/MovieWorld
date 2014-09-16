package ar.edu.itba.paw.g4.service;

import ar.edu.itba.paw.g4.model.User;
import ar.edu.itba.paw.g4.util.EmailAddress;

public interface UserService{
	
	public boolean userHasSession();
	
	public User getUserById(Integer id);
	
	public void Register(User user);
	
	public void logout(User user);	
	
	public User login(EmailAddress email, String pass);
}
