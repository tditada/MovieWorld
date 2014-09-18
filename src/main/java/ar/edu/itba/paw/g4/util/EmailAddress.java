package ar.edu.itba.paw.g4.util;

import static ar.edu.itba.paw.g4.util.ObjectHelpers.areEqual;
import static ar.edu.itba.paw.g4.util.ObjectHelpers.hash;
import static ar.edu.itba.paw.g4.util.ObjectHelpers.toStringHelper;
import static ar.edu.itba.paw.g4.util.validation.PredicateHelpers.neitherNullNorEmpty;
import static ar.edu.itba.paw.g4.util.validation.Validations.checkArgument;

import java.util.regex.Pattern;

import net.karneim.pojobuilder.GeneratePojoBuilder;

public class EmailAddress {
	private static final String EMAIL_PATTERN_STR = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*"
			+ "@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
	private static final Pattern EMAIL_PATTERN = Pattern
			.compile(EMAIL_PATTERN_STR);

	private final String localPart;
	private final String domainPart;

	public static EmailAddress build(String address) {
		checkArgument(address, neitherNullNorEmpty());
		String[] parts = address.split("@");
		if (parts.length != 2) {
			throw new IllegalArgumentException("Malformed email address");
		}
		return new EmailAddress(parts[0], parts[1]);
	}

	private static boolean isValidEmail(String localPart, String domainPart) {
		return EMAIL_PATTERN.matcher(localPart + "@" + domainPart).matches();
	}

	@GeneratePojoBuilder
	public EmailAddress(String localPart, String domainPart) {
		checkArgument(localPart, neitherNullNorEmpty());
		checkArgument(domainPart, neitherNullNorEmpty());
		checkArgument(isValidEmail(localPart, domainPart));
		this.localPart = localPart;
		this.domainPart = domainPart;
	}

	@Override
	public String toString() {
		return toStringHelper(this).add("localPart", localPart)
				.add("domainPart", domainPart).toString();
	}

	@Override
	public int hashCode() {
		return hash(localPart, domainPart);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}
		EmailAddress that = (EmailAddress) obj;
		return areEqual(this.localPart, that.localPart)
				&& areEqual(this.domainPart, that.domainPart);
	}

	public static EmailAddressBuilder builder() {
		return new EmailAddressBuilder();
	}

	public String asTextAddress() { /*
									 * TODO check if this shouldn't either be
									 * merged with toString or a static method
									 */
		return localPart + "@" + domainPart;
	}

}
