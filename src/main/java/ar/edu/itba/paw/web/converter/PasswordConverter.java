package ar.edu.itba.paw.web.converter;

import java.util.Locale;

import org.apache.wicket.util.convert.IConverter;
import org.springframework.stereotype.Component;

import ar.edu.itba.paw.model.user.Password;

@SuppressWarnings("serial")
public class PasswordConverter implements IConverter<Password> {

	@Override
	public Password convertToObject(String arg0, Locale arg1) {
		return new Password(arg0);
	}

	@Override
	public String convertToString(Password arg0, Locale arg1) {
		return arg0.getPasswordString();
	}

}
