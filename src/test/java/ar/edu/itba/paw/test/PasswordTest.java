package ar.edu.itba.paw.test;

import org.junit.Test;

import com.google.common.collect.Range;

import ar.edu.itba.paw.model.user.Password;

public class PasswordTest {
	private static final Range<Integer> PASSWORD_LENGTH_RANGE = Range.closed(
			10, 255);

	@Test(expected = IllegalArgumentException.class)
	public void testConstructorFailOnNullPassword() {
		new Password(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testConstructorFailOnEmptyPassword() {
		new Password("");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testConstructorFailOnTooShortPassword() {
		String passwordText = "";
		for (int i = 0; i < PASSWORD_LENGTH_RANGE.lowerEndpoint() - 1; i++) {
			passwordText += "a";
		}
		new Password(passwordText);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testConstructorFailOnTooLongPassword() {
		String passwordText = "";
		for (int i = 0; i < PASSWORD_LENGTH_RANGE.upperEndpoint() + 1; i++) {
			passwordText += "a";
		}
		new Password(passwordText);
	}

	@Test
	public void testConstructorOnValidPasswordWithNumbersAndSpaces() {
		new Password("a  123b34 9665 645");
	}

	@Test
	public void testConstructorOnValidPasswordWithSpecialChars() {
		new Password("!!-??áçǘ ò V_");
	}

	@Test
	public void testConstructorOnValidPassword() {
		new Password("Hello world! This is my 12345 password :D");
	}
}
