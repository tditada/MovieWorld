package ar.edu.itba.paw.web.homepage;

import java.util.LinkedList;
import java.util.List;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.PropertyListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import ar.edu.itba.paw.model.comment.Comment;
import ar.edu.itba.paw.model.movie.Movie;
import ar.edu.itba.paw.model.user.User;
import ar.edu.itba.paw.model.user.UserRepo;
import ar.edu.itba.paw.web.MovieTitleStarsPanel;
import ar.edu.itba.paw.web.MovieWorldSession;

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
				return super.isVisible() && interestingComments.getObject().size()>0;
			}
		});

		add(new PropertyListView<Comment>("interestingComments", interestingComments) {
			@Override
			protected void populateItem(ListItem<Comment> item) {
				item.add(new Label("firstName", new PropertyModel<String>(item.getModel(), "user.firstName")));
				item.add(new Label("lastName", new PropertyModel<String>(item.getModel(), "user.lastName")));
				item.add(new MovieTitleStarsPanel("title", new PropertyModel<Movie>(item.getModel(), "movie.title")));
				item.add(new Label("commentSummary", new PropertyModel<Movie>(item.getModel(), "text")));
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
	
	// TODO: Performance?
    @Override
    public boolean isVisible() {
        return MovieWorldSession.get().isSignedIn();
    }

}
