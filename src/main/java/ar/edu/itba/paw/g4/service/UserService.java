package ar.edu.itba.paw.g4.service;

import ar.edu.itba.paw.g4.model.User;

public interface UserService{
	
	public boolean userHasSession();
	
	public User getUser(User user);
	
	public void register(User user);
	
	public void logout(User user);
	
	public void login(User user); //existsUser + verificar PASS -equals-
}
