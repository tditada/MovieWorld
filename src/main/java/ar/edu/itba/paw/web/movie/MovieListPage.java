package ar.edu.itba.paw.web.movie;

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
import ar.edu.itba.paw.model.genre.Genre;
import ar.edu.itba.paw.model.movie.Director;
import ar.edu.itba.paw.model.movie.Movie;
import ar.edu.itba.paw.model.movie.MovieRepo;
import ar.edu.itba.paw.model.user.User;
import ar.edu.itba.paw.model.user.UserRepo;
import ar.edu.itba.paw.util.persist.Orderings;
import ar.edu.itba.paw.web.MovieWorldSession;
import ar.edu.itba.paw.web.base.BasePage;
import ar.edu.itba.paw.web.homepage.HomePage;

@SuppressWarnings("serial")
public class MovieListPage extends BasePage {

	@SpringBean
	MovieRepo movieRepo;
	
	@SpringBean
	UserRepo userRepo;

	final IModel<Genre> genreModel;
	final IModel<Director> directorModel;

	public MovieListPage() {
		this(null, null);
	}

	public MovieListPage(final Genre genre, final Director director) {

		genreModel = new EntityModel<Genre>(Genre.class, genre);
		directorModel = new Model<Director>(director);
		final User user = MovieWorldSession.get().getCurrentUser(userRepo);
		add(new FilterMovieNavBar("filterNavBar", genreModel, directorModel));

		IModel<List<Movie>> movies = new LoadableDetachableModel<List<Movie>>() {
			@Override
			protected List<Movie> load() {
				if (director != null) {
					return movieRepo.findAllByDirector(director);
				} else if (genre != null) {
					return movieRepo.findAllByGenre(genre);
				} else {
					return movieRepo.findAllByReleaseDate(Orderings.DESC);

				}
			}
		};
		add(new Label("edit", getString("edit")){
			@Override
			public boolean isVisible() {
				if(user == null){
					return false;
				}
				return super.isVisible() && user.isAdmin();
			}
		});

		add(new PropertyListView<Movie>("movies", movies) {
			@Override
			protected void populateItem(final ListItem<Movie> item) {
				item.add(new Label("movieReleaseDate", new PropertyModel<String>(item.getModel(), "releaseDate")));
				item.add(new Label("movieDirector", new PropertyModel<String>(item.getModel(), "director")));
				item.add(new MovieTitlePanel("movieTitle", item.getModel()));
				//				item.add(new Label("movie.title", new PropertyModel<String>(item.getModel(), "title")));
				item.add(new Link<Void>("movieLink") {
					@Override
					public void onClick() {
						setResponsePage(new MoviePage(item.getModel()));
					}
				});
				Link<Void> edit = new Link<Void>("editLink"){
					@Override
					public void onClick() {
						setResponsePage(new EditMoviePage(item.getModel()));
					}
				};
				item.add(edit);
				Form<MovieListPage> deleteForm = new Form<MovieListPage>("deleteMovie") {
					@Override
					protected void onSubmit() {
						Movie movie = item.getModelObject();
						if (movie != null && user != null && user.isAdmin()) {
							movieRepo.remove(user, movie);
							setResponsePage(new MovieListPage());
						} else {

							setResponsePage(HomePage.class);
						}
						super.onSubmit();
						
					}
				};
				item.add(deleteForm);
				
				if(user == null || (user != null && !user.isAdmin())){
					edit.setVisible(false);
					deleteForm.setVisible(false);
				}

			}
		});
		

	}
}
