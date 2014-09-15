package ar.edu.itba.paw.g4.service.impl;

import java.util.List;

import ar.edu.itba.paw.g4.enums.MovieGenres;
import ar.edu.itba.paw.g4.exception.DatabaseException;
import ar.edu.itba.paw.g4.exception.ServiceException;
import ar.edu.itba.paw.g4.model.Movie;
import ar.edu.itba.paw.g4.persist.MovieDAO;
import ar.edu.itba.paw.g4.persist.impl.PSQLMovieDAO;
import ar.edu.itba.paw.g4.service.MovieService;
import static ar.edu.itba.paw.g4.util.validation.Validations.*;
import static ar.edu.itba.paw.g4.util.validation.PredicateHelpers.*;

public class MovieServiceImpl implements MovieService {
	private MovieDAO movieDAO = PSQLMovieDAO.getInstance();

	@Override
	public List<Movie> getAllMovies() {
		try {
			return movieDAO.getAll();
		} catch (DatabaseException dbe) {
			throw new ServiceException(dbe);/*
											 * TODO: deberia chequear por otros
											 * tipos de exception?
											 */
		}
	}

	@Override
	public List<Movie> getAllMoviesByGenre(MovieGenres genre) {
		checkArgument(genre, notNull());
		try {
			return movieDAO.getAllByGenre(genre);
		} catch (DatabaseException dbe) {
			throw new ServiceException(dbe);/*
											 * TODO: deberia chequear por otros
											 * tipos de exception?
											 */
		}
	}

	@Override
	public List<Movie> getNewestNMovies(int quantity) {
		checkArgument(quantity > 0);
		try {
			return movieDAO.getNewestN(quantity);
		} catch (DatabaseException dbe) {
			throw new ServiceException(dbe);/*
											 * TODO: deberia chequear por otros
											 * tipos de exception?
											 */
		}
	}
}
