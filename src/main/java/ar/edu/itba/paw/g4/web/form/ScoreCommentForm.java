package ar.edu.itba.paw.g4.web.form;

public class ScoreCommentForm {
	private int score;
	private int userId;
	private int commentId;

	public ScoreCommentForm() {
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getCommentId() {
		return commentId;
	}

	public void setCommentId(int commentId) {
		this.commentId = commentId;
	}
}
