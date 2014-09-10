package ar.edu.itba.paw.g4.model;

import static ar.edu.itba.paw.g4.utils.ObjectHelpers.areEqual;
import static ar.edu.itba.paw.g4.utils.ObjectHelpers.hash;
import static ar.edu.itba.paw.g4.utils.ObjectHelpers.toStringHelper;
import static ar.edu.itba.paw.g4.utils.validation.PredicateExtras.notEmptyStr;
import static ar.edu.itba.paw.g4.utils.validation.Validations.checkArgument;
import static com.google.common.base.Predicates.notNull;
import net.karneim.pojobuilder.GeneratePojoBuilder;
import ar.edu.itba.paw.g4.utils.persist.Entity;

public class Comment extends Entity {
	private String text;
	private int score;
	private User user;
	private Movie movie;

	@GeneratePojoBuilder
	public Comment(String text, int score, User user, Movie movie) {
		checkArgument(text, notNull(), notEmptyStr());
		checkArgument(score > 0);
		checkArgument(user != null);
		checkArgument(movie != null);

		this.text = text;
		this.score = score;
		this.user = user;
		this.movie = movie;
	}

	@Override
	public int hashCode() {
		return hash(user, score, text, movie);
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
				&& areEqual(this.user, that.user)
				&& areEqual(this.movie, that.movie);
	}

	@Override
	public String toString() {
		return toStringHelper(this).add("user", user).add("movie", movie)
				.add("score", score).add("text", text).toString();
	}

	public static CommentBuilder builder() {
		return new CommentBuilder();
	}
}
