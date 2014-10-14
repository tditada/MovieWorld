package ar.edu.itba.paw.g4.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import ar.edu.itba.paw.g4.model.Movie;
import ar.edu.itba.paw.g4.service.MovieService;

@Controller
@RequestMapping("/home")
public class HomeController {
	public static final String TOP_MOVIES_ID = "topMovies";
	public static final String RELEASES_ID = "releases";
	public static final String NEW_ADDITIONS_ID = "newAdditions";

	private static final int TOP_MOVIES_QUANTITY = 5;
	private static final int NEW_ADDITIONS_QUANTITY = 5;

	private MovieService movieService;

	@Autowired
	HomeController(MovieService movieService) {
		this.movieService = movieService;
	}

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView show() {
		List<Movie> topMovies = movieService.getTopMovies(TOP_MOVIES_QUANTITY);
		List<Movie> newAdditions = movieService
				.getNewAdditions(NEW_ADDITIONS_QUANTITY);
		List<Movie> releases = movieService.getReleases();

		ModelAndView mav = new ModelAndView();
		mav.addObject(TOP_MOVIES_ID, topMovies);
		mav.addObject(NEW_ADDITIONS_ID, newAdditions);
		mav.addObject(RELEASES_ID, releases);
		mav.setViewName("home");
		return mav;
	}
}
