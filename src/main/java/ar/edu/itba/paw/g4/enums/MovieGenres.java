package ar.edu.itba.paw.g4.enums;

import ar.edu.itba.paw.g4.util.EnumHelpers;

import com.google.common.base.Converter;
import com.google.common.base.Enums;

public enum MovieGenres {
	ACTION, COMEDY, DRAMA, TERROR, ROMANCE;

	private static final Converter<String, MovieGenres> converter = Enums
			.stringConverter(MovieGenres.class);

	public static Converter<String, MovieGenres> getConverter() {
		return converter;
	}

	public static Iterable<MovieGenres> valuesOf(Iterable<String> values) {
		return EnumHelpers.valuesOf(getConverter(), values);
	}

	public static Iterable<MovieGenres> valuesOf(String... values) {
		return EnumHelpers.valuesOf(getConverter(), values);
	}
}
