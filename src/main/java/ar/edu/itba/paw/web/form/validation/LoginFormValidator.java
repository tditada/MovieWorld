package ar.edu.itba.paw.web.form.validation;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import ar.edu.itba.paw.web.form.LoginForm;

@Component
public class LoginFormValidator implements Validator {
	private static final String EMAIL_ID = "email";
	private static final String PASSWORD_ID = "password";

	@Override
	public boolean supports(Class<?> clazz) {
		return LoginForm.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		LoginForm form = (LoginForm) target;
		checkSet(EMAIL_ID, form.getEmail(), errors);
		checkSet(PASSWORD_ID, form.getPassword(), errors);
	}

	private void checkSet(String name, Object value, Errors errors) {
		if (value == null) {
			errors.rejectValue(name, "invalid", "invalid");
		}
	}
}
