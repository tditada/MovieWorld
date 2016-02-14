package ar.edu.itba.paw.web.movie;

import java.util.Date;
import java.util.List;
import java.util.SortedSet;

import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.upload.FileUpload;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.util.ListModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.validation.IValidationError;
import org.joda.time.DateTime;

import ar.edu.itba.paw.model.ImageWrapper;
import ar.edu.itba.paw.model.genre.Genre;
import ar.edu.itba.paw.model.movie.Director;
import ar.edu.itba.paw.model.movie.Movie;
import ar.edu.itba.paw.model.movie.MovieBuilder;
import ar.edu.itba.paw.model.movie.MovieRepo;
import ar.edu.itba.paw.model.user.User;
import ar.edu.itba.paw.model.user.UserRepo;
import ar.edu.itba.paw.web.MovieWorldSession;
import ar.edu.itba.paw.web.base.SecuredPage;
import ar.edu.itba.paw.web.homepage.HomePage;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.form.DateTextField;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.form.DateTextFieldConfig;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.form.fileinput.BootstrapFileInput;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.form.fileinput.FileInputConfig;

@SuppressWarnings("serial")
public class AddMoviePage extends SecuredPage {

	@SpringBean
	MovieRepo movieRepo;
	@SpringBean
	UserRepo userRepo;

	private String title;
	private String director;
	private String summary;
	private SortedSet<Genre> genres;
	private Integer runtimeInMins;	
	private ImageWrapper picture;
	private IModel<Date> releaseDate = new Model<Date>();

	private IModel<List<FileUpload>> fileUploadsModel = new ListModel<FileUpload>();

	public AddMoviePage() {

		Form<AddMoviePage> form = new Form<AddMoviePage>("movieForm", new CompoundPropertyModel<AddMoviePage>(this)) {
			@Override
			protected void onSubmit() {
				super.onSubmit();
				
				try {
					List<FileUpload> fileUploads = fileUploadsModel.getObject();
					if (fileUploads != null) {
						if (!fileUploads.isEmpty()) {
							picture = new ImageWrapper(fileUploads.get(0).getBytes());
						}
					}
				} catch (Exception e) {
					error(getString("invalidPicture"));
				}

				User user = MovieWorldSession.get().getCurrentUser(userRepo);
				if (user == null || !user.isAdmin()) {
					setResponsePage(HomePage.class);
				} else {
					MovieBuilder mb = Movie.builder().withDirector(new Director(director)).withGenres(genres)
							.withReleaseDate(new DateTime(releaseDate.getObject())).withRuntimeInMins(runtimeInMins)
							.withSummary(summary).withTitle(title);

					Movie m;
					if (picture != null) {
						m = mb.withPicture(picture.getImage()).build();
					} else {
						m = mb.build();
					}
					movieRepo.save(m);
				}
				setResponsePage(new MovieListPage());
			}

		};
		form.add(new FeedbackPanel("feedback") {
			@Override
			public boolean isVisible() {
				return super.isVisible() && anyMessage();
			}
		});
		form.add(new MovieInputsPanel("movieInputs"));
		DateTextField dateField = new DateTextField("releaseDate", releaseDate,
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
		form.add(dateField);
		form.add(new BootstrapFileInput("file-picker", fileUploadsModel,
				new FileInputConfig().showUpload(false).showCaption(true).showPreview(false)));

		add(form);
	}

}
