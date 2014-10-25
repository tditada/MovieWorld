package ar.edu.itba.paw.g4.model;

import static org.junit.Assert.fail;

import org.junit.Test;

public class NonArtisticNameTest {
	private static final int MAX_NAME_LENGTH = 35;

	@SuppressWarnings("unused")
	private NonArtisticName sut;

	@Test
	public void testConstructorFailOnNullNonArtisticName() {
		testConstructorFailOn(null);
	}

	@Test
	public void testConstructorFailOnEmptyNonArtisticName() {
		testConstructorFailOn("");
	}

	@Test
	public void testConstructorFailOnTooLongNonArtisticName() {
		String name = "";
		for (int i = 0; i < MAX_NAME_LENGTH + 1; i++) {
			name += "a";
		}
		testConstructorFailOn(name);
	}

	@Test
	public void testConstructorFailOnNonArtisticNameWithNumbers() {
		testConstructorFailOn("a123 b34");
	}

	@Test
	public void testConstructorFailOnNonArtisticNameWithInvalidSpecialChars() {
		testConstructorFailOn("!!-??");
	}

	@Test
	public void testConstructorFailOnNonArtisticNameWithMultispaces() {
		testConstructorFailOn("a                    a b");
	}

	@Test
	public void testConstructorPassOnNonArtisticNameWithValidSpecialChars() {
		sut = new NonArtisticName("áçǘ ò V");
	}

	@Test
	public void testConstructorPassOnNonArtisticNameWithSpaces() {
		sut = new NonArtisticName("aaaa aaa aa aaaa");
	}

	@Test
	public void testConstructorPassOnOkName() {
		sut = new NonArtisticName(
				"Justus Alexander zu Peckelsheim aaa");
	}

	private void testConstructorFailOn(String name) {
		try {
			this.sut = new NonArtisticName(name);
		} catch (IllegalArgumentException e) {
			return;
		}
		fail();
	}
}
