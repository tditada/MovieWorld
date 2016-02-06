package ar.edu.itba.paw.web.movie;

import java.util.List;

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
import ar.edu.itba.paw.web.MovieWorldSession;

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
		Integer amount = movie.getTotalComments();
		Label amountLabel = new Label("movie.commentAmount", PropertyModel.of(movieModel, "totalComments"));
		Label commentsLabel = new Label("commentsLabel",getString("comments"));
		Label noCommentsLabel = new Label("noCommentsLabel", getString("noComments"));
		Label commentLabel = new Label("commentLabel", getString("comment"));
		if(amount == 0){
			noCommentsLabel.setVisible(true);
			commentsLabel.setVisible(false);
			commentLabel.setVisible(false);
			amountLabel.setVisible(false);
		}else if(amount == 1){
			commentLabel.setVisible(true);
			noCommentsLabel.setVisible(false);
			commentsLabel.setVisible(false);
			amountLabel.setVisible(true);
		}else{
			commentsLabel.setVisible(true);
			commentLabel.setVisible(false);
			noCommentsLabel.setVisible(false);
			amountLabel.setVisible(true);
		}
		add(amountLabel);
		add(commentsLabel);
		add(noCommentsLabel);
		add(commentLabel);
		
		IModel<List<Comment>> comments = new LoadableDetachableModel<List<Comment>>() {
			@Override
			protected List<Comment> load() {
				return movie.getCommentsAsList();
			}
		};
		
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
