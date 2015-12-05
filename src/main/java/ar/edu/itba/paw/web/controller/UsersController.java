package ar.edu.itba.paw.web.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import ar.edu.itba.paw.model.user.User;
import ar.edu.itba.paw.model.user.UserRepo;
import ar.edu.itba.paw.web.form.HiddenInterestingUserForm;
import ar.edu.itba.paw.web.form.LoginForm;
import ar.edu.itba.paw.web.form.RegisterForm;
import ar.edu.itba.paw.web.form.validation.LoginFormValidator;
import ar.edu.itba.paw.web.form.validation.RegisterFormValidator;

@Controller
@RequestMapping("/users")
public class UsersController {
	private static final String AUTH_FAILED_ID = "authFailed";
	private static final String COMMENTS_USER_ID = "commentsUser";
	private static final String USER_ID = "user";

	private static final String SESSION_USER_ID = "user";

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
	public ModelAndView showRegister(HttpSession session) {
		ModelAndView mav = new ModelAndView();
		User user = getLoggedUserFromSession(session);

		if (user != null) {
			// user is already registered
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

		if (userLoggedIn(session)) {
			// user is already registered
			mav.setViewName("redirect:/app/home");
			return mav;
		}

		registerFormValidator.validate(registerForm, errors);
		if (errors.hasErrors()) {
			mav.setViewName("register");
			return mav;
		}

		User user = registerForm.build();
		users.register(user);

		logUserInSession(session, user);

		mav.setViewName("redirect:/app/home");
		return mav;
	}

	@RequestMapping(value = "login", method = RequestMethod.GET)
	public ModelAndView showLogin(HttpSession session) {
		ModelAndView mav = new ModelAndView();

		if (userLoggedIn(session)) {
			// user is already logged in
			mav.setViewName("redirect:/app/home");
			return mav;
		}

		mav.addObject("loginForm", new LoginForm());
		mav.setViewName("login");
		return mav;
	}

	@RequestMapping(value = "login", method = RequestMethod.POST)
	public ModelAndView login(LoginForm loginForm, Errors errors,
			HttpSession session) {
		ModelAndView mav = new ModelAndView();

		if (userLoggedIn(session)) {
			// user is already logged in
			mav.setViewName("redirect:/app/home");
			return mav;
		}

		loginFormValidator.validate(loginForm, errors);
		if (errors.hasErrors()) {
			mav.setViewName("login");
			return mav;
		}

		User user = users.authenticate(loginForm.getEmail(),
				loginForm.getPassword());
		if (user == null) {
			mav.addObject(AUTH_FAILED_ID, true);
			mav.setViewName("login");
			return mav;
		}
		logUserInSession(session, user);

		mav.addObject(USER_ID, user);
		mav.setViewName("redirect:/app/home");
		return mav;
	}

	@RequestMapping(value = "/user/logout", method = RequestMethod.POST)
	public String logout(HttpSession session) {
		if (userLoggedIn(session)) {
			// user should be logged in to be logged out
			session.invalidate();
		}
		// in any case, just go back to the home screen
		return "redirect:/app/home";
	}

	@RequestMapping(value = "/profile", method = RequestMethod.GET)
	public ModelAndView userComments(@RequestParam(required = false) User user,
			HttpSession session) {
		ModelAndView mav = new ModelAndView();

		User currentUser = getLoggedUserFromSession(session);
		if (user == null || currentUser == null) {
			// users have to be logged in to see others' comments
			mav.setViewName("redirect:/app/home");
			return mav;
		}
		mav.addObject("isInterestingUser", currentUser.isinterestingUser(user));
		mav.addObject("addInterestingForm", new HiddenInterestingUserForm());
		mav.addObject(COMMENTS_USER_ID, user);
		mav.addObject(USER_ID, currentUser);
		mav.setViewName("user/comments");
		return mav;
	}

	@RequestMapping(value = "list", method = RequestMethod.GET)
	public ModelAndView showUsers(HttpSession session) {
		ModelAndView mav = new ModelAndView();
		User user = getLoggedUserFromSession(session);
		if (user == null) {
			// users have to be logged in to see others
			mav.setViewName("redirect:/app/home");
			return mav;
		}
		mav.addObject("users", users.findAll());
		mav.addObject(USER_ID, user);
		mav.setViewName("user/all");
		return mav;
	}

	@RequestMapping(value = "changeInterest", method = RequestMethod.POST)
	public ModelAndView changeInterest(HiddenInterestingUserForm form,
			HttpSession session) {
		ModelAndView mav = new ModelAndView();
		User interestedUser = form.getInterestedUser();
		User interestingUser = form.getInterestingUser();

		if (interestedUser.isinterestingUser(interestingUser)) {
			interestedUser.removeInterestingUser(interestingUser);
		} else {
			interestedUser.addInterestingUser(interestingUser);

		}
		mav.setViewName("redirect:/app/users/profile?user="
				+ form.getInterestingUser().getId());
		return mav;
	}

	private User getLoggedUserFromSession(HttpSession session) {
		Integer userId = (Integer) session.getAttribute(SESSION_USER_ID);
		if (userId == null) {
			return null;
		}
		return users.findById(userId);
	}

	private void logUserInSession(HttpSession session, User user) {
		session.setAttribute(SESSION_USER_ID, user.getId());
	}

	private boolean userLoggedIn(HttpSession session) {
		return getLoggedUserFromSession(session) != null;
	}
}
