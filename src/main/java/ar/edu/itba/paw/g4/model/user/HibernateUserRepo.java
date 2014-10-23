package ar.edu.itba.paw.g4.model.user;

import static ar.edu.itba.paw.g4.util.validation.PredicateHelpers.notNull;
import static ar.edu.itba.paw.g4.util.validation.Validations.checkArgument;
import static org.joda.time.DateTime.now;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import ar.edu.itba.paw.g4.model.AbstractHibernateRepo;
import ar.edu.itba.paw.g4.model.Comment;
import ar.edu.itba.paw.g4.model.EmailAddress;
import ar.edu.itba.paw.g4.model.Password;

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
	public User findUserByEmail(String email) {
		checkArgument(email, notNull());

		List<User> users = find("from user where email=?", email);
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
	public User authenticate(EmailAddress email, Password password) {
		checkArgument(email, notNull());
		checkArgument(password, notNull());

		User user = findUserByEmail(email.asTextAddress());
		if (!user.getPassword().equals(password)) {
			// TODO: check!
			throw new RuntimeException("Wrong Password");
		}
		return user;
	}

	@Override
	public void addComment(Comment comment) {
		checkArgument(comment, notNull());
		
		User user = comment.getUser();
		user.addComment(comment);

	}
}
