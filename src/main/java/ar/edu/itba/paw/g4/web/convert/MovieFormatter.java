package ar.edu.itba.paw.g4.web.convert;

import java.text.ParseException;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import ar.edu.itba.paw.g4.model.movie.Movie;
import ar.edu.itba.paw.g4.service.MovieService;

@Component
public class MovieFormatter implements Formatter<Movie> {
	private MovieService movieService;

	@Autowired
	public MovieFormatter(MovieService movieService) {
		this.movieService = movieService;
	}

	@Override
	public Movie parse(String arg0, Locale arg1) throws ParseException {
		return movieService.getMovieById(Integer.valueOf(arg0));
	}

	@Override
	public String print(Movie arg0, Locale arg1) {
		return String.valueOf(arg0.getId());
	}

}
