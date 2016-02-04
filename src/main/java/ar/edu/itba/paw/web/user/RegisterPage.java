package ar.edu.itba.paw.web.user;

import org.apache.wicket.markup.ComponentTag;
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
import org.joda.time.LocalDate;

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

	private NonArtisticName firstName;
	private NonArtisticName lastName;
	private Email email;
	private String birthDate;
	private Password password;
	private Password confirmPassword;

	public RegisterPage() {

		Form<RegisterPage> form = new Form<RegisterPage>("registerForm",
				new CompoundPropertyModel<RegisterPage>(this)) {
			@Override
			protected void onSubmit() {
				DateTime date = DateTime.parse(birthDate);
				User user = User.builder().withFirstName(firstName).withLastName(lastName).withPassword(password)
						.withEmail(email).withBirthDate(date).build();
				try {
					users.register(user);
					MovieWorldSession.get().signIn(user.getEmail().getTextAddress(),
							user.getPassword().getPasswordString(), users);
					setResponsePage(HomePage.class);
				} catch (Exception e) {
					error(getString("user.exists"));
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
			public void error(IValidationError error) {
				error(getString("invalidName"));
			}
		};
		firstname.setRequired(true);
		form.add(firstname);

		TextField<String> lastname = new TextField<String>("lastName") {
			@Override
			public void error(IValidationError error) {
				error(getString("invalidName"));
			}
		};
		lastname.setRequired(true);
		form.add(lastname);

		TextField<Email> email = new TextField<Email>("email") {
			@Override
			public void error(IValidationError error) {
				error(getString("errorEmail"));
			}
		};
		email.setRequired(true);
		form.add(email);

		form.add(new DateTextField("birthDate",
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

	     form.add(new RequiredTextField<Password>("password") {
              @Override
              public void error(IValidationError error) {
                  error(getString(getId() + ".invalid"));
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
	      
        form.add(new RequiredTextField<Password>("confirmPassword") {
            @Override
            protected void onInitialize() {
                super.onInitialize();
                add(new IValidator<Password>() {
                    @Override
                    public void validate(
                            IValidatable<Password> validatable) {
                        if (password != null && !validatable.getValue()
                                .equals(password)) {
                            validatable
                                    .error(new ValidationError(this));
                        }
                    }
                });
            }

            @Override
            protected void onComponentTag(ComponentTag tag) {
                super.onComponentTag(tag);
                tag.put("value", "");
            }

            @Override
            public void error(IValidationError error) {
                error(getString("confirmPassError"));
            }

            @Override
            protected String getInputType() {
                return "password";
            }
        });
//		confirmPassword.setRequired(true);
//		password.add(new EqualValidator(confirmPassword));
//		form.add(confirmPassword);
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
