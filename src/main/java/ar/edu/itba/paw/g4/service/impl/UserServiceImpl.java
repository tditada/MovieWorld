package ar.edu.itba.paw.g4.service.impl;

import static ar.edu.itba.paw.g4.util.validation.PredicateHelpers.notNull;
import static ar.edu.itba.paw.g4.util.validation.Validations.checkArgument;
import ar.edu.itba.paw.g4.exception.ServiceException;
import ar.edu.itba.paw.g4.model.EmailAddress;
import ar.edu.itba.paw.g4.model.User;
import ar.edu.itba.paw.g4.persist.UserDAO;
import ar.edu.itba.paw.g4.persist.impl.PSQLUserDAO;
import ar.edu.itba.paw.g4.service.UserService;

public class UserServiceImpl implements UserService {
	private static UserService instance = new UserServiceImpl();

	private final UserDAO userDAO = PSQLUserDAO.getInstance();

	private UserServiceImpl() {
	}

	public static UserService getInstance() {
		return instance;
	}

	@Override
	public void register(User user) {
		checkArgument(user, notNull());
		checkArgument(!user.isPersisted());

		User dbUser = userDAO.getByEmail(user.getEmail());
		if (dbUser != null) {
			throw new ServiceException("Cannot register an existing user (id="
					+ dbUser.getId() + ")");
		}
		userDAO.save(user);
	}

	@Override
	public User getUserByEmail(EmailAddress email) {
		checkArgument(email, notNull());

		User user = userDAO.getByEmail(email);
		if (user == null) {
			throw new ServiceException("Invalid user");
		}
		return user;
	}

	@Override
	public User authenticate(EmailAddress email, String pass) {
		checkArgument(email, notNull());
		checkArgument(pass, notNull());

		User user = getUserByEmail(email);
		if (!user.getPassword().equals(pass)) {
			throw new ServiceException("Wrong Password");
		}
		return user;
	}
}
