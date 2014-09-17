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
	private static String PASS = "password";
	private static String BIRTHDAY = "birthday";
	private HttpServletRequest request;

	public UserServiceImpl(HttpServletRequest request) {
		this.request = request;
	}

	@Override
	public boolean userHasSession() {
		HttpSession session = request.getSession();
		return SessionAttributesAreNull(session);
	}

	private boolean SessionAttributesAreNull(HttpSession session) {
		return session.getAttribute(NAME_ID) == null
				&& session.getAttribute(LAST_NAME) == null
				&& session.getAttribute(EMAIL_ID) == null
				&& session.getAttribute(PASS) == null
				&& session.getAttribute(BIRTHDAY) == null;
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
	public void Register(User user) {
		// Guarda el usuario en la BD
	}

	@Override
	public void logout(User user) {
		HttpSession session = request.getSession();
		session.setAttribute(NAME_ID, null);
		session.setAttribute(EMAIL_ID, null);

	}

	@Override
	public void Login(User user) {
		HttpSession session = request.getSession();
		session.setAttribute(NAME_ID, user.getFirstName());
		session.setAttribute(EMAIL_ID, user.getLastName());
		session.setAttribute(LAST_NAME, user.getLastName());
		session.setAttribute(PASS, user.getPassword());
		session.setAttribute(BIRTHDAY, user.getBirthDate());
	}

}
