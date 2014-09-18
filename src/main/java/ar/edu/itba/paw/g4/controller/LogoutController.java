package ar.edu.itba.paw.g4.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@SuppressWarnings("serial")
public class LogoutController extends HttpServlet {
	// private UserService userService = UserServiceImpl.getInstance();
	private static String NAME_ID = "firstname";
	private static String LASTNAME_ID = "lastname";
	private static String EMAIL_ID = "email";
	private static String PASS_ID = "password";
	private static String BIRTHDAY_ID = "birthday";

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		HttpSession session = req.getSession();
		session.setAttribute(NAME_ID, null);
		session.setAttribute(EMAIL_ID, null);
		session.setAttribute(LASTNAME_ID, null);
		session.setAttribute(PASS_ID, null);
		session.setAttribute(BIRTHDAY_ID, null);
		req.getRequestDispatcher(req.getHeader("referer"));
	}

}