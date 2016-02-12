package ar.edu.itba.paw.web.base;

import org.apache.wicket.spring.injection.annot.SpringBean;

import ar.edu.itba.paw.model.user.UserRepo;
import ar.edu.itba.paw.web.MovieWorldSession;
import ar.edu.itba.paw.web.homepage.HomePage;

@SuppressWarnings("serial")
public abstract class SecuredPage extends BasePage {
	
	@SpringBean UserRepo userRepo;

	public SecuredPage() {
		MovieWorldSession session = MovieWorldSession.get();
		if (!session.getCurrentUser(userRepo).isAdmin()) {
			setResponsePage(HomePage.class);
			
		}
	}
	
}