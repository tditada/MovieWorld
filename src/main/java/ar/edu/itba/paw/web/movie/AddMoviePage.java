package ar.edu.itba.paw.web.movie;

import java.util.List;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.NumberTextField;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.form.upload.FileUpload;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.util.ListModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.validation.IValidatable;
import org.apache.wicket.validation.IValidationError;
import org.apache.wicket.validation.IValidator;
import org.apache.wicket.validation.ValidationError;

import ar.edu.itba.paw.model.ImageWrapper;
import ar.edu.itba.paw.model.genre.Genre;
import ar.edu.itba.paw.model.genre.GenreRepo;
import ar.edu.itba.paw.model.movie.Director;
import ar.edu.itba.paw.model.movie.MovieRepo;
import ar.edu.itba.paw.util.persist.Orderings;
import ar.edu.itba.paw.web.base.BasePage;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.form.DateTextField;

@SuppressWarnings("serial")
public class AddMoviePage extends BasePage {

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
//	private ImageWrapper picture;
//	private IModel<List<FileUpload>> fileUploadsModel = new ListModel<FileUpload>();

	// TODO: Just the admin can see this page
	public AddMoviePage() {
		super();
		Form<AddMoviePage> addMovieForm = new Form<AddMoviePage>("movieForm",
				new CompoundPropertyModel<AddMoviePage>(this)) {
			@Override
			protected void onSubmit() {
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

		TextField<String> movieTitleField = new RequiredTextField<String>("movieTitle") {
			@Override
			public void error(IValidationError error) {
				error(getString("invalidMovieName"));
			}
		};

		TextField<String> directorField = new RequiredTextField<String>("director") {
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

		TextField<String> summaryField = new RequiredTextField<String>("summary") {
			@Override
			public void error(IValidationError error) {
				error(getString("invalidSummary"));
			}
		};

		TextField<String> genresField = new RequiredTextField<String>("genres") {
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

		NumberTextField<Integer> runtimeField = new NumberTextField<Integer>("runtime") {
			@Override
			public void error(IValidationError error) {
				error(getString("invalidRuntime"));
			}
		};
		runtimeField.setRequired(true);
		runtimeField.setMinimum(1);

		addMovieForm.add(new DateTextField("release") {
			@Override
			public boolean isRequired() {
				return true;
			}

			@Override
			public void error(IValidationError error) {
				error(getString("invalidRelease"));
			}
		});

//		addMovieForm.add(new BootstrapFileInput("file-picker", fileUploadsModel,
//				new BootstrapFileInputConfig().maxFileCount(1).showUpload(false).showCaption(true).showPreview(false)) {
//			@Override
//			protected void onInitialize() {
//				super.onInitialize();
////				setFileMaxSize(Bytes.bytes(ImageWrapper.MAX_SIZE));
//			}
//		});

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
