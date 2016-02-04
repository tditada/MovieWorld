package ar.edu.itba.paw.web.base;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import ar.edu.itba.paw.model.movie.Movie;
import ar.edu.itba.paw.model.user.UserRepo;
import ar.edu.itba.paw.web.MovieWorldSession;
import ar.edu.itba.paw.web.homepage.HomePage;
import ar.edu.itba.paw.web.movie.MoviePage;

@SuppressWarnings("serial")
public class BasePage extends WebPage {
	
	public BasePage() {

		add(new NavBar("navbar"));
	}
	
//    @Override
//    public void renderHead(IHeaderResponse response) {
//        super.renderHead(response);
//        response.render(JavaScriptHeaderItem.forReference(getApplication()
//                .getJavaScriptLibrarySettings().getJQueryReference()));
//        response.render(JavaScriptHeaderItem.forReference(
//                new JavaScriptResourceReference(MovieWorldResources.class,
//                        "bootstrap-select/dist/js/bootstrap-select.min.js")));
//        response.render(CssHeaderItem.forReference(
//                new CssResourceReference(MovieWorldResources.class,
//                        "bootstrap-select/dist/css/bootstrap-select.min.css")));
//    }
}
