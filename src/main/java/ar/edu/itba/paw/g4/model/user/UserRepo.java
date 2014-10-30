package ar.edu.itba.paw.g4.model.user;

import ar.edu.itba.paw.g4.model.Email;
import ar.edu.itba.paw.g4.model.Password;

public interface UserRepo {
	public User findById(final int id);

	public User findUserByEmail(Email email);

	public void register(User user);

	public User authenticate(Email email, Password password);

	public void save(final User user);
	
	public User getAdmin();
}
