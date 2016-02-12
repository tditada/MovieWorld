package ar.edu.itba.paw.web.base;

import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import ar.edu.itba.paw.domain.EntityModel;
import ar.edu.itba.paw.model.movie.Movie;
import ar.edu.itba.paw.model.user.User;
import ar.edu.itba.paw.model.user.UserRepo;
import ar.edu.itba.paw.web.MovieWorldSession;
import ar.edu.itba.paw.web.comment.ReportedCommentsPage;
import ar.edu.itba.paw.web.movie.AddEditMoviePage;
import ar.edu.itba.paw.web.user.LoginPage;
import ar.edu.itba.paw.web.user.UserCommentsPage;

@SuppressWarnings("serial")
public class UserNavBar extends Panel {

	@SpringBean
	UserRepo userRepo;

	public UserNavBar(String id) {
		super(id);
		User currentUser = MovieWorldSession.get().getCurrentUser(userRepo);
		String firstName = "";
		String lastName = "";
		if (currentUser != null || MovieWorldSession.get().isSignedIn()) {
			firstName = currentUser.getFirstName().getNameString();
			lastName = currentUser.getLastName().getNameString();
		}

		// No user Logged Links
		add(new Link<Void>("loginRegisterLink") {

			@Override
			public void onClick() {
				setResponsePage(new LoginPage());
			}

			@Override
			public boolean isVisible() {
				return !MovieWorldSession.get().isSignedIn();
			}

		});

//		 Logged User Link
//		Label firstNameLabel = new Label("user.firstName", firstName);
//		Label lastNameLabel = new Label("user.lastName", lastName);
//		if( MovieWorldSession.get().isSignedIn()){
//			firstNameLabel.isVisible();
//			lastNameLabel.isVisible();
//		}

		add(new Link<Void>("myCommentsLink") {

			@Override
			public void onClick() {
				setResponsePage(new UserCommentsPage(MovieWorldSession.get().getCurrentUser(userRepo)));
			}

			@Override
			public boolean isVisible() {
				return MovieWorldSession.get().isSignedIn();
			}

		});
		
        add(new Link<Void>("logoutLink") {
            @Override
            public void onClick() {
                MovieWorldSession.get().signOut();
                setResponsePage(getApplication().getHomePage());
            }

            @Override
            public boolean isVisible() {
                return MovieWorldSession.get().isSignedIn();
            }
        });

		// Admin Links
		add(new Link<Void>("InsertMovieLink") {

			@Override
			public void onClick() {
				setResponsePage(new AddEditMoviePage(null));
			}

			@Override
			public boolean isVisible() {
				User currentUser = MovieWorldSession.get().getCurrentUser(userRepo);
				return currentUser!= null && currentUser.isAdmin();
			}

		});

		add(new Link<Void>("ReportedCommentsLink") {

			@Override
			public void onClick() {
				setResponsePage(new ReportedCommentsPage());
			}

			@Override
			public boolean isVisible() {
				User currentUser = MovieWorldSession.get().getCurrentUser(userRepo);
				return currentUser!= null && currentUser.isAdmin();
			}

		});

	}

}
