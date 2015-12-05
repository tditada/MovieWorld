package ar.edu.itba.paw.web.formatters;

import java.text.ParseException;
import java.util.Locale;

import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import ar.edu.itba.paw.model.movie.Director;

@Component
public class DirectorFormatter implements Formatter<Director> {
	@Override
	public Director parse(String arg0, Locale arg1) throws ParseException {
		return new Director(arg0);
	}

	@Override
	public String print(Director arg0, Locale arg1) {
		return arg0.getName();
	}

}
