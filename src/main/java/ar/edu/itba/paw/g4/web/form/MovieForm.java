package ar.edu.itba.paw.g4.web.form;

import java.util.SortedSet;

import org.joda.time.DateTime;

import ar.edu.itba.paw.g4.model.movie.Director;
import ar.edu.itba.paw.g4.model.movie.Movie;
import ar.edu.itba.paw.g4.model.movie.MovieGenre;

public class MovieForm {
	private String title;
	private Director director;
	private String summary;
	private SortedSet<MovieGenre> genres;
	private int runtimeInMins;
	private DateTime releaseDate;

	public MovieForm() {
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Director getDirector() {
		return director;
	}

	public void setDirector(Director director) {
		this.director = director;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public SortedSet<MovieGenre> getGenres() {
		return genres;
	}

	public void setGenres(SortedSet<MovieGenre> genres) {
		this.genres = genres;
	}

	public int getRuntimeInMins() {
		return runtimeInMins;
	}

	public void setRuntimeInMins(int runtimeInMins) {
		this.runtimeInMins = runtimeInMins;
	}

	public DateTime getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(DateTime releaseDate) {
		this.releaseDate = releaseDate;
	}

	public Movie build() {
		return Movie.builder().withDirector(director).withGenres(genres)
				.withReleaseDate(releaseDate).withRuntimeInMins(runtimeInMins)
				.withSummary(summary).withTitle(title).build();
	}

}
