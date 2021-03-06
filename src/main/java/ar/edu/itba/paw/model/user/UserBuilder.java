package ar.edu.itba.paw.model.user;

import javax.annotation.Generated;

import org.joda.time.DateTime;

@Generated("PojoBuilder")
public class UserBuilder implements Cloneable {
	protected UserBuilder self;
	protected NonArtisticName value$firstName$ar$edu$itba$paw$model$NonArtisticName;
	protected boolean isSet$firstName$ar$edu$itba$paw$model$NonArtisticName;
	protected NonArtisticName value$lastName$ar$edu$itba$paw$model$NonArtisticName;
	protected boolean isSet$lastName$ar$edu$itba$paw$model$NonArtisticName;
	protected Email value$lastName$ar$edu$itba$paw$model$Email;
	protected boolean isSet$email$ar$edu$itba$paw$model$Email;
	protected Password value$password$ar$edu$itba$paw$model$Password;
	protected boolean isSet$password$ar$edu$itba$paw$model$Password;
	protected DateTime value$birthDate$org$joda$time$DateTime;
	protected boolean isSet$birthDate$org$joda$time$DateTime;
	protected Integer value$id$java$lang$Integer;
	protected boolean isSet$id$java$lang$Integer;

	/**
	 * Creates a new {@link UserBuilder}.
	 */
	public UserBuilder() {
		self = (UserBuilder) this;
	}

	/**
	 * Sets the default value for the {@link User#firstName} property.
	 *
	 * @param value
	 *            the default value
	 * @return this builder
	 */
	public UserBuilder withFirstName(NonArtisticName value) {
		this.value$firstName$ar$edu$itba$paw$model$NonArtisticName = value;
		this.isSet$firstName$ar$edu$itba$paw$model$NonArtisticName = true;
		return self;
	}

	/**
	 * Sets the default value for the {@link User#lastName} property.
	 *
	 * @param value
	 *            the default value
	 * @return this builder
	 */
	public UserBuilder withLastName(NonArtisticName value) {
		this.value$lastName$ar$edu$itba$paw$model$NonArtisticName = value;
		this.isSet$lastName$ar$edu$itba$paw$model$NonArtisticName = true;
		return self;
	}

	/**
	 * Sets the default value for the {@link User#email} property.
	 *
	 * @param value
	 *            the default value
	 * @return this builder
	 */
	public UserBuilder withEmail(Email value) {
		this.value$lastName$ar$edu$itba$paw$model$Email = value;
		this.isSet$email$ar$edu$itba$paw$model$Email = true;
		return self;
	}

	/**
	 * Sets the default value for the {@link User#password} property.
	 *
	 * @param value
	 *            the default value
	 * @return this builder
	 */
	public UserBuilder withPassword(Password value) {
		this.value$password$ar$edu$itba$paw$model$Password = value;
		this.isSet$password$ar$edu$itba$paw$model$Password = true;
		return self;
	}

	/**
	 * Sets the default value for the {@link User#birthDate} property.
	 *
	 * @param value
	 *            the default value
	 * @return this builder
	 */
	public UserBuilder withBirthDate(DateTime value) {
		this.value$birthDate$org$joda$time$DateTime = value;
		this.isSet$birthDate$org$joda$time$DateTime = true;
		return self;
	}

	/**
	 * Sets the default value for the {@link User#id} property.
	 *
	 * @param value
	 *            the default value
	 * @return this builder
	 */
	public UserBuilder withId(Integer value) {
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
			UserBuilder result = (UserBuilder) super.clone();
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
	public UserBuilder but() {
		return (UserBuilder) clone();
	}

	/**
	 * Creates a new {@link User} based on this builder's settings.
	 *
	 * @return the created User
	 */
	public User build() {
		try {
			User result = new User(
					value$firstName$ar$edu$itba$paw$model$NonArtisticName,
					value$lastName$ar$edu$itba$paw$model$NonArtisticName,
					value$lastName$ar$edu$itba$paw$model$Email,
					value$password$ar$edu$itba$paw$model$Password,
					value$birthDate$org$joda$time$DateTime, false/* FIXME */);
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
