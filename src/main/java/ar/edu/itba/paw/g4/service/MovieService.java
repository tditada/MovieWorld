package ar.edu.itba.paw.g4.service;

import java.util.List;

import ar.edu.itba.paw.g4.model.Director;
import ar.edu.itba.paw.g4.model.MovieGenres;
import ar.edu.itba.paw.g4.model.movie.Movie;

public interface MovieService {
	public List<Movie> getMovieList();

	public List<Movie> getAllMoviesByGenre(MovieGenres genre);

	public List<Movie> getNewAdditions(int quantity);

	public List<Movie> getReleases();

	public List<Movie> getAllMoviesByDirector(Director director);

	public Movie getMovieById(int id);

	public List<Movie> getTopMovies(int quantity);

	public List<Director> getAllDirectors();
}
