package ar.edu.itba.paw.g4.model;

import static ar.edu.itba.paw.g4.util.ObjectHelpers.areEqual;
import static ar.edu.itba.paw.g4.util.ObjectHelpers.hash;
import static ar.edu.itba.paw.g4.util.ObjectHelpers.toStringHelper;
import static ar.edu.itba.paw.g4.util.validation.PredicateHelpers.notNull;
import static ar.edu.itba.paw.g4.util.validation.Validations.checkArgument;
import static com.google.common.collect.BoundType.CLOSED;
import static com.google.common.collect.Range.range;

import com.google.common.collect.Range;

public class Password {
	public static final int MIN_PASSWORD_LENGTH = 10;
	public static final int MAX_PASSWORD_LENGTH = 255;

	private static final Range<Integer> PASSWORD_LENGTH_RANGE = range(
			MIN_PASSWORD_LENGTH, CLOSED, MAX_PASSWORD_LENGTH, CLOSED);

	private String passwordString;
	
	public Password() {
	}

	public static boolean isValid(String password) {
		return notNull().apply(password)
				&& PASSWORD_LENGTH_RANGE.contains(password.length());
	}

	public Password(String passwordString) {
		checkArgument(isValid(passwordString));
		this.passwordString = passwordString;
	}

	public String getPasswordString() {
		return passwordString;
	}

	@Override
	public int hashCode() {
		return hash(passwordString);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Password other = (Password) obj;
		return areEqual(this.passwordString, other.passwordString);
	}

	@Override
	public String toString() {
		return toStringHelper(this).add("passwordString", passwordString)
				.toString();
	}

}
