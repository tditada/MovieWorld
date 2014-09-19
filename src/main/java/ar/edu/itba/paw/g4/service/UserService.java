package ar.edu.itba.paw.g4.service;

import ar.edu.itba.paw.g4.model.EmailAddress;
import ar.edu.itba.paw.g4.model.User;
public interface UserService{
	
//	public boolean userHasSession();
	
	public User getUserById(Integer id);
	
	public User getUserByEmail(EmailAddress email);
	
	public void register(User user);
	
//	public void logout(User user);	
//	
//	public User login(EmailAddress email, String pass);
	
	public User authenticate(EmailAddress email, String pass);
}