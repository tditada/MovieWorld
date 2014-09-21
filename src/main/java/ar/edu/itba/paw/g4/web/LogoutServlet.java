package ar.edu.itba.paw.g4.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@SuppressWarnings("serial")
public class LogoutServlet extends HttpServlet {
	private static final String FIRST_NAME_ID = "firstname";
	private static final String LAST_NAME_ID = "lastname";
	private static final String EMAIL_ID = "email";
	private static final String PASS_ID = "password";
	private static final String BIRTHDAY_ID = "birthday";

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		session.setAttribute(FIRST_NAME_ID, null);
		session.setAttribute(EMAIL_ID, null);
		session.setAttribute(LAST_NAME_ID, null);
		session.setAttribute(PASS_ID, null);
		session.setAttribute(BIRTHDAY_ID, null);
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		String redirectUrl = httpResponse.encodeRedirectURL("home");
		httpResponse.sendRedirect(redirectUrl);
	}

}
