package com.mx.bmv.tecnologia.sms;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.apache.cxf.transport.servlet.CXFServlet;
import org.apache.log4j.Logger;
import org.springframework.core.env.PropertySourcesPropertyResolver;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

public class AppInitializer implements WebApplicationInitializer {
	private static final Logger LOGGER = Logger.getLogger(WebApplicationInitializer.class);
	
 	public PropertySourcesPropertyResolver propertySourcesPropertyResolver;

 	
	public void onStartup(ServletContext container) throws ServletException {
		AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
		context.register(ServiceConfiguration.class);
		container.addListener(new ContextLoaderListener(context));
		
		ServletRegistration.Dynamic cxfDispatcher = container.addServlet("cxfDispatcher", new CXFServlet());
		cxfDispatcher.setLoadOnStartup(1);
		cxfDispatcher.addMapping("/*");

		//JCM Refresa el contexto de spring para poder recuperar un bean
		context.refresh();
		
	}

	
}
