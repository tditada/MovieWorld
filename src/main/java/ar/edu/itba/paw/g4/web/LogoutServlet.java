package ar.edu.itba.paw.g4.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@SuppressWarnings("serial")
public class LogoutServlet extends HttpServlet {
	// private UserService userService = UserServiceImpl.getInstance();
	private static String NAME_ID = "firstname";
	private static String LASTNAME_ID = "lastname";
	private static String EMAIL_ID = "email";
	private static String PASS_ID = "password";
	private static String BIRTHDAY_ID = "birthday";

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		session.setAttribute(NAME_ID, null);
		session.setAttribute(EMAIL_ID, null);
		session.setAttribute(LASTNAME_ID, null);
		session.setAttribute(PASS_ID, null);
		session.setAttribute(BIRTHDAY_ID, null);
		String redirectUrl = ((HttpServletResponse) response)
				.encodeRedirectURL("home");
		((HttpServletResponse) response).sendRedirect(redirectUrl);
	}

}
