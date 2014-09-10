package ar.edu.itba.paw.g4.model;

import static ar.edu.itba.paw.g4.utils.ObjectHelpers.areEqual;
import static ar.edu.itba.paw.g4.utils.ObjectHelpers.hash;
import static ar.edu.itba.paw.g4.utils.ObjectHelpers.toStringHelper;
import static ar.edu.itba.paw.g4.utils.validation.PredicateExtras.notEmptyColl;
import static ar.edu.itba.paw.g4.utils.validation.PredicateExtras.notEmptyStr;
import static ar.edu.itba.paw.g4.utils.validation.Validations.checkArgument;
import static com.google.common.base.Predicates.notNull;
import static org.joda.time.DateTime.now;

import java.util.List;

import net.karneim.pojobuilder.GeneratePojoBuilder;

import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.joda.time.Period;

import ar.edu.itba.paw.g4.enums.MovieGenres;
import ar.edu.itba.paw.g4.utils.persist.Entity;

public class Movie extends Entity {
	private static final int MAX_DAYS_AS_RELEASE = 6;

	private String name;
	private DateTime creationDate;
	private DateTime releaseDate;
	private List<MovieGenres> genres;
	private Director director;
	private int durationInMins;
	private String summary;

	@GeneratePojoBuilder
	public Movie(DateTime creationDate, DateTime releaseDate, String name,
			List<MovieGenres> genres, Director director, int durationInMins,
			String summary) {
		checkArgument(creationDate != null);
		checkArgument(releaseDate != null);
		checkArgument(director != null);
		checkArgument(summary != null);
		checkArgument(name, notNull(), notEmptyStr());
		checkArgument(genres, notNull(), notEmptyColl());
		checkArgument(durationInMins > 0);

		this.creationDate = creationDate;
		this.name = name;
		this.genres = genres;
		this.director = director;
		this.durationInMins = durationInMins;
		this.summary = summary;
	}

	public DateTime getCreationDate() {
		return creationDate;
	}

	public String getName() {
		return name;
	}

	public List<MovieGenres> getGenre() {
		return genres;
	}

	public Director getDirector() {
		return director;
	}

	public int getDurationInMins() {
		return durationInMins;
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
		return toStringHelper(this).add("name", name)
				.add("creationDate", creationDate)
				.add("releaseDate", releaseDate).add("genres", genres)
				.add("director", director)
				.add("durationInMins", durationInMins).add("summary", summary)
				.toString();
	}

	@Override
	public int hashCode() {
		return hash(name, creationDate, releaseDate, genres, director,
				durationInMins, summary);
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
		return areEqual(this.name, that.name)
				&& areEqual(this.releaseDate, that.releaseDate)
				&& areEqual(this.creationDate, that.creationDate)
				&& areEqual(this.genres, that.genres)
				&& areEqual(this.director, that.director)
				&& areEqual(this.durationInMins, that.durationInMins)
				&& areEqual(this.summary, that.summary);
	}

	public static MovieBuilder builder() {
		return new MovieBuilder();
	}

}
