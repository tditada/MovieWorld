package ar.edu.itba.paw.g4.services;

import ar.edu.itba.paw.g4.model.User;
import ar.edu.itba.paw.g4.util.EmailAddress;

public interface GenericUserService{
	
	
	public boolean userHasSession();
	
	public User getUserById(Integer id);
	
	public User getUserLogin(EmailAddress email, String pass);
	
	public void Register(User user);
	
	public void logout(User user);
	
	public void Login(User user); //existsUser + verificar PASS -equals-
}
