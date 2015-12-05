package ar.edu.itba.paw.web.form.validation;

import static ar.edu.itba.paw.util.ObjectHelpers.areEqual;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import ar.edu.itba.paw.model.user.Email;
import ar.edu.itba.paw.model.user.UserRepo;
import ar.edu.itba.paw.web.form.RegisterForm;

@Component
public class RegisterFormValidator implements Validator {
	private static final String FIRST_NAME_ID = "firstName";
	private static final String LAST_NAME_ID = "lastName";
	private static final String BIRTH_DATE_ID = "birthDate";
	private static final String EMAIL_ID = "email";
	private static final String PASSWORD_ID = "password";
	private static final String PASSWORD_CONFIRMATION_ID = "passwordConfirmation";

	private static final String USER_EXISTS_ID = "userExists";

	private UserRepo users;

	@Autowired
	public RegisterFormValidator(UserRepo users) {
		this.users = users;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return RegisterForm.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		RegisterForm form = (RegisterForm) target;
		checkSet(FIRST_NAME_ID, form.getFirstName(), errors);
		checkSet(LAST_NAME_ID, form.getLastName(), errors);
		checkSet(BIRTH_DATE_ID, form.getBirthDate(), errors);

		Email email = form.getEmail();
		checkSet(EMAIL_ID, email, errors);

		if (email != null && users.findUserByEmail(email) != null) {
			errors.reject(USER_EXISTS_ID, "User exists");
		}

		checkSet(PASSWORD_ID, form.getPassword(), errors);

		if (form.getBirthDate() != null && !form.getBirthDate().isBeforeNow()) {
			errors.rejectValue(BIRTH_DATE_ID, "after.now",
					"Invalid birth date (after current date)");
		}

		checkSet(PASSWORD_CONFIRMATION_ID, form.getPasswordConfirmation(),
				errors);

		if (!areEqual(form.getPassword(), form.getPasswordConfirmation())) {
			errors.rejectValue(PASSWORD_CONFIRMATION_ID, "not.matching",
					"Passwords must match");
		}
	}

	private void checkSet(String name, Object value, Errors errors) {
		if (value == null) {
			errors.rejectValue(name, "invalid", "invalid");
		}
	}
}
