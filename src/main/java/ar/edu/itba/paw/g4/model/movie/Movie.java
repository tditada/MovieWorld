package ar.edu.itba.paw.g4.model.movie;

import static ar.edu.itba.paw.g4.util.ObjectHelpers.areEqual;
import static ar.edu.itba.paw.g4.util.ObjectHelpers.hash;
import static ar.edu.itba.paw.g4.util.validation.PredicateHelpers.neitherNullNorEmpty;
import static ar.edu.itba.paw.g4.util.validation.PredicateHelpers.notEmptyColl;
import static ar.edu.itba.paw.g4.util.validation.PredicateHelpers.notNull;
import static ar.edu.itba.paw.g4.util.validation.Validations.checkArgument;
import static org.joda.time.DateTime.now;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.persistence.AttributeOverride;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
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

import ar.edu.itba.paw.g4.model.Score;
import ar.edu.itba.paw.g4.model.comment.Comment;
import ar.edu.itba.paw.g4.model.genre.Genre;
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
	@Type(type = "org.joda.time.contrib.hibernate.PersistentDateTime")
	@Column(nullable = false)
	private DateTime creationDate;

	@Type(type = "org.joda.time.contrib.hibernate.PersistentDateTime")
	@Column(nullable = false)
	private DateTime releaseDate;

	@Sort(type = SortType.NATURAL)
	@ManyToMany(cascade = CascadeType.ALL)
	private SortedSet<Genre> genres; // a movie can have many different
											// genres and a genre can have many
											// different movies

	@Embedded
	@AttributeOverride(name = "name", column = @Column(name = "director"))
	private Director director;

	@Check(constraints = "runtimeInMins > 0")
	private int runtimeInMins;

	@Column(nullable = false)
	private String summary;

	@Check(constraints = "totalScore >= 0")
	private int totalScore;

	@Sort(type = SortType.NATURAL)
	@OneToMany(mappedBy = "movie", cascade = CascadeType.ALL)
	private SortedSet<Comment> comments = new TreeSet<Comment>();

	Movie() {
	}

	@GeneratePojoBuilder
	public Movie(DateTime releaseDate, String title,
			SortedSet<Genre> genres, Director director, int runtimeInMins,
			String summary) {
		setTitle(title);
		setReleaseDate(releaseDate);
		setGenres(genres);
		setDirector(director);
		setRuntimeInMins(runtimeInMins);
		setSummary(summary);

		this.creationDate = now();
		this.totalScore = 0;
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
		this.totalScore += comment.getMovieScore().getValue();

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

	public Score getAverageScore() {
		if (getTotalComments() == 0) {
			return new Score(0);
		}
		return new Score(totalScore / getTotalComments());
	}

	public DateTime getCreationDate() {
		return creationDate;
	}

	public String getTitle() {
		return title;
	}

	public Set<Genre> getGenres() {
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

	public void setTitle(String title) {
		checkArgument(title, neitherNullNorEmpty());
		checkArgument(title.length() <= MAX_TITLE_LENGTH);
		this.title = title;
	}

	public void setDirector(Director director) {
		checkArgument(director, notNull());
		this.director = director;
	}

	public void setGenres(SortedSet<Genre> genres) {
		checkArgument(genres, notNull(), notEmptyColl());
		this.genres = genres;
	}

	public void setSummary(String summary) {
		checkArgument(summary, notNull());
		this.summary = summary;
	}

	public void setReleaseDate(DateTime releaseDate) {
		checkArgument(releaseDate, notNull());
		this.releaseDate = releaseDate;
	}

	public void setRuntimeInMins(int runtimeInMins) {
		checkArgument(runtimeInMins > 0);
		this.runtimeInMins = runtimeInMins;
	}

	public void removeComment(User admin, Comment comment) {
		checkArgument(admin, notNull());
		checkArgument(comment, notNull());
		checkArgument(admin.isAdmin());

		if (!comments.contains(comment)) {
			// this will only happen when removeComment is called in a
			// callback
			return;
		}

		comments.remove(comment);
		this.totalScore -= comment.getMovieScore().getValue();

		admin.removeComment(comment);
	}

	public List<Comment> getCommentsScoreableBy(User user) {
		List<Comment> scoreableComments = new LinkedList<>();
		for (Comment comment : comments) {
			if (comment.canBeScoredBy(user)) {
				scoreableComments.add(comment);
			}
		}
		return Collections.unmodifiableList(scoreableComments);
	}

	public List<Comment> getCommentsReportableBy(User user) {
		List<Comment> reportableComments = new LinkedList<>();
		for (Comment comment : comments) {
			if (comment.isReportableBy(user)) {
				reportableComments.add(comment);
			}
		}
		return Collections.unmodifiableList(reportableComments);
	}

	@Override
	public String toString() {
		// TODO
		return "YYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYY";
		// return toStringHelper(this).add("name", title).add("id", getId())
		// .add("creationDate", creationDate)
		// .add("releaseDate", releaseDate).add("genres", genres)
		// .add("director", director).add("durationInMins", runtimeInMins)
		// .add("summary", summary).add("totalScore", totalScore)
		// .toString();
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
