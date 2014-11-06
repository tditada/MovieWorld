package ar.edu.itba.paw.g4.web.form.validation;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import ar.edu.itba.paw.g4.web.form.MovieForm;

@Component
public class MovieFormValidator implements Validator {
	private static final String TITLE_ID = "title";
	private static final String DIRECTOR_ID = "director";
	private static final String SUMMARY_ID = "summary";
	private static final String GENRES_ID = "genres";
	private static final String RUNTIME_ID = "runtimeInMins";
	private static final String RELEASEDATE_ID = "releaseDate";
	private static final String PICTURE_ID = "picture";
	private static final int PICTURE_MAX_SIZE = 2000000;

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
		checkSet(GENRES_ID, form.getGenres(), errors);

		Integer runtimeInMins = form.getRuntimeInMins();
		checkSet(RUNTIME_ID, runtimeInMins, errors);
		if (runtimeInMins != null && runtimeInMins <= 0) {
			errors.rejectValue(RUNTIME_ID, "non.positive",
					"Runtimes must be strictly positive numbers");
		}

		CommonsMultipartFile item = form.getPicture();
		if (!item.isEmpty() && !item.getContentType().startsWith("image")) {
			errors.rejectValue(PICTURE_ID, "Invalid image type");
		}
		if (!item.isEmpty() && item.getSize() > PICTURE_MAX_SIZE) {
			errors.rejectValue(PICTURE_ID,
					"Invalid image size, it should be smaller");
		}

		checkSet(RELEASEDATE_ID, form.getReleaseDate(), errors);
	}

	private void checkSet(String name, Object value, Errors errors) {
		if (value == null) {
			errors.rejectValue(name, "invalid", "invalid");
		}
	}
}
