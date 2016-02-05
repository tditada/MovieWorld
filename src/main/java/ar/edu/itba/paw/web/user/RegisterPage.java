package ar.edu.itba.paw.web.user;

import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
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

//TODO: Captcha
@SuppressWarnings("serial")
public class RegisterPage extends BasePage {

	@SpringBean
	private UserRepo users;

	private String firstName;
	private String lastName;
	private String email;
	private String birthday;
	public String password;
	private String confirmPassword;


	
	public RegisterPage() {
		Form<RegisterPage> form = new Form<RegisterPage>("registerForm",
				new CompoundPropertyModel<RegisterPage>(this)) {
			@Override
			protected void onSubmit() {
				String[] b = birthday.split("/");
				birthday = b[b.length-1] + "-" + b[0] + "-" + b[1];
				User user =
				 User.builder().withFirstName(new NonArtisticName(firstName)).withLastName(new NonArtisticName(lastName)).withPassword( new Password(password))
				 .withEmail(new Email(email)).withBirthDate(new DateTime(birthday)).build();
				 try {
				 users.register(user);
				 MovieWorldSession.get().signIn(user.getEmail().getTextAddress(),
				 user.getPassword().getPasswordString(), users);
				 setResponsePage(HomePage.class);
				 } catch (Exception e) {
					error(getString("userExists"));
					setResponsePage(RegisterPage.class);
				 }
			}

			@Override
			protected void onError() {
				super.onError();
				setResponsePage(RegisterPage.this);
			}

			@Override
			protected void onInitialize() {
				// TODO Auto-generated method stub
				super.onInitialize();
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

		form.add(new RequiredTextField<String>("password") {
			
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

			@Override
			protected String getInputType() {
				return "password";
			}
		});
//		password.setRequired(true);
//		form.add(password);

		TextField<String> confirmPassword = new RequiredTextField<String>("confirmPassword") {

			@Override
			protected void onInitialize() {
				super.onInitialize();
				add(new IValidator<String>() {
					@Override
					public void validate(IValidatable<String> validatable) {
						if (password!=null && !password.equals(validatable.getValue())) {
							validatable.error(new ValidationError(this));
						}
					}
				});
			}
			
			@Override
			public void error(IValidationError error) {
				error(getString("confirmPassError"));
			}

			@Override
			protected String getInputType() {
				return "password";
			}
		};
		confirmPassword.setRequired(true);
		form.add(confirmPassword);
		add(form);
	}

}

// @RequestMapping(value = "register", method = RequestMethod.GET)
// public ModelAndView showRegister(HttpSession session) {
// ModelAndView mav = new ModelAndView();
// User user = getLoggedUserFromSession(session);
//
// if (user != null) {
// // user is already registered
// mav.setViewName("redirect:/app/home");
// } else {
// mav.addObject("registerForm", new RegisterForm());
// mav.setViewName("register");
// }
// return mav;
// }
//
// @RequestMapping(value = "register", method = RequestMethod.POST)
// public ModelAndView register(RegisterForm registerForm, Errors errors,
// HttpSession session) {
// ModelAndView mav = new ModelAndView();
//
// if (userLoggedIn(session)) {
// // user is already registered
// mav.setViewName("redirect:/app/home");
// return mav;
// }
//
// registerFormValidator.validate(registerForm, errors);
// if (errors.hasErrors()) {
// mav.setViewName("register");
// return mav;
// }
//
// User user = registerForm.build();
// users.register(user);
//
// logUserInSession(session, user);
//
// mav.setViewName("redirect:/app/home");
// return mav;
// }
