package ar.edu.itba.paw.web.user.register;

import javax.servlet.http.HttpServletRequest;

import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.form.validation.EqualPasswordInputValidator;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.request.http.WebRequest;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.validation.INullAcceptingValidator;
import org.apache.wicket.validation.IValidatable;
import org.apache.wicket.validation.IValidationError;
import org.apache.wicket.validation.IValidator;
import org.apache.wicket.validation.ValidationError;
import org.joda.time.DateTime;

import ar.edu.itba.paw.model.user.Email;
import ar.edu.itba.paw.model.user.NonArtisticName;
import ar.edu.itba.paw.model.user.Password;
import ar.edu.itba.paw.model.user.User;
import ar.edu.itba.paw.model.user.UserRepo;
import ar.edu.itba.paw.web.MovieWorldSession;
import ar.edu.itba.paw.web.base.BasePage;
import ar.edu.itba.paw.web.homepage.HomePage;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.form.DateTextField;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.form.DateTextFieldConfig;
import net.tanesha.recaptcha.ReCaptchaImpl;
import net.tanesha.recaptcha.ReCaptchaResponse;

@SuppressWarnings("serial")
public class RegisterPage extends BasePage {
	
	public static int CAPTCHA_SIZE = 5;

	@SpringBean
	private UserRepo users;

	private String firstName;
	private String lastName;
	private String email;
	private String birthday;
	public String password;
	private String confirmPassword;
	private String imagePassword;

	public RegisterPage() {
		Form<RegisterPage> form = new Form<RegisterPage>("registerForm",
				new CompoundPropertyModel<RegisterPage>(this)) {
			@Override
			protected void onSubmit() {
				String[] b = birthday.split("/");
				birthday = b[b.length - 1] + "-" + b[0] + "-" + b[1];
				User user = User.builder().withFirstName(new NonArtisticName(firstName))
						.withLastName(new NonArtisticName(lastName)).withPassword(new Password(password))
						.withEmail(new Email(email)).withBirthDate(new DateTime(birthday)).build();
				try {
					users.register(user);
					MovieWorldSession.get().signIn(user.getEmail().getTextAddress(),
							user.getPassword().getPasswordString(), users);
					setResponsePage(HomePage.class);
				} catch (Exception e) {
					error(getString("userExists"));
					setResponsePage(RegisterPage.this);
				}
			}

			@Override
			protected void onError() {
				super.onError();
				setResponsePage(RegisterPage.this);
			}

		};
		form.add(new FeedbackPanel("feedback") {
			@Override
			public boolean isVisible() {
				return super.isVisible() && anyMessage();
			}
		});

		TextField<String> firstname = new TextField<String>("firstName") {
			@Override
			protected void onInitialize() {
				super.onInitialize();
				add(new IValidator<String>() {
					@Override
					public void validate(IValidatable<String> validatable) {
						if (!NonArtisticName.isValid(validatable.getValue())) {
							validatable.error(new ValidationError(this));
						}
					}
				});
			}

			@Override
			public void error(IValidationError error) {
				error(getString("invalidName"));
			}
		};
		firstname.setRequired(true);
		form.add(firstname);

		TextField<String> lastname = new TextField<String>("lastName") {

			@Override
			protected void onInitialize() {
				super.onInitialize();
				add(new IValidator<String>() {
					@Override
					public void validate(IValidatable<String> validatable) {
						if (!NonArtisticName.isValid(validatable.getValue())) {
							validatable.error(new ValidationError(this));
						}
					}
				});
			}

			@Override
			public void error(IValidationError error) {
				error(getString("invalidLastName"));
			}
		};

		lastname.setRequired(true);
		form.add(lastname);

		TextField<String> email = new TextField<String>("email") {
			@Override
			protected void onInitialize() {
				super.onInitialize();
				add(new IValidator<String>() {
					@Override
					public void validate(IValidatable<String> validatable) {
						if (!Email.isValid(validatable.getValue())) {
							validatable.error(new ValidationError(this));
						}
					}
				});
			}

			@Override
			public void error(IValidationError error) {
				error(getString("errorEmail"));
			}

			@Override
			protected void onValid() {
				super.onValid();
				Email.isValid(getConvertedInput());
			}
		};
		email.setRequired(true);
		form.add(email);

		form.add(new DateTextField("birthday",
				new DateTextFieldConfig().autoClose(true).withStartDate(DateTime.now().minusYears(100))
						.withView(DateTextFieldConfig.View.Decade).withEndDate(DateTime.now())) {
			@Override
			public boolean isRequired() {
				return true;
			}

			@Override
			public void error(IValidationError error) {
				error(getString("invalidBirthday"));
			}
		});

		PasswordTextField password = new PasswordTextField("password") {

			@Override
			protected void onInitialize() {
				super.onInitialize();
				add(new IValidator<String>() {
					@Override
					public void validate(IValidatable<String> validatable) {
						if (!Password.isValid(validatable.getValue())) {
							validatable.error(new ValidationError(this));
						}
					}
				});
			}

			@Override
			public void error(IValidationError error) {
				error(getString("errorPassword"));
			}
		};
		password.isRequired();
		form.add(password);
		
		PasswordTextField confirmPassword = new PasswordTextField("confirmPassword") {
			@Override
			public void error(IValidationError error) {
				error(getString("confirmPassError"));
			}
		};
		confirmPassword.setRequired(true);
		form.add(confirmPassword);
		form.add(new EqualPasswordInputValidator(password, confirmPassword));
		ReCaptchaPanel recaptcha = new ReCaptchaPanel("captchaPanel");
		form.add(recaptcha);
		add(form);
	}
	

}
