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
		Label release = new Label("release", "Release");
		add(release);
		if(!movieModel.getObject().isRelease()){
			release.setVisible(false);
		}
	}

}