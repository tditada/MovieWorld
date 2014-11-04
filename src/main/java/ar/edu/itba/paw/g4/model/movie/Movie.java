package ar.edu.itba.paw.g4.model.movie;

import static ar.edu.itba.paw.g4.util.ObjectHelpers.areEqual;
import static ar.edu.itba.paw.g4.util.ObjectHelpers.hash;
import static ar.edu.itba.paw.g4.util.ObjectHelpers.toStringHelper;
import static ar.edu.itba.paw.g4.util.validation.PredicateHelpers.neitherNullNorEmpty;
import static ar.edu.itba.paw.g4.util.validation.PredicateHelpers.notEmptyColl;
import static ar.edu.itba.paw.g4.util.validation.PredicateHelpers.notNull;
import static ar.edu.itba.paw.g4.util.validation.Validations.checkArgument;
import static org.joda.time.DateTime.now;

import java.util.Collections;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.persistence.AttributeOverride;
import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import net.karneim.pojobuilder.GeneratePojoBuilder;

import org.hibernate.annotations.Check;
import org.hibernate.annotations.Sort;
import org.hibernate.annotations.SortType;
import org.joda.time.DateTime;
import org.joda.time.Interval;

import ar.edu.itba.paw.g4.model.Director;
import ar.edu.itba.paw.g4.model.MovieGenre;
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
	@Column(nullable = false)
	private DateTime creationDate;

	@Column(nullable = false)
	private DateTime releaseDate;

	@ElementCollection
	@CollectionTable(name = "genres", joinColumns = @JoinColumn(name = "movie_id"))
	@Column(nullable = false)
	// ^ TODO: check!
	private Set<MovieGenre> genres;

	// @ElementCollection(targetClass = MovieGenres.class)
	// @Enumerated(EnumType.STRING)
	// private Set<MovieGenres> genres;

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
	@OneToMany(mappedBy = "", cascade = CascadeType.ALL)
	private SortedSet<Comment> comments = new TreeSet<Comment>();

	Movie() {
	}

	@GeneratePojoBuilder
	public Movie(DateTime releaseDate, String title, Set<MovieGenre> genres,
			Director director, int runtimeInMins, String summary) {
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

	public Set<MovieGenre> getGenres() {
		return Collections.unmodifiableSet(genres);
	}

	public boolean isCommentableBy(User user) {
		checkArgument(user, notNull());
		if (releaseDate.isBeforeNow()) {
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

	public void setGenres(Set<MovieGenre> genres) {
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

		admin.removeComment(comment);
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
