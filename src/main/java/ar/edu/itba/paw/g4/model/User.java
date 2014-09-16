package ar.edu.itba.paw.g4.model;

import static ar.edu.itba.paw.g4.util.ObjectHelpers.areEqual;
import static ar.edu.itba.paw.g4.util.ObjectHelpers.hash;
import static ar.edu.itba.paw.g4.util.ObjectHelpers.toStringHelper;
import static ar.edu.itba.paw.g4.util.validation.PredicateHelpers.neitherNullNorEmpty;
import static ar.edu.itba.paw.g4.util.validation.PredicateHelpers.notNull;
import static ar.edu.itba.paw.g4.util.validation.Validations.checkArgument;
import net.karneim.pojobuilder.GeneratePojoBuilder;

import org.joda.time.DateTime;

import ar.edu.itba.paw.g4.util.EmailAddress;
import ar.edu.itba.paw.g4.util.persist.Entity;

public class User extends Entity {
	private static final int MIN_PASSWORD_LENGTH = 10;

	private String firstName;
	private String lastName;
	private EmailAddress email;
	private String password;
	private DateTime birthDate;

	@GeneratePojoBuilder
	public User(String firstName, String lastName, EmailAddress email,
			String password, DateTime birthDate) {
		checkArgument(email, notNull());
		checkArgument(birthDate, notNull());
		checkArgument(firstName, neitherNullNorEmpty());
		checkArgument(lastName, neitherNullNorEmpty());
		checkArgument(password, notNull());
		checkArgument(password.length() >= MIN_PASSWORD_LENGTH);

		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.birthDate = birthDate;
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

	@Override
	public String toString() {
		return toStringHelper(this).add("id", getId())
				.add("firstName", firstName).add("lastName", lastName)
				.add("email", email).add("password", password)
				.add("birthDate", birthDate).toString();
	}

	@Override
	public int hashCode() {
		return hash(firstName, lastName, email, password, birthDate);
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
				&& areEqual(this.birthDate, that.birthDate);
	}

	public static UserBuilder builder() {
		return new UserBuilder();
	}
}
