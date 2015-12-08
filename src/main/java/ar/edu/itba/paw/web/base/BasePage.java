package ar.edu.itba.paw.web.base;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import ar.edu.itba.paw.domain.EntityModel;
import ar.edu.itba.paw.model.user.User;
import ar.edu.itba.paw.model.user.UserRepo;
import ar.edu.itba.paw.web.MovieWorldSession;

public class BasePage extends WebPage {
	
	@SpringBean private UserRepo users;

	public BasePage() {
		add(new Label("username", new PropertyModel<String>(MovieWorldSession.get(), "email")));
	}
}
