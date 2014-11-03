package ar.edu.itba.paw.g4.web.form;

import java.util.Set;

import org.joda.time.DateTime;

import ar.edu.itba.paw.g4.model.Director;
import ar.edu.itba.paw.g4.model.MovieGenres;
import ar.edu.itba.paw.g4.model.movie.Movie;

public class MovieForm {
	private String filmTitle;
	private Director filmDirector;
	private String filmSummary;
	private Set<MovieGenres> filmGenres;
	private int filmRuntimeInMins;
	private DateTime filmReleaseDate;

	public MovieForm() {
	}

	public String getfilmTitle() {
		return filmTitle;
	}

	public void setfilmTitle(String filmTitle) {
		this.filmTitle = filmTitle;
	}

	public Director getfilmDirector() {
		return filmDirector;
	}

	public void setfilmDirector(Director filmDirector) {
		this.filmDirector = filmDirector;
	}

	public String getfilmSummary() {
		return filmSummary;
	}

	public void setfilmSummary(String filmSummary) {
		this.filmSummary = filmSummary;
	}

	public int getfilmRuntimeInMins() {
		return filmRuntimeInMins;
	}

	public void setfilmRuntimeInMins(int filmRuntimeInMins) {
		this.filmRuntimeInMins = filmRuntimeInMins;
	}

	public DateTime getfilmReleaseDate() {
		return filmReleaseDate;
	}

	public void setfilmReleaseDate(DateTime filmReleaseDate) {
		this.filmReleaseDate = filmReleaseDate;
	}

	public Set<MovieGenres> getFilmGenres() {
		return filmGenres;
	}

	public void setFilmGenres(Set<MovieGenres> filmGenres) {
		this.filmGenres = filmGenres;
	}

	public Movie build() {
		try {
			Movie movie = Movie.builder().withCreationDate(DateTime.now())
					.withDirector(filmDirector).withGenres(filmGenres)
					.withReleaseDate(filmReleaseDate)
					.withRuntimeInMins(filmRuntimeInMins)
					.withSummary(filmSummary).withTitle(filmTitle)
					.withTotalScore(0).build();
			return movie;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
