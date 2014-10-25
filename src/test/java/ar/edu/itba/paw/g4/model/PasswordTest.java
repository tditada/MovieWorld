package ar.edu.itba.paw.g4.model;

import static org.junit.Assert.fail;

import org.junit.Test;

import com.google.common.collect.Range;

public class PasswordTest {
	private static final Range<Integer> PASSWORD_LENGTH_RANGE = Range.closed(
			10, 255);

	@SuppressWarnings("unused")
	private Password sut;

	@Test
	public void testConstructorFailOnNullPassword() {
		testConstructorFailOn(null);
	}

	@Test
	public void testConstructorFailOnEmptyPassword() {
		testConstructorFailOn("");
	}

	@Test
	public void testConstructorFailOnTooShortPassword() {
		String password = "";
		for (int i = 0; i < PASSWORD_LENGTH_RANGE.lowerEndpoint() - 1; i++) {
			password += "a";
		}
		testConstructorFailOn(password);
	}

	@Test
	public void testConstructorFailOnTooLongPassword() {
		String password = "";
		for (int i = 0; i < PASSWORD_LENGTH_RANGE.upperEndpoint() + 1; i++) {
			password += "a";
		}
		testConstructorFailOn(password);
	}

	@Test
	public void testConstructorPassOnPasswordWithNumbersAndSpaces() {
		sut = new Password("a  123b34 9665 645");
	}

	@Test
	public void testConstructorPassOnPasswordWithSpecialChars() {
		sut = new Password("!!-??áçǘ ò V_");
	}

	@Test
	public void testConstructorPassOnOkPassword() {
		sut = new Password("Hello world! This is my password :D");
	}

	private void testConstructorFailOn(String name) {
		try {
			this.sut = new Password(name);
		} catch (IllegalArgumentException e) {
			return;
		}
		fail();
	}
}
