package ar.edu.itba.paw.test;

import org.junit.Test;

import ar.edu.itba.paw.model.movie.Director;

public class DirectorTest {
	private static final int MAX_NAME_LENGTH = 70;

	@SuppressWarnings("unused")
	private Director sut;

	@Test(expected = IllegalArgumentException.class)
	public void testConstructorFailOnNullName() {
		new Director(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testConstructorFailOnEmptyName() {
		new Director("");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testConstructorFailOnTooLongName() {
		String name = "";
		for (int i = 0; i < MAX_NAME_LENGTH + 1; i++) {
			name += "a";
		}
		new Director(name);
	}

	@Test
	public void testConstructorPassOnValidName() {
		sut = new Director("Some random name"); // spaces are allowed
	}
}
