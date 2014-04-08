package com.example;

import org.apache.cxf.jaxrs.servlet.CXFNonSpringJaxrsServlet;
import org.apache.cxf.jaxrs.validation.JAXRSBeanValidationInInterceptor;
import org.apache.cxf.jaxrs.validation.JAXRSBeanValidationOutInterceptor;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import com.example.rs.JaxRsApiApplication;

public class ServerStarter {
    public static void main( final String[] args ) throws Exception {
        final Server server = new Server( 8080 );
                
        // Register and map the dispatcher servlet
        final ServletHolder servletHolder = new ServletHolder( new CXFNonSpringJaxrsServlet() );
        servletHolder.setInitParameter( "javax.ws.rs.Application",
        	JaxRsApiApplication.class.getName() );        
        servletHolder.setInitParameter( "jaxrs.inInterceptors",
        	JAXRSBeanValidationInInterceptor.class.getName() );	
        servletHolder.setInitParameter( "jaxrs.outInterceptors",
            JAXRSBeanValidationOutInterceptor.class.getName() );	
        
        final ServletContextHandler context = new ServletContextHandler();         
        context.setContextPath( "/" );
        context.addServlet( servletHolder, "/rest/*" );
                                  
        server.setHandler( context );
        server.start();
        server.join();    
    }
}

