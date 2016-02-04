package ar.edu.itba.paw.web.movie;

import java.util.List;

import org.apache.wicket.markup.html.basic.Label;
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
import ar.edu.itba.paw.util.persist.Orderings;
import ar.edu.itba.paw.web.base.BasePage;

@SuppressWarnings("serial")
public class MovieListPage extends BasePage {

	@SpringBean
	MovieRepo movieRepo;

	final IModel<Genre> genreModel;
	final IModel<Director> directorModel;

	public MovieListPage() {
		this(null, null);
	}

	public MovieListPage(final Genre genre, final Director director) {

		genreModel = new EntityModel<Genre>(Genre.class, genre);
		directorModel = new Model<Director>(director);
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
		add(new Label("edit", getString("edit")));

		add(new PropertyListView<Movie>("movies", movies) {
			@Override
			protected void populateItem(final ListItem<Movie> item) {
				item.add(new Label("movie.releaseDate", new PropertyModel<String>(item.getModel(), "releaseDate")));
				item.add(new Label("movie.director.name", new PropertyModel<String>(item.getModel(), "director")));
				item.add(new Label("movie.title", new PropertyModel<String>(item.getModel(), "title")));
				item.add(new Link<Void>("movie.link") {
					@Override
					public void onClick() {
						Movie m = (Movie) item.getModelObject();
						setResponsePage(new MoviePage(m));
					}
				});

			}
		});

	}
}
