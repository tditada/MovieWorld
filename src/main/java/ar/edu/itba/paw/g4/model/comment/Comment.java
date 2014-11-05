package ar.edu.itba.paw.g4.model.comment;

import static ar.edu.itba.paw.g4.util.ObjectHelpers.areEqual;
import static ar.edu.itba.paw.g4.util.ObjectHelpers.hash;
import static ar.edu.itba.paw.g4.util.ObjectHelpers.toStringHelper;
import static ar.edu.itba.paw.g4.util.validation.PredicateHelpers.neitherNullNorEmpty;
import static ar.edu.itba.paw.g4.util.validation.PredicateHelpers.notNull;
import static ar.edu.itba.paw.g4.util.validation.Validations.checkArgument;
import static org.joda.time.DateTime.now;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import net.karneim.pojobuilder.GeneratePojoBuilder;

import org.hibernate.annotations.Check;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import ar.edu.itba.paw.g4.model.Score;
import ar.edu.itba.paw.g4.model.movie.Movie;
import ar.edu.itba.paw.g4.model.user.User;
import ar.edu.itba.paw.g4.util.persist.PersistentEntity;

@Entity
@Table(name = "comments")
public class Comment extends PersistentEntity implements Comparable<Comment> {
	@Check(constraints = "length(text) > 0")
	@Column(nullable = false)
	private String text;

	@Embedded
	@AttributeOverride(name = "score", column = @Column(name = "movieScore", nullable = false))
	private Score movieScore;

	@OneToMany
	private Set<User> scorers = new HashSet<>();

	@Column(nullable = false)
	@Check(constraints = "totalCommentScore >= 0")
	private int totalCommentScore;

	@Type(type = "org.joda.time.contrib.hibernate.PersistentDateTime")
	@Column(nullable = false)
	private DateTime creationDate;

	@ManyToOne
	private Movie movie;

	@ManyToOne
	private User user;

	Comment() {
	}

	@GeneratePojoBuilder
	Comment(String text, Score movieScore, User user, Movie movie) {
		checkArgument(movieScore, notNull());
		checkArgument(text, neitherNullNorEmpty());
		checkArgument(user, notNull());
		checkArgument(movie, notNull());
		checkArgument(movie.isCommentableBy(user));

		this.text = text;
		this.movieScore = movieScore;
		this.user = user;
		this.movie = movie;

		this.creationDate = now();
		this.totalCommentScore = 0;
	}

	public boolean canBeScoredBy(User user) {
		checkArgument(user, notNull());

		return !this.user.equals(user) && scorers.contains(user);
	}

	public void addScore(User user, Score commentScore) {
		checkArgument(user, notNull());
		checkArgument(commentScore, notNull());
		checkArgument(canBeScoredBy(user));

		scorers.add(user);
		totalCommentScore += commentScore.getValue();
	}

	public int getAverageCommentScore() {
		if (totalCommentScore == 0) {
			return 0;
		}
		return totalCommentScore / scorers.size();
	}

	//
	// @Override
	// public int compareTo(Comment other) {
	// // greater to smaller
	// return Double.compare(other.getAverageCommentScore(),
	// this.getAverageCommentScore());
	// // return ((Double) commentAverageScore).compareTo((Double)
	// // c.commentAverageScore);
	// }

	public String getText() {
		return text;
	}

	public Score getMovieScore() {
		return movieScore;
	}

	public User getUser() {
		return user;
	}

	public Movie getMovie() {
		return movie;
	}

	public DateTime getCreationDate() {
		return creationDate;
	}

	public Set<User> getUsersThatScore() {// TODO
		return scorers;
	}

	@Override
	public int compareTo(Comment other) {
		if (other.equals(this)) {
			return 0;
		}

		Integer comparison = Double.compare(other.getAverageCommentScore(),
				this.getAverageCommentScore());
		if (comparison == 0) {
			return other.getUser().getFirstName().getNameString()
					.compareTo(user.getFirstName().getNameString());
		}
		return comparison;
	}

	@Override
	public int hashCode() {
		return hash(user, movie);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}
		Comment that = (Comment) obj;
		return areEqual(this.user, that.user)
				&& areEqual(this.movie, that.movie);
	}

	@Override
	public String toString() {
		return toStringHelper(this).add("id", getId()).add("user", user)
				.add("movie", movie).add("score", movieScore).add("text", text)
				.add("creationDate", creationDate)
				.add("scoredByUsers", scorers)
				.add("totalCommentScore", totalCommentScore).toString();
	}

	public static CommentBuilder builder() {
		return new CommentBuilder();
	}

}
