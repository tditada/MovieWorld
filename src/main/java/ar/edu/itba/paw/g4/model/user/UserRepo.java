package ar.edu.itba.paw.g4.model.user;

import java.util.List;

public interface UserRepo {
	public User findById(final int id);

	public User findUserByEmail(Email email);

	public void register(User user);

	public User authenticate(Email email, Password password);

	public void save(final User user);

	// public User getAdmin();

	public List<User> findAll();
}
