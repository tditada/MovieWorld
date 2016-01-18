package ar.edu.itba.paw.web.converter;

import java.util.Locale;

import org.apache.wicket.util.convert.IConverter;
import org.springframework.beans.factory.annotation.Autowired;

import ar.edu.itba.paw.model.movie.Movie;
import ar.edu.itba.paw.model.movie.MovieRepo;

@SuppressWarnings("serial")
public class MovieConverter implements IConverter<Movie> {
	private MovieRepo movies;

	@Autowired
	public MovieConverter(MovieRepo movies) {
		this.movies = movies;
	}

	@Override
	public Movie convertToObject(String arg0, Locale arg1) {
		Movie movie = movies.findById(Integer.valueOf(arg0));
		if (movie == null) {
			throw new IllegalArgumentException("There is no movie with id=" + arg0);
		}
		return movie;
	}

	@Override
	public String convertToString(Movie arg0, Locale arg1) {
		return String.valueOf(arg0.getId());

	}

}
