package ar.edu.itba.paw.web.movie;

import java.util.Date;
import java.util.List;
import java.util.SortedSet;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.CheckBoxMultipleChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.NumberTextField;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.form.upload.FileUpload;
import org.apache.wicket.markup.html.image.NonCachingImage;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.util.ListModel;
import org.apache.wicket.request.resource.DynamicImageResource;
import org.apache.wicket.request.resource.IResource;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.validation.IValidatable;
import org.apache.wicket.validation.IValidationError;
import org.apache.wicket.validation.IValidator;
import org.apache.wicket.validation.ValidationError;
import org.joda.time.DateTime;

import ar.edu.itba.paw.domain.EntityModel;
import ar.edu.itba.paw.model.ImageWrapper;
import ar.edu.itba.paw.model.genre.Genre;
import ar.edu.itba.paw.model.genre.GenreRepo;
import ar.edu.itba.paw.model.movie.Director;
import ar.edu.itba.paw.model.movie.Movie;
import ar.edu.itba.paw.model.movie.MovieBuilder;
import ar.edu.itba.paw.model.movie.MovieRepo;
import ar.edu.itba.paw.model.user.User;
import ar.edu.itba.paw.model.user.UserRepo;
import ar.edu.itba.paw.util.persist.Orderings;
import ar.edu.itba.paw.web.MovieWorldSession;
import ar.edu.itba.paw.web.base.SecuredPage;
import ar.edu.itba.paw.web.homepage.HomePage;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.form.DateTextField;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.form.DateTextFieldConfig;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.form.fileinput.BootstrapFileInput;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.form.fileinput.FileInputConfig;

@SuppressWarnings("serial")
public class AddEditMoviePage extends SecuredPage {

	@SpringBean
	MovieRepo movieRepo;
	@SpringBean
	GenreRepo genreRepo;
	@SpringBean
	UserRepo userRepo;

	private String movieTitle;
	private String director;
	private String summary;
	private SortedSet<Genre> genres;
	private Integer runtime;
	private String releaseDate;
//	private String releaseDate = DateTime.now().toString();
	private ImageWrapper picture;
	private IModel<List<FileUpload>> fileUploadsModel = new ListModel<FileUpload>();
	private boolean deletePicture = false;
	private IModel<Movie> movieModel;
	private boolean edit;

