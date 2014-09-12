package ar.edu.itba.paw.g4.util.persist;

import static ar.edu.itba.paw.g4.util.validation.PredicateHelpers.notNull;
import static ar.edu.itba.paw.g4.util.validation.Validations.checkArgument;

public abstract class Entity {
	private Integer id;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		checkArgument(id, notNull());
		this.id = id;
	}

	public boolean isPersisted() {
		return id != null;
	}
}
