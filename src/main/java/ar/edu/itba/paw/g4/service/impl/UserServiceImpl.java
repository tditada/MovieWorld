package ar.edu.itba.paw.g4.service.impl;

import ar.edu.itba.paw.g4.exception.ServiceException;
import ar.edu.itba.paw.g4.model.EmailAddress;
import ar.edu.itba.paw.g4.model.User;
import ar.edu.itba.paw.g4.persist.UserDAO;
import ar.edu.itba.paw.g4.persist.impl.PSQLUserDAO;
import ar.edu.itba.paw.g4.service.UserService;

//TODO: Pasar session
public class UserServiceImpl implements UserService {

	private UserDAO userdao = PSQLUserDAO.getInstance();
	private static UserService instance = new UserServiceImpl();
	private static String NAME_ID = "name";
	private static String LAST_NAME = "lastname";
	private static String EMAIL_ID = "email";
	private static String PASS = "password";
	private static String BIRTHDAY = "birthday";

	private UserServiceImpl() {
	}

	public static UserService getInstance() {
		return instance;
	}

	@Override
	public User getUserById(Integer id) {
		userdao.getById(id);
		return null;
	}

	@Override
	public void register(User user) {
		User dbUser = userdao.getByEmail(user.getEmail());
		if (dbUser == null) {
			userdao.save(user); 
		} else {
			throw new ServiceException("Usuario ya existente");
		}

	}

	@Override
	public User getUserByEmail(EmailAddress email) {
		User user = userdao.getByEmail(email);
		if (user == null) {
			throw new ServiceException("Invalid user");
		}
		return user;
	}

	@Override
	public User authenticate(EmailAddress email, String pass) {
		User user = getUserByEmail(email);
		if (!checkPassword(user, pass)) {
			throw new ServiceException("Wrong Password");
		}
		return user;
	}

	private boolean checkPassword(User user, String pass) {
		return user.getPassword().equals(pass);
	}

	// public void logout(User user) {
	// // HttpSession session = request.getSession();
	// // session.setAttribute(NAME_ID, null);
	// // session.setAttribute(EMAIL_ID, null);
	// }
	//
	// public User login(EmailAddress email, String pass) {
	// // User user=authentication(email,pass);
	// // CreateUserSession(user);
	// // return user
	// return null;
	// }

	// private boolean SessionAttributesAreNull(HttpSession session){
	// return session.getAttribute(NAME_ID)==null &&
	// session.getAttribute(LAST_NAME)==null &&
	// session.getAttribute(EMAIL_ID)==null && session.getAttribute(PASS)==null
	// && session.getAttribute(BIRTHDAY)==null;
	// }

	// private void CreateUserSession(User user) {
	// HttpSession session = request.getSession();
	// session.setAttribute(NAME_ID, user.getFirstName());
	// session.setAttribute(EMAIL_ID, user.getLastName());
	// session.setAttribute(LAST_NAME, user.getLastName());
	// session.setAttribute(PASS, user.getPassword());
	// session.setAttribute(BIRTHDAY, user.getBirthDate());
	// }

	// @Override
	// public boolean userHasSession() {
	// return false;
	// // HttpSession session = request.getSession();
	// // return SessionAttributesAreNull(session);
	// }

}
