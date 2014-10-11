package ar.edu.itba.paw.g4.web.form;

import static ar.edu.itba.paw.g4.util.validation.PredicateHelpers.notNull;
import static ar.edu.itba.paw.g4.util.validation.Validations.checkArgument;

import java.util.Map;

import ar.edu.itba.paw.g4.model.EmailAddress;
import ar.edu.itba.paw.g4.model.User;
import ar.edu.itba.paw.g4.web.form.LoginForm.LoginFormFields;

public class LoginForm extends AbstractForm<LoginFormFields> {
	private Map<String, String> params;

	public static LoginForm extractFrom(Map<String, String> params) {
		checkArgument(params, notNull());
		return new LoginForm(params);
	}

	private LoginForm(Map<String, String> params) {
		super(LoginFormFields.class);
		this.params = params;
	}

	@Override
	protected boolean isValidField(LoginFormFields field) {
		switch (field) {
		case EMAIL:
			return EmailAddress.isValidAddress(getFieldValue(field));
		case PASSWORD:
			return User.isValidPassword(getFieldValue(field));
		}
		return false;
	}

	@Override
	public String getFieldKey(LoginFormFields field) {
		return field.id;
	}

	@Override
	public String getFieldValue(LoginFormFields field) {
		return params.get(field.id);
	}

	public EmailAddress getEmailAddress() {
		return EmailAddress.buildFrom(LoginFormFields.EMAIL.id);
	}

	public enum LoginFormFields {
		EMAIL("email"), PASSWORD("password");

		private String id;

		private LoginFormFields(String id) {
			this.id = id;
		}
	}
}
