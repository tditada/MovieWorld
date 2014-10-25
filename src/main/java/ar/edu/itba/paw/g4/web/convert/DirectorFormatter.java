package ar.edu.itba.paw.g4.web.convert;

import java.text.ParseException;
import java.util.Locale;

import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import ar.edu.itba.paw.g4.model.Director;

@Component
public class DirectorFormatter implements Formatter<Director> {
	@Override
	public Director parse(String arg0, Locale arg1) throws ParseException {
		return Director.builder().withName(arg0).build();
	}

	@Override
	public String print(Director arg0, Locale arg1) {
		return arg0.getName();
	}

}