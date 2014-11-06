package ar.edu.itba.paw.g4.model.genre;

import static ar.edu.itba.paw.g4.util.ObjectHelpers.areEqual;
import static ar.edu.itba.paw.g4.util.ObjectHelpers.hash;
import static ar.edu.itba.paw.g4.util.ObjectHelpers.toStringHelper;
import static ar.edu.itba.paw.g4.util.validation.PredicateHelpers.neitherNullNorEmpty;
import static ar.edu.itba.paw.g4.util.validation.Validations.checkArgument;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.Check;

import ar.edu.itba.paw.g4.util.persist.PersistentEntity;

@Entity
@Table(name = "genres", uniqueConstraints = @UniqueConstraint(columnNames = { "name" }))
public class Genre extends PersistentEntity implements Comparable<Genre> {
	private static final int MAX_GENRE_LENGTH = 25;

	@Check(constraints = "length(name) > 0")
	@Column(nullable = false, length = MAX_GENRE_LENGTH)
	private String name;

	Genre() {
	}

	public Genre(String name) {
		checkArgument(name, neitherNullNorEmpty());
		checkArgument(name.length() <= MAX_GENRE_LENGTH);
		this.name = name;
	}

	public String getName() {
		return name;
	}

	@Override
	public int compareTo(Genre other) {
		return name.compareTo(other.name);
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
		Genre other = (Genre) obj;
		return areEqual(this.name, other.name);
	}

	@Override
	public String toString() {
		return toStringHelper(this).add("name", name).toString();
	}
}