package ar.edu.itba.paw.web.captcha;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.wicket.extensions.markup.html.captcha.CaptchaImageResource;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.image.NonCachingImage;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.validation.IValidationError;
import org.apache.wicket.validation.validator.StringValidator;

@SuppressWarnings("serial")
public class CaptchaPanel extends Panel {
	public CaptchaPanel(String id, int nChars) {
	    super(id);

	    String random = RandomStringUtils.randomAlphanumeric(nChars);
	    TextField<String> textField = new TextField<String>("imagePassword"){
	    	@Override
	    	public void error(IValidationError error) {
	    		error(getString("errorCaptcha"));
	    	}
	    };
	    textField.setRequired(true);
	    textField.add(StringValidator.maximumLength(nChars));

	    Image image = new NonCachingImage("image", 
	                                      new CaptchaImageResource(random));
	    textField.add(new CaptchaValidator(random));
	    add(image);
	    add(textField);    
	    }
}
