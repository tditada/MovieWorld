package ar.edu.itba.paw.g4.util.persist;

public abstract class Entity {
	private Integer id;

	public Entity() {
		this.id = null;
	}

	public Entity(int id) {
		this.id = id;
	}

	public Integer getId() {
		return id;
	}
}
