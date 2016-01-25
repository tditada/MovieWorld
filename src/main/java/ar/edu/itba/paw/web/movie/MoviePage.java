package ar.edu.itba.paw.web.movie;

import ar.edu.itba.paw.model.movie.Movie;
import ar.edu.itba.paw.web.base.BasePage;

@SuppressWarnings("serial")
public class MoviePage extends BasePage{

	public static final String MOVIE_INFO_PANEL_ID = "movieInfoPanel";
	
	public MoviePage(Movie movie) {
		add(new MovieInfoPanel(MOVIE_INFO_PANEL_ID, movie));
	}

}
