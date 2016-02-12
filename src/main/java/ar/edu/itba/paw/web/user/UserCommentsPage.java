package ar.edu.itba.paw.web.user;

import java.util.List;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.PropertyListView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import ar.edu.itba.paw.domain.EntityModel;
import ar.edu.itba.paw.model.comment.Comment;
import ar.edu.itba.paw.model.movie.Movie;
import ar.edu.itba.paw.model.movie.MovieRepo;
import ar.edu.itba.paw.model.user.User;
import ar.edu.itba.paw.model.user.UserRepo;
import ar.edu.itba.paw.web.MovieWorldSession;
import ar.edu.itba.paw.web.base.BasePage;
import ar.edu.itba.paw.web.movie.MoviePage;
import ar.edu.itba.paw.web.movie.MovieTitlePanel;

@SuppressWarnings("serial")
public class UserCommentsPage extends BasePage {

	@SpringBean
	UserRepo userRepo;
	@SpringBean
	MovieRepo movieRepo;

	public UserCommentsPage(final User user) {

        final IModel<User> userModel = new EntityModel<>(User.class, user);
		add(new Label("userName", userModel.getObject().getFirstName().getNameString() + " "
				+ userModel.getObject().getLastName().getNameString()));
		Form<Void> removeInterestingUser = new Form<Void>("removeInterestingUser") {
			@Override
			public boolean isVisible() {
				User currentUser = MovieWorldSession.get().getCurrentUser(userRepo);
				if (currentUser == null) {
					return false;
				}
				return super.isVisible() && currentUser.isinterestingUser(userModel.getObject());
			}

			@Override
			protected void onSubmit() {
				User currentUser = MovieWorldSession.get().getCurrentUser(userRepo);
				User user = userModel.getObject();
				currentUser.removeInterestingUser(user);
				setResponsePage(new UserCommentsPage(userModel.getObject()));
			}
		};
		Form<Void> addInterestingUser = new Form<Void>("addInterestingUser") {
			@Override
			public boolean isVisible() {
				User currentUser = MovieWorldSession.get().getCurrentUser(userRepo);
				if (currentUser == null) {
					return false;
				}
				return super.isVisible() && currentUser != userModel.getObject()
						&& !currentUser.isinterestingUser(userModel.getObject());
			}

			@Override
			protected void onSubmit() {
				User currentUser = MovieWorldSession.get().getCurrentUser(userRepo);
				User user = userModel.getObject();
				currentUser.addInterestingUser(user);
				setResponsePage(new UserCommentsPage(user));
			}
		};
		add(removeInterestingUser);
		add(addInterestingUser);
		add(new Label("comments", getString("comments")) {
			@Override
			public boolean isVisible() {
				return super.isVisible() && userModel.getObject().getCommentsAsList().size() > 0;
			}
		});
		add(new Label("noComments", getString("noComments")) {
			@Override
			public boolean isVisible() {
				return super.isVisible() && userModel.getObject().getCommentsAsList().size() == 0;
			}
		});

		IModel<List<Comment>> userComments = new LoadableDetachableModel<List<Comment>>() {
			@Override
			protected List<Comment> load() {
				return userModel.getObject().getCommentsAsList();
			}
		};

		add(new PropertyListView<Comment>("userComments", userComments) {
			@Override
			protected void populateItem(final ListItem<Comment> item) {

				item.add(new Link<Void>("commentMovieLink") {
					@Override
					protected void onInitialize() {
						super.onInitialize();
						IModel<Movie> movieModel = new EntityModel<Movie>(Movie.class, item.getModelObject().getMovie());
						add(new MovieTitlePanel("commentMovieTitle",movieModel));
						
					}

					@Override
					public void onClick() {
						Movie m = item.getModel().getObject().getMovie();
						setResponsePage(new MoviePage(m));
					}
				});

				item.add(new Label("commentCreationdate", new PropertyModel<String>(item.getModel(), "creationDate")));
				item.add(new Label("commentText", new PropertyModel<String>(item.getModel(), "text")));
			}
		});
	}
}
