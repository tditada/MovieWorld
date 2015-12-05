package ar.edu.itba.paw.web.form;

import java.util.SortedSet;

import org.joda.time.DateTime;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import ar.edu.itba.paw.model.genre.Genre;
import ar.edu.itba.paw.model.movie.Director;
import ar.edu.itba.paw.model.movie.Movie;

public class MovieForm {
	private String title;
	private Director director;
	private String summary;
	private SortedSet<Genre> genres;
	private int runtimeInMins;
	private DateTime releaseDate;
	private CommonsMultipartFile picture;
	private boolean deletePicture;

	public MovieForm() {
		deletePicture=false;
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

	public SortedSet<Genre> getGenres() {
		return genres;
	}

	public void setGenres(SortedSet<Genre> genres) {
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
				.withSummary(summary).withTitle(title).withPicture(picture.getBytes()).build();
	}

	public CommonsMultipartFile getPicture() {
		return picture;
	}

	public void setPicture(CommonsMultipartFile picture) {
		this.picture = picture;
	}

	public boolean isDeletePicture() {
		return deletePicture;
	}

	public void setDeletePicture(boolean deletePicture) {
		this.deletePicture = deletePicture;
	}

}
