package ar.edu.itba.paw.g4.util;

import static com.google.common.collect.Lists.newArrayList;

import com.google.common.base.Converter;

public class EnumHelpers {
	public static <E extends Enum<E>> Iterable<E> valuesOf(
			Converter<String, E> converter, Iterable<String> values) {
		return converter.convertAll(values);
	}

	public static <E extends Enum<E>> Iterable<E> valuesOf(
			Converter<String, E> converter, String... values) {
		return valuesOf(converter, newArrayList(values));
	}
}
