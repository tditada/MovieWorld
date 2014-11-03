package ar.edu.itba.paw.g4.model.movie;

import static ar.edu.itba.paw.g4.util.ObjectHelpers.areEqual;
import static ar.edu.itba.paw.g4.util.ObjectHelpers.hash;
import static ar.edu.itba.paw.g4.util.ObjectHelpers.toStringHelper;
import static ar.edu.itba.paw.g4.util.validation.PredicateHelpers.neitherNullNorEmpty;
import static ar.edu.itba.paw.g4.util.validation.PredicateHelpers.notEmptyColl;
import static ar.edu.itba.paw.g4.util.validation.PredicateHelpers.notNull;
import static ar.edu.itba.paw.g4.util.validation.Validations.checkArgument;

import java.util.Collections;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import net.karneim.pojobuilder.GeneratePojoBuilder;

import org.hibernate.annotations.Check;
import org.hibernate.annotations.Sort;
import org.hibernate.annotations.SortType;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;
import org.joda.time.Interval;

import ar.edu.itba.paw.g4.model.Director;
import ar.edu.itba.paw.g4.model.MovieGenres;
import ar.edu.itba.paw.g4.model.comment.Comment;
import ar.edu.itba.paw.g4.model.user.User;
import ar.edu.itba.paw.g4.util.persist.PersistentEntity;

@Entity
@Table(name = "movies", uniqueConstraints = @UniqueConstraint(columnNames = {
		"title", "director" }))
public class Movie extends PersistentEntity {
	static final int DAYS_AS_RELEASE = 6;
	private static final int MAX_TITLE_LENGTH = 255;

	@Column(nullable = false, length = MAX_TITLE_LENGTH)
	private String title; // artistic name for movie, so no special rules (other
							// than length) apply
	@Type(type="org.joda.time.contrib.hibernate.PersistentDateTime")
	@Column(nullable = false)
	private DateTime creationDate;

	@Type(type="org.joda.time.contrib.hibernate.PersistentDateTime")
	@Column(nullable = false)
	private DateTime releaseDate;

	@ElementCollection
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private Set<MovieGenres> genres;

	@Embedded
	@AttributeOverride(name = "name", column = @Column(name = "director"))
	private Director director;

	@Check(constraints = "runtimeInMins > 0")
	private int runtimeInMins;

	@Column(nullable = false)
	private String summary;

	@Check(constraints = "totalScore >= 0")
	private int totalScore;

	@Sort(type=SortType.NATURAL)
	@OneToMany(mappedBy = "movie")
	private SortedSet<Comment> comments = new TreeSet<Comment>();

	Movie() {
	}

	@GeneratePojoBuilder
	public Movie(DateTime creationDate, DateTime releaseDate, String title,
			Set<MovieGenres> genres, Director director, int runtimeInMins,
			String summary, int totalScore) {
		checkArgument(runtimeInMins > 0);
		checkArgument(creationDate, notNull());
		checkArgument(releaseDate, notNull());
		checkArgument(director, notNull());
		checkArgument(summary, notNull());
		checkArgument(title, neitherNullNorEmpty());
		checkArgument(title.length() <= MAX_TITLE_LENGTH);
		checkArgument(genres, notNull(), notEmptyColl());
		checkArgument(totalScore >= 0);

		this.title = title;
		this.creationDate = creationDate;
		this.releaseDate = releaseDate;
		this.genres = genres;
		this.director = director;
		this.runtimeInMins = runtimeInMins;
		this.summary = summary;
		this.totalScore = totalScore;
	}

	public void addComment(Comment comment) {
		checkArgument(comment, notNull());
		checkArgument(this.equals(comment.getMovie()));

		if (comments.contains(comment)) {
			// this will only happen when addComment is called in a
			// callback
			return;
		}

		User user = comment.getUser();
		if (!isCommentableBy(user)) {
			throw new IllegalArgumentException();
		}

		comments.add(comment);
		this.totalScore += comment.getScore();

		user.addComment(comment);
	}

	public int getTotalComments() {
		return comments.size();
	}

	public int getTotalScore() {
		return totalScore;
	}

	public SortedSet<Comment> getComments() {
		return Collections.unmodifiableSortedSet(comments);
	}

	public int getAverageScore() {
		if (getTotalComments() == 0) {
			return 0;
		}
		return totalScore / getTotalComments();
	}

	public DateTime getCreationDate() {
		return creationDate;
	}

	public String getTitle() {
		return title;
	}

	public Set<MovieGenres> getGenres() {
		return Collections.unmodifiableSet(genres);
	}

	public boolean isCommentableBy(User user) {
		checkArgument(user, notNull());
		if (releaseDate.isAfterNow()) {
			return false;
		}
		for (Comment comment : comments) {
			if (comment.getUser().equals(user)) {
				return false;
			}
		}
		return true;
	}

	public boolean isRelease() {
		DateTime now = DateTime.now();
		Interval releaseInterval = new Interval(now.minusDays(DAYS_AS_RELEASE),
				now);
		return releaseInterval.contains(releaseDate);
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

	@Override
	public String toString() {
		return toStringHelper(this).add("name", title).add("id", getId())
				.add("creationDate", creationDate)
				.add("releaseDate", releaseDate).add("genres", genres)
				.add("director", director).add("durationInMins", runtimeInMins)
				.add("summary", summary).add("comments", comments)
				.add("totalScore", totalScore).toString();
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

	public void updateMovie(String title, DateTime releaseDate,
			Set<MovieGenres> genres, Director director, String summary,
			int runtimeInMins) {
		checkArgument(title, neitherNullNorEmpty());
		checkArgument(title.length() <= MAX_TITLE_LENGTH);
		checkArgument(releaseDate, notNull());
		checkArgument(genres, notNull(), notEmptyColl());
		checkArgument(director, notNull());
		checkArgument(summary, notNull());
		checkArgument(runtimeInMins > 0);

		if (this.title != title) {
			this.title = title;
		}
		if (this.releaseDate != releaseDate) {
			this.releaseDate = releaseDate;
		}
		if (this.genres != genres) {
			this.genres = genres;
		}
		if (this.director != director) {
			this.director = director;
		}
		if (this.summary != summary) {
			this.summary = summary;
		}
		if (this.runtimeInMins != runtimeInMins) {
			this.runtimeInMins = runtimeInMins;
		}
	}

	public void removeComment(Comment c) {
		comments.remove(c);
	}

}
