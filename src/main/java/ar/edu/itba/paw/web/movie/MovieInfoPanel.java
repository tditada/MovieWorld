package ar.edu.itba.paw.web.movie;

import java.util.List;

import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.image.NonCachingImage;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.resource.DynamicImageResource;
import org.apache.wicket.request.resource.IResource;

import ar.edu.itba.paw.domain.EntityModel;
import ar.edu.itba.paw.model.genre.Genre;
import ar.edu.itba.paw.model.movie.Movie;
import ar.edu.itba.paw.web.homepage.HomePage;

@SuppressWarnings("serial")
public class MovieInfoPanel extends Panel {

	public MovieInfoPanel(String id, final IModel<Movie> movieModel) {
		super(id);
		movieModel.getObject().addVisit();
		setDefaultModel(movieModel);

		add(new ListView<Genre>("movie.genres", new PropertyModel<List<Genre>>(movieModel, "GenreList")) {

			@Override
			protected void populateItem(final ListItem<Genre> item) {
				item.add(new Link<Genre>("linkGenre", item.getModel()) {
					@Override
					protected void onInitialize() {
						super.onInitialize();
						add(new Label("genre.name", new PropertyModel<String>(item.getModel(), "name")));
					}

					@Override
					public void onClick() {
						setResponsePage(HomePage.class);
					}
				});
			}
		});

		add(new Link<Movie>("linkDirector", movieModel) {
			protected void onInitialize() {
				super.onInitialize();
				add(new Label("movie.director", new PropertyModel<String>(movieModel, "director")));
			};

			@Override
			public void onClick() {
				setResponsePage(HomePage.class);
			}
		});
		add(new Label("movie.releaseDate", PropertyModel.of(movieModel, "releaseDate")));
		add(new Label("movie.runtimeInMins", PropertyModel.of(movieModel, "runtimeInMins")));
		add(new Label("movie.summary", PropertyModel.of(movieModel, "summary")));
		add(new StarsPanel("movieStarPanel", movieModel.getObject().getAverageScore().getValue()));
		add(new Label("movieVisits", new PropertyModel<Integer>(movieModel, "visits")));
		add(new NonCachingImage("picture", movieModel) {
			@Override
			protected IResource getImageResource() {
				return new DynamicImageResource() {
					@Override
					protected byte[] getImageData(Attributes attributes) {
						return getMovie().getPicture();
					}
				};
			}

			@Override
			public boolean isVisible() {
				return getMovie().getPicture() != null;
			}

			@Override
			protected void onComponentTag(ComponentTag tag) {
				super.onComponentTag(tag);
				tag.put("alt", getMovie().getTitle());
			}

			private Movie getMovie() {
				return (Movie) getInnermostModel().getObject();
			}
		});
	}

}
