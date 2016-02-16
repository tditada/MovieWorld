package ar.edu.itba.paw.web.base;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import ar.edu.itba.paw.model.movie.Movie;
import ar.edu.itba.paw.model.user.UserRepo;
import ar.edu.itba.paw.web.MovieWorldSession;
import ar.edu.itba.paw.web.common.NavBar;
import ar.edu.itba.paw.web.homepage.HomePage;
import ar.edu.itba.paw.web.movie.MoviePage;

@SuppressWarnings("serial")
public class BasePage extends WebPage {
	
	public BasePage() {
		add(new NavBar("navbar"));
	}
	
}
