package ar.edu.itba.paw.g4.persist;

import ar.edu.itba.paw.g4.util.persist.Entity;

public interface DAO<E extends Entity> {
	public void save(E entity);

	public E getById(int id);
}
