package ar.edu.itba.paw.g4.model;

import static ar.edu.itba.paw.g4.utils.ObjectHelpers.equal;
import static ar.edu.itba.paw.g4.utils.ObjectHelpers.hash;
import static ar.edu.itba.paw.g4.utils.ObjectHelpers.toStringHelper;
import static ar.edu.itba.paw.g4.utils.Validations.checkNotNullOrEmpty;
import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import java.util.List;

import net.karneim.pojobuilder.GeneratePojoBuilder;

import org.joda.time.DateTime;

import ar.edu.itba.paw.g4.enums.MovieGenres;
import ar.edu.itba.paw.g4.utils.persist.Entity;

public class Movie extends Entity {
	private String name;
	private DateTime creationDate;
	private List<MovieGenres> genres;
	private Director director;
	private int durationInMins;
	private String summary;

	@GeneratePojoBuilder
	public Movie(DateTime creationDate, String name, List<MovieGenres> genres,
			Director director, int durationInMins, String summary) {
		checkNotNull(creationDate);
		checkNotNullOrEmpty(name);
		checkArgument(!genres.isEmpty());
		checkNotNull(director);
		checkArgument(durationInMins > 0);
		checkNotNull(summary);

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

	@Override
	public String toString() {
		return toStringHelper(this).add("name", name)
				.add("creationDate", creationDate).add("genres", genres)
				.add("director", director)
				.add("durationInMins", durationInMins).add("summary", summary)
				.toString();
	}

	@Override
	public int hashCode() {
		return hash(name, creationDate, genres, director, durationInMins,
				summary);
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
		return equal(this.name, that.name)
				&& equal(this.creationDate, that.creationDate)
				&& equal(this.genres, that.genres)
				&& equal(this.director, that.director)
				&& equal(this.durationInMins, that.durationInMins)
				&& equal(this.summary, that.summary);
	}

}
