package ar.edu.itba.paw.domain;

import static ar.edu.itba.paw.util.validation.PredicateHelpers.notNull;
import static ar.edu.itba.paw.util.validation.Validations.checkArgument;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class PersistentEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	public Integer getId() {
		return id;
	}

	public boolean isNew() {
		return (getId() == null);
	}

	public void setId(Integer id) {
		checkArgument(id, notNull());
		this.id = id;
	}

}
