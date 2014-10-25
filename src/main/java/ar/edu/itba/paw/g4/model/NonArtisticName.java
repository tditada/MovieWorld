package ar.edu.itba.paw.g4.model;

import static ar.edu.itba.paw.g4.util.ObjectHelpers.areEqual;
import static ar.edu.itba.paw.g4.util.ObjectHelpers.hash;
import static ar.edu.itba.paw.g4.util.ObjectHelpers.toStringHelper;
import static ar.edu.itba.paw.g4.util.validation.PredicateHelpers.neitherNullNorEmpty;
import static ar.edu.itba.paw.g4.util.validation.Validations.checkArgument;
import static org.apache.commons.lang3.StringUtils.isAlphaSpace;
import static org.apache.commons.lang3.StringUtils.normalizeSpace;

import javax.persistence.Embeddable;

@Embeddable
public class NonArtisticName {
	private static final int MAX_NAME_LENGTH = 35;

	private String nameString;

	public NonArtisticName() {
	}

	public static boolean isValid(String name) {
		return neitherNullNorEmpty().apply(name)
				&& name.length() <= MAX_NAME_LENGTH && isAlphaSpace(name)
				&& normalizeSpace(name).equals(name);
	}

	public NonArtisticName(String nameString) {
		checkArgument(isValid(nameString));
		this.nameString = nameString;
	}

	public String getNameString() {
		return nameString;
	}

	@Override
	public int hashCode() {
		return hash(nameString);
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
		NonArtisticName other = (NonArtisticName) obj;
		return areEqual(this.nameString, other.nameString);
	}

	@Override
	public String toString() {
		return toStringHelper(this).add("nameString", nameString).toString();
	}

}