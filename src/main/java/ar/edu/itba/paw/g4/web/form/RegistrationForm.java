package ar.edu.itba.paw.g4.web.form;

import org.joda.time.DateTime;

import ar.edu.itba.paw.g4.model.EmailAddress;
import ar.edu.itba.paw.g4.model.NonArtisticName;
import ar.edu.itba.paw.g4.model.Password;
import ar.edu.itba.paw.g4.model.User;

public class RegistrationForm {
	private NonArtisticName firstName;
	private NonArtisticName lastName;
	private EmailAddress email;
	private Password password;
	private Password passwordConfirmation;
	private DateTime birthDate;

	public RegistrationForm() {
	}

	public RegistrationForm(NonArtisticName firstName,
			NonArtisticName lastName, EmailAddress email, Password password,
			Password passwordConfirmation, DateTime birthDate) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.passwordConfirmation = passwordConfirmation;
		this.birthDate = birthDate;
	}

	public NonArtisticName getFirstName() {
		return firstName;
	}

	public void setFirstName(NonArtisticName firstName) {
		this.firstName = firstName;
	}

	public NonArtisticName getLastName() {
		return lastName;
	}

	public void setLastName(NonArtisticName lastName) {
		this.lastName = lastName;
	}

	public EmailAddress getEmail() {
		return email;
	}

	public void setEmail(EmailAddress email) {
		this.email = email;
	}

	public Password getPassword() {
		return password;
	}

	public void setPassword(Password password) {
		this.password = password;
	}

	public Password getPasswordConfirmation() {
		return passwordConfirmation;
	}

	public void setPasswordConfirmation(Password passwordConfirmation) {
		this.passwordConfirmation = passwordConfirmation;
	}

	public DateTime getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(DateTime birthDate) {
		this.birthDate = birthDate;
	}

	public User build() {
		return User.builder().withFirstName(firstName).withLastName(lastName)
				.withPassword(password).withEmail(email.asTextAddress())
				.withBirthDate(birthDate).build();
	}
}
