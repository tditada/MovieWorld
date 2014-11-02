package ar.edu.itba.paw.g4.web.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import ar.edu.itba.paw.g4.model.user.User;
import ar.edu.itba.paw.g4.model.user.UserRepo;
import ar.edu.itba.paw.g4.web.form.InterestingUserForm;
import ar.edu.itba.paw.g4.web.form.LoginForm;
import ar.edu.itba.paw.g4.web.form.RegistrationForm;
import ar.edu.itba.paw.g4.web.form.validation.LoginFormValidator;
import ar.edu.itba.paw.g4.web.form.validation.RegistrationFormValidator;

//Change to /users
@Controller
@RequestMapping("/users")
public class UserController {
	private static final String AUTH_FAILED_ID = "authFailed";
	private static final String USER_ID = "user";
	private static final String USER_PARAM_ID = "user_id";
	private static final int NO_USER = -1;
	private static final String COMMENTS_USER_ID = "commentsUser";

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
		session.setAttribute(USER_PARAM_ID, user.getId());
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
		session.setAttribute(USER_PARAM_ID, user.getId());
		mav.addObject(USER_ID, user);

		mav.setViewName("redirect:/app/home");
		return mav;
	}

	// TODO: ¿no debería invalidar la sesión esto? ¿Alcanza?
	@RequestMapping(value = "/user/logout", method = RequestMethod.POST)
	public String logout(HttpSession session) {
		session.setAttribute(USER_PARAM_ID, NO_USER);
		return "redirect:/app/home";
	}

	@RequestMapping(value = "/profile", method = RequestMethod.GET)
	public ModelAndView userComments(
			@RequestParam(value = "id", required = false) User user,
			HttpSession session) {
		ModelAndView mav = new ModelAndView();
		InterestingUserForm form = new InterestingUserForm();
		if(isNotLogged(session,mav)){
			return mav;
		}
		mav.addObject("addInterestingForm",form);
		mav.addObject(COMMENTS_USER_ID, user);
		setUserInMav(session, mav);
		mav.setViewName("user/profile");
		return mav;
	}

	@RequestMapping(value = "list", method = RequestMethod.GET)
	public ModelAndView showUsers(HttpSession session) {
		ModelAndView mav = new ModelAndView();
//		if(isNotLogged(session,mav)){
//			return mav;
//		}
		mav.addObject("users", users.findAll());
		setUserInMav(session, mav);
		mav.setViewName("user/all");
		return mav;
	}

	// TODO: este es un método comun a todos los Controllers.. da moverlo a
	// algun lado?
	private void setUserInMav(HttpSession session, ModelAndView mav) {
		if (session.getAttribute(USER_PARAM_ID) != null) {
			User user = users.findById((int) session
					.getAttribute(USER_PARAM_ID));
			mav.addObject(USER_ID, user);
		}
	}
	
	private boolean isNotLogged(HttpSession session, ModelAndView mav){
		if(session.getAttribute(USER_PARAM_ID)==null){
			mav.setViewName("redirect:/app/home");
			return true;
		}
		return false;
	}

}
