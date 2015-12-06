package ar.edu.itba.paw.web.homepage;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import ar.edu.itba.paw.domain.EntityModel;
import ar.edu.itba.paw.model.movie.MovieRepo;
import ar.edu.itba.paw.model.user.User;
import ar.edu.itba.paw.model.user.UserRepo;
import ar.edu.itba.paw.web.MovieWorldSession;

public class HomePage extends WebPage {

	private static final long serialVersionUID = -8016357471277898793L;

	private static final int TOP_MOVIES_QUANTITY = 5;
	private static final int NEW_ADDITIONS_QUANTITY = 5;

	private static final String SESSION_USER_ID = "user";

	public static final String TOP_MOVIES_ID = "topMovies";
	public static final String RELEASES_ID = "releases";
	public static final String NEW_ADDITIONS_ID = "newAdditions";
	private static final String USER_ID = "user";

	private static final String INTERESTING_COMMENTS_ID = "interestingComments";
	
	@SpringBean private MovieRepo movies;
	@SpringBean private UserRepo users;
	
	public HomePage() {
		User currentUser = MovieWorldSession.get().getCurrentUser(users);
		EntityModel<User> currentUserModel = new EntityModel<User>(User.class, currentUser);
		add(new Label("username", new PropertyModel<String>(MovieWorldSession.get(), "email")));
		
//		IModel<List<Movie>> topFiveMovies = new LoadableDetachableModel<List<Movie>>() {
//			@Override
//			protected List<Movie> load() {
//				return movies.findTopMovies(TOP_MOVIES_QUANTITY);
//			}
//		};
//		
//		add(new PropertyListView<Movie>("topFive.movie", topFiveMovies) {
//			@Override
//			protected void populateItem(ListItem<Movie> item) {
//				item.add(new MovieStar(item.getModel().getObject()));
//
//				item.add(new Link<Movie>("details", item.getModel()) {
//					@Override
//					public void onClick() {
////						setResponsePage(new MovieDetailsPage(getModelObject()));
//					}
//				});
//			}
//		});

	}
}
