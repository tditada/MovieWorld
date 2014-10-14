package ar.edu.itba.paw.g4.web.form;

import javax.validation.constraints.NotNull;

import ar.edu.itba.paw.g4.model.EmailAddress;
import ar.edu.itba.paw.g4.model.Password;

public class LoginForm {
	@NotNull
	private EmailAddress email;
	@NotNull
	private Password password;

	public LoginForm() {
	}

	public LoginForm(EmailAddress email, Password password) {
		this.email = email;
		this.password = password;
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

}
