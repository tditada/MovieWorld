package ar.edu.itba.paw.g4.model;

import org.junit.Test;

public class NonArtisticNameTest {
	private static final int MAX_NAME_LENGTH = 35;

	@Test(expected = IllegalArgumentException.class)
	public void testConstructorFailOnNullNonArtisticName() {
		new NonArtisticName(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testConstructorFailOnEmptyNonArtisticName() {
		new NonArtisticName("");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testConstructorFailOnTooLongNonArtisticName() {
		String name = "";
		for (int i = 0; i < MAX_NAME_LENGTH + 1; i++) {
			name += "a";
		}
		new NonArtisticName(name);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testConstructorFailOnNonArtisticNameWithNumbers() {
		new NonArtisticName("a123 b34");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testConstructorFailOnNonArtisticNameWithInvalidSpecialChars() {
		new NonArtisticName("!!-??");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testConstructorFailOnNonArtisticNameWithMultispaces() {
		new NonArtisticName("a                    a b");
	}

	@Test
	public void testConstructorPassOnNonArtisticNameWithValidSpecialChars() {
		new NonArtisticName("áçǘ ò V");
	}

	@Test
	public void testConstructorPassOnNonArtisticNameWithSpaces() {
		new NonArtisticName("aaaa aaa aa aaaa");
	}

	@Test
	public void testConstructorPassOnOkName() {
		new NonArtisticName("Justus Alexander zu Peckelsheim aaa");
	}

}
