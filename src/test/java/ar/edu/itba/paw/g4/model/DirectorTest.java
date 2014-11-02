package ar.edu.itba.paw.g4.model;

import static org.junit.Assert.fail;

import org.junit.Test;

public class DirectorTest {
	private static final int MAX_NAME_LENGTH = 70;

	@SuppressWarnings("unused")
	private Director sut;

	@Test
	public void testConstructorFailOnNullName() {
		testConstructorFailOn(null);
	}

	@Test
	public void testConstructorFailOnEmptyName() {
		testConstructorFailOn("");
	}

	@Test
	public void testConstructorFailOnTooLongName() {
		String name = "";
		for (int i = 0; i < MAX_NAME_LENGTH + 1; i++) {
			name += "a";
		}
		testConstructorFailOn(name);
	}

	@Test
	public void testConstructorPassOnOkName() {
		sut = new Director("Some random name"); // spaces are allowed
	}

	private void testConstructorFailOn(String name) {
		try {
			this.sut = new Director(name);
		} catch (IllegalArgumentException e) {
			return;
		}
		fail();
	}
}
