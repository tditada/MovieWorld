package ar.edu.itba.paw.g4.controller;

import static ar.edu.itba.paw.g4.util.view.ErrorHelper.manageError;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ar.edu.itba.paw.g4.enums.LoginField;
import ar.edu.itba.paw.g4.exception.ServiceException;
import ar.edu.itba.paw.g4.model.EmailAddress;
import ar.edu.itba.paw.g4.model.User;
import ar.edu.itba.paw.g4.service.UserService;
import ar.edu.itba.paw.g4.service.impl.UserServiceImpl;

@SuppressWarnings("serial")
public class LoginController extends HttpServlet {
	private UserService userService = UserServiceImpl.getInstance();
	private static String NAME_ID = "firstname";
	private static String LASTNAME_ID = "lastname";
	private static String EMAIL_ID = "email";
	private static String PASS_ID = "password";
	private static String BIRTHDAY_ID = "birthday";

	private static String REFERER_ID = "referer";
	private static String BASE_ERROR_ID = "error";

	private static final int MIN = 1;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(req, resp);
	}

	// TODO: Validaciones (que el jsp verifique)
	// ¿Manejo de errores?
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		try {
			String emailParam = req.getParameter(EMAIL_ID);
			EmailAddress email = EmailAddress.buildFrom(emailParam);
			String password = req.getParameter(PASS_ID);
			List<Boolean> errors = new LinkedList<Boolean>();

			if (!isLoginValid(emailParam, password, errors)) {
				for (int i = 0; i < errors.size(); i++) {
					int fieldEnum = LoginField.values()[i].value;
					req.setAttribute(BASE_ERROR_ID + fieldEnum, errors.get(i));
				}
				req.setAttribute(EMAIL_ID, emailParam);
				req.setAttribute(PASS_ID, password);
				doGet(req, resp);
			}

			User user = userService.authenticate(email, password);
			createUserSession(user, req);
			if (req.getHeader(REFERER_ID).equals(
					"http://localhost:8081/MovieWorld/login")) { // XXX
				resp.sendRedirect("home");

			} else {
				resp.sendRedirect(req.getHeader(REFERER_ID));
			}
		} catch (ServiceException e) {
			manageError(e, req, resp);
		}
	}

	public static boolean isLoginValid(String email, String password,
			List<Boolean> errors) {
		int fieldValue = 0;

		if (email.length() >= MIN && email.length() <= EmailAddress.MAX_LENGTH) {
			errors.add(fieldValue, false);
		} else {
			fieldValue = LoginField.EMAIL.value;
			errors.add(fieldValue, true);
		}

		if (password.length() >= User.MIN_PASSWORD_LENGTH
				&& email.length() <= User.MAX_PASSWORD_LENGTH) {
			errors.add(fieldValue, false);
		} else {
			fieldValue = LoginField.PASSWORD.value;
			errors.add(fieldValue, true);
		}
		return !(errors.get(LoginField.EMAIL.value) || errors
				.get(LoginField.PASSWORD.value));
	}

	private void createUserSession(User user, HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.setAttribute(NAME_ID, user.getFirstName());
		session.setAttribute(EMAIL_ID, user.getEmail());
		session.setAttribute(LASTNAME_ID, user.getLastName());
		session.setAttribute(PASS_ID, user.getPassword());
		session.setAttribute(BIRTHDAY_ID, user.getBirthDate());
	}

}
