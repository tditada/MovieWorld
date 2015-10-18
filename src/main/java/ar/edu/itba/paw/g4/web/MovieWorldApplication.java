package ar.edu.itba.paw.g4.web;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.Page;
//import org.apache.wicket.ConverterLocator;
//import org.apache.wicket.IConverterLocator;
//import org.apache.wicket.Session;
//import org.apache.wicket.request.IRequestHandler;
//import org.apache.wicket.request.Request;
//import org.apache.wicket.request.Response;
//import org.apache.wicket.request.cycle.AbstractRequestCycleListener;
//import org.apache.wicket.request.cycle.RequestCycle;
//import org.apache.wicket.settings.IExceptionSettings;
//import org.apache.wicket.spring.injection.annot.SpringComponentInjector;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import ar.edu.itba.paw.g4.web.homepage.HomePage;

@Component
@PropertySource("classpath:login.properties")
public class MovieWorldApplication extends WebApplication {

  
	@Override
	public Class<? extends Page> getHomePage() {
		return HomePage.class;
	}


}
