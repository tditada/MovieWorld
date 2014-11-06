package ar.edu.itba.paw.g4.model;

import static java.lang.System.currentTimeMillis;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

import java.util.SortedSet;

import org.joda.time.DateTime;
import org.junit.Test;

import ar.edu.itba.paw.g4.model.genre.Genre;
import ar.edu.itba.paw.g4.model.movie.Director;
import ar.edu.itba.paw.g4.model.movie.Movie;
import ar.edu.itba.paw.g4.model.movie.MovieBuilder;
import ar.edu.itba.paw.g4.model.user.User;

public class MovieTest {
	
	
	@Test(expected = IllegalArgumentException.class)
	public void testConstructorFailOnRunTimeInMinsZero() throws Exception {
		getDefaultMovieBuilder().withRuntimeInMins(0).build();
	}

	@Test(expected = IllegalArgumentException.class)
	public void testConstructorFailOnNullTitle() throws Exception {
		getDefaultMovieBuilder().withTitle(null).build();
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testConstructorFailOnNullReleaseDate() throws Exception {
		getDefaultMovieBuilder().withReleaseDate(null).build();
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testConstructorFailOnNullGenres() throws Exception {
		getDefaultMovieBuilder().withGenres(null).build();
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testConstructorFailOnNullDirector() throws Exception {
		getDefaultMovieBuilder().withDirector(null).build();
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testConstructorFailOnNullSummary() throws Exception {
		getDefaultMovieBuilder().withSummary(null).build();
	}
	
	@Test
	public void testIsReleaseDayFalseOneYearBeforeNow() throws Exception {
		boolean isRelease=getDefaultMovieBuilder().withReleaseDate(DateTime.now().minusYears(1)).build().isRelease();
		assertEquals(isRelease,false);
	}
	
	@Test
	public void testIsReleaseDayTrueWithNowMinusMovieClassReleaseConstant() throws Exception {
		boolean isRelease=getDefaultMovieBuilder().withReleaseDate(DateTime.now().minusDays(Movie.DAYS_AS_RELEASE)).build().isRelease();
		assertEquals(isRelease,true);
	}
	
	@Test
	public void testIsReleaseDayTrueWithNowMinusMovieClassReleaseConstantMinusOne() throws Exception {
		boolean isRelease=getDefaultMovieBuilder().withReleaseDate(DateTime.now().minusDays(Movie.DAYS_AS_RELEASE-1)).build().isRelease();
		assertEquals(isRelease,true);
	}
	
	@Test
	public void testIsReleaseDayTrueWithNowMinusMovieClassReleaseConstantPlusOne() throws Exception {
		boolean isRelease=getDefaultMovieBuilder().withReleaseDate(DateTime.now().minusDays(Movie.DAYS_AS_RELEASE+1)).build().isRelease();
		assertEquals(isRelease,false);
	}
	
	@Test
	public void removePictureGetsPictureInNull() throws Exception{
		Movie movie=getDefaultMovieBuilder().build();
		movie.removePicture();
		assertEquals(movie.getPicture(),null);
	}
	
	@Test
	public void testMovieIsNotCommentableIfReleaseIsAfterNow() throws Exception {
		Movie movie=getDefaultMovieBuilder().withReleaseDate(DateTime.now().plusDays(2)).build();
		assertEquals(movie.isCommentableBy(mock(User.class)),false);
	}
	
	@Test
	public void testMovieIsNotCommentableIfUserIsNull() throws Exception {
		Movie movie=getDefaultMovieBuilder().withReleaseDate(DateTime.now().plusDays(2)).build();
		assertEquals(movie.isCommentableBy(null),false);
	}
	
	private MovieBuilder getDefaultMovieBuilder() {
		String title = "title";
		DateTime releaseDate = new DateTime(currentTimeMillis() - 30000);
		SortedSet<Genre> genres = (SortedSet<Genre>) mock(SortedSet.class);
		Director director = mock(Director.class);
		String summary = "summary";
		int runtimeInMins=1;

		return Movie.builder().withDirector(director).withGenres(genres).withReleaseDate(releaseDate).withSummary(summary).withTitle(title).withRuntimeInMins(runtimeInMins);
	}
	


}
