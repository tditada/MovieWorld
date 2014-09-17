package ar.edu.itba.paw.g4.service;

import java.util.List;

import ar.edu.itba.paw.g4.enums.MovieGenres;
import ar.edu.itba.paw.g4.model.Director;
import ar.edu.itba.paw.g4.model.Movie;

public interface MovieService {
	public List<Movie> getMovieList();

	public List<Movie> getAllMoviesByGenre(MovieGenres genre);

	public List<Movie> getNewAdditions(int maxQuantity);
	
	public List<Movie> getReleases();

	public List<Movie> getAllMoviesByDirector(Director director);
	
	public Movie getMovieById(int id);
}