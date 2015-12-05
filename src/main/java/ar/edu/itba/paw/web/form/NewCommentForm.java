package ar.edu.itba.paw.web.form;

import ar.edu.itba.paw.model.Score;

public class NewCommentForm {
	private Score movieScore;
	private String commentText;

	public NewCommentForm() {
	}

	public Score getMovieScore() {
		return movieScore;
	}

	public void setMovieScore(Score movieScore) {
		this.movieScore = movieScore;
	}

	public String getCommentText() {
		return commentText;
	}

	public void setCommentText(String commentText) {
		this.commentText = commentText;
	}
}
