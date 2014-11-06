package ar.edu.itba.paw.g4.web.form.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import ar.edu.itba.paw.g4.model.movie.Director;
import ar.edu.itba.paw.g4.model.movie.MovieRepo;
import ar.edu.itba.paw.g4.web.form.MovieForm;

@Component
public class InsertMovieFormValidator extends MovieFormValidator {
	private static final String MOVIE_EXISTS_ID = "movieExists";

	private MovieRepo movies;

	@Autowired
	public InsertMovieFormValidator(MovieRepo movies) {
		this.movies = movies;
	}

	@Override
	public void validate(Object target, Errors errors) {
		MovieForm form = (MovieForm) target;
		String title = form.getTitle();
		Director director = form.getDirector();
		if (title != null && director != null
				&& movies.findByTitleAndDirector(title, director) != null) {
			errors.reject(MOVIE_EXISTS_ID, "Movie exists");
		}
	}
}
