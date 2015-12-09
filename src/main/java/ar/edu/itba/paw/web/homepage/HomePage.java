package ar.edu.itba.paw.web.homepage;

import java.util.List;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.PropertyListView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import ar.edu.itba.paw.model.movie.Movie;
import ar.edu.itba.paw.model.movie.MovieRepo;
import ar.edu.itba.paw.model.user.UserRepo;
import ar.edu.itba.paw.web.base.BasePage;

//TODO: Links
//TODO: Comentarios
//TODO: estrellas al lado de las top5 peliculas
public class HomePage extends BasePage {

	private static final long serialVersionUID = -8016357471277898793L;

	private static final int TOP_MOVIES_QUANTITY = 5;
	private static final int NEW_ADDITIONS_QUANTITY = 5;

//	private static final String SESSION_USER_ID = "user";

	public static final String TOP_MOVIES_ID = "topMovies";
	public static final String RELEASES_ID = "releases";
	public static final String NEW_ADDITIONS_ID = "newAdditions";
//	private static final String USER_ID = "user";

//	private static final String INTERESTING_COMMENTS_ID = "interestingComments";
	
	@SpringBean private MovieRepo movies;
	@SpringBean private UserRepo users;
	
	public HomePage() {
				
		//TOP 5
		IModel<List<Movie>> topFiveMovies = new LoadableDetachableModel<List<Movie>>() {
			@Override
			protected List<Movie> load() {
				return movies.findTopMovies(TOP_MOVIES_QUANTITY);
			}
		};		
		
		add(new PropertyListView<Movie>("topFive.movie", topFiveMovies) {
			@Override
			protected void populateItem(ListItem<Movie> item) {
				item.add(new Label("title", new PropertyModel<String>(item.getModel(), "title")));
				item.add(new Label("summary",new PropertyModel<String>(item.getModel(), "summary")));
			}
		});
		
		
		//NEW ADDITIONS
		IModel<List<Movie>> mostRecentMovies = new LoadableDetachableModel<List<Movie>>() {
			@Override
			protected List<Movie> load() {
				return movies.findNewAdditions(NEW_ADDITIONS_QUANTITY); 
			}
		};
				
		add(new PropertyListView<Movie>("mostRecent.movie", mostRecentMovies) {
			@Override
			protected void populateItem(ListItem<Movie> item) {
				item.add(new Label("title", new PropertyModel<String>(item.getModel(), "title")));
				item.add(new Label("creationDate", new PropertyModel<String>(item.getModel(), "creationDate")));
				item.add(new Label("TotalComments", new PropertyModel<String>(item.getModel(), "TotalComments")));

			}
		});
		
		//RELEASES (Title, Summary)
		IModel<List<Movie>> releasesMovie = new LoadableDetachableModel<List<Movie>>() {
			@Override
			protected List<Movie> load() {
				return movies.findReleases(); 
			}
		};
				
		add(new PropertyListView<Movie>("releases.movie", releasesMovie) {
			@Override
			protected void populateItem(ListItem<Movie> item) {
				item.add(new Label("title", new PropertyModel<String>(item.getModel(), "title")));
				item.add(new Label("ShortSummary", new PropertyModel<String>(item.getModel(), "ShortSummary")));
			}
		});
		
		
		// (If there is a user) Interesting Users' Comments of the Last Week
		
		

	}
}
