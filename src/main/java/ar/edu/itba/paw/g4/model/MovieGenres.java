package ar.edu.itba.paw.g4.model;

import static ar.edu.itba.paw.g4.util.validation.Validations.checkArgument;

import javax.persistence.Id;

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

	private MovieGenres(String name) {
		this.name = name;
	}

	public String getGenreName() {
		return name;
	}
}
