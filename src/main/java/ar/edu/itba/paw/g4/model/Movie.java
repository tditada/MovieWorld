package ar.edu.itba.paw.g4.model;

import static ar.edu.itba.paw.g4.util.ObjectHelpers.areEqual;
import static ar.edu.itba.paw.g4.util.ObjectHelpers.hash;
import static ar.edu.itba.paw.g4.util.ObjectHelpers.toStringHelper;
import static ar.edu.itba.paw.g4.util.validation.PredicateHelpers.neitherNullNorEmpty;
import static ar.edu.itba.paw.g4.util.validation.PredicateHelpers.noRepetitionsList;
import static ar.edu.itba.paw.g4.util.validation.PredicateHelpers.notEmptyColl;
import static ar.edu.itba.paw.g4.util.validation.PredicateHelpers.notNull;
import static ar.edu.itba.paw.g4.util.validation.Validations.checkArgument;

import java.util.List;

import net.karneim.pojobuilder.GeneratePojoBuilder;

import org.joda.time.DateTime;
import org.joda.time.Interval;

import ar.edu.itba.paw.g4.enums.MovieGenres;
import ar.edu.itba.paw.g4.util.persist.Entity;

public class Movie extends Entity {
	public static final int DAYS_AS_RELEASE = 6;
	private static final int MIN_SCORE = 0;
	private static final int MAX_SCORE = 5;

	private String title;
	private DateTime creationDate;
	private DateTime releaseDate;
	private List<MovieGenres> genres;
	private Director director;
	private int runtimeInMins;
	private String summary;
	private int averageScore;

	@GeneratePojoBuilder
	public Movie(DateTime creationDate, DateTime releaseDate, String title,
			List<MovieGenres> genres, Director director, int runtimeInMins,
			String summary, int averageScore) {
		checkArgument(runtimeInMins > 0);
		checkArgument(creationDate, notNull());
		checkArgument(releaseDate, notNull());
		checkArgument(director, notNull());
		checkArgument(summary, notNull());
		checkArgument(title, neitherNullNorEmpty());
		checkArgument(genres, notNull(), notEmptyColl(), noRepetitionsList());
		checkArgument(averageScore >= MIN_SCORE && averageScore <= MAX_SCORE);

		this.title = title;
		this.creationDate = creationDate;
		this.releaseDate = releaseDate;
		this.genres = genres;
		this.director = director;
		this.runtimeInMins = runtimeInMins;
		this.summary = summary;
		this.averageScore = averageScore;
	}

	public int getAverageScore() {
		return averageScore;
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
				.add("summary", summary).add("score", averageScore).toString();
	}

	@Override
	public int hashCode() {
		return hash(title, creationDate, releaseDate, genres, director,
				runtimeInMins, summary, averageScore);
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
				&& areEqual(this.summary, that.summary)
				&& areEqual(this.averageScore, that.averageScore);
	}

	public static MovieBuilder builder() {
		return new MovieBuilder();
	}

}
