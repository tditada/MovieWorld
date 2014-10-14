package ar.edu.itba.paw.g4.model.builder;

import javax.annotation.Generated;

import ar.edu.itba.paw.g4.model.EmailAddress;

@Generated("PojoBuilder")
public class EmailAddressBuilder implements Cloneable {
	protected EmailAddressBuilder self;
	protected String value$localPart$java$lang$String;
	protected boolean isSet$localPart$java$lang$String;
	protected String value$domainPart$java$lang$String;
	protected boolean isSet$domainPart$java$lang$String;

	/**
	 * Creates a new {@link EmailAddressBuilder}.
	 */
	public EmailAddressBuilder() {
		self = (EmailAddressBuilder) this;
	}

	/**
	 * Sets the default value for the {@link EmailAddress#localPart} property.
	 *
	 * @param value
	 *            the default value
	 * @return this builder
	 */
	public EmailAddressBuilder withLocalPart(String value) {
		this.value$localPart$java$lang$String = value;
		this.isSet$localPart$java$lang$String = true;
		return self;
	}

	/**
	 * Sets the default value for the {@link EmailAddress#domainPart} property.
	 *
	 * @param value
	 *            the default value
	 * @return this builder
	 */
	public EmailAddressBuilder withDomainPart(String value) {
		this.value$domainPart$java$lang$String = value;
		this.isSet$domainPart$java$lang$String = true;
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
			EmailAddressBuilder result = (EmailAddressBuilder) super.clone();
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
	public EmailAddressBuilder but() {
		return (EmailAddressBuilder) clone();
	}

	/**
	 * Creates a new {@link EmailAddress} based on this builder's settings.
	 *
	 * @return the created EmailAddress
	 */
	public EmailAddress build() {
		try {
			EmailAddress result = new EmailAddress(
					value$localPart$java$lang$String,
					value$domainPart$java$lang$String);
			return result;
		} catch (RuntimeException ex) {
			throw ex;
		} catch (Throwable t) {
			throw new java.lang.reflect.UndeclaredThrowableException(t);
		}
	}
}
