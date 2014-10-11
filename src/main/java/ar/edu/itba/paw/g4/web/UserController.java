package ar.edu.itba.paw.g4.web;

import static ar.edu.itba.paw.g4.web.form.RegistrationForm.RegistrationFormFields.FIRST_NAME;
import static ar.edu.itba.paw.g4.web.form.RegistrationForm.RegistrationFormFields.LAST_NAME;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import ar.edu.itba.paw.g4.model.User;
import ar.edu.itba.paw.g4.service.UserService;
import ar.edu.itba.paw.g4.web.form.LoginForm;
import ar.edu.itba.paw.g4.web.form.LoginForm.LoginFormFields;
import ar.edu.itba.paw.g4.web.form.RegistrationForm;
import ar.edu.itba.paw.g4.web.form.RegistrationForm.RegistrationFormFields;

@Controller
public class UserController {
	private static final String USER_ID = "user";

	private static final String FIRST_NAME_ID = "firstname";
	private static final String LAST_NAME_ID = "lastname";
	private static final String BIRTHDAY_ID = "birthday";
	private static final String EMAIL_ID = "email";
	private static final String PASS_ID = "password";

	private UserService userService;

	@Autowired
	public UserController(UserService userService) {
		this.userService = userService;
	}

	@RequestMapping(method = RequestMethod.GET, value = "registration")
	public ModelAndView showRegistration(@RequestParam(USER_ID) User user) {
		ModelAndView mav = new ModelAndView();
		if (user != null) {
			mav.setViewName("redirect:home");
		} else {
			mav.setViewName("/WEB-INF/jsp/registration.jsp");
		}
		return mav;
	}

	@RequestMapping(method = RequestMethod.POST, value = "registration")
	public ModelAndView register(
			@RequestParam Map<String, String> allRequestParams, ModelMap model,
			HttpSession session) {
		ModelAndView mav = new ModelAndView();
		RegistrationForm form = RegistrationForm.extractFrom(allRequestParams);

		if (!form.isValid()) {
			// TODO: check!
			mav.addAllObjects(allRequestParams);
			mav.setViewName("redirect:registration");
			return mav;
		}

		User user = User
				.builder()
				.withFirstName(form.getFieldValue(FIRST_NAME))
				.withLastName(form.getFieldValue(LAST_NAME))
				.withPassword(
						form.getFieldValue(RegistrationFormFields.PASSWORD))
				.withEmail(form.getEmailAddress())
				.withBirthDate(form.getBirthDate()).build();
		// try {
		userService.register(user);
		updateSession(user, session);
		mav.setViewName("redirect:home");
		return mav;
		// TODO } catch (ServiceException e) {
		// manageError(e, request, response);
		// }
	}

	@RequestMapping(method = RequestMethod.GET, value = "login")
	public ModelAndView showLogin() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/WEB-INF/jsp/login.jsp");
		return mav;
	}

	@RequestMapping(method = RequestMethod.POST, value = "login")
	public ModelAndView login(
			@RequestParam Map<String, String> allRequestParams, ModelMap model,
			HttpSession session) {
		ModelAndView mav = new ModelAndView();
		// try {
		LoginForm form = LoginForm.extractFrom(allRequestParams);
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

		if (!form.isValid()) {
			mav.addAllObjects(form.getErrors());
			mav.setViewName("redirect:login");
			return mav;
		}

		// User user = userService.authenticate(emailAddress, password);
		User user = userService.authenticate(form.getEmailAddress(),
				form.getFieldValue(LoginFormFields.PASSWORD));
		updateSession(user, session);

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

		mav.setViewName("redirect:home");
		return mav;
		// TODO } catch (ServiceException e) {
		// manageError(e, request, response);
		// }
	}

	@RequestMapping(method = RequestMethod.POST)
	public String logout(HttpSession session) {
		// TODO: check!
		session.setAttribute(FIRST_NAME_ID, null);
		session.setAttribute(EMAIL_ID, null);
		session.setAttribute(LAST_NAME_ID, null);
		session.setAttribute(PASS_ID, null);
		session.setAttribute(BIRTHDAY_ID, null);

		return "redirect:home";
	}

	private void updateSession(User user, HttpSession session) {
		// TODO: Check!
		session.setAttribute(FIRST_NAME_ID, user.getFirstName());
		session.setAttribute(EMAIL_ID, user.getEmail());
		session.setAttribute(LAST_NAME_ID, user.getLastName());
		session.setAttribute(PASS_ID, user.getPassword());
		session.setAttribute(BIRTHDAY_ID, user.getBirthDate());
	}
}
