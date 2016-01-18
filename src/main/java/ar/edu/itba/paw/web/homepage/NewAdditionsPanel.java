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
public class NewAdditionsPanel extends Panel{
	@SpringBean private MovieRepo movies;
	
	public NewAdditionsPanel(String id, final int NEW_ADDITIONS_QUANTITY) {
		super(id);

		//NEW ADDITIONS
		
		IModel<List<Movie>> mostRecentMovies = new LoadableDetachableModel<List<Movie>>() {
			@Override
			protected List<Movie> load() {
				return movies.findNewAdditions(NEW_ADDITIONS_QUANTITY); 
			}
		};
				
		add(new PropertyListView<Movie>("mostRecent.movie", mostRecentMovies) {
			@Override
			protected void populateItem(ListItem<Movie> item) {
				item.add(new Label("title", new PropertyModel<String>(item.getModel(), "title")));
				item.add(new Label("creationDate", new PropertyModel<String>(item.getModel(), "creationDate")));
				item.add(new Label("TotalComments", new PropertyModel<String>(item.getModel(), "TotalComments")));

			}
		});
	}

}
