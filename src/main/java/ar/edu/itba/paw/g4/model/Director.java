package ar.edu.itba.paw.g4.model;

import static ar.edu.itba.paw.g4.utils.ObjectHelpers.equal;
import static ar.edu.itba.paw.g4.utils.ObjectHelpers.hash;
import static ar.edu.itba.paw.g4.utils.ObjectHelpers.toStringHelper;
import static ar.edu.itba.paw.g4.utils.Validations.checkNotNullOrEmpty;
import ar.edu.itba.paw.g4.utils.persist.Entity;
import net.karneim.pojobuilder.GeneratePojoBuilder;

public class Director extends Entity {
	private String name; // TODO: change this and User's first and last names to
							// HumanName or sth like that

	@GeneratePojoBuilder
	public Director(String name) {
		checkNotNullOrEmpty(name);
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
		return equal(this.name, that.name);
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
