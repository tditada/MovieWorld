package ar.edu.itba.paw.g4.service.impl;

import static org.joda.time.DateTime.*;
import static ar.edu.itba.paw.g4.util.validation.PredicateHelpers.notNull;
import static ar.edu.itba.paw.g4.util.validation.Validations.checkArgument;

import java.util.List;

import ar.edu.itba.paw.g4.enums.MovieGenres;
import ar.edu.itba.paw.g4.exception.DatabaseException;
import ar.edu.itba.paw.g4.exception.ServiceException;
import ar.edu.itba.paw.g4.model.Director;
import ar.edu.itba.paw.g4.model.Movie;
import ar.edu.itba.paw.g4.persist.MovieDAO;
import ar.edu.itba.paw.g4.persist.impl.PSQLMovieDAO;
import ar.edu.itba.paw.g4.service.MovieService;
import ar.edu.itba.paw.g4.util.persist.Orderings;

public class MovieServiceImpl implements MovieService {
	private static final MovieService instance = new MovieServiceImpl();

	private MovieDAO movieDAO = PSQLMovieDAO.getInstance();

	public static MovieService getInstance() {
		return instance;
	}

	private MovieServiceImpl() {
	}

	@Override
	public List<Movie> getMovieList() {
		try {
			return movieDAO.getAllByReleaseDate(Orderings.DESC);
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
	public List<Movie> getNewAdditions(int maxQuantity) {
		checkArgument(maxQuantity > 0);
		try {
			return movieDAO.getNewestNByCreationDate(maxQuantity);
		} catch (DatabaseException dbe) {
			throw new ServiceException(dbe);/*
											 * TODO: deberia chequear por otros
											 * tipos de exception?
											 */
		}
	}

	@Override
	public List<Movie> getAllMoviesByDirector(Director director) {
		checkArgument(director, notNull());
		try {
			return movieDAO.getAllByDirector(director);
		} catch (DatabaseException dbe) {
			throw new ServiceException(dbe);/*
											 * TODO: deberia chequear por otros
											 * tipos de exception?
											 */
		}
	}

	@Override
	public List<Movie> getReleases() {
		try {
			return movieDAO.getAllByReleaseDateInRange(
					now().minusDays(Movie.DAYS_AS_RELEASE), now());
		} catch (DatabaseException dbe) {
			throw new ServiceException(dbe);/*
											 * TODO: deberia chequear por otros
											 * tipos de exception?
											 */
		}
	}

	@Override
	public Movie getMovieById(int id) {
		try {
			return movieDAO.getById(id);
		} catch (DatabaseException dbe) {
			throw new ServiceException(dbe);/*
											 * TODO: deberia chequear por otros
											 * tipos de exception?
											 */
		}
	}

}