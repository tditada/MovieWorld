package ar.edu.itba.paw.web.common;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import ar.edu.itba.paw.model.user.User;
import ar.edu.itba.paw.model.user.UserRepo;
import ar.edu.itba.paw.web.MovieWorldSession;
import ar.edu.itba.paw.web.comment.ReportedCommentsPage;
import ar.edu.itba.paw.web.movie.AddMoviePage;
import ar.edu.itba.paw.web.user.LoginPage;
import ar.edu.itba.paw.web.user.UserCommentsPage;

@SuppressWarnings("serial")
public class UserNavBar extends Panel {

	@SpringBean
	UserRepo userRepo;

	public UserNavBar(String id) {
		super(id);
		User user = MovieWorldSession.get().getCurrentUser(userRepo);

		// User links
		Link<Void> loginLink = new Link<Void>("loginRegisterLink") {
			@Override
			public void onClick() {
				setResponsePage(new LoginPage());
			}
		};
		add(loginLink);

		Label logInAs = new Label("logInAs", new StringResourceModel("logInAs", this, new Model<User>(user)));
		add(logInAs);

		Link<Void> commentsLink = new Link<Void>("myCommentsLink") {
			@Override
			public void onClick() {
				setResponsePage(new UserCommentsPage(MovieWorldSession.get().getCurrentUser(userRepo)));
			}
		};
		add(commentsLink);

		Link<Void> logoutLink = new Link<Void>("logoutLink") {
			@Override
			public void onClick() {
				MovieWorldSession.get().signOut();
				setResponsePage(getApplication().getHomePage());
			}
		};
		add(logoutLink);
		
		if (!MovieWorldSession.get().isSignedIn()) {
			commentsLink.setVisible(false);
			logoutLink.setVisible(false);
			logInAs.setVisible(false);
		} else {
			loginLink.setVisible(false);
		}

		
		// Admin Links
		Link<Void> insertMovieLink = new Link<Void>("InsertMovieLink") {

			@Override
			public void onClick() {
				setResponsePage(new AddMoviePage());
			}

		};
		add(insertMovieLink);

		Link<Void> reportedLink = new Link<Void>("ReportedCommentsLink") {

			@Override
			public void onClick() {
				setResponsePage(new ReportedCommentsPage());
			}
		};
		add(reportedLink);

		if (user == null || (user != null && !user.isAdmin())) {
			insertMovieLink.setVisible(false);
			reportedLink.setVisible(false);
		}

	}

}
