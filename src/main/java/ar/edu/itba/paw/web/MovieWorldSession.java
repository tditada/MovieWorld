package ar.edu.itba.paw.web;

import org.apache.wicket.Session;
import org.apache.wicket.protocol.http.WebSession;
import org.apache.wicket.request.Request;

import ar.edu.itba.paw.model.user.Email;
import ar.edu.itba.paw.model.user.Password;
import ar.edu.itba.paw.model.user.User;
import ar.edu.itba.paw.model.user.UserRepo;

public class MovieWorldSession extends WebSession {

	private static final long serialVersionUID = -670935308465563666L;
	private String email;
	private int id;

	public static MovieWorldSession get() {
		return (MovieWorldSession) Session.get();
	}

	public MovieWorldSession(Request request) {
		super(request);
	}

	public String getEmail() {
		return email;
	}

	public boolean signIn(String email, String password, UserRepo users) {
		Email mail = new Email(email);
		User user = users.findUserByEmail(mail);
		if (user != null && users.authenticate(mail, new Password(password)) != null) {
			this.email = email;
			this.id = user.getId();
			return true;
		}
		return false;
	}
	
	public User getCurrentUser(UserRepo users) {
		if (!isSignedIn()) { return null; }
		
		return users.findById(getUserId());
	}


	public boolean isSignedIn() {
		return email != null;
	}
	
	public int getUserId() {
		return id;
	}
	
	public void signOut() {
		invalidate();
		clear();
	}

}
