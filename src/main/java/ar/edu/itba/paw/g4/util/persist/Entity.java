package ar.edu.itba.paw.g4.util.persist;

public abstract class Entity {
	private Integer id;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public boolean isPersisted() {
		return id == null;
	}
}
