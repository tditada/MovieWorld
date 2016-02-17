package ar.edu.itba.paw.web.comment;

import java.text.MessageFormat;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.NumberTextField;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.validation.IValidatable;
import org.apache.wicket.validation.IValidationError;
import org.apache.wicket.validation.IValidator;
import org.apache.wicket.validation.ValidationError;

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

	private Integer commentScore; 
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
				movieModel.setObject(movie);
				setResponsePage(new MoviePage(movieModel));
			}
		};
		form.add(new FeedbackPanel("feedback") {
			@Override
			public boolean isVisible() {
				return super.isVisible() && anyMessage();
			}
		});
		NumberTextField<Integer> commentScore = new NumberTextField<Integer>("commentScore");
		commentScore.setRequired(true);
		commentScore.setMaximum(5);
		commentScore.setMinimum(1);
		TextArea<String> commentText = new TextArea<String>("commentText"){
			@Override
			protected void onInitialize() {
				super.onInitialize();
				add(new IValidator<String>() {
					@Override
					public void validate(IValidatable<String> validatable) {
						if (validatable.getValue().length() > Comment.MAX_TEXT_LENGTH) {
							validatable.error(new ValidationError(this));
						}
					}
				});
			}
			@Override
			public void error(IValidationError error) {
				error(MessageFormat.format(getString("commentTextInvalid"), Comment.MAX_TEXT_LENGTH));
			}
		};
		commentText.setRequired(true);
		Label cannotComment = new Label("cannotComment", getString("cannotComment"));
		form.add(commentScore);
		form.add(commentText);
		add(writeCommentLabel);
		add(form);
		add(cannotComment);

		if (user != null) {
			boolean ableToComment = movie.isCommentableBy(user);
			if (ableToComment) {
				cannotComment.setVisible(false);
			} else if (!ableToComment) {
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
