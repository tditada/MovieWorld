package ar.edu.itba.paw.g4.model;

import static ar.edu.itba.paw.g4.util.ObjectHelpers.areEqual;
import static ar.edu.itba.paw.g4.util.ObjectHelpers.hash;
import static ar.edu.itba.paw.g4.util.ObjectHelpers.toStringHelper;
import static ar.edu.itba.paw.g4.util.validation.PredicateHelpers.notNull;
import static ar.edu.itba.paw.g4.util.validation.Validations.checkArgument;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class MovieGenre {
	private static final int MAX_GENRE_LENGTH = 25;

	@Column(nullable = false)
	private String name;

	MovieGenre() {
	}

	public MovieGenre(String name) {
		checkArgument(name, notNull());
		checkArgument(name.length() <= MAX_GENRE_LENGTH);
	}

	public String getName() {
		return name;
	}

	@Override
	public int hashCode() {
		return hash(name);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		MovieGenre other = (MovieGenre) obj;
		return areEqual(this.name, other.name);
	}

	@Override
	public String toString() {
		return toStringHelper(this).add("name", name).toString();
	}
}
