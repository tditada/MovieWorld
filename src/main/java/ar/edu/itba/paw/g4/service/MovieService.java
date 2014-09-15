package ar.edu.itba.paw.g4.service;

import java.util.List;

import ar.edu.itba.paw.g4.enums.MovieGenres;
import ar.edu.itba.paw.g4.model.Movie;

public interface MovieService {
	public List<Movie> getAllMovies();

	public List<Movie> getAllMoviesByGenre(MovieGenres genre);

	public List<Movie> getNewestNMovies(int quantity);
}
