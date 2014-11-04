package ar.edu.itba.paw.g4.model.comment;

import static ar.edu.itba.paw.g4.util.ObjectHelpers.areEqual;
import static ar.edu.itba.paw.g4.util.ObjectHelpers.hash;
import static ar.edu.itba.paw.g4.util.ObjectHelpers.toStringHelper;
import static ar.edu.itba.paw.g4.util.validation.PredicateHelpers.neitherNullNorEmpty;
import static ar.edu.itba.paw.g4.util.validation.PredicateHelpers.notNull;
import static ar.edu.itba.paw.g4.util.validation.Validations.checkArgument;
import static org.joda.time.DateTime.now;

import java.util.Map;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import net.karneim.pojobuilder.GeneratePojoBuilder;

import org.hibernate.annotations.Check;
import org.joda.time.DateTime;

import ar.edu.itba.paw.g4.model.Score;
import ar.edu.itba.paw.g4.model.movie.Movie;
import ar.edu.itba.paw.g4.model.user.User;
import ar.edu.itba.paw.g4.util.persist.PersistentEntity;

@Entity
@Table(name = "comments"/*
						 * TODO: Check! is this ok? , uniqueConstraints =
						 * 
						 * @UniqueConstraint(columnNames = { "movie", "user" })
						 */)
public class Comment extends PersistentEntity implements Comparable<Comment> {
	@Check(constraints = "length(text) > 0")
	@Column(nullable = false)
	private String text;

	@Embedded
	@AttributeOverride(name = "score", column = @Column(name = "movieScore", nullable = false))
	private Score movieScore;

	@ElementCollection
	// @AttributeOverrides({ @AttributeOverride(name = "value.score", column =
	// @Column(name = "score")) })
	private Map<User, Score> commentScoreByUser;

	@Column(nullable = false)
	@Check(constraints = "totalCommentScore >= 0")
	private int totalCommentScore;

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

		return !this.user.equals(user) && commentScoreByUser.containsKey(user);
	}

	public void addScore(User user, Score commentScore) {
		checkArgument(user, notNull());
		checkArgument(commentScore, notNull());
		checkArgument(canBeScoredBy(user));

		commentScoreByUser.put(user, commentScore);
		totalCommentScore += commentScore.getValue();
	}

	public int getAverageCommentScore() {
		if (totalCommentScore == 0) {
			return 0;
		}
		return totalCommentScore / commentScoreByUser.size();
	}

	@Override
	public int compareTo(Comment other) {
		// greater to smaller
		return Double.compare(other.getAverageCommentScore(),
				this.getAverageCommentScore());
		// return ((Double) commentAverageScore).compareTo((Double)
		// c.commentAverageScore);
	}

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
				.add("commentScoreByUser", commentScoreByUser)
				.add("totalCommentScore", totalCommentScore).toString();
	}

	public static CommentBuilder builder() {
		return new CommentBuilder();
	}
}
