package ar.edu.itba.paw.web.comment;

import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import ar.edu.itba.paw.model.comment.Comment;
import ar.edu.itba.paw.model.comment.CommentRepo;
import ar.edu.itba.paw.model.user.User;
import ar.edu.itba.paw.model.user.UserRepo;
import ar.edu.itba.paw.web.MovieWorldSession;
import ar.edu.itba.paw.web.homepage.HomePage;

@SuppressWarnings("serial")
public class DropReportedCommentsPanel extends Panel {

	@SpringBean UserRepo userRepo;
	@SpringBean CommentRepo commentRepo;
	
	public DropReportedCommentsPanel(String id, final IModel<Comment> comment) {
		super(id);
		add(new Form<DropReportedCommentsPanel>("dropReports",
				new CompoundPropertyModel<DropReportedCommentsPanel>(this)) {
			@Override
			protected void onSubmit() {
				User currentUser = MovieWorldSession.get().getCurrentUser(userRepo);
				if (comment.getObject() == null || currentUser == null || !currentUser.isAdmin()) {
					setResponsePage(HomePage.class);
				}
				comment.getObject().dropReports(currentUser);
				commentRepo.save(comment.getObject());
				super.onSubmit();
				setResponsePage(new ReportedCommentsPage());
			}
		});
	}

}
