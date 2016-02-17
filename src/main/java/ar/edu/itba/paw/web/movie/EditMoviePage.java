package ar.edu.itba.paw.web.movie;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.upload.FileUpload;
import org.apache.wicket.markup.html.image.NonCachingImage;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.util.ListModel;
import org.apache.wicket.request.resource.DynamicImageResource;
import org.apache.wicket.request.resource.IResource;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.util.lang.Bytes;
import org.apache.wicket.util.upload.FileUploadException;
import org.apache.wicket.validation.IValidationError;
import org.joda.time.DateTime;

import ar.edu.itba.paw.model.movie.Movie;
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
public class EditMoviePage extends SecuredPage {

	@SpringBean
	UserRepo userRepo;

	@SpringBean
	MovieRepo movieRepo;

	private IModel<List<FileUpload>> fileUploadsModel = new ListModel<FileUpload>();
	private IModel<Date> releaseDate = new Model<Date>();
	private IModel<Boolean> deletePicture = new Model<Boolean>(false);
	private IModel<Movie> movieModel;

	public EditMoviePage(IModel<Movie> model) {
		this.movieModel = model;
		releaseDate.setObject(movieModel.getObject().getReleaseDate().toDate());
		Form<Movie> form = new Form<Movie>("movieForm", new CompoundPropertyModel<Movie>(movieModel)) {
			@Override
			protected void onSubmit() {
				User user = MovieWorldSession.get().getCurrentUser(userRepo);
				if (user == null || !user.isAdmin() || movieModel.getObject() == null) {
					setResponsePage(HomePage.class);
				}
				try {
					List<FileUpload> fileUploads = fileUploadsModel.getObject();
					if (fileUploads != null) {
						if (!fileUploads.isEmpty()) {
							movieModel.getObject().setPicture(fileUploads.get(0).getBytes());
						}
					}
				} catch (Exception e) {
					error(getString("invalidPicture"));
				}

				if (deletePicture.getObject()) {
					movieModel.getObject().setPicture(null);
				}
				movieRepo.save(movieModel.getObject());
				setResponsePage(new MovieListPage());
				super.onSubmit();
			}

			@Override
			protected void onFileUploadException(FileUploadException e, Map<String, Object> model) {
				error(" picture is too large");
			}
		};
		form.add(new MovieInputsPanel("movieInputs"));
		form.add(new FeedbackPanel("feedback") {
			@Override
			public boolean isVisible() {
				return super.isVisible() && anyMessage();
			}
		});
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
		NonCachingImage image = new NonCachingImage("picture") {
			@Override
			protected IResource getImageResource() {
				return new DynamicImageResource() {
					@Override
					protected byte[] getImageData(Attributes attributes) {
						return movieModel.getObject().getPicture();
					}
				};
			}
		};
		form.add(image);

		form.add(new BootstrapFileInput("file-picker", fileUploadsModel,
				new FileInputConfig().showUpload(false).showCaption(true).showPreview(false)));

		CheckBox delete = new CheckBox("deletePicture", deletePicture) {
			@Override
			protected void onInitialize() {
				add(new Label("deletePicText", getString("deletePicText")));
				super.onInitialize();
			}
		};
		if (movieModel.getObject() == null
				|| (movieModel.getObject() != null && movieModel.getObject().getPicture() == null)) {
			image.setVisible(false);
			delete.setVisible(false);
		}
		form.add(delete);
		form.setMaxSize(Bytes.kilobytes(100));
		add(form);
	}
}