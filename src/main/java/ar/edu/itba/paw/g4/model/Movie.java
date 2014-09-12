package ar.edu.itba.paw.g4.model;

import static ar.edu.itba.paw.g4.util.ObjectHelpers.areEqual;
import static ar.edu.itba.paw.g4.util.ObjectHelpers.hash;
import static ar.edu.itba.paw.g4.util.ObjectHelpers.toStringHelper;
import static ar.edu.itba.paw.g4.util.validation.PredicateHelpers.neitherNullNorEmpty;
import static ar.edu.itba.paw.g4.util.validation.PredicateHelpers.notEmptyColl;
import static ar.edu.itba.paw.g4.util.validation.PredicateHelpers.notNull;
import static ar.edu.itba.paw.g4.util.validation.Validations.checkArgument;
import static org.joda.time.DateTime.now;

import java.util.List;

import net.karneim.pojobuilder.GeneratePojoBuilder;

import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.joda.time.Period;

import ar.edu.itba.paw.g4.enums.MovieGenres;
import ar.edu.itba.paw.g4.util.persist.Entity;

public class Movie extends Entity {
	private static final int MAX_DAYS_AS_RELEASE = 6;

	private String title;
	private DateTime creationDate;
	private DateTime releaseDate;
	private List<MovieGenres> genres;
	private Director director;
	private int runtimeInMins;
	private String summary;

	@GeneratePojoBuilder
	public Movie(DateTime creationDate, DateTime releaseDate, String title,
			List<MovieGenres> genres, Director director, int runtimeInMins,
			String summary) {
		checkArgument(runtimeInMins > 0);
		checkArgument(creationDate, notNull());
		checkArgument(releaseDate, notNull());
		checkArgument(director, notNull());
		checkArgument(summary, notNull());
		checkArgument(title, neitherNullNorEmpty());
		checkArgument(genres, notNull(), notEmptyColl());

		this.title = title;
		this.creationDate = creationDate;
		this.releaseDate = releaseDate;
		this.genres = genres;
		this.director = director;
		this.runtimeInMins = runtimeInMins;
		this.summary = summary;
	}

	public DateTime getCreationDate() {
		return creationDate;
	}

	public String getTitle() {
		return title;
	}

	public List<MovieGenres> getGenre() {
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
		Interval releaseInterval = new Interval(releaseDate,
				Period.days(MAX_DAYS_AS_RELEASE));
		return releaseInterval.contains(now());
	}

	@Override
	public String toString() {
		return toStringHelper(this).add("name", title)
				.add("creationDate", creationDate)
				.add("releaseDate", releaseDate).add("genres", genres)
				.add("director", director).add("durationInMins", runtimeInMins)
				.add("summary", summary).toString();
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
