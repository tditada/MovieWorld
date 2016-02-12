package ar.edu.itba.paw.web.movie;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;

import ar.edu.itba.paw.model.movie.Movie;

@SuppressWarnings("serial")
public class MoviePageHeaderPanel extends Panel{

	public MoviePageHeaderPanel(String id, final IModel<Movie> movieModel) {
		super(id);
		add(new MovieTitlePanel("title",movieModel));

		add(new Label("release", "Release"){
			@Override
			public boolean isVisible() {
				return movieModel.getObject().isRelease();
			}
		});
	}

}