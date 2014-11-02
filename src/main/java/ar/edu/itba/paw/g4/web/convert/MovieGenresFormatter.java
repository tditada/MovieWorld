package ar.edu.itba.paw.g4.web.convert;

import java.text.ParseException;
import java.util.Locale;

import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import ar.edu.itba.paw.g4.model.MovieGenres;

@Component
public class MovieGenresFormatter implements Formatter<MovieGenres> {

	@Override
	public MovieGenres parse(String arg0, Locale arg1) throws ParseException {
		return MovieGenres.valueOf(arg0.toUpperCase());
	}

	@Override
	public String print(MovieGenres arg0, Locale arg1) {
		return arg0.getGenreName();
	}
}
