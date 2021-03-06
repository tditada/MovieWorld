package ar.edu.itba.paw.model.movie;

import static ar.edu.itba.paw.util.ObjectHelpers.areEqual;
import static ar.edu.itba.paw.util.ObjectHelpers.hash;
import static ar.edu.itba.paw.util.ObjectHelpers.toStringHelper;
import static ar.edu.itba.paw.util.validation.PredicateHelpers.neitherNullNorEmpty;
import static ar.edu.itba.paw.util.validation.Validations.checkArgument;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.hibernate.annotations.Check;

@SuppressWarnings("serial")
@Embeddable
public class Director implements Serializable{
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
	
	public static boolean isValid(String name){
		try{
			checkArgument(name, neitherNullNorEmpty());
			checkArgument(name.length() <= MAX_NAME_LENGTH);
		}catch(Exception e){
			return false;
		}
		return true;
		
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
