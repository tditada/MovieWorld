package ar.edu.itba.paw.web.movie;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.PropertyModel;

import ar.edu.itba.paw.model.movie.Movie;

@SuppressWarnings("serial")
public class MovieTitlePanel extends Panel{

	public static final String MOVIE_TITLE_PANEL_ID = "movieTitlePanel";
	
	public MovieTitlePanel(String id, final Movie movie) {
		super(id);
		add(new Label("title",  new PropertyModel<String>(movie, "title")));

		add(new Label("release", "Release"){
			@Override
			public boolean isVisible() {
				return !movie.isRelease();
			}
		});
	}

}