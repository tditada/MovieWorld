package ar.edu.itba.paw.model.comment;

import javax.annotation.Generated;

import ar.edu.itba.paw.model.Score;
import ar.edu.itba.paw.model.movie.Movie;
import ar.edu.itba.paw.model.user.User;

@Generated("PojoBuilder")
public class CommentBuilder implements Cloneable {
	protected CommentBuilder self;
	protected String value$text$java$lang$String;
	protected boolean isSet$text$java$lang$String;
	protected Score value$score$ar$edu$itba$paw$model$Score;
	protected boolean isSet$score$ar$edu$itba$paw$model$Score;
	protected User value$user$ar$edu$itba$paw$model$User;
	protected boolean isSet$user$ar$edu$itba$paw$model$User;
	protected Movie value$movie$ar$edu$itba$paw$model$Movie;
	protected boolean isSet$movie$ar$edu$itba$paw$model$Movie;
	protected Integer value$id$java$lang$Integer;
	protected boolean isSet$id$java$lang$Integer;

	/**
	 * Creates a new {@link CommentBuilder}.
	 */
	public CommentBuilder() {
		self = (CommentBuilder) this;
	}

	/**
	 * Sets the default value for the {@link Comment#text} property.
	 *
	 * @param value
	 *            the default value
	 * @return this builder
	 */
	public CommentBuilder withText(String value) {
		this.value$text$java$lang$String = value;
		this.isSet$text$java$lang$String = true;
		return self;
	}

	/**
	 * Sets the default value for the {@link Comment#score} property.
	 *
	 * @param value
	 *            the default value
	 * @return this builder
	 */
	public CommentBuilder withScore(Score value) {
		this.value$score$ar$edu$itba$paw$model$Score = value;
		this.isSet$score$ar$edu$itba$paw$model$Score = true;
		return self;
	}

	/**
	 * Sets the default value for the {@link Comment#user} property.
	 *
	 * @param value
	 *            the default value
	 * @return this builder
	 */
	public CommentBuilder withUser(User value) {
		this.value$user$ar$edu$itba$paw$model$User = value;
		this.isSet$user$ar$edu$itba$paw$model$User = true;
		return self;
	}

	/**
	 * Sets the default value for the {@link Comment#movie} property.
	 *
	 * @param value
	 *            the default value
	 * @return this builder
	 */
	public CommentBuilder withMovie(Movie value) {
		this.value$movie$ar$edu$itba$paw$model$Movie = value;
		this.isSet$movie$ar$edu$itba$paw$model$Movie = true;
		return self;
	}

	/**
	 * Sets the default value for the {@link Comment#id} property.
	 *
	 * @param value
	 *            the default value
	 * @return this builder
	 */
	public CommentBuilder withId(Integer value) {
		this.value$id$java$lang$Integer = value;
		this.isSet$id$java$lang$Integer = true;
		return self;
	}

	/**
	 * Returns a clone of this builder.
	 *
	 * @return the clone
	 */
	@Override
	public Object clone() {
		try {
			CommentBuilder result = (CommentBuilder) super.clone();
			result.self = result;
			return result;
		} catch (CloneNotSupportedException e) {
			throw new InternalError(e.getMessage());
		}
	}

	/**
	 * Returns a clone of this builder.
	 *
	 * @return the clone
	 */
	public CommentBuilder but() {
		return (CommentBuilder) clone();
	}

	/**
	 * Creates a new {@link Comment} based on this builder's settings.
	 *
	 * @return the created Comment
	 */
	public Comment build() {
		try {
			Comment result = new Comment(value$text$java$lang$String,
					value$score$ar$edu$itba$paw$model$Score,
					value$user$ar$edu$itba$paw$model$User,
					value$movie$ar$edu$itba$paw$model$Movie);
			if (isSet$id$java$lang$Integer) {
				result.setId(value$id$java$lang$Integer);
			}
			return result;
		} catch (RuntimeException ex) {
			throw ex;
		} catch (Throwable t) {
			throw new java.lang.reflect.UndeclaredThrowableException(t);
		}
	}
}
