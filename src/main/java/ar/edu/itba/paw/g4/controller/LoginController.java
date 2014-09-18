package ar.edu.itba.paw.g4.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ar.edu.itba.paw.g4.model.User;
import ar.edu.itba.paw.g4.service.UserService;
import ar.edu.itba.paw.g4.service.impl.UserServiceImpl;
import ar.edu.itba.paw.g4.util.EmailAddress;

@SuppressWarnings("serial")
public class LoginController extends HttpServlet {
	private UserService userService = UserServiceImpl.getInstance();
	private static UserService instance;
	private static String NAME_ID = "firstname";
	private static String LASTNAME_ID = "lastname";
	private static String EMAIL_ID = "email";
	private static String PASS_ID = "password";
	private static String PASS2_ID = "secondPassword";
	private static String BIRTHDAY_ID = "birthday";

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(req, resp);
	}

	// TODO: Validaciones (paso booleans en el request y que el jsp verifique)
	// ¿Manejo de errores?
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String emailParam = req.getParameter(EMAIL_ID);
		EmailAddress email = EmailAddress.build(emailParam);
		String password = req.getParameter(PASS_ID);
		User user = userService.authenticate(email, password);
		createUserSession(user, req);
		req.getRequestDispatcher(req.getHeader("referer")).forward(req, resp);
	}

	// QUE RECIBA SESSION
	private void createUserSession(User user, HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.setAttribute(NAME_ID, user.getFirstName());
		session.setAttribute(EMAIL_ID, user.getLastName());
		session.setAttribute(LASTNAME_ID, user.getLastName());
		session.setAttribute(PASS_ID, user.getPassword());
		session.setAttribute(BIRTHDAY_ID, user.getBirthDate());
	}

}
