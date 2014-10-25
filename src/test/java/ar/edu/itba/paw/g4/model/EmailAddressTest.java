package ar.edu.itba.paw.g4.model;

import static org.junit.Assert.fail;

import org.junit.Test;

public class EmailAddressTest {
	private static final int MAX_EMAIL_LENGTH = 100;

	@SuppressWarnings("unused")
	private EmailAddress sut;

	@Test
	public void testConstructorFailOnNullEmail() {
		testConstructorFailOn(null);
	}

	@Test
	public void testConstructorFailOnEmptyEmail() {
		testConstructorFailOn("");
	}

	@Test
	public void testConstructorFailOnTooLongEmail() {
		String email = "";
		for (int i = 0; i < MAX_EMAIL_LENGTH + 1; i++) {
			if (i == MAX_EMAIL_LENGTH / 2) {
				email += "@";
			} else {
				email += "a";
			}
		}
		testConstructorFailOn(email);
	}

	@Test
	public void testConstructorFailOnEmailWithoutSeparator() {
		testConstructorFailOn("aaaaaaaaaaaaa");
	}

	@Test
	public void testConstructorFailOnEmailWithoutHost() {
		testConstructorFailOn("a@");
	}

	@Test
	public void testConstructorFailOnEmailWithoutAccount() {
		testConstructorFailOn("@a.com");
	}

	@Test
	public void testConstructorFailOnEmailWithoutBaseDomain() {
		testConstructorFailOn("a@a");
	}

	@Test
	public void testConstructorPassOnOkName() {
		sut = EmailAddress.buildFrom("asd.asdasd@asdasd.com"); // yes, dots are
																// allowed
	}

	private void testConstructorFailOn(String name) {
		try {
			this.sut = EmailAddress.buildFrom(name);
		} catch (IllegalArgumentException e) {
			return;
		}
		fail();
	}
}
