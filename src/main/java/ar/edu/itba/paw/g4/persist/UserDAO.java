package ar.edu.itba.paw.g4.persist;

import ar.edu.itba.paw.g4.model.EmailAddress;
import ar.edu.itba.paw.g4.model.user.User;
import ar.edu.itba.paw.g4.util.persist.GenericDAO;

public interface UserDAO extends GenericDAO<User> {
	
	public User getByEmail(final String string);
	
	public void save(final User user);
	
	public User getById(final int id);
}
