package ar.edu.itba.paw.web.movie;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import ar.edu.itba.paw.domain.EntityModel;
import ar.edu.itba.paw.model.comment.Comment;
import ar.edu.itba.paw.model.comment.CommentRepo;
import ar.edu.itba.paw.model.movie.Movie;

@SuppressWarnings("serial")
public class MovieCommentsPanel extends Panel {

	private EntityModel<Movie> movieModel;

	@SpringBean
	private CommentRepo commentRepo;

	public MovieCommentsPanel(String id, final Movie movie) {
		super(id);
		movieModel = new EntityModel<Movie>(Movie.class, movie);
		// add(new Label("commentAmount", new PropertyModel<String>(movie,
		// "title")));
		// TODO Auto-generated constructor stub

		add(new Label("movie.commentAmount", PropertyModel.of(movieModel, "totalComments")));

		IModel<List<Comment>> comments = new LoadableDetachableModel<List<Comment>>() {
			@Override
			protected List<Comment> load() {
				Set<Comment> sortedSet = movie.getComments();
				List<Comment> list = new ArrayList<Comment>(sortedSet);
				return list;
			}
		};
		
//		IModel<List<Comment>> commentsModel = new EntityModel<List<Comment>>(List.class, comments

		add(new ListView<Comment>("comments", comments) {

			@Override
			protected void populateItem(final ListItem<Comment> item) {
				String userName = item.getModelObject().getUser().getFirstName().getNameString()
						+ item.getModelObject().getUser().getLastName().getNameString();
				item.add(new Label("comment.user", userName));

				item.add(new StarsPanel("movie.scorePanel", item.getModelObject().getMovieScore().getValue()));

				EntityModel<Comment> commentModel = new EntityModel<Comment>(Comment.class,
						movie.getComments().first());
				item.add(new Label("comment.creationdate", PropertyModel.of(commentModel, "creationDate")));

				item.add(new Label("comment.text", PropertyModel.of(commentModel, "text")));
				item.add(new StarsPanel("comment.CommentScorePanel",
						item.getModelObject().getAverageCommentScore().getValue()));
			}
		});

	}
}
