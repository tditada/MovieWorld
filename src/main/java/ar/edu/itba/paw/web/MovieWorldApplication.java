package ar.edu.itba.paw.web;
import org.apache.wicket.Page;
import org.apache.wicket.Session;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.request.Request;
import org.apache.wicket.request.Response;
import org.apache.wicket.request.resource.PackageResourceReference;
import org.apache.wicket.request.resource.ResourceReference;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;

import ar.edu.itba.paw.web.common.HibernateRequestCycleListener;
import ar.edu.itba.paw.web.homepage.HomePage;

@PropertySource("classpath:login.properties")
public class MovieWorldApplication extends WebApplication {
//	public static final ResourceReference ADD_ICON = new PackageResourceReference(DemoWicketApp.class, "resources/add.png");
//	public static final ResourceReference EDIT_ICON = new PackageResourceReference(DemoWicketApp.class, "resources/edit.gif");
//	public static final ResourceReference DELETE_ICON = new PackageResourceReference(DemoWicketApp.class, "resources/delete.gif");
	public static final ResourceReference STAR = new PackageResourceReference(MovieWorldApplication.class, "resources/star.png");

	private final SessionFactory sessionFactory;
//	private final SubjectRepo subjects;
	
	@Autowired
	public MovieWorldApplication(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
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

//	@Override
//	protected IConverterLocator newConverterLocator() {
//		ConverterLocator converterLocator = new ConverterLocator();
//		converterLocator.set(Professor.class, new ProfessorConverter());
//		converterLocator.set(Department.class, new DepartmentConverter());
//		converterLocator.set(Subject.class, new SubjectConverter(subjects));
//		return converterLocator;
//	}
  

}
