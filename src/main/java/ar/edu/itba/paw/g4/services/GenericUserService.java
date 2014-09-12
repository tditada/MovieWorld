package ar.edu.itba.paw.g4.services;

import ar.edu.itba.paw.g4.model.User;

public interface GenericUserService{
	
	public boolean userHasSession();
	
	public User getUser(User user);
	
	public void Register(User user);
	
	public void logout(User user);
	
	public void Login(User user); //existsUser + verificar PASS -equals-
}
