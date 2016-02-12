package ar.edu.itba.paw.web.comment;

import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.NumberTextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import ar.edu.itba.paw.domain.EntityModel;
import ar.edu.itba.paw.model.Score;
import ar.edu.itba.paw.model.comment.Comment;
import ar.edu.itba.paw.model.comment.CommentRepo;
import ar.edu.itba.paw.model.movie.Movie;
import ar.edu.itba.paw.model.user.User;
import ar.edu.itba.paw.model.user.UserRepo;
import ar.edu.itba.paw.web.MovieWorldSession;
import ar.edu.itba.paw.web.homepage.HomePage;
import ar.edu.itba.paw.web.movie.MoviePage;

@SuppressWarnings("serial")
public class ScoreCommentForm extends Panel {

	private Integer score;
	@SpringBean
	UserRepo userRepo;
	@SpringBean
	CommentRepo commentRepo;

	public ScoreCommentForm(String id, final Comment c) {
		super(id);

		final User currentUser = MovieWorldSession.get().getCurrentUser(userRepo);
		Form<ScoreCommentForm> form = new Form<ScoreCommentForm>("scoreCommentForm",
				new CompoundPropertyModel<ScoreCommentForm>(this)){
			@Override
			protected void onSubmit() {
				Movie movie = c.getMovie();

				if (currentUser == null || movie == null) {
					setResponsePage(HomePage.class);
				}
				
				c.addScore(currentUser, new Score(score));
				commentRepo.save(c);
				
				IModel<Movie> movieModel = new EntityModel<Movie>(Movie.class,movie);
				setResponsePage(new MoviePage(movieModel));
			}
		};
		NumberTextField<Integer> scoreField = new NumberTextField<Integer>("score");
		scoreField.setRequired(true);
		scoreField.setMaximum(5);
		scoreField.setMinimum(0);
		form.add(scoreField);
		add(form);
		
		add(new ReportCommentPanel("reportCommentPanel",c));

		if (currentUser == null) {
			this.setVisible(false);
		}else if(currentUser != null && c.getUser().equals(currentUser)){
			this.setVisible(false);
		}else if(currentUser != null && !c.canBeScoredBy(currentUser)){
			this.setVisible(false);
		}
	}

}
