package ar.edu.itba.paw.g4.servlet;

import static ar.edu.itba.paw.g4.util.validation.Validations.validateRegister;
import static ar.edu.itba.paw.g4.util.view.ErrorHelper.manageError;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import ar.edu.itba.paw.g4.enums.RegistrationField;
import ar.edu.itba.paw.g4.exception.ServiceException;
import ar.edu.itba.paw.g4.model.User;
import ar.edu.itba.paw.g4.service.UserService;
import ar.edu.itba.paw.g4.service.impl.UserServiceImpl;
import ar.edu.itba.paw.g4.util.EmailAddress;

@SuppressWarnings("serial")
public class RegistrationServlet extends HttpServlet {
	// TODO: mover y que no se pise con otros errors mismo nombre
	private static int ERROR = -1;
	private static int OK = 0;

	private UserService userservice = UserServiceImpl.getInstance();
	private static String NAME_ID = "firstname";
	private static String LASTNAME_ID = "lastname";
	private static String EMAIL_ID = "email";
	private static String PASSWORD_ID = "password";
	private static String SECONDPASSWORD_ID = "secondPassword";
	private static String BIRTHDAY_ID = "birthday";

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.getRequestDispatcher("/WEB-INF/jsp/registration.jsp").forward(req,
				resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		String name = req.getParameter(NAME_ID);
		String lastName = req.getParameter(LASTNAME_ID);
		String email = req.getParameter(EMAIL_ID);
		String password = req.getParameter(PASSWORD_ID);
		String secondPassword = req.getParameter(SECONDPASSWORD_ID);
		String birthday = req.getParameter(BIRTHDAY_ID);

		List<Boolean> errors = new LinkedList<Boolean>();

		DateTime birthDate = new DateTime();
		boolean dateError = false;
		if (formatDate(req, birthDate) == ERROR) {
			req.setAttribute("error" + RegistrationField.BIRTHDAY.value, true);
			dateError = true;
		}

		if (!validateRegister(name, lastName, email, password, secondPassword,
				birthday, errors) || dateError) {
			for (int i = 0; i < errors.size(); i++) {
				int fieldEnum = RegistrationField.values()[i].value;
				if(req.getAttribute("error" + fieldEnum)==null){
					req.setAttribute("error" + fieldEnum, errors.get(i));			
				}
				if (errors.get(i)) {
					setAttributes(req, name, lastName, email, birthday);
				}
			}
			doGet(req, resp);
			return;
		}
		User user = User.builder().withFirstName(name).withLastName(lastName)
				.withPassword(password).withEmail(EmailAddress.build(email))
				.withBirthDate(birthDate).build();
		try {
			userservice.register(user);
			req.getRequestDispatcher("login").forward(req, resp);
		} catch (ServiceException e) {
			manageError(e, req, resp);
		}
	}

	private void setAttributes(HttpServletRequest req, String name,
			String lastName, String email, String birthday) {
		setAttribute(req, NAME_ID, name);
		setAttribute(req, LASTNAME_ID, lastName);
		setAttribute(req, EMAIL_ID, email);
		setAttribute(req, BIRTHDAY_ID, birthday);
	}

	private void setAttribute(HttpServletRequest req, String attrName,
			String attr) {
		req.setAttribute(attrName, attr);
	}

	private int formatDate(HttpServletRequest req, DateTime birthDate) {
		try {
			DateTimeFormatter formatter = DateTimeFormat
					.forPattern("mm-dd-yyyy");
			birthDate = formatter.parseDateTime(req.getParameter(BIRTHDAY_ID));
		} catch (IllegalArgumentException e) {
			return ERROR;
		}
		return OK;
	}
}
