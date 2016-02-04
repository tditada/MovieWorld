package ar.edu.itba.paw.model.user;

import static ar.edu.itba.paw.util.ObjectHelpers.areEqual;
import static ar.edu.itba.paw.util.ObjectHelpers.hash;
import static ar.edu.itba.paw.util.ObjectHelpers.toStringHelper;
import static ar.edu.itba.paw.util.validation.PredicateHelpers.neitherNullNorEmpty;
import static ar.edu.itba.paw.util.validation.Validations.checkArgument;
import static java.util.regex.Pattern.compile;

import java.io.Serializable;
import java.util.regex.Pattern;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.hibernate.annotations.Check;

@Embeddable
public class Email implements Serializable{
	public static final int MAX_ADDRESS_LENGTH = 100;
	private static final String EMAIL_PATTERN_STR = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*"
			+ "@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";

	private static final Pattern EMAIL_PATTERN = compile(EMAIL_PATTERN_STR);

	@Check(constraints = "length(email) > 0")
	@Column(name = "email", length = MAX_ADDRESS_LENGTH, nullable = false)
	private String textAddress;

	Email() {
	}

	public Email(String textAddress) {
		checkArgument(isValid(textAddress));
		this.textAddress = textAddress;
	}

	public static boolean isValid(String textAddress) {
		return neitherNullNorEmpty().apply(textAddress)
				&& textAddress.length() <= MAX_ADDRESS_LENGTH
				&& EMAIL_PATTERN.matcher(textAddress).matches();
	}

	public String getTextAddress() {
		return textAddress;
	}

	@Override
	public String toString() {
		return toStringHelper(this).add("textAddress", textAddress).toString();
	}

	@Override
	public int hashCode() {
		return hash(textAddress);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}
		Email that = (Email) obj;
		return areEqual(this.textAddress, that.textAddress);
	}

}
