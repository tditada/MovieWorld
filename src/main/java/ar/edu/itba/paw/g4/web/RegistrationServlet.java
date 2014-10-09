package ar.edu.itba.paw.g4.web;

import static ar.edu.itba.paw.g4.util.web.ErrorHelper.manageError;

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

import ar.edu.itba.paw.g4.exception.ServiceException;
import ar.edu.itba.paw.g4.model.EmailAddress;
import ar.edu.itba.paw.g4.model.User;
import ar.edu.itba.paw.g4.service.UserService;
import ar.edu.itba.paw.g4.service.impl.UserServiceImpl;
import ar.edu.itba.paw.g4.util.web.SessionHelper;

@SuppressWarnings("serial")
public class RegistrationServlet extends HttpServlet {
	private static final int ERROR = -1;
	private static final int OK = 0;

	private static final int MIN = 1;
	private static final int MIN_BIRTHDAY_STR_LEN = 8;
	private static final int MAX_BIRTHDAY_STR_LEN = 10;

	private static final String FIRST_NAME_ID = "firstname";
	private static final String LAST_NAME_ID = "lastname";
	private static final String EMAIL_ID = "email";
	private static final String PASSWORD_ID = "password";
	private static final String SECOND_PASSWORD_ID = "secondPassword";
	private static final String BIRTHDAY_ID = "birthday";
	private static final String BASE_ERROR_ID = "error";
	private static final String USER_ID = "user";

	private final UserService userservice = UserServiceImpl.getInstance();

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		if (request.getAttribute(USER_ID) != null) {
			String redirectUrl = response.encodeRedirectURL("home");
			response.sendRedirect(redirectUrl);
		} else {
			request.getRequestDispatcher("/WEB-INF/jsp/registration.jsp")
					.forward(request, response);
		}
	}

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String name = request.getParameter(FIRST_NAME_ID);
		String lastName = request.getParameter(LAST_NAME_ID);
		String email = request.getParameter(EMAIL_ID);
		String password = request.getParameter(PASSWORD_ID);
		String secondPassword = request.getParameter(SECOND_PASSWORD_ID);
		String birthday = request.getParameter(BIRTHDAY_ID);

		List<Boolean> errors = new LinkedList<Boolean>();

		DateTime birthDate = new DateTime();
		boolean dateError = false;
		boolean emailError = false;
		if (formatDate(request, birthDate) == ERROR) {
			request.setAttribute(
					BASE_ERROR_ID + RegistrationField.BIRTHDAY.ordinal(), true);
			dateError = true;
		}
		if (validFormatEmail(email) == ERROR) {
			emailError = true;
			request.setAttribute(
					BASE_ERROR_ID + RegistrationField.EMAIL.ordinal(), true);
		}

		if (!validateRegister(name, lastName, email, password, secondPassword,
				birthday, errors) || dateError || emailError) {
			for (int i = 0; i < errors.size(); i++) {
				int fieldEnum = RegistrationField.values()[i].ordinal();
				if (request.getAttribute(BASE_ERROR_ID + fieldEnum) == null) {
					request.setAttribute(BASE_ERROR_ID + fieldEnum,
							errors.get(i));
				}
				if (errors.get(i)) {
					setAttributes(request, name, lastName, email, birthday);
				}
			}
			doGet(request, response);
			return;
		}
		User user = User.builder().withFirstName(name).withLastName(lastName)
				.withPassword(password)
				.withEmail(EmailAddress.buildFrom(email))
				.withBirthDate(birthDate).build();
		try {
			userservice.register(user);
			SessionHelper.createUserSession(user, request);
			String redirectUrl = response.encodeRedirectURL("home");
			response.sendRedirect(redirectUrl);
		} catch (ServiceException e) {
			manageError(e, request, response);
		}
	}

	private void setAttributes(HttpServletRequest req, String name,
			String lastName, String email, String birthday) {
		setAttribute(req, FIRST_NAME_ID, name);
		setAttribute(req, LAST_NAME_ID, lastName);
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

	public int validFormatEmail(String email) {
		try {
			EmailAddress.buildFrom(email);
		} catch (IllegalArgumentException e) {
			return ERROR;
		}
		return OK;
	}

	private boolean validateRegister(String name, String lastName,
			String email, String password, String secondPassword,
			String birthday, List<Boolean> errors) {
		validateLengthInRangeAndOther(name, MIN, User.MAX_NAME_LENGTH, errors,
				RegistrationField.NAME.ordinal(),
				User.isValidNonArtisticName(name));
		validateLengthInRangeAndOther(lastName, MIN, User.MAX_NAME_LENGTH,
				errors, RegistrationField.LASTNAME.ordinal(),
				User.isValidNonArtisticName(lastName));
		validateLengthInRange(email, MIN, EmailAddress.MAX_LENGTH, errors,
				RegistrationField.EMAIL.ordinal());
		validateLengthInRange(password, User.MIN_PASSWORD_LENGTH,
				User.MAX_PASSWORD_LENGTH, errors,
				RegistrationField.PASSWORD.ordinal());
		validateLengthInRangeAndOther(secondPassword, User.MIN_PASSWORD_LENGTH,
				User.MAX_PASSWORD_LENGTH, errors,
				RegistrationField.SECONDPASSWORD.ordinal(),
				password.equals(secondPassword));
		validateLengthInRangeAndOther(birthday, MIN_BIRTHDAY_STR_LEN,
				MAX_BIRTHDAY_STR_LEN, errors,
				RegistrationField.BIRTHDAY.ordinal(), birthday != null);

		return !(errors.get(LoginField.EMAIL.ordinal())
				|| errors.get(LoginField.PASSWORD.ordinal())
				|| errors.get(RegistrationField.NAME.ordinal())
				|| errors.get(RegistrationField.LASTNAME.ordinal())
				|| errors.get(RegistrationField.SECONDPASSWORD.ordinal()) || errors
					.get(RegistrationField.BIRTHDAY.ordinal()));
	}

	private void validateLengthInRangeAndOther(String param, int min, int max,
			List<Boolean> errors, int fieldValue, boolean secondValidation) {
		if (!(param.length() >= min && param.length() <= max)
				|| !secondValidation) {
			errors.add(fieldValue, true);
		} else {
			errors.add(fieldValue, false);
		}
	}

	private void validateLengthInRange(String param, int min, int max,
			List<Boolean> errors, int fieldValue) {
		validateLengthInRangeAndOther(param, min, max, errors, fieldValue, true);
	}

	private enum RegistrationField {
		NAME, LASTNAME, EMAIL, PASSWORD, SECONDPASSWORD, BIRTHDAY;
	}
}
