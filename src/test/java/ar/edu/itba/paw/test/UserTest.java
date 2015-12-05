package ar.edu.itba.paw.test;

import static java.lang.System.currentTimeMillis;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

import org.joda.time.DateTime;
import org.junit.After;
import org.junit.Test;

import ar.edu.itba.paw.model.comment.Comment;
import ar.edu.itba.paw.model.movie.Movie;
import ar.edu.itba.paw.model.user.Email;
import ar.edu.itba.paw.model.user.NonArtisticName;
import ar.edu.itba.paw.model.user.Password;
import ar.edu.itba.paw.model.user.User;
import ar.edu.itba.paw.model.user.UserBuilder;

public class UserTest {

	private User sut;

	@Test(expected = IllegalArgumentException.class)
	public void testConstructorFailOnNullFirstName() throws Exception {
		getDefaultUserBuilder().withFirstName(null).build();
	}

	@Test(expected = IllegalArgumentException.class)
	public void testConstructorFailOnNullLastName() throws Exception {
		getDefaultUserBuilder().withLastName(null).build();
	}

	@Test(expected = IllegalArgumentException.class)
	public void testConstructorFailOnNullEmail() throws Exception {
		getDefaultUserBuilder().withEmail(null).build();
	}

	@Test(expected = IllegalArgumentException.class)
	public void testConstructorFailOnNullPassword() throws Exception {
		getDefaultUserBuilder().withPassword(null).build();
	}

	@Test(expected = IllegalArgumentException.class)
	public void testConstructorFailOnNullBirthDate() throws Exception {
		getDefaultUserBuilder().withBirthDate(null).build();
	}

	@Test(expected = IllegalArgumentException.class)
	public void testAddCommentFailOnNullComment() throws Exception {
		sut = getDefaultUserBuilder().build();
		sut.addComment(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testAddCommentFailOnOtherUsersComment() throws Exception {
		sut = getDefaultUserBuilder().build();

		User otherUser = getDefaultUserBuilder().withEmail(
				new Email("testEmail@testEmail.org")).build();

		// to avoid the method failing because of this condition
		Movie commentableMovie = mock(Movie.class);
		when(commentableMovie.isCommentableBy(sut)).thenReturn(true);

		Comment otherUsersComment = mockComment(otherUser, commentableMovie);

		sut.addComment(otherUsersComment);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testAddCommentFailOnUncommentableMovie() throws Exception {
		sut = getDefaultUserBuilder().build();

		Movie uncommentableMovie = mock(Movie.class);
		when(uncommentableMovie.isCommentableBy(sut)).thenReturn(false);

		Comment comment = mockComment(sut, uncommentableMovie);

		sut.addComment(comment);
	}

	@Test
	public void testAddValidComment() {
		sut = getDefaultUserBuilder().build();

		Movie commentableMovie = mock(Movie.class);
		when(commentableMovie.isCommentableBy(sut)).thenReturn(true);

		Comment comment = mockComment(sut, commentableMovie);

		sut.addComment(comment);

		// verify that Movie's addComment was called JUST ONCE
		verify(commentableMovie, times(1)).addComment(comment);

		assertTrue(sut.getComments().contains(comment));
	}

	@After
	public void tearDown() {
		sut = null;
	}

	private Comment mockComment(User user, Movie movie) {
		Comment comment = mock(Comment.class);
		when(comment.getUser()).thenReturn(user);
		when(comment.getMovie()).thenReturn(movie);

		return comment;
	}

	private UserBuilder getDefaultUserBuilder() {
		NonArtisticName mockFirstName = mock(NonArtisticName.class);
		NonArtisticName mocklastName = mock(NonArtisticName.class);
		Password mockPassword = mock(Password.class);
		Email mockEmail = mock(Email.class);

		// cannot mock DateTimes, they are final
		DateTime pastDate = new DateTime(currentTimeMillis() - 10000);

		return User.builder().withBirthDate(pastDate).withEmail(mockEmail)
				.withFirstName(mockFirstName).withLastName(mocklastName)
				.withPassword(mockPassword);
	}
}
