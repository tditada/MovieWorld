package ar.edu.itba.paw.g4.service.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import ar.edu.itba.paw.g4.model.User;
import ar.edu.itba.paw.g4.service.UserService;

public class UserServiceImpl implements UserService {


	// private UserDAO userdao=UserDAO.getInstance();
	private static String NAME_ID = "name";
	private static String LAST_NAME = "lastname";
	private static String EMAIL_ID = "email";
	private static String PASS_ID = "password";
	private static String BIRTHDAY_ID = "birthday";
	private HttpServletRequest request; // XXX

	public UserServiceImpl(HttpServletRequest request) {
		this.request = request; // XXX
	}

	@Override
	public boolean userHasSession() {
		HttpSession session = request.getSession();
		return sessionAttributesAreNull(session);
	}

	private boolean sessionAttributesAreNull(HttpSession session) {
		return session.getAttribute(NAME_ID) == null
				&& session.getAttribute(LAST_NAME) == null
				&& session.getAttribute(EMAIL_ID) == null
				&& session.getAttribute(PASS_ID) == null
				&& session.getAttribute(BIRTHDAY_ID) == null;
	}

	private boolean userExists(User user) {
		// Revisa la BD para ver si el usuario existe
		return false;
	}

	@Override
	public User getUser(User user) {
		// userDAO.getUser(user.getId());
		return null;
	}

	@Override
	public void register(User user) {
		// Guarda el usuario en la BD
	}

	@Override
	public void logout(User user) {
		HttpSession session = request.getSession();
		session.setAttribute(NAME_ID, null);
		session.setAttribute(EMAIL_ID, null);

	}

	@Override
	public void login(User user) {
		HttpSession session = request.getSession();
		session.setAttribute(NAME_ID, user.getFirstName());
		session.setAttribute(EMAIL_ID, user.getLastName());
		session.setAttribute(LAST_NAME, user.getLastName());
		session.setAttribute(PASS_ID, user.getPassword());
		session.setAttribute(BIRTHDAY_ID, user.getBirthDate());
	}

}
