package ar.edu.itba.paw.model.genre;

import java.util.List;

import ar.edu.itba.paw.util.persist.Orderings;

public interface GenreRepo {

	void save(Genre genre);

	void remove(Genre genre);

	Genre findById(int id);

	Genre findByName(String name);

	List<Genre> findAllOrderedByName(Orderings ordering);

}
