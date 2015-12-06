package ar.edu.itba.paw.web.movie;

import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.model.IModel;

import ar.edu.itba.paw.domain.EntityModel;
import ar.edu.itba.paw.model.movie.Movie;
import ar.edu.itba.paw.web.MovieWorldApplication;


@SuppressWarnings("serial")
public class MovieStar extends Image {
	public final int RATING_TRESHOLD = 4;
	private IModel<Movie> movieModel;
	
	protected void onConfigure() {
		super.onConfigure();
		
		if (4 - movieModel.getObject().getTotalScore() > 0) {
			setVisible(false);
		}
	}
	
	public MovieStar(Movie movie) {
		super("star", MovieWorldApplication.STAR);
		this.movieModel = new EntityModel<Movie>(Movie.class, movie);
	}
}
