package ar.edu.itba.paw.web.comment;

import java.util.List;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.PropertyListView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import ar.edu.itba.paw.domain.EntityModel;
import ar.edu.itba.paw.model.comment.Comment;
import ar.edu.itba.paw.model.comment.CommentRepo;
import ar.edu.itba.paw.model.movie.Movie;
import ar.edu.itba.paw.model.user.UserRepo;
import ar.edu.itba.paw.util.persist.Orderings;
import ar.edu.itba.paw.web.base.SecuredPage;
import ar.edu.itba.paw.web.movie.MoviePage;
import ar.edu.itba.paw.web.user.UserCommentsPage;

@SuppressWarnings("serial")
public class ReportedCommentsPage extends SecuredPage {

	@SpringBean
	CommentRepo commentRepo;
	
	@SpringBean
	UserRepo userRepo;

	public ReportedCommentsPage() {
		IModel<List<Comment>> reportedComments = new LoadableDetachableModel<List<Comment>>() {
			@Override
			protected List<Comment> load() {
				return commentRepo.findReportedOrderedByReports(Orderings.DESC);
			}
		};

		add(new PropertyListView<Comment>("reportedComments", reportedComments) {
			@Override
			protected void populateItem(final ListItem<Comment> item) {
				item.add(new Link<Void>("commentUserLink") {
					protected void onInitialize() {
						super.onInitialize();
						add(new Label("commentUserEmail",
								new PropertyModel<String>(item.getModelObject().getUser(), "Email")));
					}

					@Override
					public void onClick() {
						setResponsePage(new UserCommentsPage(item.getModelObject().getUser()));
					};
				});
				item.add(new Link<Void>("commentMovieLink") {
					protected void onInitialize() {
						super.onInitialize();
						add(new Label("commentMovieTitle",
								new PropertyModel<String>(item.getModelObject().getMovie(), "title")));
					}

					@Override
					public void onClick() {
						IModel<Movie> movieModel = new EntityModel<Movie>(Movie.class,item.getModelObject().getMovie());
						setResponsePage(new MoviePage(movieModel));
					};
				});
				item.add(new Label("commentText", new PropertyModel<String>(item.getModelObject(), "text")));
				item.add(new Label("commentCreationDate", PropertyModel.of(item.getModelObject(), "creationDate")));
				item.add(new DeleteCommentPanel("deleteComment", item.getModelObject(), true));
				item.add(new DropReportedCommentsPanel("dropComments",item.getModel()));
				item.add(new Label("reports", new PropertyModel<Integer>(item.getModelObject(), "reportedSize")));
			};
		});
	}
	
}
