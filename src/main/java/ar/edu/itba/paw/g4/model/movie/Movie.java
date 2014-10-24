package ar.edu.itba.paw.g4.model.movie;

import static ar.edu.itba.paw.g4.util.ObjectHelpers.areEqual;
import static ar.edu.itba.paw.g4.util.ObjectHelpers.hash;
import static ar.edu.itba.paw.g4.util.ObjectHelpers.toStringHelper;
import static ar.edu.itba.paw.g4.util.validation.PredicateHelpers.neitherNullNorEmpty;
import static ar.edu.itba.paw.g4.util.validation.PredicateHelpers.noRepetitionsList;
import static ar.edu.itba.paw.g4.util.validation.PredicateHelpers.notEmptyColl;
import static ar.edu.itba.paw.g4.util.validation.PredicateHelpers.notNull;
import static ar.edu.itba.paw.g4.util.validation.Validations.checkArgument;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
import org.joda.time.DateTime;
import org.joda.time.Interval;

import ar.edu.itba.paw.g4.model.Comment;
import ar.edu.itba.paw.g4.model.Director;
import ar.edu.itba.paw.g4.model.MovieGenres;
import ar.edu.itba.paw.g4.model.builder.MovieBuilder;
import ar.edu.itba.paw.g4.model.user.User;
import ar.edu.itba.paw.g4.util.persist.PersistentEntity;

@Entity
@Table(name = "movies", uniqueConstraints = { @UniqueConstraint(columnNames = {
		"title", "director" }) })
public class Movie extends PersistentEntity {
	public static final int DAYS_AS_RELEASE = 6;
	public static final int MAX_TITLE_LENGTH = 255;

	@Column(nullable = false, length = MAX_TITLE_LENGTH)
	private String title; // artistic name for movie, so no special rules (other
							// than length) apply
	@Column(nullable = false)
	private DateTime creationDate;

	@Column(nullable = false)
	private DateTime releaseDate;

	@Column(nullable = false)
	@ElementCollection
	@Enumerated(EnumType.STRING)
	private List<MovieGenres> genres;

	@Embedded
	@AttributeOverride(name = "name", column = @Column(name = "director"))
	private Director director;

	@Check(constraints = "runtimeInMins > 0")
	private int runtimeInMins;

	@Column(nullable = false)
	private String summary;

	@Check(constraints = "totalScore >= 0")
	private int totalScore;

	@OneToMany(mappedBy = "movie")
	private Set<Comment> comments = new HashSet<Comment>();

	public Movie() {
	}

	@GeneratePojoBuilder
	public Movie(DateTime creationDate, DateTime releaseDate, String title,
			List<MovieGenres> genres, Director director, int runtimeInMins,
			String summary, int totalScore) {
		checkArgument(runtimeInMins > 0);
		checkArgument(creationDate, notNull());
		checkArgument(releaseDate, notNull());
		checkArgument(director, notNull());
		checkArgument(summary, notNull());
		checkArgument(title, neitherNullNorEmpty());
		checkArgument(title.length() <= MAX_TITLE_LENGTH);
		checkArgument(genres, notNull(), notEmptyColl(), noRepetitionsList());
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
		checkArgument(isCommentableBy(comment.getUser()));

		if (comments.contains(comment)) {
			return;
		}

		comments.add(comment);
		this.totalScore += comment.getScore();

		User user = comment.getUser();
		user.addComment(comment);
	}

	public int getTotalComments() {
		return comments.size();
	}

	public int getTotalScore() {
		return totalScore;
	}

	public Set<Comment> getComments() {
		return Collections.unmodifiableSet(comments);
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

	public List<MovieGenres> getGenres() {
		return Collections.unmodifiableList(genres);
	}

	public boolean isCommentableBy(User user) {
		checkArgument(user, notNull());
		if (!DateTime.now().isAfter(releaseDate)) {
			return false;
		}
		for (Comment c : comments) {
			if (c.getUser().equals(user)) {
				return false;
			}
		}
		return true;
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

}
