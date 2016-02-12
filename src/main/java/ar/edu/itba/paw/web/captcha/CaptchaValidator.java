package ar.edu.itba.paw.web.captcha;

import org.apache.wicket.validation.IValidatable;
import org.apache.wicket.validation.IValidator;
import org.apache.wicket.validation.ValidationError;

@SuppressWarnings("serial")
public class CaptchaValidator implements IValidator<String> {

	private String randomString;

	public CaptchaValidator(String randomString) {
		this.randomString = randomString;
	}

	@Override
	public void validate(IValidatable<String> validatable) {
		if (!randomString.equals(validatable.getValue())) {
			validatable.error(new ValidationError(this));
		}
	}
	
}
