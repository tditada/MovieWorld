package ar.edu.itba.paw.g4.service.impl;

import java.util.List;

import ar.edu.itba.paw.g4.model.Movie;
import ar.edu.itba.paw.g4.persist.MovieDAO;
import ar.edu.itba.paw.g4.persist.impl.PSQLMovieDAO;
import ar.edu.itba.paw.g4.service.MovieService;

public class MovieServiceImpl implements MovieService {
	private MovieDAO movieDAO = PSQLMovieDAO.getInstance();

	@Override
	public List<Movie> getAllMovies() {
		return movieDAO.getAll();
	}

}
