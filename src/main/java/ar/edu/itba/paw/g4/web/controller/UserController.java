package ar.edu.itba.paw.g4.web.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import ar.edu.itba.paw.g4.model.user.User;
import ar.edu.itba.paw.g4.model.user.UserRepo;
import ar.edu.itba.paw.g4.web.form.LoginForm;
import ar.edu.itba.paw.g4.web.form.RegistrationForm;
import ar.edu.itba.paw.g4.web.form.validation.LoginFormValidator;
import ar.edu.itba.paw.g4.web.form.validation.RegistrationFormValidator;

@Controller
@RequestMapping("/user")
@SessionAttributes({ "user" })
public class UserController {
	private static final String AUTH_FAILED_ID = "authFailed";
	private static final String USER_ID = "user";

	private UserRepo users;
	private RegistrationFormValidator registrationFormValidator;
	private LoginFormValidator loginFormValidator;

	@Autowired
	public UserController(UserRepo users,
			RegistrationFormValidator registrationFormValidator,
			LoginFormValidator loginFormValidator) {
		this.users = users;
		this.registrationFormValidator = registrationFormValidator;
		this.loginFormValidator = loginFormValidator;
	}

	@RequestMapping(value = "register", method = RequestMethod.GET)
	public ModelAndView showRegistration(
			@RequestParam(value = USER_ID, required = false) User user) {
		ModelAndView mav = new ModelAndView();
		if (user != null) {
			mav.setViewName("redirect:/app/home");
		} else {
			mav.addObject("registrationForm", new RegistrationForm());
			mav.setViewName("register");
		}
		return mav;
	}

	@RequestMapping(value = "register", method = RequestMethod.POST)
	public ModelAndView register(RegistrationForm registrationForm,
			Errors errors, HttpSession session) {
		ModelAndView mav = new ModelAndView();
		registrationFormValidator.validate(registrationForm, errors);
		if (errors.hasErrors()) {
			mav.setViewName("register");
			return mav;
		}

		User user = registrationForm.build();
		users.register(user);
		session.setAttribute(USER_ID, user);
		mav.setViewName("redirect:/app/home");
		return mav;
	}

	@RequestMapping(value = "login", method = RequestMethod.GET)
	public ModelAndView showLogin() {
		ModelAndView mav = new ModelAndView();
		mav.addObject("loginForm", new LoginForm());
		mav.setViewName("login");
		return mav;
	}

	@RequestMapping(value = "login", method = RequestMethod.POST)
	public ModelAndView login(LoginForm loginForm, Errors errors,
			HttpSession session) {
		ModelAndView mav = new ModelAndView();
		// LoginForm form = LoginForm.extractFrom(allRequestParams);

		// EmailAddress emailAddress = EmailAddress.buildFrom(email);

		// List<Boolean> errors = new LinkedList<Boolean>();
		// if (!isLoginValid(email, password, errors)) {
		// for (int i = 0; i < errors.size(); i++) {
		// int fieldEnum = LoginField.values()[i].value;
		// mav.addObject(BASE_ERROR_ID + fieldEnum, errors.get(i));
		// }
		// mav.addObject(EMAIL_ID, email);
		// mav.addObject(PASS_ID, password);

		// return "redirect:login";
		// }

		// if (!form.isValid()) {
		// mav.addAllObjects(form.getErrors());
		// mav.setViewName("redirect:/app/login");
		// return mav;
		// }

		loginFormValidator.validate(loginForm, errors);
		if (errors.hasErrors()) {
			mav.setViewName("login");
			return mav;
		}

		// User user = userService.authenticate(emailAddress, password);
		User user = users.authenticate(loginForm.getEmail(),
				loginForm.getPassword());
		if (user == null) {
			mav.addObject(AUTH_FAILED_ID, true);
			mav.setViewName("login");
			return mav;
		}
		session.setAttribute(USER_ID, user);

		// TODO
		// String[] splitReferer = request.getHeader(REFERER_ID).split("/");
		// String refererEnd = splitReferer[splitReferer.length - 1];
		// if (refererEnd.equals("login")) {
		// String redirectUrl = ((HttpServletResponse) response)
		// .encodeRedirectURL("home");
		// ((HttpServletResponse) response).sendRedirect(redirectUrl);
		// } else {
		// return response.sendRedirect(request.getHeader(REFERER_ID));
		// }
		// return "redirect:home";

		mav.setViewName("redirect:/app/home");
		return mav;
	}

	@RequestMapping(value = "logout", method = RequestMethod.POST)
	public String logout(HttpSession session) {
		session.setAttribute(USER_ID, null);
		return "redirect:/app/home";
	}

	@RequestMapping(value = "comments", method = RequestMethod.GET)
	public ModelAndView userComments(@ModelAttribute User user) {
		return new ModelAndView("user/comments");
	}
}