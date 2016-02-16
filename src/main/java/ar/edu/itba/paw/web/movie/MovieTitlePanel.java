package ar.edu.itba.paw.web.movie;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;

import ar.edu.itba.paw.model.movie.Movie;
import de.agilecoders.wicket.core.markup.html.bootstrap.image.GlyphIconType;
import de.agilecoders.wicket.core.markup.html.bootstrap.image.Icon;

@SuppressWarnings("serial")
public class MovieTitlePanel extends Panel{

	public MovieTitlePanel(String id, final IModel<Movie> movieModel) {
		super(id);
		Icon icon = new Icon("loveIcon", GlyphIconType.heart);
		add(icon);
		if(movieModel.getObject().getAverageScore().getValue()<4){
			icon.setVisible(false);
		}
		add(new Label("title", new PropertyModel<String>(movieModel,"title")));
	}

}
