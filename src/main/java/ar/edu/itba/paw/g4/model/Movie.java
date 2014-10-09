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

import org.joda.time.DateTime;
import org.joda.time.Interval;

import ar.edu.itba.paw.g4.model.builder.MovieBuilder;
import ar.edu.itba.paw.g4.util.persist.Entity;

public class Movie extends Entity {
	public static final int DAYS_AS_RELEASE = 6;
	public static final int MAX_TITLE_LENGTH = 255;

	private String title; // artistic name for movie, so no special rules (other
							// than length) apply
	private DateTime creationDate;
	private DateTime releaseDate;
	private List<MovieGenres> genres;
	private Director director;
	private int runtimeInMins;
	private String summary;
	private int totalScore;
	private int totalComments;

	// @GeneratePojoBuilder
	public Movie(DateTime creationDate, DateTime releaseDate, String title,
			List<MovieGenres> genres, Director director, int runtimeInMins,
			String summary, int totalScore, int totalComments) {
		checkArgument(runtimeInMins > 0);
		checkArgument(creationDate, notNull());
		checkArgument(releaseDate, notNull());
		checkArgument(director, notNull());
		checkArgument(summary, notNull());
		checkArgument(title, neitherNullNorEmpty());
		checkArgument(title.length() <= MAX_TITLE_LENGTH);
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

	public void addComment(Comment comment) {
		this.totalScore += comment.getScore();
		this.totalComments++;
	}

	public int getTotalComments() {
		return totalComments;
	}

	public int getTotalScore() {
		return totalScore;
	}

	public int getAverageScore() {
		if (totalComments == 0) {
			return 0;
		}
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

	public boolean isCommentable() {
		return DateTime.now().isAfter(releaseDate);
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
		return hash(title, director);
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
				&& areEqual(this.director, that.director);
	}

	public static MovieBuilder builder() {
		return new MovieBuilder();
	}

}
