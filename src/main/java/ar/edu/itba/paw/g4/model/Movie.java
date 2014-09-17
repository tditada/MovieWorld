package ar.edu.itba.paw.g4.model;

import static ar.edu.itba.paw.g4.util.ObjectHelpers.areEqual;
import static ar.edu.itba.paw.g4.util.ObjectHelpers.hash;
import static ar.edu.itba.paw.g4.util.ObjectHelpers.toStringHelper;
import static ar.edu.itba.paw.g4.util.validation.PredicateHelpers.neitherNullNorEmpty;
import static ar.edu.itba.paw.g4.util.validation.PredicateHelpers.noRepetitionsList;
import static ar.edu.itba.paw.g4.util.validation.PredicateHelpers.notEmptyColl;
import static ar.edu.itba.paw.g4.util.validation.PredicateHelpers.notNull;
import static ar.edu.itba.paw.g4.util.validation.Validations.checkArgument;
import static ar.edu.itba.paw.g4.util.validation.Validations.checkState;

import java.util.List;

import net.karneim.pojobuilder.GeneratePojoBuilder;

import org.joda.time.DateTime;
import org.joda.time.Interval;

import ar.edu.itba.paw.g4.enums.MovieGenres;
import ar.edu.itba.paw.g4.util.persist.Entity;

public class Movie extends Entity {
	public static final int DAYS_AS_RELEASE = 6;

	private String title;
	private DateTime creationDate;
	private DateTime releaseDate;
	private List<MovieGenres> genres;
	private Director director;
	private int runtimeInMins;
	private String summary;
	private int totalScore;
	private int totalComments;

	@GeneratePojoBuilder
	public Movie(DateTime creationDate, DateTime releaseDate, String title,
			List<MovieGenres> genres, Director director, int runtimeInMins,
			String summary, int totalScore, int totalComments) {
		checkArgument(runtimeInMins > 0);
		checkArgument(creationDate, notNull());
		checkArgument(releaseDate, notNull());
		checkArgument(director, notNull());
		checkArgument(summary, notNull());
		checkArgument(title, neitherNullNorEmpty());
		checkArgument(genres, notNull(), notEmptyColl(), noRepetitionsList());
		checkArgument(totalScore >= 0);
		checkArgument(totalComments >= 0);

		this.title = title;
		this.creationDate = creationDate;
		this.releaseDate = releaseDate;
		this.genres = genres;
		this.director = director;
		this.runtimeInMins = runtimeInMins;
		this.summary = summary;
		this.totalScore = totalScore;
		this.totalComments = totalComments;
	}

	public int getTotalComments() {
		return totalComments;
	}

	public int getTotalScore() {
		return totalScore;
	}

	public int getAverageScore() {
		checkState(totalComments > 0);
		return totalScore / totalComments;
	}

	public DateTime getCreationDate() {
		return creationDate;
	}

	public String getTitle() {
		return title;
	}

	public List<MovieGenres> getGenres() {
		return genres;
	}

	public Director getDirector() {
		return director;
	}

	public int getRuntimeInMins() {
		return runtimeInMins;
	}

	public String getSummary() {
		return summary;
	}

	public DateTime getReleaseDate() {
		return releaseDate;
	}

	public boolean isRelease() {
		DateTime now = DateTime.now();
		Interval releaseInterval = new Interval(now.minusDays(DAYS_AS_RELEASE),
				now);
		return releaseInterval.contains(releaseDate);
	}

	@Override
	public String toString() {
		return toStringHelper(this).add("name", title).add("id", getId())
				.add("creationDate", creationDate)
				.add("releaseDate", releaseDate).add("genres", genres)
				.add("director", director).add("durationInMins", runtimeInMins)
				.add("summary", summary).add("totalScore", totalScore)
				.add("totalComments", totalComments).toString();
	}

	@Override
	public int hashCode() {
		return hash(title, creationDate, releaseDate, genres, director,
				runtimeInMins, summary);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}
		Movie that = (Movie) obj;
		return areEqual(this.title, that.title)
				&& areEqual(this.releaseDate, that.releaseDate)
				&& areEqual(this.creationDate, that.creationDate)
				&& areEqual(this.genres, that.genres)
				&& areEqual(this.director, that.director)
				&& areEqual(this.runtimeInMins, that.runtimeInMins)
				&& areEqual(this.summary, that.summary);
	}

	public static MovieBuilder builder() {
		return new MovieBuilder();
	}

}
