package ar.edu.itba.paw.g4.web.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import ar.edu.itba.paw.g4.model.movie.Movie;
import ar.edu.itba.paw.g4.model.movie.MovieRepo;
import ar.edu.itba.paw.g4.model.user.User;
import ar.edu.itba.paw.g4.model.user.UserRepo;

@Controller
@RequestMapping("/home")
public class HomeController {
	public static final String TOP_MOVIES_ID = "topMovies";
	public static final String RELEASES_ID = "releases";
	public static final String NEW_ADDITIONS_ID = "newAdditions";

	private static final int TOP_MOVIES_QUANTITY = 5;
	private static final int NEW_ADDITIONS_QUANTITY = 5;
	
	private static final String USER_PARAM_ID ="user_id";
	private static final String USER_ID="user";
	private static final String INTERESTING_COMMENTS_ID = "interestingComments";

	private MovieRepo movies;
	private UserRepo users;

	@Autowired
	HomeController(MovieRepo movies, UserRepo users) {
		this.movies = movies;
		this.users=users;
	}

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView show(HttpSession session) {
		List<Movie> topMovies = movies.findTopMovies(TOP_MOVIES_QUANTITY);
		List<Movie> newAdditions = movies
				.findNewAdditions(NEW_ADDITIONS_QUANTITY);
		List<Movie> releases = movies.findReleases();
		ModelAndView mav = new ModelAndView();
		if(session.getAttribute(USER_PARAM_ID)!=null){
			User user = users.findById((int)session.getAttribute(USER_PARAM_ID));
			mav.addObject(USER_ID, user);
			mav.addObject(INTERESTING_COMMENTS_ID, user.getInterestingComments());
		}
		mav.addObject(TOP_MOVIES_ID, topMovies);
		mav.addObject(NEW_ADDITIONS_ID, newAdditions);
		mav.addObject(RELEASES_ID, releases);
		mav.setViewName("home");
		return mav;
	}
}
