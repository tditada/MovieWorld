package ar.edu.itba.paw.web.converter;

import java.util.Locale;

import org.apache.wicket.util.convert.IConverter;
import org.springframework.beans.factory.annotation.Autowired;

import ar.edu.itba.paw.model.comment.Comment;
import ar.edu.itba.paw.model.comment.CommentRepo;

@SuppressWarnings("serial")
public class CommentConverter implements IConverter<Comment> {
	private CommentRepo comments;
	
	@Autowired
	public CommentConverter(CommentRepo comments) {
		this.comments=comments;
	}

	@Override
	public Comment convertToObject(String arg0, Locale arg1) {
		Comment comment = comments.findById(Integer.valueOf(arg0));
		if (comment == null) {
			throw new IllegalArgumentException("There is no comment with id="
					+ arg0);
		}
		return comment;
	}

	@Override
	public String convertToString(Comment arg0, Locale arg1) {
		return String.valueOf(arg0.getId());
	}
}
