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
import ar.edu.itba.paw.g4.web.form.LoginForm;
import ar.edu.itba.paw.g4.web.form.RegisterForm;
import ar.edu.itba.paw.g4.web.form.validation.LoginFormValidator;
import ar.edu.itba.paw.g4.web.form.validation.RegisterFormValidator;

@Controller
@RequestMapping("/users")
public class UsersController {
	private static final String AUTH_FAILED_ID = "authFailed";
	private static final String CURR_USER_ID = "user";
	// private static final String USER_PARAM_ID = "user_id";
	private static final String COMMENTS_USER_ID = "commentsUser";

	private UserRepo users;
	private RegisterFormValidator registerFormValidator;
	private LoginFormValidator loginFormValidator;

	@Autowired
	public UsersController(UserRepo users,
			RegisterFormValidator registerFormValidator,
			LoginFormValidator loginFormValidator) {
		this.users = users;
		this.registerFormValidator = registerFormValidator;
		this.loginFormValidator = loginFormValidator;
	}

	@RequestMapping(value = "register", method = RequestMethod.GET)
	public ModelAndView showRegister(
			@RequestParam(value = CURR_USER_ID, required = false) User user) {
		ModelAndView mav = new ModelAndView();
		if (user != null) {
			mav.setViewName("redirect:/app/home");
		} else {
			mav.addObject("registerForm", new RegisterForm());
			mav.setViewName("register");
		}
		return mav;
	}

	@RequestMapping(value = "register", method = RequestMethod.POST)
	public ModelAndView register(RegisterForm registerForm, Errors errors,
			HttpSession session) {
		ModelAndView mav = new ModelAndView();
		registerFormValidator.validate(registerForm, errors);
		if (errors.hasErrors()) {
			mav.setViewName("register");
			return mav;
		}

		User user = registerForm.build();
		users.register(user);
		session.setAttribute(CURR_USER_ID, user);
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
		session.setAttribute(CURR_USER_ID, user);

		mav.addObject(CURR_USER_ID, user);
		mav.setViewName("redirect:/app/home");
		return mav;
	}

	@RequestMapping(value = "/user/logout", method = RequestMethod.POST)
	public String logout(HttpSession session) {
		session.setAttribute(CURR_USER_ID, null);
		return "redirect:/app/home";
	}

	@RequestMapping(value = "/user/comments", method = RequestMethod.GET)
	public ModelAndView userComments(@RequestParam(required = false) User user,
			HttpSession session) {
		ModelAndView mav = new ModelAndView();
		User currentUser = getCurrentUserFromSession(session);
		if (currentUser == null) {
			mav.setViewName("redirect:/app/home");
			return mav;
		}
		mav.addObject(COMMENTS_USER_ID, user);
		mav.addObject(CURR_USER_ID, currentUser);
		mav.setViewName("user/comments");
		return mav;
	}

	@RequestMapping(value = "list", method = RequestMethod.GET)
	public ModelAndView showUsers(HttpSession session) {
		ModelAndView mav = new ModelAndView();
		// if(isNotLogged(session,mav)){
		// return mav;
		// }
		mav.addObject("users", users.findAll());
		mav.addObject(CURR_USER_ID, getCurrentUserFromSession(session));
		mav.setViewName("user/all");
		return mav;
	}

	private User getCurrentUserFromSession(HttpSession session) {
		return (User) session.getAttribute(CURR_USER_ID);
	}
}
