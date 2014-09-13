package ar.edu.itba.paw.g4.model;

import static ar.edu.itba.paw.g4.utils.ObjectHelpers.areEqual;
import static ar.edu.itba.paw.g4.utils.ObjectHelpers.hash;
import static ar.edu.itba.paw.g4.utils.ObjectHelpers.toStringHelper;
import static ar.edu.itba.paw.g4.utils.validation.Validations.checkArgument;
import static com.google.common.base.Predicates.*;
import static ar.edu.itba.paw.g4.utils.validation.PredicateExtras.*;
import net.karneim.pojobuilder.GeneratePojoBuilder;

import org.joda.time.DateTime;

import ar.edu.itba.paw.g4.utils.EmailAddress;
import ar.edu.itba.paw.g4.utils.persist.Entity;

public class User extends Entity {
	private String firstName;
	private String lastName;
	private EmailAddress email;
	private String password; /*
							 * TODO: encrypt maybe? md5 is supposed to be easy
							 * TODO: validate min length!
							 */
	private DateTime birthDate;
	private boolean vip;

	@GeneratePojoBuilder
	public User(String firstName, String lastName, EmailAddress email,
			String password, DateTime birthDate, boolean vip) {
		checkArgument(email != null);
		checkArgument(birthDate != null);
		checkArgument(firstName, notNull(), notEmptyStr());
		checkArgument(lastName, notNull(), notEmptyStr());
		checkArgument(password, notNull(), notEmptyStr());

		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.birthDate = birthDate;
		this.vip = vip;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public EmailAddress getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}

	public DateTime getBirthDate() {
		return birthDate;
	}

	public boolean isVip() {
		return vip;
	}

	@Override
	public String toString() {
		return toStringHelper(this).add("firstName", firstName)
				.add("lastName", lastName).add("email", email)
				.add("password", password).add("birthDate", birthDate)
				.add("vip", vip).toString();
	}

	@Override
	public int hashCode() {
		return hash(firstName, lastName, email, password, birthDate, vip);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}
		User that = (User) obj;
		return areEqual(this.firstName, that.firstName)
				&& areEqual(this.lastName, that.lastName)
				&& areEqual(this.email, that.email)
				&& areEqual(this.password, that.password)
				&& areEqual(this.birthDate, that.birthDate)
				&& areEqual(this.vip, that.vip);
	}

	public static UserBuilder builder() {
		return new UserBuilder();
	}
}
