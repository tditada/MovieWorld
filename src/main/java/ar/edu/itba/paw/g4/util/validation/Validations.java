package ar.edu.itba.paw.g4.util.validation;

import com.google.common.base.Predicate;

public class Validations {
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

}
