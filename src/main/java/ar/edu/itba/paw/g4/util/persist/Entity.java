package ar.edu.itba.paw.g4.util.persist;

public abstract class Entity {
	private Integer id;

	public Entity(Integer id) {
		this.id = id;
	}

	public Integer getId() {
		return id;
	}
}
