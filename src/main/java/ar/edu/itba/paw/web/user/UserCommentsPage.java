package ar.edu.itba.paw.web.user;

import java.util.List;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.PropertyListView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.Model;
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
	
	private IModel<User> userModel;

	public UserCommentsPage(User user) {

        userModel = new Model<User>(user);
		add(new Label("userName", userModel.getObject().getFirstName().getNameString() + " "
				+ userModel.getObject().getLastName().getNameString()));
		User currentUser = MovieWorldSession.get().getCurrentUser(userRepo);
		Form<Void> removeInterestingUser = new Form<Void>("removeInterestingUser") {
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
			protected void onSubmit() {
				User currentUser = MovieWorldSession.get().getCurrentUser(userRepo);
				User user = userModel.getObject();
				currentUser.addInterestingUser(user);
				userRepo.save(currentUser);
				setResponsePage(new UserCommentsPage(user));
			}
		};
		add(removeInterestingUser);
		add(addInterestingUser);
		
		if(currentUser == null || (currentUser!=null && currentUser.equals(userModel.getObject()))){
			removeInterestingUser.setVisible(false);
			addInterestingUser.setVisible(false);
		}else if(currentUser!=null && !currentUser.isinterestingUser(userModel.getObject())){
			removeInterestingUser.setVisible(false);
			addInterestingUser.setVisible(true);
		}else if(currentUser!=null && currentUser.isinterestingUser(userModel.getObject())){
			removeInterestingUser.setVisible(true);
			addInterestingUser.setVisible(false);
		}

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
						IModel<Movie> movieModel =  new EntityModel<Movie>(Movie.class,item.getModel().getObject().getMovie());
						setResponsePage(new MoviePage(movieModel));
					}
				});

				item.add(new Label("commentCreationdate", new PropertyModel<String>(item.getModel(), "creationDate")));
				item.add(new Label("commentText", new PropertyModel<String>(item.getModel(), "text")));
			}
		});
	}
}
