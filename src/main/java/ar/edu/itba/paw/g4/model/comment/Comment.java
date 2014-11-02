package ar.edu.itba.paw.g4.model.comment;

import static ar.edu.itba.paw.g4.util.ObjectHelpers.areEqual;
import static ar.edu.itba.paw.g4.util.ObjectHelpers.hash;
import static ar.edu.itba.paw.g4.util.ObjectHelpers.toStringHelper;
import static ar.edu.itba.paw.g4.util.validation.PredicateHelpers.neitherNullNorEmpty;
import static ar.edu.itba.paw.g4.util.validation.PredicateHelpers.notNull;
import static ar.edu.itba.paw.g4.util.validation.Validations.checkArgument;
import static org.joda.time.DateTime.now;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import net.karneim.pojobuilder.GeneratePojoBuilder;

import org.hibernate.annotations.Check;
import org.joda.time.DateTime;

import ar.edu.itba.paw.g4.model.movie.Movie;
import ar.edu.itba.paw.g4.model.user.User;
import ar.edu.itba.paw.g4.util.persist.PersistentEntity;

@Entity
@Table(name = "comments"/*
						 * TODO: Check! is this ok? , uniqueConstraints =
						 * 
						 * @UniqueConstraint(columnNames = { "movie", "user" })
						 */)
public class Comment extends PersistentEntity {
	private static final int MIN_SCORE = 0;
	private static final int MAX_SCORE = 5;

	@Check(constraints = "length(text) > 0")
	@Column(nullable = false)
	private String text;

	@Check(constraints = "(score >=" + MIN_SCORE + " AND " + "score <= "
			+ MAX_SCORE + ")")
	private int score;

	@Column(nullable = false)
	private DateTime creationDate;

	@ManyToOne
	private Movie movie;

	@ManyToOne
	private User user;

	Comment() {
	}

	@GeneratePojoBuilder
	Comment(String text, int score, User user, Movie movie,
			DateTime creationDate) {
		checkArgument(score >= MIN_SCORE && score <= MAX_SCORE);
		checkArgument(text, neitherNullNorEmpty());
		checkArgument(user, notNull());
		checkArgument(movie, notNull());
		checkArgument(movie.isCommentableBy(user));
		checkArgument(creationDate.isBeforeNow() || creationDate == null);

		this.text = text;
		this.score = score;
		this.user = user;
		this.movie = movie;
		this.creationDate = creationDate != null ? creationDate : now();
	}

	public String getText() {
		return text;
	}

	public int getScore() {
		return score;
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
				.add("movie", movie).add("score", score).add("text", text)
				.add("creationDate", creationDate).toString();
	}

	public static CommentBuilder builder() {
		return new CommentBuilder();
	}
}
