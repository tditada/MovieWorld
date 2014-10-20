package ar.edu.itba.paw.g4.util.persist;

public interface GenericDAO<E extends PersistentEntity> {
	public void save(E entity);

	public E getById(int id);
}
