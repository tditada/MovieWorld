package ar.edu.itba.paw.web.movie;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.PropertyModel;

import ar.edu.itba.paw.model.movie.Movie;
import de.agilecoders.wicket.core.markup.html.bootstrap.image.GlyphIconType;
import de.agilecoders.wicket.core.markup.html.bootstrap.image.Icon;

@SuppressWarnings("serial")
public class MoviePageHeaderPanel extends Panel{

	public MoviePageHeaderPanel(String id, final Movie movie) {
		super(id);
		add(new Label("title",  new PropertyModel<String>(movie, "title")));

		add(new Label("release", "Release"){
			@Override
			public boolean isVisible() {
				return movie.isRelease();
			}
		});
//		
//		add(new Icon("love",GlyphIconType.heart){
//			@Override
//			public boolean isVisible() {
//				return super.isVisible() && movie.getAverageScore().getValue()>=4;
//			}
//		});
	}

}