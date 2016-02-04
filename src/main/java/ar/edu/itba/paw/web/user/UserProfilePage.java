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

@SuppressWarnings("serial")
public class UserProfilePage extends BasePage {

	@SpringBean
	UserRepo userRepo;
	@SpringBean
	MovieRepo movieRepo;

	public UserProfilePage(User user) {
		final IModel<User> userModel = new EntityModel<>(User.class, user);

		add(new Label("userName", user.getFirstName().getNameString() + " " + user.getLastName().getNameString()));
		Form<Void> removeInterestingUser = new Form<Void>("removeInterestingUser") {
			@Override
			public boolean isVisible() {
				User currentUser = MovieWorldSession.get().getCurrentUser(userRepo);
				return super.isVisible() && currentUser.isinterestingUser(userModel.getObject());
			}

			@Override
			protected void onSubmit() {
				User currentUser = MovieWorldSession.get().getCurrentUser(userRepo);
				User user = userModel.getObject();
				currentUser.removeInterestingUser(user);
				setResponsePage(new UserProfilePage(user));
			}
		};
		Form<Void> addInterestingUser = new Form<Void>("addInterestingUser") {
			@Override
			public boolean isVisible() {
				User currentUser = MovieWorldSession.get().getCurrentUser(userRepo);
				return super.isVisible() && !currentUser.isinterestingUser(userModel.getObject());
			}

			@Override
			protected void onSubmit() {
				User currentUser = MovieWorldSession.get().getCurrentUser(userRepo);
				User user = userModel.getObject();
				currentUser.addInterestingUser(user);
				setResponsePage(new UserProfilePage(user));
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

		add(new PropertyListView<Comment>("user.comments", userComments) {
			@Override
			protected void populateItem(final ListItem<Comment> item) {

				item.add(new Link<Void>("comment.movie.link") {
					@Override
					protected void onInitialize() {
						super.onInitialize();
						add(new Label("comment.movie.title", new PropertyModel<String>(item.getModel(), "title")));
					}

					@Override
					public void onClick() {
						Movie m = item.getModel().getObject().getMovie();
						setResponsePage(new MoviePage(m));
					}
				});
				
				item.add(new Label("comment.screationDate", new PropertyModel<String>(item.getModel(), "creationDate")));
			}
		});
		
		

	}
}
