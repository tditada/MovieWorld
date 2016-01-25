package ar.edu.itba.paw.web.movie;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.PropertyModel;

import ar.edu.itba.paw.model.movie.Movie;

@SuppressWarnings("serial")
public class MovieInfoPanel extends Panel{

	public MovieInfoPanel(String id, Movie movie) {
		super(id);
//		IModel<Movie> movieModel = new EntityModel<Movie>(Movie.class, movie);
		add(new Label("title",  new PropertyModel<String>(movie, "title")));
	}

}
