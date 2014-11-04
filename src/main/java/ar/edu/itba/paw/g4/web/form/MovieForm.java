package ar.edu.itba.paw.g4.web.form;

import static org.joda.time.DateTime.now;

import java.util.Set;

import org.joda.time.DateTime;

import ar.edu.itba.paw.g4.model.Director;
import ar.edu.itba.paw.g4.model.MovieGenres;
import ar.edu.itba.paw.g4.model.movie.Movie;

public class MovieForm {
	private String title;
	private Director director;
	private String summary;
	private Set<MovieGenres> genres;
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

	public Set<MovieGenres> getGenres() {
		return genres;
	}

	public void setGenres(Set<MovieGenres> genres) {
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
		try {
			Movie movie = Movie.builder().withCreationDate(now())
					.withDirector(director).withGenres(genres)
					.withReleaseDate(releaseDate)
					.withRuntimeInMins(runtimeInMins).withSummary(summary)
					.withTitle(title).withTotalScore(0).build();
			return movie;
		} catch (Exception e) {
			System.out.println("Error in  bulding");
			e.printStackTrace();
		}
		return null;
	}

}
