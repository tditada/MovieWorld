package ar.edu.itba.paw.model.user;

import static ar.edu.itba.paw.util.ObjectHelpers.areEqual;
import static ar.edu.itba.paw.util.ObjectHelpers.hash;
import static ar.edu.itba.paw.util.ObjectHelpers.toStringHelper;
import static ar.edu.itba.paw.util.validation.PredicateHelpers.notNull;
import static ar.edu.itba.paw.util.validation.Validations.checkArgument;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.hibernate.annotations.Check;

import com.google.common.collect.Range;

@Embeddable
public class Password {
	public static final int MIN_PASSWORD_LENGTH = 10;
	public static final int MAX_PASSWORD_LENGTH = 255;

	private static final Range<Integer> PASSWORD_LENGTH_RANGE = Range.closed(
			MIN_PASSWORD_LENGTH, MAX_PASSWORD_LENGTH);

	@Check(constraints = "length(password) >= 10")
	@Column(name = "password", length = MAX_PASSWORD_LENGTH, nullable = false)
	private String passwordString;

	Password() {
	}

	public Password(String passwordString) {
		checkArgument(isValid(passwordString));
		this.passwordString = passwordString;
	}

	public static boolean isValid(String password) {
		return notNull().apply(password)
				&& PASSWORD_LENGTH_RANGE.contains(password.length());
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
