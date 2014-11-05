package ar.edu.itba.paw.g4.web.formatters;

import java.text.ParseException;
import java.util.Locale;

import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import ar.edu.itba.paw.g4.model.movie.MovieGenre;

@Component
public class MovieGenreFormatter implements Formatter<MovieGenre> {

	@Override
	public MovieGenre parse(String arg0, Locale arg1) throws ParseException {
		return new MovieGenre(arg0);
	}

	@Override
	public String print(MovieGenre arg0, Locale arg1) {
		return arg0.getName();
	}
}
