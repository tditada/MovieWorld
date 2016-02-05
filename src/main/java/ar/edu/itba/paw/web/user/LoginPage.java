package ar.edu.itba.paw.web.user;

import org.apache.tools.ant.taskdefs.email.EmailAddress;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.validation.IValidationError;

import ar.edu.itba.paw.model.user.UserRepo;
import ar.edu.itba.paw.web.MovieWorldSession;
import ar.edu.itba.paw.web.base.BasePage;
import ar.edu.itba.paw.web.homepage.HomePage;

@SuppressWarnings("serial")
public class LoginPage extends BasePage {
	@SpringBean
	private UserRepo users;

	private String email;
	private String password;

	public LoginPage() {

		Form<LoginPage> form = new Form<LoginPage>("loginForm", new CompoundPropertyModel<LoginPage>(this)) {
			@Override
			protected void onSubmit() {
				MovieWorldSession session = MovieWorldSession.get();
				if (session.signIn(email, password, users)) {
					continueToOriginalDestination();
					setResponsePage(HomePage.class);
				} else {
					error(getString("errorLogin"));
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
		
		form.add(new RequiredTextField<String>("email") {
			@Override
			public void error(IValidationError error) {
				error(getString("errorLogin"));
			}
		});
		form.add(new RequiredTextField<String>("password") {
			@Override
			public void error(IValidationError error) {
				error(getString("errorLogin"));
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
