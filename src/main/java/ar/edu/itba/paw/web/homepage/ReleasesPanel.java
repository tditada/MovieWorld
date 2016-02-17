package ar.edu.itba.paw.web.homepage;

import java.text.MessageFormat;
import java.util.List;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.PropertyListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import ar.edu.itba.paw.model.movie.Movie;
import ar.edu.itba.paw.model.movie.MovieRepo;
import ar.edu.itba.paw.web.movie.MoviePage;
import ar.edu.itba.paw.web.movie.MovieTitlePanel;

@SuppressWarnings("serial")
public class ReleasesPanel extends Panel {
	@SpringBean
	private MovieRepo movies;

	public ReleasesPanel(String id) {
		super(id);

		final IModel<List<Movie>> releasesMovie = new LoadableDetachableModel<List<Movie>>() {
			@Override
			protected List<Movie> load() {
				return movies.findReleases();
			}
		};

		add(new PropertyListView<Movie>("releases.movie", releasesMovie) {
			@Override
			protected void populateItem(ListItem<Movie> item) {
				final IModel<Movie> movieModel = item.getModel();
				item.add(new Link<Void>("movieLink") {
					@Override
					protected void onInitialize() {
						add(new MovieTitlePanel("title", movieModel));
						super.onInitialize();
					}

					@Override
					public void onClick() {
						setResponsePage(new MoviePage(movieModel));
					}
				});
				item.add(new Label("summary", new PropertyModel<String>(item.getModel(), "summary")));
			}
		});
		IModel<String> stringModel = new Model<String>();
		Label noReleases = new Label("noReleases",stringModel){
			@Override
			protected void onInitialize() {
				setDefaultModelObject(MessageFormat.format(getString("noReleases"), Movie.DAYS_AS_RELEASE));
				super.onInitialize();
			}
		};
		add(noReleases);
		if (releasesMovie.getObject().size() != 0) {
			noReleases.setVisible(false);
		}
	}

}
