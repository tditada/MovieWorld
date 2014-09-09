package ar.edu.itba.paw.g4.utils;

import static ar.edu.itba.paw.g4.utils.ObjectHelpers.equal;
import static ar.edu.itba.paw.g4.utils.ObjectHelpers.hash;
import static ar.edu.itba.paw.g4.utils.ObjectHelpers.toStringHelper;
import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import java.util.regex.Pattern;

import net.karneim.pojobuilder.GeneratePojoBuilder;

public class EmailAddress {
	private static final String EMAIL_PATTERN_STR = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*"
			+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	private static final Pattern EMAIL_PATTERN = Pattern
			.compile(EMAIL_PATTERN_STR);

	private final String localPart;
	private final String domainPart;

	@GeneratePojoBuilder
	public EmailAddress(String localPart, String domainPart) {
		checkNotNull(localPart);
		checkNotNull(domainPart);
		checkArgument(isValidEmail(localPart, domainPart));
		this.localPart = localPart;
		this.domainPart = domainPart;
	}

	private static boolean isValidEmail(String localPart, String domainPart) {
		return EMAIL_PATTERN.matcher(localPart + "@" + domainPart).matches();
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
		return equal(this.localPart, that.localPart)
				&& equal(this.domainPart, that.domainPart);
	}

}
