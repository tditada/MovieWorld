package ar.edu.itba.paw.web.form.validation;

import static ar.edu.itba.paw.util.validation.PredicateHelpers.neitherNullNorEmpty;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import ar.edu.itba.paw.web.form.NewCommentForm;

@Component
public class NewCommentFormValidator implements Validator {

	private static final String COMMENT_TEXT_ID = "commentText";
	private static final String MOVIE_SCORE_ID = "movieScore";

	@Override
	public boolean supports(Class<?> clazz) {
		return NewCommentForm.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		NewCommentForm form = (NewCommentForm) target;

		if (!neitherNullNorEmpty().apply(form.getCommentText())) {
			errors.rejectValue(COMMENT_TEXT_ID, "empty",
					"Comment text cannot be empty");
		}

		checkSet(MOVIE_SCORE_ID, form.getMovieScore(), errors);
	}

	private void checkSet(String name, Object value, Errors errors) {
		if (value == null) {
			errors.rejectValue(name, "invalid", "invalid");
		}
	}
}
