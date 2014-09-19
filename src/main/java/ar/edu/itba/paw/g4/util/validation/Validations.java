package ar.edu.itba.paw.g4.util.validation;

import static ar.edu.itba.paw.g4.util.validation.PredicateHelpers.neitherNullNorEmpty;

import java.util.LinkedList;
import java.util.List;
import ar.edu.itba.paw.g4.enums.*;
import com.google.common.base.Predicate;

public class Validations {
	
	private static int MIN=1;
	private static int MAX_NAME=35;
	private static int MAX_EMAIL=100;
	private static int MAX_PASSWORD=255;
	private static int MIN_BIRTHDAY=8;
	
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
	
	public static List<Boolean> validateLogin(String email, String password){
		List<Boolean> list=new LinkedList<Boolean>();
		for(int i=0;i<=LoginField.maxValue();i++){
			list.add(i,false);
		}
		if(!(email.length() >= MIN && email.length() <= MAX_EMAIL)){
			list.add(LoginField.EMAIL.value, true);
		}else if(!(password.length() >= MIN && email.length() <= MAX_PASSWORD)){
			list.add(LoginField.PASSWORD.value, true);
		}
		return list;
	}
	
	public static List<Boolean> validateRegister(String name, String lastName, String email, String password, String secondPassword, String birthday){
		List<Boolean> list=new LinkedList<Boolean>();
		for(int i=0;i<=RegistrationField.maxValue();i++){
			list.add(i,false);
		}
		if(!(name.length() >= MIN && name.length() <= MAX_NAME)){
			list.add(RegistrationField.NAME.value, true);
		}else if(!(lastName.length() >= MIN && lastName.length() <= MAX_NAME)){
			list.add(RegistrationField.LASTNAME.value, true);
		}else if(!(email.length() >= MIN && email.length() <= MAX_EMAIL)){
			list.add(RegistrationField.EMAIL.value, true);
		}else if(!(password.length() >= MIN && email.length() <= MAX_PASSWORD)){
			list.add(RegistrationField.PASSWORD.value, true);
		}else if(!(secondPassword.length() >= MIN && secondPassword.length() <= MAX_PASSWORD && password.equals(secondPassword))){
			list.add(RegistrationField.SECONDPASSWORD.value, true);
		}else if(!(birthday.length()>= MIN_BIRTHDAY && birthday!=null)){
			list.add(RegistrationField.BIRTHDAY.value, true);
		}
		return list;
	}

}
