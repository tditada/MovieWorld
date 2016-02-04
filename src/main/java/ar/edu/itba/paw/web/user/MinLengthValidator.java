package ar.edu.itba.paw.web.user;

import org.apache.wicket.util.lang.Classes;
import org.apache.wicket.validation.IValidatable;
import org.apache.wicket.validation.IValidator;
import org.apache.wicket.validation.ValidationError;

@SuppressWarnings("serial")
public class MinLengthValidator implements IValidator<String> { 

	private int value;

	public MinLengthValidator(int value) {
		this.value = value;
	}

	@Override
	public void validate(IValidatable<String> validatable) {

		if(validatable.getValue().length() < value){
			ValidationError error = new ValidationError(); 
			error.addKey(resourceKey()); 
			error.setVariable("min_length", value); 
			validatable.error(error);
		}
	}

	protected String resourceKey(){
		return Classes.simpleName(MinLengthValidator.class);
	}

}
