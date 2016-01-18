package ar.edu.itba.paw.web.homepage;

import ar.edu.itba.paw.web.base.BasePage;

//TODO: Links
//TODO: Comentarios
//TODO: estrellas al lado de las top5 peliculas

@SuppressWarnings("serial")
public class HomePage extends BasePage {

	private static final int TOP_MOVIES_QUANTITY = 5;
	private static final int NEW_ADDITIONS_QUANTITY = 5;

//	private static final String SESSION_USER_ID = "user";

	public static final String TOP_MOVIES_ID = "topMovies";
	public static final String RELEASES_ID = "releasesMovies";
	public static final String NEW_ADDITIONS_ID = "newAdditions";
//	private static final String USER_ID = "user";

//	private static final String INTERESTING_COMMENTS_ID = "interestingComments";
	
	public HomePage() {
				
	    add(new TopPanel(TOP_MOVIES_ID, TOP_MOVIES_QUANTITY));
		add(new NewAdditionsPanel(NEW_ADDITIONS_ID, NEW_ADDITIONS_QUANTITY));
		add(new ReleasesPanel(RELEASES_ID));
		
		// (If there is a user) Interesting Users' Comments of the Last Week
		
		

	}
}
