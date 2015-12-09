package ar.edu.itba.paw.web.converter;

import java.util.Locale;

import org.apache.wicket.util.convert.IConverter;

import ar.edu.itba.paw.model.user.Email;

@SuppressWarnings("serial")
public class EmailConverter implements IConverter<Email> {

	@Override
	public Email convertToObject(String arg0, Locale arg1) {
		return new Email(arg0);
	}

	@Override
	public String convertToString(Email arg0, Locale arg1) {
		return arg0.getTextAddress();
	}

}
