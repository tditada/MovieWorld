package ar.edu.itba.paw.g4.model;

import static ar.edu.itba.paw.g4.util.ObjectHelpers.areEqual;
import static ar.edu.itba.paw.g4.util.ObjectHelpers.hash;
import static ar.edu.itba.paw.g4.util.ObjectHelpers.toStringHelper;
import static ar.edu.itba.paw.g4.util.validation.PredicateHelpers.neitherNullNorEmpty;
import static ar.edu.itba.paw.g4.util.validation.PredicateHelpers.notNull;
import static ar.edu.itba.paw.g4.util.validation.Validations.checkArgument;
import static org.joda.time.DateTime.now;
import net.karneim.pojobuilder.GeneratePojoBuilder;

import org.joda.time.DateTime;

import ar.edu.itba.paw.g4.util.persist.Entity;

public class Comment extends Entity {
	private String text;
	private int score;
	private User user;
	private Movie movie;
	private DateTime creationDate;

	@GeneratePojoBuilder
	public Comment(String text, int score, User user, Movie movie,
			DateTime creationDate) {
		checkArgument(score > 0);
		checkArgument(text, neitherNullNorEmpty());
		checkArgument(user, notNull());
		checkArgument(movie, notNull());

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
		return hash(user, score, text, movie, creationDate);
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
		return areEqual(this.score, that.score)
				&& areEqual(this.text, that.text)
				&& areEqual(this.creationDate, that.creationDate)
				&& areEqual(this.user, that.user)
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
