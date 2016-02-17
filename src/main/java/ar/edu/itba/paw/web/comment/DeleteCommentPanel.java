package ar.edu.itba.paw.web.comment;

import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import ar.edu.itba.paw.domain.EntityModel;
import ar.edu.itba.paw.model.comment.Comment;
import ar.edu.itba.paw.model.comment.CommentRepo;
import ar.edu.itba.paw.model.movie.Movie;
import ar.edu.itba.paw.model.movie.MovieRepo;
import ar.edu.itba.paw.model.user.User;
import ar.edu.itba.paw.model.user.UserRepo;
import ar.edu.itba.paw.web.MovieWorldSession;
import ar.edu.itba.paw.web.movie.MoviePage;

@SuppressWarnings("serial")
public class DeleteCommentPanel extends Panel {

	@SpringBean
	CommentRepo commentRepo;
	@SpringBean
	UserRepo userRepo;
	@SpringBean
	MovieRepo movieRepo;

	public DeleteCommentPanel(String id, final Comment comment, final boolean isAdminPage) {
		super(id);
		final User user = MovieWorldSession.get().getCurrentUser(userRepo);
		add(new Form<DeleteCommentPanel>("deleteComment", new CompoundPropertyModel<DeleteCommentPanel>(this)) {
			@Override
			protected void onSubmit() {
				if (comment != null && user != null && user.isAdmin()) {
//					comment.getUser().removeComment(comment);
//					userRepo.save(comment.getUser());
//					movieRepo.save(comment.getMovie());
					commentRepo.remove(user, comment);
				}
				super.onSubmit();
				if (isAdminPage) {
					setResponsePage(new ReportedCommentsPage());
				} else {
					IModel<Movie> movieModel = new EntityModel<Movie>(Movie.class,comment.getMovie());
					setResponsePage(new MoviePage(movieModel));
				}
			}
		});
	}

}
