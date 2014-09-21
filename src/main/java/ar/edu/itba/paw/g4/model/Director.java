package ar.edu.itba.paw.g4.model;

import static ar.edu.itba.paw.g4.util.ObjectHelpers.areEqual;
import static ar.edu.itba.paw.g4.util.ObjectHelpers.hash;
import static ar.edu.itba.paw.g4.util.ObjectHelpers.toStringHelper;
import static ar.edu.itba.paw.g4.util.validation.PredicateHelpers.neitherNullNorEmpty;
import static ar.edu.itba.paw.g4.util.validation.Validations.checkArgument;
import ar.edu.itba.paw.g4.model.builder.DirectorBuilder;

public class Director {
	private static final int MAX_NAME_LENGTH = 70;

	private String name; // artistic name, so no special conditions apply here

	// @GeneratePojoBuilder
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

	public static DirectorBuilder builder() {
		return new DirectorBuilder();
	}
}
