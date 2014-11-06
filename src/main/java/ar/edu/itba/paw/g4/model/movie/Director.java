package ar.edu.itba.paw.g4.model.movie;

import static ar.edu.itba.paw.g4.util.ObjectHelpers.areEqual;
import static ar.edu.itba.paw.g4.util.ObjectHelpers.hash;
import static ar.edu.itba.paw.g4.util.ObjectHelpers.toStringHelper;
import static ar.edu.itba.paw.g4.util.validation.PredicateHelpers.neitherNullNorEmpty;
import static ar.edu.itba.paw.g4.util.validation.Validations.checkArgument;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.hibernate.annotations.Check;

@Embeddable
public class Director {
	public static final int MAX_NAME_LENGTH = 70;

	@Check(constraints = "length(name) > 0")
	@Column(length = MAX_NAME_LENGTH, nullable = false)
	private String name; // artistic name, so no special conditions apply here

	Director() {
	}

	public Director(String name) {
		checkArgument(name, neitherNullNorEmpty());
		checkArgument(name.length() <= MAX_NAME_LENGTH);
		this.name = name;
	}

	public String getName() {
		return name;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}
		Director that = (Director) obj;
		return areEqual(this.name, that.name);
	}

	@Override
	public int hashCode() {
		return hash(name);
	}

	@Override
	public String toString() {
		return toStringHelper(this).add("name", name).toString();
	}
}
