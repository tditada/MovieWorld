package ar.edu.itba.paw.web;
import java.util.SortedSet;

import org.apache.wicket.ConverterLocator;
import org.apache.wicket.IConverterLocator;
import org.apache.wicket.Page;
import org.apache.wicket.Session;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.request.Request;
import org.apache.wicket.request.Response;
import org.apache.wicket.request.resource.PackageResourceReference;
import org.apache.wicket.request.resource.ResourceReference;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;
import org.hibernate.SessionFactory;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;

import ar.edu.itba.paw.model.Score;
import ar.edu.itba.paw.model.comment.Comment;
import ar.edu.itba.paw.model.comment.CommentRepo;
import ar.edu.itba.paw.model.genre.Genre;
import ar.edu.itba.paw.model.genre.GenreRepo;
import ar.edu.itba.paw.model.movie.Director;
import ar.edu.itba.paw.model.movie.Movie;
import ar.edu.itba.paw.model.movie.MovieRepo;
import ar.edu.itba.paw.model.user.Email;
import ar.edu.itba.paw.model.user.NonArtisticName;
import ar.edu.itba.paw.model.user.Password;
import ar.edu.itba.paw.model.user.User;
import ar.edu.itba.paw.model.user.UserRepo;
import ar.edu.itba.paw.web.common.HibernateRequestCycleListener;
import ar.edu.itba.paw.web.converter.CommentConverter;
import ar.edu.itba.paw.web.converter.DateConverter;
import ar.edu.itba.paw.web.converter.DirectorConverter;
import ar.edu.itba.paw.web.converter.EmailConverter;
import ar.edu.itba.paw.web.converter.GenreConverter;
import ar.edu.itba.paw.web.converter.GenreSortedSetConverter;
import ar.edu.itba.paw.web.converter.MovieConverter;
import ar.edu.itba.paw.web.converter.NonArtisticNameConverter;
import ar.edu.itba.paw.web.converter.PasswordConverter;
import ar.edu.itba.paw.web.converter.ScoreConverter;
import ar.edu.itba.paw.web.converter.UserConverter;
import ar.edu.itba.paw.web.homepage.HomePage;

public class MovieWorldApplication extends WebApplication {
	public static final ResourceReference STAR = new PackageResourceReference(MovieWorldApplication.class, "resources/star.png");

	private final SessionFactory sessionFactory;
	private final CommentRepo commentRepo;
	private final GenreRepo genreRepo;
	private final MovieRepo movieRepo;
	private final UserRepo userRepo;
	
	@Autowired
	public MovieWorldApplication(SessionFactory sessionFactory, CommentRepo commentRepo, GenreRepo genreRepo, MovieRepo movieRepo, UserRepo userRepo) {
		this.sessionFactory = sessionFactory;
		this.commentRepo = commentRepo;
		this.genreRepo = genreRepo;	
		this.movieRepo = movieRepo;
		this.userRepo = userRepo;
	}
	
	@Override
	public Class<? extends Page> getHomePage() {
		return HomePage.class;
	}
	
	@Override
	protected void init() {
		super.init();
		getComponentInstantiationListeners().add(new SpringComponentInjector(this));
		getRequestCycleListeners().add(new HibernateRequestCycleListener(sessionFactory));

	}

	@Override
	public Session newSession(Request request, Response response) {
		return new MovieWorldSession(request);
	}

	@Override
	protected IConverterLocator newConverterLocator() {
		ConverterLocator converterLocator = new ConverterLocator();
		converterLocator.set(DateTime.class, new DateConverter());
		converterLocator.set(Comment.class, new CommentConverter(commentRepo));
		converterLocator.set(Director.class, new DirectorConverter());
		converterLocator.set(Email.class, new EmailConverter());
		converterLocator.set(Genre.class, new GenreConverter(genreRepo));
//		converterLocator.set(SortedSet.class, new GenreSortedSetConverter(genreRepo));
		converterLocator.set(Movie.class,new MovieConverter(movieRepo));
		converterLocator.set(NonArtisticName.class, new NonArtisticNameConverter());
		converterLocator.set(Password.class, new PasswordConverter());
		converterLocator.set(Score.class, new ScoreConverter());
		converterLocator.set(User.class, new UserConverter(userRepo));
		return converterLocator;
	}
  

}
