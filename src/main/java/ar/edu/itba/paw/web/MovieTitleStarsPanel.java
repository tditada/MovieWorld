package ar.edu.itba.paw.web;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;

import ar.edu.itba.paw.model.movie.Movie;


@SuppressWarnings("serial")
public class MovieTitleStarsPanel extends Panel {
//    private static final int MIN_SCORE_TO_MARK = 4;

    public MovieTitleStarsPanel(String id, final IModel<Movie> model) {
        super(id, model);
        add(new Label("title", PropertyModel.of(model, "title")));

//        add(new Icon("icon", GlyphIconType.thumbsup) {
//            @Override
//            public boolean isVisible() {
//                return model.getObject() != null && model.getObject()
//                        .getAverageScore().getValue() >= MIN_SCORE_TO_MARK;
//            }
//        });
    }
}
