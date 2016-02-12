package ar.edu.itba.paw.web.homepage;

import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import ar.edu.itba.paw.model.user.UserRepo;
import ar.edu.itba.paw.web.MovieWorldSession;
import ar.edu.itba.paw.web.base.BasePage;

//TODO: Links

@SuppressWarnings("serial")
public class HomePage extends BasePage {
	
	@SpringBean UserRepo userRepo;

	private static final int TOP_MOVIES_QUANTITY = 5;
	private static final int NEW_ADDITIONS_QUANTITY = 5;

	public static final String TOP_MOVIES_ID = "topMovies";
	public static final String RELEASES_ID = "releasesMovies";
	public static final String NEW_ADDITIONS_ID = "newAdditions";
	private static final String INTERESTING_COMMENTS_ID = "interestingComments";

	public HomePage() {

		add(new TopPanel(TOP_MOVIES_ID, TOP_MOVIES_QUANTITY));
		add(new NewAdditionsPanel(NEW_ADDITIONS_ID, NEW_ADDITIONS_QUANTITY));
		add(new ReleasesPanel(RELEASES_ID));
		
		Panel interestingComments = new InterestingCommentsPanel(INTERESTING_COMMENTS_ID);
		add(interestingComments);
		if(MovieWorldSession.get().getCurrentUser(userRepo) == null){
			interestingComments.setVisible(false);
		}
	}
}
