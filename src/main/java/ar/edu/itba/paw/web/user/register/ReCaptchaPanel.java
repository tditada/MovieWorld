package ar.edu.itba.paw.web.user.register;

import javax.servlet.http.HttpServletRequest;

import org.apache.wicket.Component;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.MarkupStream;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.http.WebRequest;
import org.apache.wicket.validation.INullAcceptingValidator;
import org.apache.wicket.validation.IValidatable;
import org.apache.wicket.validation.IValidator;
import org.apache.wicket.validation.ValidationError;

import net.tanesha.recaptcha.ReCaptchaFactory;
import net.tanesha.recaptcha.ReCaptchaImpl;
import net.tanesha.recaptcha.ReCaptchaResponse;

@SuppressWarnings("serial")
public class ReCaptchaPanel extends Panel {
	 
    private final static String PUBLIC_KEY = "6LeaD9sSAAAAAN6wKF8Vk4A4Q-raGG4r7RyZ9IwX";
    private final static String PRIVATE_KEY = "6LeaD9sSAAAAAKC5Gyi9Oq-jZ8ps4bykRHzx4jQC";
 
    public ReCaptchaPanel(String id) {
        super(id);
        add(reCaptchaComponent());
        
    }
    private Component reCaptchaComponent() {
        final FormComponent reCaptcha = new FormComponent("reCaptchaComponent", new Model()) {
            @Override
            public void onComponentTagBody(final MarkupStream markupStream, final ComponentTag openTag) {
                replaceComponentTagBody(markupStream, openTag,
                        ReCaptchaFactory.newReCaptcha(PUBLIC_KEY, PRIVATE_KEY, false)
                                .createRecaptchaHtml("errorText", "clean", null));
            }
        };
     
        reCaptcha.add(reCaptchaValidator());
        return reCaptcha;
    }
    
	  private IValidator reCaptchaValidator() {
	        return new INullAcceptingValidator() {
	            @Override
	            public void validate(IValidatable validatable) {
	                if (!isValid()) {
	                    validatable.error(new ValidationError().addKey("recaptcha.validation.error"));
	                }
	            }
	        };
	    }
	     
	    public boolean isValid() {
	        final ReCaptchaImpl reCaptcha = new ReCaptchaImpl();
	        reCaptcha.setPrivateKey(PRIVATE_KEY);
	     
	        final HttpServletRequest servletRequest = (HttpServletRequest ) ((WebRequest) getRequest()).getContainerRequest();
	        final String remoteAddress = servletRequest.getRemoteAddr();
	        final String challengeField = servletRequest.getParameter("recaptcha_challenge_field");
	        final String responseField = servletRequest.getParameter("recaptcha_response_field");
	     
	        final ReCaptchaResponse reCaptchaResponse = reCaptcha.checkAnswer(remoteAddress, challengeField, responseField);
	        return reCaptchaResponse.isValid();
	    }

  
}