package ar.edu.itba.paw.model.genre;

import static ar.edu.itba.paw.util.persist.hibernate.HQLQueryHelpers.asHQLOrdering;
import static ar.edu.itba.paw.util.validation.PredicateHelpers.notNull;
import static ar.edu.itba.paw.util.validation.Validations.checkArgument;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ar.edu.itba.paw.model.AbstractHibernateRepo;
import ar.edu.itba.paw.util.persist.Orderings;

@Repository
public class HibernateGenreRepo extends AbstractHibernateRepo implements
		GenreRepo {

	@Autowired
	public HibernateGenreRepo(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

	@Override
	public Genre findById(int id) {
		return get(Genre.class, id);
	}

	@Override
	public void save(Genre genre) {
		checkArgument(genre, notNull());
		super.save(genre);
	}

	@Override
	public void remove(Genre genre) {
		checkArgument(genre, notNull());
		super.remove(genre);
	}

	@Override
	public List<Genre> findAllOrderedByName(Orderings ordering) {
		checkArgument(ordering, notNull());
		return find("from Genre genre order by genre.name "
				+ asHQLOrdering(ordering));
	}

	@Override
	public Genre findByName(String name) {
		checkArgument(name, notNull());
		List<Genre> genres = find("from Genre genre where genre.name=?", name);
		if (genres.isEmpty()) {
			return null;
		}
		return genres.get(0);
	}

}
