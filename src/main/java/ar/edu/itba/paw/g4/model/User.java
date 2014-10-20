package ar.edu.itba.paw.g4.model;

import static ar.edu.itba.paw.g4.util.ObjectHelpers.areEqual;
import static ar.edu.itba.paw.g4.util.ObjectHelpers.hash;
import static ar.edu.itba.paw.g4.util.ObjectHelpers.toStringHelper;
import static ar.edu.itba.paw.g4.util.validation.PredicateHelpers.notNull;
import static ar.edu.itba.paw.g4.util.validation.Validations.checkArgument;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import net.karneim.pojobuilder.GeneratePojoBuilder;

import org.joda.time.DateTime;

import ar.edu.itba.paw.g4.model.NonArtisticName;
import ar.edu.itba.paw.g4.model.builder.UserBuilder;
import ar.edu.itba.paw.g4.util.persist.PersistentEntity;

@Entity
@Table(name = "users",uniqueConstraints={@UniqueConstraint(columnNames={"email"})})
public class User extends PersistentEntity {
	public static final int MIN_PASSWORD_LENGTH = 10;
	public static final int MAX_PASSWORD_LENGTH = 255;
	public static final int MAX_NAME_LENGTH = 35;
	public static final int MAX_EMAIL_LENGTH = 100;

//TODO: Ver por qué no compila el CLOSED y los metodos de validacion comentados
//	private static final Range<Integer> PASSWORD_LENGTH_RANGE = range(
//			MIN_PASSWORD_LENGTH, CLOSED, MAX_PASSWORD_LENGTH, CLOSED);
	@Column(nullable=false,length=MAX_NAME_LENGTH)
	private String firstName;
	@Column(nullable=false,length=MAX_NAME_LENGTH)
	private String lastName;
	@Column(nullable=false, length=MAX_EMAIL_LENGTH)
	private String email;
	@Column(nullable=false, length=MAX_PASSWORD_LENGTH)
	private String password;
	@Column(nullable=false)
	private DateTime birthDate;
	
	@OneToMany(mappedBy="user", cascade=CascadeType.ALL)
	private Set<Comment> comments = new HashSet<Comment>();


//	public static boolean isValidNonArtisticName(String name) {
//		return neitherNullNorEmpty().apply(name)
//				&& name.length() <= MAX_NAME_LENGTH && isAlphaSpace(name)
//				&& normalizeSpace(name).equals(name);
//	}
//
//	public static boolean isValidPassword(String password) {
//		return notNull().apply(password)
//				&& PASSWORD_LENGTH_RANGE.contains(password.length());
//	}
	
	public User() {
	}


	@GeneratePojoBuilder
	public User(NonArtisticName firstName, NonArtisticName lastName,
			String email, Password password, DateTime birthDate) {

		checkArgument(email, notNull());
		checkArgument(birthDate, notNull());
		checkArgument(password, notNull());
		checkArgument(firstName, notNull());
		checkArgument(lastName, notNull());

		this.firstName = firstName.getNameString();
		this.lastName = lastName.getNameString();
		this.email = email;
		this.password = password.getPasswordString();
		this.birthDate = birthDate;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getEmail() {
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