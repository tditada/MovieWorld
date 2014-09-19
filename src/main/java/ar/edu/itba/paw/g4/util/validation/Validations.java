package ar.edu.itba.paw.g4.util.validation;

import java.util.List;

import ar.edu.itba.paw.g4.enums.LoginField;
import ar.edu.itba.paw.g4.enums.RegistrationField;

import com.google.common.base.Predicate;

public class Validations {

	private static int MIN = 1;
	private static int MAX_NAME = 35;
	private static int MAX_EMAIL = 100;
	private static int MIN_PASSWORD = 10;
	private static int MAX_PASSWORD = 255;
	private static int MIN_BIRTHDAY = 8;
	private static int MAX_BIRTHDAY = 10;
	

	private static final ExceptionFactory ILLEGAL_ARG_EXCF = new ExceptionFactory() {
		@Override
		public void throwException(String cause) {
			throw new IllegalArgumentException(cause);
		}
	};

	private static final ExceptionFactory NULLP_EXCF = new ExceptionFactory() {
		@Override
		public void throwException(String cause) {
			throw new NullPointerException(cause);
		}
	};

	private static final ExceptionFactory ILLEGAL_STATE_EXCF = new ExceptionFactory() {
		@Override
		public void throwException(String cause) {
			throw new IllegalStateException(cause);
		}
	};

	public static void checkArgument(boolean expr) {
		check(ILLEGAL_ARG_EXCF, expr);
	}

	@SafeVarargs
	public static <T> void checkArgument(T value,
			Predicate<? super T>... predicates) {
		check(ILLEGAL_ARG_EXCF, value, predicates);
	}

	public static void checkState(boolean expr) {
		check(ILLEGAL_STATE_EXCF, expr);
	}

	@SafeVarargs
	public static <T> void checkState(T value,
			Predicate<? super T>... predicates) {
		check(ILLEGAL_STATE_EXCF, value, predicates);
	}

	public static <T> void checkNotNull(T value) {
		check(NULLP_EXCF, value != null);
	}

	private static void check(ExceptionFactory exceptionFactory, boolean expr) {
		if (!expr) {
			exceptionFactory.throwException("Validation failure");
		}
	}

	@SafeVarargs
	private static <T> void check(ExceptionFactory exceptionFactory, T value,
			Predicate<? super T>... predicates) {
		checkArgument(predicates.length > 0);
		int i = 0;
		for (Predicate<? super T> predicate : predicates) {
			if (!predicate.apply(value)) {
				exceptionFactory.throwException("Validation failure "
						+ "(predicate " + i + ")");
			}
			i++;
		}
	}

	public static boolean isLoginValid(String email, String password,
			List<Boolean> errors) {
		int fieldValue = 0;

		if (!(email.length() >= getMIN() && email.length() <= MAX_EMAIL)) {
			fieldValue = LoginField.EMAIL.value;
			errors.add(fieldValue, true);
		} else {
			errors.add(fieldValue, false);
		}

		if (!(password.length() >= getMIN_PASSWORD() && email.length() <= getMAX_PASSWORD())) {
			fieldValue = LoginField.PASSWORD.value;
			errors.add(fieldValue, true);
		} else {
			errors.add(fieldValue, false);
		}
		return !(errors.get(LoginField.EMAIL.value) || errors
				.get(LoginField.PASSWORD.value));
	}

	public static boolean validateRegister(String name, String lastName,
			String email, String password, String secondPassword,
			String birthday, List<Boolean> errors) {
		validateLengthAndOther(name, getMIN(), getMAX_NAME(), errors,
				RegistrationField.NAME.value, isAlpha(name));
		validateLengthAndOther(lastName, getMIN(), getMAX_NAME(), errors,
				RegistrationField.LASTNAME.value, isAlpha(lastName));
		validateLength(email, getMIN(), MAX_EMAIL, errors,
				RegistrationField.EMAIL.value);
		validateLength(password, getMIN_PASSWORD(), getMAX_PASSWORD(), errors,
				RegistrationField.PASSWORD.value);
		validateLengthAndOther(secondPassword, getMIN_PASSWORD(), getMAX_PASSWORD(),
				errors, RegistrationField.SECONDPASSWORD.value,
				password.equals(secondPassword));
		validateLengthAndOther(birthday, MIN_BIRTHDAY, MAX_BIRTHDAY, errors,
				RegistrationField.BIRTHDAY.value, birthday != null);

		return !(errors.get(LoginField.EMAIL.value)
				|| errors.get(LoginField.PASSWORD.value)
				|| errors.get(RegistrationField.NAME.value)
				|| errors.get(RegistrationField.LASTNAME.value)
				|| errors.get(RegistrationField.SECONDPASSWORD.value) || errors
					.get(RegistrationField.BIRTHDAY.value));
	}

	private static void validateLengthAndOther(String param, int min, int max,
			List<Boolean> errors, int fieldValue, boolean secondValidation) {
		if (!(param.length() >= min && param.length() <= max)
				|| !secondValidation) {
			errors.add(fieldValue, true);
		} else {
			errors.add(fieldValue, false);
		}
	}

	private static void validateLength(String param, int min, int max,
			List<Boolean> errors, int fieldValue) {
		validateLengthAndOther(param, min, max, errors, fieldValue, true);
	}

	private static boolean isAlpha(String name) {
		return name.matches("[a-zA-Z]+");
	}

	public static int getMIN_PASSWORD() {
		return MIN_PASSWORD;
	}

	public static void setMIN_PASSWORD(int mIN_PASSWORD) {
		MIN_PASSWORD = mIN_PASSWORD;
	}

	public static int getMAX_PASSWORD() {
		return MAX_PASSWORD;
	}

	public static void setMAX_PASSWORD(int mAX_PASSWORD) {
		MAX_PASSWORD = mAX_PASSWORD;
	}

	public static int getMAX_NAME() {
		return MAX_NAME;
	}

	public static void setMAX_NAME(int mAX_NAME) {
		MAX_NAME = mAX_NAME;
	}

	public static int getMIN() {
		return MIN;
	}

	public static void setMIN(int mIN) {
		MIN = mIN;
	}

}
