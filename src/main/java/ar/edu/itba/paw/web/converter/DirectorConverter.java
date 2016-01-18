package ar.edu.itba.paw.web.converter;

import java.util.Locale;

import org.apache.wicket.util.convert.IConverter;

import ar.edu.itba.paw.model.movie.Director;

@SuppressWarnings("serial")
public class DirectorConverter implements IConverter<Director> {

	@Override
	public Director convertToObject(String arg0, Locale arg1) {
		return new Director(arg0);
	}

	@Override
	public String convertToString(Director arg0, Locale arg1) {
		return arg0.getName();
	}

}
