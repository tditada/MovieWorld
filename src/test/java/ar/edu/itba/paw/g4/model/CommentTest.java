package ar.edu.itba.paw.g4.model;

import static org.joda.time.DateTime.now;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Test;

import ar.edu.itba.paw.g4.model.comment.Comment;
import ar.edu.itba.paw.g4.model.comment.CommentBuilder;
import ar.edu.itba.paw.g4.model.movie.Movie;
import ar.edu.itba.paw.g4.model.user.User;

public class CommentTest {
	private static final int MIN_SCORE = 0;
	private static final int MAX_SCORE = 5;

	@Test(expected = IllegalArgumentException.class)
	public void testConstructorNullText() throws Exception {
		getDefaultCommentBuilder().withText(null).build();
	}

	@Test(expected = IllegalArgumentException.class)
	public void testConstructorEmptyText() throws Exception {
		getDefaultCommentBuilder().withText("").build();
	}

	@Test(expected = IllegalArgumentException.class)
	public void testConstructorTooSmallScore() throws Exception {
		getDefaultCommentBuilder().withScore(MIN_SCORE - 1).build();
	}

	@Test(expected = IllegalArgumentException.class)
	public void testConstructorTooBigScore() throws Exception {
		getDefaultCommentBuilder().withScore(MAX_SCORE + 1).build();
	}

	@Test(expected = IllegalArgumentException.class)
	public void testConstructorNullMovie() throws Exception {
		getDefaultCommentBuilder().withMovie(null).build();
	}

	@Test(expected = IllegalArgumentException.class)
	public void testConstructorNullUser() throws Exception {
		getDefaultCommentBuilder().withMovie(mock(Movie.class)).withUser(null)
				.build();
	}

	@Test(expected = IllegalArgumentException.class)
	public void testConstructorUserUnableToComment() throws Exception {
		Movie movieMock = mock(Movie.class);
		User userMock = mock(User.class);

		when(movieMock.isCommentableBy(userMock)).thenReturn(false);
		getDefaultCommentBuilder().withMovie(movieMock).withUser(userMock)
				.build();
	}

	public void testConstructorValidCommentMinScore() {
		getDefaultCommentBuilder().withScore(MIN_SCORE).build();
	}

	public void testConstructorValidCommentMaxScore() {
		getDefaultCommentBuilder().withScore(MAX_SCORE).build();
	}

	private CommentBuilder getDefaultCommentBuilder() {
		User mockedUser = mock(User.class);
		Movie mockedMovie = mock(Movie.class);
		when(mockedMovie.isCommentableBy(mockedUser)).thenReturn(true);

		return Comment.builder().withText("Hello world!")
				.withMovie(mockedMovie).withUser(mockedUser)
				.withCreationDate(now()).withScore(MIN_SCORE);
	}
}
