package ar.edu.itba.paw.web.form;

import org.joda.time.DateTime;

import ar.edu.itba.paw.model.user.Email;
import ar.edu.itba.paw.model.user.NonArtisticName;
import ar.edu.itba.paw.model.user.Password;
import ar.edu.itba.paw.model.user.User;

public class RegisterForm {
	private NonArtisticName firstName;
	private NonArtisticName lastName;
	private Email email;
	private Password password;
	private Password passwordConfirmation;
	private DateTime birthDate;

	public RegisterForm() {
	}

	public RegisterForm(NonArtisticName firstName,
			NonArtisticName lastName, Email email, Password password,
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

	public Email getEmail() {
		return email;
	}

	public void setEmail(Email email) {
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
				.withPassword(password).withEmail(email)
				.withBirthDate(birthDate).build();
	}
}
