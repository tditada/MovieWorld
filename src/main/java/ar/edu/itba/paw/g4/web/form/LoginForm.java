package ar.edu.itba.paw.g4.web.form;

import ar.edu.itba.paw.g4.model.EmailAddress;
import ar.edu.itba.paw.g4.model.Password;

public class LoginForm {
	private EmailAddress email;
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
