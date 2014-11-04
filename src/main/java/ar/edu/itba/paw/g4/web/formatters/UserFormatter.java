package ar.edu.itba.paw.g4.web.formatters;

import java.text.ParseException;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import ar.edu.itba.paw.g4.model.user.User;
import ar.edu.itba.paw.g4.model.user.UserRepo;

@Component
public class UserFormatter implements Formatter<User> {
	private UserRepo users;

	@Autowired
	public UserFormatter(UserRepo users) {
		this.users = users;
	}

	@Override
	public String print(User arg0, Locale arg1) { // TODO:check!
		return String.valueOf(arg0.getId());
	}

	@Override
	public User parse(String id, Locale arg1) throws ParseException {
		return users.findById(Integer.valueOf(id));
	}

}
