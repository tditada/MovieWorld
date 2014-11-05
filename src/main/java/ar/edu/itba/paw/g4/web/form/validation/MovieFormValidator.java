package ar.edu.itba.paw.g4.web.form.validation;

import java.util.Set;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import ar.edu.itba.paw.g4.model.MovieGenre;
import ar.edu.itba.paw.g4.web.form.MovieForm;

@Component
public class MovieFormValidator implements Validator {
	private static final String TITLE_ID = "title";
	private static final String DIRECTOR_ID = "director";
	private static final String SUMMARY_ID = "summary";
	private static final String GENRES_ID = "genres";
	private static final String RUNTIME_ID = "runtimeInMins";
	private static final String RELEASEDATE_ID = "releaseDate";

	@Override
	public boolean supports(Class<?> clazz) {
		return MovieForm.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		MovieForm form = (MovieForm) target;
		checkSet(TITLE_ID, form.getTitle(), errors);
		checkSet(DIRECTOR_ID, form.getDirector(), errors);
		checkSet(SUMMARY_ID, form.getSummary(), errors);

		Set<MovieGenre> genres = form.getGenres();
		checkSet(GENRES_ID, genres, errors);
		if (genres != null && genres.contains(null)) {
			errors.rejectValue(GENRES_ID, "genre.invalid",
					"Invalid genre in set");
		}

		Integer runtimeInMins = form.getRuntimeInMins();
		checkSet(RUNTIME_ID, runtimeInMins, errors);
		if (runtimeInMins != null && runtimeInMins <= 0) {
			errors.rejectValue(RUNTIME_ID, "non.positive",
					"Runtimes must be strictly positive numbers");
		}

		checkSet(RELEASEDATE_ID, form.getReleaseDate(), errors);
	}

	private void checkSet(String name, Object value, Errors errors) {
		if (value == null) {
			errors.rejectValue(name, "invalid", "invalid");
		}
	}
}
