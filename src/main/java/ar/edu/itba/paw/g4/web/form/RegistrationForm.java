package ar.edu.itba.paw.g4.web.form;

import static ar.edu.itba.paw.g4.util.validation.PredicateHelpers.notNull;
import static ar.edu.itba.paw.g4.util.validation.Validations.checkArgument;

import java.util.Map;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import ar.edu.itba.paw.g4.model.EmailAddress;
import ar.edu.itba.paw.g4.model.User;
import ar.edu.itba.paw.g4.web.form.RegistrationForm.RegistrationFormFields;

public class RegistrationForm extends AbstractForm<RegistrationFormFields> {
	private static final String DATE_TIME_FORMAT = "mm-dd-yyyy";
	private static final DateTimeFormatter dateTimeFormatter = DateTimeFormat
			.forPattern(DATE_TIME_FORMAT);

	private Map<String, String> params;

	public static RegistrationForm extractFrom(Map<String, String> params) {
		checkArgument(params, notNull());
		return new RegistrationForm(params);
	}

	private RegistrationForm(Map<String, String> params) {
		super(RegistrationFormFields.class);
		this.params = params;
	}

	@Override
	public boolean isValidField(RegistrationFormFields field) {
		String fieldValue = getFieldValue(field);
		switch (field) {
		case FIRST_NAME:
			return User.isValidNonArtisticName(fieldValue);
		case LAST_NAME:
			return User.isValidNonArtisticName(fieldValue);
		case EMAIL:
			return EmailAddress.isValidAddress(fieldValue);
		case PASSWORD:
		case SECOND_PASSWORD:
			return isValidPassword(fieldValue,
					params.get(RegistrationFormFields.SECOND_PASSWORD));
		case BIRTH_DATE:
			return isValidBirthDate(fieldValue);
		default:
			// Should never happen
			throw new IllegalArgumentException(
					"There is no validation for field " + field);
		}
	}

	public EmailAddress getEmailAddress() {
		return EmailAddress
				.buildFrom(getFieldValue(RegistrationFormFields.EMAIL));
	}

	public DateTime getBirthDate() {
		return dateTimeFormatter
				.parseDateTime(getFieldValue(RegistrationFormFields.BIRTH_DATE));
	}

	@Override
	public String getFieldValue(RegistrationFormFields field) {
		return params.get(field.id);
	}

	@Override
	public String getFieldKey(RegistrationFormFields field) {
		return params.get(field.id);
	}

	private boolean isValidPassword(String password, String secondPassword) {
		return User.isValidPassword(password)
				&& password.equals(secondPassword);
	}

	private boolean isValidBirthDate(String birthDate) {
		try {
			dateTimeFormatter.parseDateTime(birthDate);
			return true;
		} catch (IllegalArgumentException e) {
			return false;
		}
	}

	public enum RegistrationFormFields {
		FIRST_NAME("firstname"), LAST_NAME("lastname"), EMAIL("email"), PASSWORD(
				"password"), SECOND_PASSWORD("secondPassword"), BIRTH_DATE(
				"birthday");

		private String id;

		private RegistrationFormFields(String id) {
			this.id = id;
		}
	}

}
