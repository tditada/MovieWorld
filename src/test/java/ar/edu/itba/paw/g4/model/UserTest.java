package ar.edu.itba.paw.g4.model;

import static java.lang.System.currentTimeMillis;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

import org.joda.time.DateTime;
import org.junit.After;
import org.junit.Test;

import ar.edu.itba.paw.g4.model.comment.Comment;
import ar.edu.itba.paw.g4.model.movie.Movie;
import ar.edu.itba.paw.g4.model.user.Email;
import ar.edu.itba.paw.g4.model.user.NonArtisticName;
import ar.edu.itba.paw.g4.model.user.Password;
import ar.edu.itba.paw.g4.model.user.User;
import ar.edu.itba.paw.g4.model.user.UserBuilder;

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
	public void testAddValidCommentFirstCall() {
		sut = getDefaultUserBuilder().build();

		Movie commentableMovie = mock(Movie.class);
		when(commentableMovie.isCommentableBy(sut)).thenReturn(true);

		Comment comment = mockComment(sut, commentableMovie);

		sut.addComment(comment);

		// verify that Movie's addComment was called JUST ONCE
		verify(commentableMovie, times(1)).addComment(comment);

		assertTrue(sut.getComments().contains(comment));
	}

	@Test
	public void testAddValidCommentAsCallback() { // TODO: check!!!
		sut = getDefaultUserBuilder().build();

		Movie commentableMovie = mock(Movie.class);
		// the movie was already commented by User sut
		when(commentableMovie.isCommentableBy(sut)).thenReturn(false);

		Comment comment = mockComment(sut, commentableMovie);

		// First call
		sut.addComment(comment);

		// Movie's callback
		sut.addComment(comment);

		// the comment should still be there
		assertTrue(sut.getComments().contains(comment));

		verify(commentableMovie, never()).addComment(comment);
		// verify that Movie's addComment was called JUST ONCE in the whole
		// interaction
		verify(commentableMovie, times(1)).addComment(comment);
	}

	// falta testear que se comporte bien si es llamado por segunda vez, como va
	// a pasar cuando lo haga movie (o x ej si de una arrancaron x el addComment
	// de Movie)

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
