package ar.edu.itba.paw.web.user;

import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.util.lang.Classes;
import org.apache.wicket.validation.IValidatable;
import org.apache.wicket.validation.IValidator;
import org.apache.wicket.validation.ValidationError;

import ar.edu.itba.paw.model.user.Password;


@SuppressWarnings("serial")
public class EqualValidator implements IValidator<Password> { 

	private TextField<Password> pass;

	public EqualValidator(TextField<Password> pass) {
		this.pass = pass;
	}

	@Override
	public void validate(IValidatable<Password> validatable) {

		if (!validatable.getValue().equals(pass)) {
			ValidationError error = new ValidationError(); 
			error.addKey(resourceKey()); 
			error.setVariable("password", pass.getValue()); 
			validatable.error(error);
		}
	}

	protected String resourceKey(){
		return Classes.simpleName(EqualValidator.class);
	}
}
