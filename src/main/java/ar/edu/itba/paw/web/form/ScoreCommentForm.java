package ar.edu.itba.paw.web.form;

import ar.edu.itba.paw.model.Score;
import ar.edu.itba.paw.model.comment.Comment;

public class ScoreCommentForm {
	private Score score;
	private Comment comment;

	public ScoreCommentForm() {
	}

	public Score getScore() {
		return score;
	}

	public void setScore(Score score) {
		this.score = score;
	}

	public Comment getComment() {
		return comment;
	}

	public void setComment(Comment comment) {
		this.comment = comment;
	}
}
