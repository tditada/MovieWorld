package ar.edu.itba.paw.web.comment;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import ar.edu.itba.paw.model.comment.Comment;
import ar.edu.itba.paw.model.comment.CommentRepo;
import ar.edu.itba.paw.model.user.User;
import ar.edu.itba.paw.model.user.UserRepo;
import ar.edu.itba.paw.web.MovieWorldSession;
import ar.edu.itba.paw.web.homepage.HomePage;

@SuppressWarnings("serial")
public class ReportCommentPanel extends Panel{
	
	@SpringBean UserRepo userRepo;
	@SpringBean CommentRepo commentRepo;

	public ReportCommentPanel(String id, final Comment c) {
		super(id);
		final User currentUser = MovieWorldSession.get().getCurrentUser(userRepo);
		Form<ReportCommentPanel> reportForm = new Form<ReportCommentPanel>("reportForm",new CompoundPropertyModel<ReportCommentPanel>(this)){
			@Override
			protected void onSubmit() {
				if (currentUser == null || c == null || !c.isReportableBy(currentUser)) {
					setResponsePage(HomePage.class);
				}
				c.addReport(currentUser);
				commentRepo.save(c);
				super.onSubmit();
				
			}
		};
		Label reportedLabel = new Label("reported", getString("reported"));
		add(reportForm);
		add(reportedLabel);
		
		if(currentUser== null || (currentUser!= null && currentUser.isAdmin())){
			this.setVisible(false);
		}else{
			if (!c.isReportableBy(currentUser)) {
				reportForm.setVisible(false);
			} else {
				reportedLabel.setVisible(false);
			}
		}
		
	}
	
	
}
