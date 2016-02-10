package ar.edu.itba.paw.web.movie;

import ar.edu.itba.paw.model.movie.Movie;
import ar.edu.itba.paw.web.base.BasePage;
import ar.edu.itba.paw.web.comment.MovieCommentFormPanel;
import ar.edu.itba.paw.web.comment.MovieCommentsPanel;

@SuppressWarnings("serial")
public class MoviePage extends BasePage {

	public static final String MOVIE_TITLE_PANEL_ID = "movieTitlePanel";
	public static final String MOVIE_INFO_PANEL_ID = "movieInfoPanel";
	public static final String MOVIE_COMMENTS_PANEL_ID = "movieCommentsPanel";
	public static final String MOVIE_COMMENTS_FORM_PANEL_ID = "movieCommentFormPanel";

	public MoviePage(Movie movie) {
		add(new MovieTitlePanel(MOVIE_TITLE_PANEL_ID, movie));
		add(new MovieInfoPanel(MOVIE_INFO_PANEL_ID, movie));
		 add(new MovieCommentsPanel(MOVIE_COMMENTS_PANEL_ID, movie));
		 add(new MovieCommentFormPanel(MOVIE_COMMENTS_FORM_PANEL_ID, movie));
	}

}
