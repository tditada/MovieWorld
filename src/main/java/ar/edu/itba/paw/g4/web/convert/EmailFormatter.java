package ar.edu.itba.paw.g4.web.convert;

import java.text.ParseException;
import java.util.Locale;

import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import ar.edu.itba.paw.g4.model.EmailAddress;

@Component
public class EmailFormatter implements Formatter<EmailAddress> {

	@Override
	public EmailAddress parse(String arg0, Locale arg1) throws ParseException {
		return EmailAddress.buildFrom(arg0);
	}

	@Override
	public String print(EmailAddress arg0, Locale arg1) {
		return arg0.asTextAddress();
	}

}
