package ar.edu.itba.paw.g4.model.builder;

import java.util.Set;

import javax.annotation.Generated;

import org.joda.time.DateTime;

import ar.edu.itba.paw.g4.model.Director;
import ar.edu.itba.paw.g4.model.MovieGenres;
import ar.edu.itba.paw.g4.model.movie.Movie;

@Generated("PojoBuilder")
public class MovieBuilder implements Cloneable {
	protected MovieBuilder self;
	protected DateTime value$creationDate$org$joda$time$DateTime;
	protected boolean isSet$creationDate$org$joda$time$DateTime;
	protected DateTime value$releaseDate$org$joda$time$DateTime;
	protected boolean isSet$releaseDate$org$joda$time$DateTime;
	protected String value$title$java$lang$String;
	protected boolean isSet$title$java$lang$String;
	protected Set<MovieGenres> value$genres$java$util$Set;
	protected boolean isSet$genres$java$util$Set;
	protected Director value$director$ar$edu$itba$paw$g4$model$Director;
	protected boolean isSet$director$ar$edu$itba$paw$g4$model$Director;
	protected int value$runtimeInMins$int;
	protected boolean isSet$runtimeInMins$int;
	protected String value$summary$java$lang$String;
	protected boolean isSet$summary$java$lang$String;
	protected int value$totalScore$int;
	protected boolean isSet$totalScore$int;
	protected Integer value$id$java$lang$Integer;
	protected boolean isSet$id$java$lang$Integer;

	/**
	 * Creates a new {@link MovieBuilder}.
	 */
	public MovieBuilder() {
		self = (MovieBuilder) this;
	}

	/**
	 * Sets the default value for the {@link Movie#creationDate} property.
	 *
	 * @param value
	 *            the default value
	 * @return this builder
	 */
	public MovieBuilder withCreationDate(DateTime value) {
		this.value$creationDate$org$joda$time$DateTime = value;
		this.isSet$creationDate$org$joda$time$DateTime = true;
		return self;
	}

	/**
	 * Sets the default value for the {@link Movie#releaseDate} property.
	 *
	 * @param value
	 *            the default value
	 * @return this builder
	 */
	public MovieBuilder withReleaseDate(DateTime value) {
		this.value$releaseDate$org$joda$time$DateTime = value;
		this.isSet$releaseDate$org$joda$time$DateTime = true;
		return self;
	}

	/**
	 * Sets the default value for the {@link Movie#title} property.
	 *
	 * @param value
	 *            the default value
	 * @return this builder
	 */
	public MovieBuilder withTitle(String value) {
		this.value$title$java$lang$String = value;
		this.isSet$title$java$lang$String = true;
		return self;
	}

	/**
	 * Sets the default value for the {@link Movie#genres} property.
	 *
	 * @param value
	 *            the default value
	 * @return this builder
	 */
	public MovieBuilder withGenres(Set<MovieGenres> value) {
		this.value$genres$java$util$Set = value;
		this.isSet$genres$java$util$Set = true;
		return self;
	}

	/**
	 * Sets the default value for the {@link Movie#director} property.
	 *
	 * @param value
	 *            the default value
	 * @return this builder
	 */
	public MovieBuilder withDirector(Director value) {
		this.value$director$ar$edu$itba$paw$g4$model$Director = value;
		this.isSet$director$ar$edu$itba$paw$g4$model$Director = true;
		return self;
	}

	/**
	 * Sets the default value for the {@link Movie#runtimeInMins} property.
	 *
	 * @param value
	 *            the default value
	 * @return this builder
	 */
	public MovieBuilder withRuntimeInMins(int value) {
		this.value$runtimeInMins$int = value;
		this.isSet$runtimeInMins$int = true;
		return self;
	}

	/**
	 * Sets the default value for the {@link Movie#summary} property.
	 *
	 * @param value
	 *            the default value
	 * @return this builder
	 */
	public MovieBuilder withSummary(String value) {
		this.value$summary$java$lang$String = value;
		this.isSet$summary$java$lang$String = true;
		return self;
	}

	/**
	 * Sets the default value for the {@link Movie#totalScore} property.
	 *
	 * @param value
	 *            the default value
	 * @return this builder
	 */
	public MovieBuilder withTotalScore(int value) {
		this.value$totalScore$int = value;
		this.isSet$totalScore$int = true;
		return self;
	}

	/**
	 * Sets the default value for the {@link Movie#id} property.
	 *
	 * @param value
	 *            the default value
	 * @return this builder
	 */
	public MovieBuilder withId(Integer value) {
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
			MovieBuilder result = (MovieBuilder) super.clone();
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
	public MovieBuilder but() {
		return (MovieBuilder) clone();
	}

	/**
	 * Creates a new {@link Movie} based on this builder's settings.
	 *
	 * @return the created Movie
	 */
	public Movie build() {
		try {
			Movie result = new Movie(value$creationDate$org$joda$time$DateTime,
					value$releaseDate$org$joda$time$DateTime,
					value$title$java$lang$String, value$genres$java$util$Set,
					value$director$ar$edu$itba$paw$g4$model$Director,
					value$runtimeInMins$int, value$summary$java$lang$String,
					value$totalScore$int);
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
