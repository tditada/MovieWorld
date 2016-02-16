package ar.edu.itba.paw.web.base;

import org.apache.wicket.spring.injection.annot.SpringBean;

import ar.edu.itba.paw.model.user.UserRepo;
import ar.edu.itba.paw.web.MovieWorldSession;
import ar.edu.itba.paw.web.homepage.HomePage;

@SuppressWarnings("serial")
public class UsersPage extends BasePage{

	@SpringBean UserRepo userRepo;

	public UsersPage() {
		MovieWorldSession session = MovieWorldSession.get();
		if (!session.isSignedIn()) {
			setResponsePage(HomePage.class);
		}
	}
}
