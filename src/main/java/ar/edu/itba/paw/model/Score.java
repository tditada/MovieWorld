package ar.edu.itba.paw.model;

import static ar.edu.itba.paw.util.ObjectHelpers.areEqual;
import static ar.edu.itba.paw.util.ObjectHelpers.hash;
import static ar.edu.itba.paw.util.ObjectHelpers.toStringHelper;
import static ar.edu.itba.paw.util.validation.Validations.checkArgument;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.hibernate.annotations.Check;

@Embeddable
public class Score implements Comparable<Score>, Serializable  {
	public static final int MIN_SCORE = 0;
	public static final int MAX_SCORE = 5;

	@Check(constraints = "(score >=" + MIN_SCORE + " and " + "score <="
			+ MAX_SCORE + ")")
	@Column(name = "score", nullable = false)
	private int value;

	public Score() {
	}

	public Score(int value) {
		checkArgument(value >= MIN_SCORE && value <= MAX_SCORE);
		this.value = value;
	}

	public int getValue() {
		return value;
	}

	@Override
	public int hashCode() {
		return hash(value);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Score other = (Score) obj;
		return areEqual(this.value, other.value);
	}

	@Override
	public String toString() {
		return toStringHelper(this).add("value", value).toString();
	}

	@Override
	public int compareTo(Score other) {
		return Integer.compare(this.value, other.value);
	}

}
