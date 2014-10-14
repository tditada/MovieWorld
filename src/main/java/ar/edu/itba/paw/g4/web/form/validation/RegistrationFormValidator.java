package ar.edu.itba.paw.g4.web.form.validation;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import ar.edu.itba.paw.g4.web.form.RegistrationForm;

@Component
public class RegistrationFormValidator implements Validator {
	// private static final String FIRST_NAME_ID = "firstname";
	// private static final String LAST_NAME_ID = "lastname";
	// private static final String BIRTH_DATE_ID = "birthday";
	// private static final String EMAIL_ID = "email";
	// private static final String PASS_ID = "password";
	private static final String PASS_CONFIRMATION_ID = "secondPassword";

	@Override
	public boolean supports(Class<?> clazz) {
		return RegistrationForm.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		RegistrationForm form = (RegistrationForm) target;
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

		if (!form.getPassword().equals(form.getPasswordConfirmation())) {
			errors.rejectValue(PASS_CONFIRMATION_ID, "not.matching");
		}
	}
}
