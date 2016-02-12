package ar.edu.itba.paw.web.comment;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.NumberTextField;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import ar.edu.itba.paw.model.Score;
import ar.edu.itba.paw.model.comment.Comment;
import ar.edu.itba.paw.model.comment.CommentRepo;
import ar.edu.itba.paw.model.movie.Movie;
import ar.edu.itba.paw.model.movie.MovieRepo;
import ar.edu.itba.paw.model.user.User;
import ar.edu.itba.paw.model.user.UserRepo;
import ar.edu.itba.paw.web.MovieWorldSession;
import ar.edu.itba.paw.web.movie.MoviePage;

@SuppressWarnings("serial")
public class MovieCommentFormPanel extends Panel {

	@SpringBean
	UserRepo userRepo;

	@SpringBean
	CommentRepo commentRepo;
	
	@SpringBean
	MovieRepo movieRepo;

	private Integer commentScore; // Score
	private String commentText;

	public MovieCommentFormPanel(String id, final IModel<Movie> movieModel) {
		super(id);
		final User user = MovieWorldSession.get().getCurrentUser(userRepo);
		final Movie movie = movieModel.getObject();

		Label writeCommentLabel = new Label("writeComment", getString("writeComment"));
		Form<MovieCommentFormPanel> form = new Form<MovieCommentFormPanel>("commentForm",
				new CompoundPropertyModel<MovieCommentFormPanel>(this)) {
			@Override
			protected void onSubmit() {
				super.onSubmit();
				Score s = new Score(commentScore);
				Comment comment = Comment.builder().withMovie(movie).withUser(user).withText(commentText).withScore(s)
						.build();
				user.addComment(comment);
				commentRepo.save(comment);
				movieRepo.save(movie);
				setResponsePage(new MoviePage(movieModel));
			}
		};
		NumberTextField<Integer> commentScore = new NumberTextField<Integer>("commentScore");
		commentScore.setRequired(true);
		commentScore.setMaximum(5);
		commentScore.setMinimum(0);
		TextArea<String> commentText = new TextArea<String>("commentText");
		Label cannotComment = new Label("cannotComment", getString("cannotComment.text"));
		form.add(commentScore);
		form.add(commentText);
		add(writeCommentLabel);
		add(form);
		add(cannotComment);

		if (user != null) {
			boolean ableToComment = movie.isCommentableBy(user);
			writeCommentLabel.setVisible(true);
			if (ableToComment) {
				cannotComment.setVisible(false);
				form.setVisible(true);
			} else if (!ableToComment) {
				cannotComment.setVisible(true);
				form.setVisible(false);
			}
		} else {
			writeCommentLabel.setVisible(false);
			cannotComment.setVisible(false);
			form.setVisible(false);
			this.setVisible(false);
		}
	}

}
