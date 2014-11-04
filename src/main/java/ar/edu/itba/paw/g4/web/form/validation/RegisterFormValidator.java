package ar.edu.itba.paw.g4.web.form.validation;

import static ar.edu.itba.paw.g4.util.ObjectHelpers.areEqual;
import static org.joda.time.DateTime.now;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import ar.edu.itba.paw.g4.web.form.RegisterForm;

@Component
public class RegisterFormValidator implements Validator {
	private static final String FIRST_NAME_ID = "firstName";
	private static final String LAST_NAME_ID = "lastName";
	private static final String BIRTH_DATE_ID = "birthDate";
	private static final String EMAIL_ID = "email";
	private static final String PASSWORD_ID = "password";
	private static final String PASSWORD_CONFIRMATION_ID = "passwordConfirmation";

	@Override
	public boolean supports(Class<?> clazz) {
		return RegisterForm.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		RegisterForm form = (RegisterForm) target;
		// CHECK
		// if (!NonArtisticName.isValid(form.getFirstName())) {
		// errors.rejectValue(FIRST_NAME_ID, "invalid");
		// }
		//
		// if (!NonArtisticName.isValid(form.getLastName())) {
		// errors.rejectValue(LAST_NAME_ID, "invalid");
		// }
		//
		// if (!EmailAddress.isValid(form.get)) {
		// errors.rejectValue(arg0, arg1);
		// }
		//
		// if (!Password.isValid(form.getPassword())) {
		// errors.rejectValue(PASS_ID, "invalid");
		// }

		checkSet(FIRST_NAME_ID, form.getFirstName(), errors);
		checkSet(LAST_NAME_ID, form.getLastName(), errors);
		checkSet(BIRTH_DATE_ID, form.getBirthDate(), errors);
		checkSet(EMAIL_ID, form.getEmail(), errors);
		checkSet(PASSWORD_ID, form.getEmail(), errors);
		checkSet(PASSWORD_CONFIRMATION_ID, form.getPasswordConfirmation(),
				errors);

		if (form.getBirthDate() != null && !form.getBirthDate().isBefore(now())) {
			errors.rejectValue(BIRTH_DATE_ID, "after.now",
					"Invalid birth date (after current date)");
		}

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
