package ar.edu.itba.paw.g4.model;

import static ar.edu.itba.paw.g4.util.validation.Validations.checkArgument;

import javax.persistence.Id;

import ar.edu.itba.paw.g4.util.EnumHelpers;

import com.google.common.base.Converter;
import com.google.common.base.Enums;

public enum MovieGenres {
	ACTION("Action"), COMEDY("Comedy"), DRAMA("Drama"), ROMANCE("Romance"), THRILLER(
			"Thriller"), FAMILY("Family"), HISTORY("History"), ADVENTURE(
			"Adventure"), CRIME("Crime"), MYSTERY("Mystery"), HORROR("Horror"), FANTASY(
			"Fantasy"), BIOGRAPHY("Biography"), SPORT("Sport"), WAR("War"), DOCUMENTARY(
			"Documentary"), MUSIC("Music"), MUSICAL("Musical"), ANIMATION(
			"Animation"), WESTERN("Western"), SCIFI("Sci-fi");

	private static final int MAX_GENRE_LENGTH = 25;

	@Id
	private int id;

	private String name;

	private MovieGenres() {
		checkArgument(this.name().length() < MAX_GENRE_LENGTH);
	}

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

	private MovieGenres(String name) {
		this.name = name;
	}

	public String getGenreName() {
		return name;
	}
}
