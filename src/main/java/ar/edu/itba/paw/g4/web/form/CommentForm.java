package ar.edu.itba.paw.g4.web.form;

public class CommentForm {
	private int movieScore;
	private String commentText;

	public CommentForm() {
	}

	public int getMovieScore() {
		return movieScore;
	}

	public void setMovieScore(int movieScore) {
		this.movieScore = movieScore;
	}

	public String getCommentText() {
		return commentText;
	}

	public void setCommentText(String commentText) {
		this.commentText = commentText;
	}
}
