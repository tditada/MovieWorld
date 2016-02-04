package ar.edu.itba.paw.web.user;

import javax.validation.constraints.Max;

import org.apache.tools.ant.taskdefs.email.EmailAddress;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.validation.IValidationError;

import ar.edu.itba.paw.model.user.Password;
import ar.edu.itba.paw.model.user.UserRepo;
import ar.edu.itba.paw.web.MovieWorldSession;
import ar.edu.itba.paw.web.base.BasePage;
import ar.edu.itba.paw.web.homepage.HomePage;

@SuppressWarnings("serial")
public class LoginPage extends BasePage {
	@SpringBean
	private UserRepo users;

	private EmailAddress email;
	private Password password;

	public LoginPage() {

		Form<LoginPage> form = new Form<LoginPage>("loginForm", new CompoundPropertyModel<LoginPage>(this)) {
			@Override
			protected void onSubmit() {
				MovieWorldSession session = MovieWorldSession.get();
				if (session.signIn(email.getAddress(), password.getPasswordString(), users)) {
					continueToOriginalDestination();
					setResponsePage(getApplication().getHomePage());
				} else {
					error(getString("invalidCredentials"));
				}
			}
		};

		add(new FeedbackPanel("feedback"){
            @Override
            public boolean isVisible() {
            	this.setMaxMessages(1);
                return super.isVisible() && anyMessage();
            }
        });
		
		form.add(new RequiredTextField<EmailAddress>("email") {
			@Override
			public void error(IValidationError error) {
				error(getString("errorLogin"));
			}
		});
		form.add(new RequiredTextField<Password>("password") {
			@Override
			public void error(IValidationError error) {
				error(getString("errorLogin"));
			}

			@Override
			protected void onComponentTag(ComponentTag tag) {
				super.onComponentTag(tag);
				tag.put("value", "");
			}

			@Override
			protected String getInputType() {
				return "password";
			}
		});
		form.add(new Link<Void>("register") {
			@Override
			public void onClick() {
				// TODO: change link
				setResponsePage(new RegisterPage());
			}
		});
		add(form);
	}
}
