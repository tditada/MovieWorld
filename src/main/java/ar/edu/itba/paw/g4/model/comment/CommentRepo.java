package ar.edu.itba.paw.g4.model.comment;

public interface CommentRepo {
	public Comment findById(int id);

	public void save(Comment comment);

	public void remove(Comment comment);
}
