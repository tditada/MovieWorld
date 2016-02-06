package ar.edu.itba.paw.web.movie;

import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.NumberTextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import ar.edu.itba.paw.model.comment.Comment;
import ar.edu.itba.paw.model.user.User;
import ar.edu.itba.paw.model.user.UserRepo;
import ar.edu.itba.paw.web.MovieWorldSession;

@SuppressWarnings("serial")
public class ScoreCommentForm extends Panel {

	private Integer score;
	@SpringBean
	UserRepo userRepo;

	public ScoreCommentForm(String id, Comment c) {
		super(id);

		User currentUser = MovieWorldSession.get().getCurrentUser(userRepo);
		Form<ScoreCommentForm> form = new Form<ScoreCommentForm>("scoreCommentForm",
				new CompoundPropertyModel<ScoreCommentForm>(this));
		NumberTextField<Integer> scoreField = new NumberTextField<Integer>("score");
		scoreField.setRequired(true);
		scoreField.setMaximum(5);
		scoreField.setMinimum(0);
		form.add(scoreField);
		add(form);

		if (currentUser == null) {
			this.setVisible(false);
		}else if(currentUser != null && c.getUser().equals(currentUser)){
			this.setVisible(false);
		}else if(currentUser != null && !c.canBeScoredBy(currentUser)){
			this.setVisible(false);
		}
	}

}
