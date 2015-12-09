package ar.edu.itba.paw.web.converter;

import java.util.Locale;

import org.apache.wicket.util.convert.IConverter;
import org.springframework.beans.factory.annotation.Autowired;

import ar.edu.itba.paw.model.user.User;
import ar.edu.itba.paw.model.user.UserRepo;

@SuppressWarnings("serial")
public class UserConverter implements IConverter<User> {
	private UserRepo users;

	@Autowired
	public UserConverter(UserRepo users) {
		this.users = users;
	}

	@Override
	public User convertToObject(String id, Locale arg1) {
		return users.findById(Integer.valueOf(id));
	}

	@Override
	public String convertToString(User arg0, Locale arg1) {
		return String.valueOf(arg0.getId());
	}

}
