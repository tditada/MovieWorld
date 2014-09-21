package ar.edu.itba.paw.g4.service;

import ar.edu.itba.paw.g4.model.EmailAddress;
import ar.edu.itba.paw.g4.model.User;

public interface UserService {

	public User getUserByEmail(EmailAddress email);

	public void register(User user);

	public User authenticate(EmailAddress email, String pass);

}