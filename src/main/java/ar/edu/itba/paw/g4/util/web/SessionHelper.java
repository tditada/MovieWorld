package ar.edu.itba.paw.g4.util.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import ar.edu.itba.paw.g4.model.User;

public class SessionHelper {
	private static final String FIRST_NAME_ID = "firstname";
	private static final String LAST_NAME_ID = "lastname";
	private static final String EMAIL_ID = "email";
	private static final String PASS_ID = "password";
	private static final String BIRTHDAY_ID = "birthday";

	public static void createUserSession(User user, HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.setAttribute(FIRST_NAME_ID, user.getFirstName());
		session.setAttribute(EMAIL_ID, user.getEmail());
		session.setAttribute(LAST_NAME_ID, user.getLastName());
		session.setAttribute(PASS_ID, user.getPassword());
		session.setAttribute(BIRTHDAY_ID, user.getBirthDate());
	}
}
