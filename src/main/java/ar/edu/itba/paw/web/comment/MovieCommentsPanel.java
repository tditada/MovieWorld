package ar.edu.itba.paw.web.comment;

import java.util.List;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
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
import ar.edu.itba.paw.model.user.User;
import ar.edu.itba.paw.model.user.UserRepo;
import ar.edu.itba.paw.web.MovieWorldSession;
import ar.edu.itba.paw.web.movie.StarsPanel;
import ar.edu.itba.paw.web.user.UserCommentsPage;

@SuppressWarnings("serial")
public class MovieCommentsPanel extends Panel {

	private EntityModel<Movie> movieModel;

	@SpringBean
	private CommentRepo commentRepo;
	@SpringBean
	private UserRepo userRepo;

	public MovieCommentsPanel(String id, final IModel<Movie> movieModel) {
		super(id);
		Integer amount = movieModel.getObject().getTotalComments();
		Label amountLabel = new Label("movie.commentAmount", PropertyModel.of(movieModel, "totalComments"));
		Label commentsLabel = new Label("commentsLabel", getString("comments"));
		Label noCommentsLabel = new Label("noCommentsLabel", getString("noComments"));
		Label commentLabel = new Label("commentLabel", getString("comment"));
		
		if (amount == 0) {
			commentsLabel.setVisible(false);
			commentLabel.setVisible(false);
			amountLabel.setVisible(false);
		} else if (amount == 1) {
			noCommentsLabel.setVisible(false);
			commentsLabel.setVisible(false);
		} else {
			commentLabel.setVisible(false);
			noCommentsLabel.setVisible(false);
		}
		add(amountLabel);
		add(commentsLabel);
		add(noCommentsLabel);
		add(commentLabel);

		IModel<List<Comment>> comments = new LoadableDetachableModel<List<Comment>>() {
			@Override
			protected List<Comment> load() {
				return movieModel.getObject().getCommentsAsList();
			}
		};

		add(new ListView<Comment>("comments", comments) {

			@Override
			protected void populateItem(final ListItem<Comment> item) {
				item.add(new Link<Void>("userLink") {
					@Override
					protected void onInitialize() {
						String userName = item.getModelObject().getUser().getFirstName().getNameString()
								+ item.getModelObject().getUser().getLastName().getNameString();
						add(new Label("commentUser", userName));
						super.onInitialize();
					}

					@Override
					public void onClick() {
						setResponsePage(new UserCommentsPage(item.getModelObject().getUser()));
					}
				});

				item.add(new StarsPanel("movieScorePanel", item.getModelObject().getMovieScore().getValue()));

				item.add(new Label("commentCreationdate", PropertyModel.of(item.getModelObject(), "creationDate")));
				item.add(new Label("commentText", PropertyModel.of(item.getModelObject(), "text")));
				item.add(new StarsPanel("commentCommentScorePanel",
						item.getModelObject().getAverageCommentScore().getValue()));
				item.add(new ScoreCommentFormPanel("scoreCommentForm", item.getModelObject()));
				item.add(new ReportCommentPanel("reportCommentPanel", item.getModelObject()));
				User currentUser = MovieWorldSession.get().getCurrentUser(userRepo);
				DeleteCommentPanel delete = new DeleteCommentPanel("deleteCommentForm", item.getModelObject(), false);
				item.add(delete);
				if (currentUser == null || (currentUser != null && !currentUser.isAdmin())) {
					delete.setVisible(false);
				}
			}
		});

	}
}
