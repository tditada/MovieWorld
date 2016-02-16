package ar.edu.itba.paw.web.common;

import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import ar.edu.itba.paw.model.user.UserRepo;
import ar.edu.itba.paw.web.MovieWorldSession;
import ar.edu.itba.paw.web.homepage.HomePage;
import ar.edu.itba.paw.web.movie.MovieListPage;
import ar.edu.itba.paw.web.user.UsersListPage;

@SuppressWarnings("serial")
public class NavBar extends Panel {

	@SpringBean
	private UserRepo users;

	public NavBar(String id) {
		super(id);
		add(new BookmarkablePageLink<Void>("homepageLink", HomePage.class));
		add(new Link<Void>("allMoviesLink") {
			@Override
			public void onClick() {
				setResponsePage(new MovieListPage());
			}
		});

		Link<Void> usersLink = new Link<Void>("otherUsersLink") {
			@Override
			public void onClick() {
				setResponsePage(UsersListPage.class);
			}
		};
		add(usersLink);
		addNavBars();
		
		// Visibility
		if(MovieWorldSession.get().getCurrentUser(users)==null){
			usersLink.setVisible(false);
		}
		
	}
	
	protected void addNavBars(){
		add(new UserNavBar("userNavBar"));
	}
}
