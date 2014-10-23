package ar.edu.itba.paw.g4.model.user;

import ar.edu.itba.paw.g4.model.Comment;
import ar.edu.itba.paw.g4.model.EmailAddress;
import ar.edu.itba.paw.g4.model.Password;

public interface UserRepo {
	public User findById(final int id);

	public User findUserByEmail(String email);

	public void save(final User user);

	public void register(User user);

	public User authenticate(EmailAddress email, Password password);

	public void addComment(Comment comment);
}