	// TODO: Just the admin can see this page
	// Set pic max size
	public AddEditMoviePage(final Movie movie) {
		super();

		movieModel = new EntityModel<>(Movie.class, movie);
		if (movie != null) {
			edit = true;
		}
		final User user = MovieWorldSession.get().getCurrentUser(userRepo);
		Form<AddEditMoviePage> addMovieForm = new Form<AddEditMoviePage>("movieForm",
				new CompoundPropertyModel<AddEditMoviePage>(this)) {
			@Override
				
			protected void onSubmit() {
				String[] s = releaseDate.split("/");
				releaseDate = s[s.length - 1] + "-" + s[0] + "-" + s[1];
				if (!edit) {
					// Insert Movie
					if (user == null || !user.isAdmin()) {
						setResponsePage(HomePage.class);
					} else {
						MovieBuilder mb = Movie.builder().withDirector(new Director(director)).withGenres(genres)
								.withReleaseDate(new DateTime(releaseDate)).withRuntimeInMins(runtime)
								.withSummary(summary).withTitle(movieTitle);
						Movie m;
						if (picture != null) {
							m = mb.withPicture(picture.getImage()).build();
						} else {
							m = mb.withPicture(null).build();
						}
						movieRepo.save(m);
					}
				} else {
					// edit
					// User user = getLoggedUserFromSession(session);
					// Movie movie = getMovieFromSession(session);
					// if (user == null || !user.isAdmin() || movie == null) {
					// mav.setViewName("redirect:/app/home");
					// return mav;
					// }
					//
					// // TODO: editMovieFormValidator
					// movieFormValidator.validate(movieForm, errors);
					// if (errors.hasErrors()) {
					// mav.addObject(USER_ID, user);
					// mav.addObject(MOVIE_ID, movie);
					// mav.setViewName("movies/edit");
					// return mav;
					// }
					//
					// movie.setTitle(movieForm.getTitle());
					// movie.setReleaseDate(movieForm.getReleaseDate());
					// movie.setGenres(movieForm.getGenres());
					// movie.setDirector(movieForm.getDirector());
					// movie.setSummary(movieForm.getSummary());
					// movie.setRuntimeInMins(movieForm.getRuntimeInMins());
					// if (movieForm.getPicture() != null) {
					// movie.setPicture(movieForm.getPicture().getBytes());
					// }
					// if (movieForm.isDeletePicture()) {
					// movie.removePicture();
					// }
				}
				setResponsePage(MovieListPage.class);
				super.onSubmit();
			}

//			@Override
//			protected void onError() {
//				error(getString("movieExists"));
//			}
		};

		addMovieForm.add(new FeedbackPanel("feedback") {
			@Override
			public boolean isVisible() {
				return super.isVisible() && anyMessage();
			}
		});

		TextField<String> movieTitleField = new RequiredTextField<String>("movieTitle", String.class) {
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
		addMovieForm.add(new CheckBoxMultipleChoice<Genre>("genres",genresModel));
		
//		TextField<String> genresField = new RequiredTextField<String>("genres", String.class) {
//			@Override
//			public void error(IValidationError error) {
//				error(getString("invalidGenre"));
//			}
//		};
//		List<Genre> genreList = genreRepo.findAllOrderedByName(Orderings.DESC);
//		String genreString = "";
//		for (Genre g : genreList) {
//			String name = g.getName();
//			name = name.substring(0, 1) + name.substring(1).toLowerCase();
//			genreString += name + ", ";
//		}
//		genreString = genreString.substring(0, genreString.length() - 2);
//		Label allGenres = new Label("genresAll", genreString);

		NumberTextField<Integer> runtimeField = new NumberTextField<Integer>("runtime", Integer.class) {
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
		
		DateTextField dateField = new DateTextField("releaseDate",
				new DateTextFieldConfig().autoClose(true).withStartDate(DateTime.now().minusYears(100))
						.withView(DateTextFieldConfig.View.Decade).withEndDate(DateTime.now())) {
			@Override
			public boolean isRequired() {
				return true;
			}

			@Override
			public void error(IValidationError error) {
				error(getString("invalidRelease"));
			}
		};
		
		if (edit) {
			movieTitleField.setModel(new PropertyModel<String>(movieModel, "title"));
			directorField.setModel(new PropertyModel<String>(movieModel, "director"));
			summaryField.setModel(new PropertyModel<String>(movieModel, "summary"));
//			genresField.setModel(new PropertyModel<String>(movieModel, "genreListString"));
			runtimeField.setModel(new PropertyModel<Integer>(movieModel, "runtimeInMins"));
			IModel<Date> dateModel = new Model<>(movieModel.getObject().getReleaseDate().toDate());
			dateField.setModel(dateModel);
		}

		addMovieForm.add(movieTitleField);
		addMovieForm.add(directorField);
		addMovieForm.add(summaryField);
//		addMovieForm.add(genresField);
//		addMovieForm.add(allGenres);
		addMovieForm.add(runtimeField);
		addMovieForm.add(dateField);

		addMovieForm.add(new NonCachingImage("picture") {
			@Override
			public boolean isVisible() {
				return movieModel.getObject() != null && movieModel.getObject().getPicture() != null;
			}

			@Override
			protected IResource getImageResource() {
				return new DynamicImageResource() {
					@Override
					protected byte[] getImageData(Attributes attributes) {
						return movieModel.getObject().getPicture();
					}
				};
			}
		});

		addMovieForm.add(new BootstrapFileInput("file-picker", fileUploadsModel,
				new BootstrapFileInputConfig().maxFileCount(1).showUpload(false).showCaption(true).showPreview(false)) {
			@Override
			protected void onInitialize() {
				super.onInitialize();
				// setFileMaxSize(Bytes.bytes(ImageWrapper.MAX_SIZE));
			}
		});

		addMovieForm.add(new CheckBox("deletePicture") {
			@Override
			protected void onInitialize() {
				add(new Label("deletePicText", getString("deletePicText")));
				super.onInitialize();
			}

			@Override
			public boolean isVisible() {
				return movieModel.getObject() != null && movieModel.getObject().getPicture() != null;
			}
		});

		add(addMovieForm);
	}

	private static class BootstrapFileInputConfig extends FileInputConfig {
		private static final de.agilecoders.wicket.jquery.IKey<Integer> maxFileCount = newKey("maxFileCount", 1);

		public BootstrapFileInputConfig maxFileCount(int i) {
			put(maxFileCount, i);
			return this;
		}
	}
}
