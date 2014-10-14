package ar.edu.itba.paw.g4.model.builder;

import javax.annotation.Generated;

import ar.edu.itba.paw.g4.model.Director;

@Generated("PojoBuilder")
public class DirectorBuilder implements Cloneable {
	protected DirectorBuilder self;
	protected String value$name$java$lang$String;
	protected boolean isSet$name$java$lang$String;

	/**
	 * Creates a new {@link DirectorBuilder}.
	 */
	public DirectorBuilder() {
		self = (DirectorBuilder) this;
	}

	/**
	 * Sets the default value for the {@link Director#name} property.
	 *
	 * @param value
	 *            the default value
	 * @return this builder
	 */
	public DirectorBuilder withName(String value) {
		this.value$name$java$lang$String = value;
		this.isSet$name$java$lang$String = true;
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
			DirectorBuilder result = (DirectorBuilder) super.clone();
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
	public DirectorBuilder but() {
		return (DirectorBuilder) clone();
	}

	/**
	 * Creates a new {@link Director} based on this builder's settings.
	 *
	 * @return the created Director
	 */
	public Director build() {
		try {
			Director result = new Director(value$name$java$lang$String);
			return result;
		} catch (RuntimeException ex) {
			throw ex;
		} catch (Throwable t) {
			throw new java.lang.reflect.UndeclaredThrowableException(t);
		}
	}
}
