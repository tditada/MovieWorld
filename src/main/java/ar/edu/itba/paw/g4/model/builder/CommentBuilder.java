package ar.edu.itba.paw.g4.model.builder;

import javax.annotation.Generated;

import org.joda.time.DateTime;

import ar.edu.itba.paw.g4.model.Comment;
import ar.edu.itba.paw.g4.model.movie.Movie;
import ar.edu.itba.paw.g4.model.user.User;

@Generated("PojoBuilder")
public class CommentBuilder implements Cloneable {
	protected CommentBuilder self;
	protected String value$text$java$lang$String;
	protected boolean isSet$text$java$lang$String;
	protected int value$score$int;
	protected boolean isSet$score$int;
	protected User value$user$ar$edu$itba$paw$g4$model$User;
	protected boolean isSet$user$ar$edu$itba$paw$g4$model$User;
	protected Movie value$movie$ar$edu$itba$paw$g4$model$Movie;
	protected boolean isSet$movie$ar$edu$itba$paw$g4$model$Movie;
	protected DateTime value$creationDate$org$joda$time$DateTime;
	protected boolean isSet$creationDate$org$joda$time$DateTime;
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
	public CommentBuilder withScore(int value) {
		this.value$score$int = value;
		this.isSet$score$int = true;
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
		this.value$user$ar$edu$itba$paw$g4$model$User = value;
		this.isSet$user$ar$edu$itba$paw$g4$model$User = true;
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
		this.value$movie$ar$edu$itba$paw$g4$model$Movie = value;
		this.isSet$movie$ar$edu$itba$paw$g4$model$Movie = true;
		return self;
	}

	/**
	 * Sets the default value for the {@link Comment#creationDate} property.
	 *
	 * @param value
	 *            the default value
	 * @return this builder
	 */
	public CommentBuilder withCreationDate(DateTime value) {
		this.value$creationDate$org$joda$time$DateTime = value;
		this.isSet$creationDate$org$joda$time$DateTime = true;
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
					value$score$int, value$user$ar$edu$itba$paw$g4$model$User,
					value$movie$ar$edu$itba$paw$g4$model$Movie,
					value$creationDate$org$joda$time$DateTime);
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
