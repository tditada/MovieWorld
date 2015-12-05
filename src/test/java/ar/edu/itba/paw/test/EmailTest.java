package ar.edu.itba.paw.test;

import org.junit.Test;

import ar.edu.itba.paw.model.user.Email;

public class EmailTest {
	private static final int MAX_EMAIL_LENGTH = 100;

	@Test(expected = IllegalArgumentException.class)
	public void testConstructorFailOnNullEmail() {
		new Email(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testConstructorFailOnEmptyEmail() {
		new Email("");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testConstructorFailOnTooLongEmail() {
		String textAddress = "";
		for (int i = 0; i < MAX_EMAIL_LENGTH + 1; i++) {
			if (i == MAX_EMAIL_LENGTH / 2) {
				textAddress += "@";
			} else {
				textAddress += "a";
			}
		}
		new Email(textAddress);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testConstructorFailOnEmailWithoutSeparator() {
		new Email("aaaaaaaaaaaaa");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testConstructorFailOnEmailWithoutHost() {
		new Email("a@");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testConstructorFailOnEmailWithoutAccount() {
		new Email("@a.com");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testConstructorFailOnEmailWithoutBaseDomain() {
		new Email("a@a");
	}

	@Test
	public void testConstructorPassOnOkName() {
		new Email("asd.asdasd@asdasd.com"); // dots are
											// allowed
	}
}
