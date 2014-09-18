package ar.edu.itba.paw.g4.service;

import ar.edu.itba.paw.g4.model.User;
import ar.edu.itba.paw.g4.util.EmailAddress;
public interface UserService{
	
//	public boolean userHasSession();
	
	public User getUserById(Integer id);
	
	public void register(User user);
	
//	public void logout(User user);	
//	
//	public User login(EmailAddress email, String pass);
	
	public User authenticate(EmailAddress email, String pass);
}