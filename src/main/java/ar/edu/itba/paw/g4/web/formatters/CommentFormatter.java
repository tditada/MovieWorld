package ar.edu.itba.paw.g4.web.formatters;

import java.text.ParseException;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import ar.edu.itba.paw.g4.model.comment.Comment;
import ar.edu.itba.paw.g4.model.comment.CommentRepo;

@Component
public class CommentFormatter implements Formatter<Comment> {
	private CommentRepo comments;

	@Autowired
	public CommentFormatter(CommentRepo comments) {
		this.comments = comments;
	}

	@Override
	public Comment parse(String arg0, Locale arg1) throws ParseException {
		Comment comment = comments.findById(Integer.valueOf(arg0));
		if (comment == null) {
			throw new IllegalArgumentException("There is no comment with id="
					+ arg0);
		}
		return comment;
	}

	@Override
	public String print(Comment arg0, Locale arg1) { // TODO:check!
		return String.valueOf(arg0.getId());
	}
}
