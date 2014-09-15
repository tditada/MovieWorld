package ar.edu.itba.paw.g4.persist;

import java.util.List;

import ar.edu.itba.paw.g4.enums.MovieGenres;
import ar.edu.itba.paw.g4.model.Movie;
import ar.edu.itba.paw.g4.util.persist.GenericDAO;

public interface MovieDAO extends GenericDAO<Movie> {

	public List<Movie> getAll();

	public List<Movie> getAllByGenre(MovieGenres genre);

}
