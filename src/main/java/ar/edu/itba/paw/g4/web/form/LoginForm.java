package ar.edu.itba.paw.g4.web.form;

import ar.edu.itba.paw.g4.model.Email;
import ar.edu.itba.paw.g4.model.Password;

public class LoginForm {
	private Email email;
	private Password password;

	public LoginForm() {
	}

	public LoginForm(Email email, Password password) {
		this.email = email;
		this.password = password;
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
}
