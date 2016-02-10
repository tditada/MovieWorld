package ar.edu.itba.paw.web.movie;

import java.util.Date;
import java.util.List;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.NumberTextField;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.validation.IValidatable;
import org.apache.wicket.validation.IValidationError;
import org.apache.wicket.validation.IValidator;
import org.apache.wicket.validation.ValidationError;
import org.joda.time.DateTime;

import ar.edu.itba.paw.model.genre.Genre;
import ar.edu.itba.paw.model.genre.GenreRepo;
import ar.edu.itba.paw.model.movie.Director;
import ar.edu.itba.paw.model.movie.Movie;
import ar.edu.itba.paw.model.movie.MovieRepo;
import ar.edu.itba.paw.util.persist.Orderings;
import ar.edu.itba.paw.web.base.BasePage;
import ar.edu.itba.paw.web.converter.DateConverter;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.form.DateTextField;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.form.DateTextFieldConfig;

@SuppressWarnings("serial")
public class AddEditMoviePage extends BasePage {

	@SpringBean
	MovieRepo movieRepo;
	@SpringBean
	GenreRepo genreRepo;

	private String movieTitle;
	private String director;
	private String summary;
	private String genres;
	private Integer runtime;
	private String release;
	// private ImageWrapper picture;
	// private IModel<List<FileUpload>> fileUploadsModel = new
	// ListModel<FileUpload>();

	// TODO: Just the admin can see this page
	public AddEditMoviePage(final IModel<Movie> movieModel) {
		super();
		Form<AddEditMoviePage> addMovieForm = new Form<AddEditMoviePage>("movieForm",
				new CompoundPropertyModel<AddEditMoviePage>(this)) {
			@Override
			protected void onSubmit() {
				if (movieModel != null && movieModel.getObject() == null) {
					// add
//					User user = getLoggedUserFromSession(session);
//					if (user == null || !user.isAdmin()) {
//						mav.setViewName("redirect:/app/home");
//						return mav;
//					}
//
//					insertMovieFormValidator.validate(movieForm, errors);
//					if (errors.hasErrors()) {
//						mav.addObject(USER_ID, user);
//						mav.setViewName("/movies/insert");
//						return mav;
//					}
//
//					Movie movie = movieForm.build();
//					movies.save(movie);
				} else {
					// edit
//					User user = getLoggedUserFromSession(session);
//					Movie movie = getMovieFromSession(session);
//					if (user == null || !user.isAdmin() || movie == null) {
//						mav.setViewName("redirect:/app/home");
//						return mav;
//					}
//
//					// TODO: editMovieFormValidator
//					movieFormValidator.validate(movieForm, errors);
//					if (errors.hasErrors()) {
//						mav.addObject(USER_ID, user);
//						mav.addObject(MOVIE_ID, movie);
//						mav.setViewName("movies/edit");
//						return mav;
//					}
//
//					movie.setTitle(movieForm.getTitle());
//					movie.setReleaseDate(movieForm.getReleaseDate());
//					movie.setGenres(movieForm.getGenres());
//					movie.setDirector(movieForm.getDirector());
//					movie.setSummary(movieForm.getSummary());
//					movie.setRuntimeInMins(movieForm.getRuntimeInMins());
//					if (movieForm.getPicture() != null) {
//						movie.setPicture(movieForm.getPicture().getBytes());
//					}
//					if (movieForm.isDeletePicture()) {
//						movie.removePicture();
//					}
				}
				super.onSubmit();
			}

			// @Override
			// protected void onError() {
			// error(getString("movieExists"));
			// }
		};

		addMovieForm.add(new FeedbackPanel("feedback") {
			@Override
			public boolean isVisible() {
				return super.isVisible() && anyMessage();
			}
		});

		TextField<String> movieTitleField = new RequiredTextField<String>("movieTitle",
				new PropertyModel<String>(movieModel, "title")) {
			@Override
			public void error(IValidationError error) {
				error(getString("invalidMovieName"));
			}
		};

		TextField<String> directorField = new RequiredTextField<String>("director",new PropertyModel<String>(movieModel, "director")) {
			@Override
			protected void onInitialize() {
				super.onInitialize();
				add(new IValidator<String>() {
					@Override
					public void validate(IValidatable<String> validatable) {
						if (Director.isValid(director)) {
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

		TextField<String> summaryField = new RequiredTextField<String>("summary", new PropertyModel<String>(movieModel, "summary")) {
			@Override
			public void error(IValidationError error) {
				error(getString("invalidSummary"));
			}
		};

		TextField<String> genresField = new RequiredTextField<String>("genres", new PropertyModel<String>(movieModel, "genreListString")) {
			@Override
			public void error(IValidationError error) {
				error(getString("invalidGenre"));
			}
		};
		List<Genre> genreList = genreRepo.findAllOrderedByName(Orderings.DESC);
		String genreString = "";
		for (Genre g : genreList) {
			String name = g.getName();
			name = name.substring(0, 1) + name.substring(1).toLowerCase();
			genreString += name + ", ";
		}
		genreString = genreString.substring(0, genreString.length() - 2);
		Label allGenres = new Label("genresAll", genreString);

		NumberTextField<Integer> runtimeField = new NumberTextField<Integer>("runtime", new PropertyModel<Integer>(movieModel, "runtimeInMins")) {
			@Override
			public void error(IValidationError error) {
				error(getString("invalidRuntime"));
			}
		};
		runtimeField.setRequired(true);
		runtimeField.setMinimum(1);
		
		IModel<Date> model = new Model<>();
		Movie m = movieModel.getObject();
		if(m!=null){
			model.setObject(m.getReleaseDate().toDate());
		}
		addMovieForm.add(new DateTextField("releaseDate", model,
                new DateTextFieldConfig().autoClose(true)
                        .withFormat(DateConverter.DATE_TIME_FORMAT)
                        .withStartDate(DateTime.now().minusYears(100))
                        .withView(DateTextFieldConfig.View.Decade)) {
            @Override
            public boolean isRequired() {
                return true;
            }

            @Override
            public void error(IValidationError error) {
                error(getString(getId() + ".invalid"));
            }
        });


//		addMovieForm.add(new DateTextField("release", new Model<>()) {
//			@Override
//			public boolean isRequired() {
//				return true;
//			}
//
//			@Override
//			public void error(IValidationError error) {
//				error(getString("invalidRelease"));
//			}
//		});

		// addMovieForm.add(new BootstrapFileInput("file-picker",
		// fileUploadsModel,
		// new
		// BootstrapFileInputConfig().maxFileCount(1).showUpload(false).showCaption(true).showPreview(false))
		// {
		// @Override
		// protected void onInitialize() {
		// super.onInitialize();
		//// setFileMaxSize(Bytes.bytes(ImageWrapper.MAX_SIZE));
		// }
		// });

		addMovieForm.add(movieTitleField);
		addMovieForm.add(directorField);
		addMovieForm.add(summaryField);
		addMovieForm.add(genresField);
		addMovieForm.add(allGenres);
		addMovieForm.add(runtimeField);
		add(addMovieForm);
	}

	// private static class BootstrapFileInputConfig extends FileInputConfig {
	// private static final IKey<Integer> maxFileCount = newKey("maxFileCount",
	// 1);
	//
	// public BootstrapFileInputConfig maxFileCount(int i) {
	// put(maxFileCount, i);
	// return this;
	// }
	// }
}
