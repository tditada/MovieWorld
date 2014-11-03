package ar.edu.itba.paw.g4.model.user;

import static ar.edu.itba.paw.g4.util.validation.PredicateHelpers.notNull;
import static ar.edu.itba.paw.g4.util.validation.Validations.checkArgument;

import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.classic.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ar.edu.itba.paw.g4.model.AbstractHibernateRepo;
import ar.edu.itba.paw.g4.model.Email;
import ar.edu.itba.paw.g4.model.Password;
import ar.edu.itba.paw.g4.model.comment.Comment;

@Repository
public class HibernateUserRepo extends AbstractHibernateRepo implements
		UserRepo {

	@Autowired
	public HibernateUserRepo(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

	@Override
	public User findById(int id) {
		return get(User.class, id);
	}

	@Override
	public User findUserByEmail(Email email) {
		checkArgument(email, notNull());

		List<User> users = find("from User where email=?", email);
		if (users.isEmpty()) {
			return null;
		}
		return users.get(0);
	}

	@Override
	public void save(User user) {
		super.save(user);
	}

	@Override
	public void register(User user) {
		checkArgument(user, notNull());
		// TODO: check! checkArgument(!user.isPersisted());

		User existentUser = findUserByEmail(user.getEmail());
		if (existentUser != null) {
			// TODO: check!
			throw new RuntimeException("Cannot register an existing user (id="
					+ existentUser.getId() + ")");
		}
		save(user);
	}

	@Override
	public User authenticate(Email email, Password password) {
		checkArgument(email, notNull());
		checkArgument(password, notNull());

		User user = findUserByEmail(email);
		if (user == null || !password.equals(user.getPassword())) {
			// TODO: check!
			// throw new RuntimeException("Wrong Password");
			return null;
		}
		return user;
	}

	// @Override
	// public User getAdmin() {
	// List<User> users = find("from User where isAdmin=TRUE");
	// if (users.isEmpty()) {
	// return null;
	// }
	// return users.get(0);
	// }

	@Override
	public void removeComment(int commentId) {
		Session session = getSession();
		Comment c = get(Comment.class,commentId);
		session.delete(c);
	}

	@Override
	public List<User> findAll() {
		return find("from User");
	}
}
