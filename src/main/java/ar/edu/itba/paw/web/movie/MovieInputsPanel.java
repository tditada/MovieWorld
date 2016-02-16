package ar.edu.itba.paw.web.movie;

import java.util.List;

import org.apache.wicket.markup.html.form.CheckBoxMultipleChoice;
import org.apache.wicket.markup.html.form.NumberTextField;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.validation.IValidatable;
import org.apache.wicket.validation.IValidationError;
import org.apache.wicket.validation.IValidator;
import org.apache.wicket.validation.ValidationError;

import ar.edu.itba.paw.model.genre.Genre;
import ar.edu.itba.paw.model.genre.GenreRepo;
import ar.edu.itba.paw.model.movie.Director;
import ar.edu.itba.paw.model.movie.Movie;
import ar.edu.itba.paw.model.movie.MovieRepo;
import ar.edu.itba.paw.model.user.UserRepo;
import ar.edu.itba.paw.util.persist.Orderings;

@SuppressWarnings("serial")
public class MovieInputsPanel extends Panel {

	@SpringBean
	MovieRepo movieRepo;
	@SpringBean
	GenreRepo genreRepo;
	@SpringBean
	UserRepo userRepo;
	
	
	public MovieInputsPanel(String id) {
		super(id);
		TextField<String> movieTitleField = new RequiredTextField<String>("title", String.class) {
			@Override
			public void error(IValidationError error) {
				error(getString("invalidMovieName"));
			}

			@Override
			protected void onInitialize() {
				super.onInitialize();
				add(new IValidator<String>() {
					@Override
					public void validate(IValidatable<String> validatable) {
						if (validatable.getValue().length() > Movie.MAX_TITLE_LENGTH) {
							validatable.error(new ValidationError(this));
						}
					}
				});
			}
		};

		TextField<String> directorField = new RequiredTextField<String>("director", String.class) {
			@Override
			protected void onInitialize() {
				super.onInitialize();
				add(new IValidator<String>() {
					@Override
					public void validate(IValidatable<String> validatable) {
						if (!Director.isValid(validatable.getValue())) {
							validatable.error(new ValidationError(this));
						}
					}
				});
			}

			@Override
			public void error(IValidationError error) {
				error(getString("directorError"));
			}

		};

		TextField<String> summaryField = new RequiredTextField<String>("summary", String.class) {
			@Override
			protected void onInitialize() {
				super.onInitialize();
				add(new IValidator<String>() {
					@Override
					public void validate(IValidatable<String> validatable) {
						if (validatable.getValue().length() > Movie.MAX_SUMMARY_LENGTH) {
							validatable.error(new ValidationError(this));
						}
					}
				});
			}

			@Override
			public void error(IValidationError error) {
				error(getString("invalidSummary"));
			}
		};

		IModel<List<Genre>> genresModel = new LoadableDetachableModel<List<Genre>>() {
			@Override
			protected List<Genre> load() {
				return genreRepo.findAllOrderedByName(Orderings.DESC);
			}
		};
		CheckBoxMultipleChoice<Genre> genresField = new CheckBoxMultipleChoice<Genre>("genres", genresModel);

		NumberTextField<Integer> runtimeField = new NumberTextField<Integer>("runtimeInMins", Integer.class) {
			@Override
			public void error(IValidationError error) {
				error(getString("invalidRuntime"));
			}

			@Override
			protected String getInputType() {
				return "number";
			}
		};
		runtimeField.setRequired(true);
		runtimeField.setMinimum(1);
		
		add(movieTitleField);
		add(directorField);
		add(summaryField);
		add(genresField);
		add(runtimeField);
	}

}
