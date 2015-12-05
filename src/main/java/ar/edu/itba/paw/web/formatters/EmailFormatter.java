package ar.edu.itba.paw.web.formatters;

import java.text.ParseException;
import java.util.Locale;

import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import ar.edu.itba.paw.model.user.Email;

@Component
public class EmailFormatter implements Formatter<Email> {

	@Override
	public Email parse(String arg0, Locale arg1) throws ParseException {
		return new Email(arg0);
	}

	@Override
	public String print(Email arg0, Locale arg1) {
		return arg0.getTextAddress();
	}

}
