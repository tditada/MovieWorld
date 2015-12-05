package ar.edu.itba.paw.web.formatters;

import java.text.ParseException;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import ar.edu.itba.paw.model.movie.Movie;
import ar.edu.itba.paw.model.movie.MovieRepo;

@Component
public class MovieFormatter implements Formatter<Movie> {
	private MovieRepo movies;

	@Autowired
	public MovieFormatter(MovieRepo movies) {
		this.movies = movies;
	}

	@Override
	public Movie parse(String arg0, Locale arg1) throws ParseException {
		Movie movie = movies.findById(Integer.valueOf(arg0));
		if (movie == null) {
			throw new IllegalArgumentException("There is no movie with id="
					+ arg0);
		}
		return movie;
	}

	@Override
	public String print(Movie arg0, Locale arg1) { // TODO:check!
		return String.valueOf(arg0.getId());
	}

}
