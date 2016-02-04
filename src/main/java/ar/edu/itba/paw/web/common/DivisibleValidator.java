package ar.edu.itba.paw.web.common;

import org.apache.wicket.util.lang.Classes;
import org.apache.wicket.validation.IValidatable;
import org.apache.wicket.validation.IValidator;
import org.apache.wicket.validation.ValidationError;

@SuppressWarnings("serial")
public class DivisibleValidator implements IValidator<Integer> {
	
	private Integer divisor;
	
	public DivisibleValidator(Integer divisor) {
		this.divisor = divisor;
	}
	
	@Override
	public void validate(IValidatable<Integer> validatable) {
		Integer value = validatable.getValue();
		if (value % divisor != 0)
		{
			ValidationError error = new ValidationError();
//			error.addKey(resourceKey());
			error.addMessageKey(resourceKey());
			error.setVariable("divisor", divisor);
			validatable.error(error);
		}
	}
	
	protected String resourceKey()	{
		return Classes.simpleName(DivisibleValidator.class);
	}
}
