package ar.edu.itba.paw.web.homepage;

import java.util.LinkedList;
import java.util.List;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.PropertyListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import ar.edu.itba.paw.domain.EntityModel;
import ar.edu.itba.paw.model.comment.Comment;
import ar.edu.itba.paw.model.movie.Movie;
import ar.edu.itba.paw.model.user.User;
import ar.edu.itba.paw.model.user.UserRepo;
import ar.edu.itba.paw.web.MovieWorldSession;
import ar.edu.itba.paw.web.movie.MoviePage;
import ar.edu.itba.paw.web.movie.MovieTitlePanel;
import ar.edu.itba.paw.web.user.UserCommentsPage;

@SuppressWarnings("serial")
public class InterestingCommentsPanel extends Panel {

	@SpringBean
	private UserRepo users;
	public InterestingCommentsPanel(String id) {
		super(id);

		final IModel<List<Comment>> interestingComments = new LoadableDetachableModel<List<Comment>>() {
			@Override
			protected List<Comment> load() {
				User user = MovieWorldSession.get().getCurrentUser(users);
				return getInterestingCommentsFor(user);
			}
		};
		
		add(new Label("noComments", getString("noComments")){
			@Override
			public boolean isVisible() {
				return super.isVisible() && interestingComments.getObject().size()==0;
			}
		});

		add(new PropertyListView<Comment>("interestingComments", interestingComments) {
			@Override
			protected void populateItem(final ListItem<Comment> item) {
				final Comment c =item.getModelObject();
				item.add(new Label("commentSummary", new PropertyModel<Movie>(item.getModel(), "text")));
				item.add(new Link<Void>("userLink"){
					@Override
					protected void onInitialize() {
						super.onInitialize();
						add(new Label("firstName", new PropertyModel<String>(item.getModel(), "user.firstName")));
						add(new Label("lastName", new PropertyModel<String>(item.getModel(), "user.lastName")));
					}
					@Override
					public void onClick() {
						setResponsePage(new UserCommentsPage(c.getUser()));
					}
				});
				item.add(new Link<Void>("movieLink"){
					IModel<Movie> movieModel = new EntityModel<Movie>(Movie.class,item.getModel().getObject().getMovie());
					@Override
					protected void onInitialize() {
                        add(new MovieTitlePanel("title", movieModel));
						super.onInitialize();
					}
					@Override
					public void onClick() {
						setResponsePage(new MoviePage(movieModel));
					}
				});
			}
		});
	}

	private List<Comment> getInterestingCommentsFor(User user) {
		List<Comment> interestingComments = new LinkedList<Comment>();
		for (User u : user.getInterestingUsers()) {
			interestingComments.addAll(u.getRecentComments());
		}
		return interestingComments;
	}

}
