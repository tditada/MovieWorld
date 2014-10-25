package ar.edu.itba.paw.g4.web.convert;

import java.text.ParseException;
import java.util.Locale;

import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import ar.edu.itba.paw.g4.model.Password;

@Component
public class PasswordFormatter implements Formatter<Password> {

	@Override
	public Password parse(String arg0, Locale arg1) throws ParseException {
		return new Password(arg0);
	}

	@Override
	public String print(Password arg0, Locale arg1) {
		return arg0.getPasswordString();
	}

}