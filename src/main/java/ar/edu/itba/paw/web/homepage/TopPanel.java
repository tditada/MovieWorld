package ar.edu.itba.paw.web.homepage;

import java.util.List;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.PropertyListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import ar.edu.itba.paw.model.movie.Movie;
import ar.edu.itba.paw.model.movie.MovieRepo;

@SuppressWarnings("serial")
public class TopPanel extends Panel{
	@SpringBean private MovieRepo movies;
	
	public TopPanel(String id, final int topQuantity) {
		super(id);
		
		//TOP 5
		IModel<List<Movie>> topFiveMovies = new LoadableDetachableModel<List<Movie>>() {
			@Override
			protected List<Movie> load() {
				return movies.findTopMovies(topQuantity);
			}
		};		
		
		add(new PropertyListView<Movie>("topFive.movie", topFiveMovies) {
			@Override
			protected void populateItem(ListItem<Movie> item) {
				item.add(new Label("title", new PropertyModel<String>(item.getModel(), "title")));
				item.add(new Label("summary",new PropertyModel<String>(item.getModel(), "summary")));
				
			}
		});
	}

}
