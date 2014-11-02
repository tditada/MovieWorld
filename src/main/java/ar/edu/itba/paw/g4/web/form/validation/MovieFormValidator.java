package ar.edu.itba.paw.g4.web.form.validation;

import java.util.Set;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import ar.edu.itba.paw.g4.model.MovieGenres;
import ar.edu.itba.paw.g4.web.form.MovieForm;

@Component
public class MovieFormValidator implements Validator {
	private static final String TITLE_ID = "filmTitle";
	private static final String DIRECTOR_ID = "filmDirector";
	private static final String SUMMARY_ID = "filmSummary";
	private static final String GENRES_ID = "filmGenres";
	private static final String RUNTIME_ID = "filmRuntimeInMins";
	private static final String RELEASEDATE_ID = "filmReleaseDate";

	@Override
	public boolean supports(Class<?> arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void validate(Object target, Errors errors) {
		MovieForm form = (MovieForm) target;
		checkSet(TITLE_ID, form.getfilmTitle(), errors);
		checkSet(DIRECTOR_ID, form.getfilmDirector(), errors);
		checkSet(SUMMARY_ID, form.getfilmSummary(), errors);
		checkSet(GENRES_ID, form.getFilmGenres(), errors);
		checkSet(RUNTIME_ID, form.getfilmRuntimeInMins(), errors);
		checkSet(RELEASEDATE_ID, form.getfilmReleaseDate(), errors);
	}

	private void checkSet(String name, Object value, Errors errors) {
		if (value == null) {
			errors.rejectValue(name, "invalid", "invalid");
		}
		switch (name) {
			case RUNTIME_ID:
				if(((int)value)<0){
					errors.rejectValue(name, "time must be a positive number","time must be a positive number");
				}
				break;
			case GENRES_ID:
				@SuppressWarnings("unchecked")
				Set<MovieGenres> set = (Set<MovieGenres>) value;
				if(set==null){
					errors.rejectValue(name, "genero invalido","genero invalido");
					break;
				}else{
					for(MovieGenres mg:set){
						if(mg==null){
							errors.rejectValue(name, "genero invalido","genero invalido");
						}
					}
					break;
				}
		}
	}

}
