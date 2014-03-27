package com.example.config;

import java.util.Arrays;

import javax.ws.rs.ext.RuntimeDelegate;

import org.apache.cxf.bus.spring.SpringBus;
import org.apache.cxf.endpoint.Server;
import org.apache.cxf.interceptor.Interceptor;
import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;
import org.apache.cxf.jaxrs.validation.JAXRSBeanValidationInInterceptor;
import org.apache.cxf.jaxrs.validation.JAXRSBeanValidationOutInterceptor;
import org.apache.cxf.jaxrs.validation.ValidationExceptionMapper;
import org.apache.cxf.message.Message;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import com.example.rs.JaxRsApiApplication;
import com.example.rs.PeopleRestService;
import com.example.services.PeopleService;
import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;

@Configuration
public class AppConfig {    
    @Bean( destroyMethod = "shutdown" )
    public SpringBus cxf() {
        return new SpringBus();
    }
    
    @Bean @DependsOn( "cxf" )
    public Server jaxRsServer() {
        final JAXRSServerFactoryBean factory = 
            RuntimeDelegate.getInstance().createEndpoint( 
                jaxRsApiApplication(), 
                JAXRSServerFactoryBean.class 
            );
        factory.setServiceBeans( Arrays.< Object >asList( peopleRestService() ) );
        factory.setAddress( factory.getAddress() );
        factory.setInInterceptors( 
            Arrays.< Interceptor< ? extends Message > >asList( 
                new JAXRSBeanValidationInInterceptor()
            ) 
        );
        factory.setOutInterceptors( 
            Arrays.< Interceptor< ? extends Message > >asList( 
                new JAXRSBeanValidationOutInterceptor() 
            ) 
        );
        factory.setProviders( 
            Arrays.asList( 
                new ValidationExceptionMapper(), 
                new JacksonJsonProvider() 
            ) 
        );
        
        return factory.create();
    }
    
    @Bean 
    public JaxRsApiApplication jaxRsApiApplication() {
        return new JaxRsApiApplication();
    }
    
    @Bean 
    public PeopleRestService peopleRestService() {
        return new PeopleRestService();
    }
    
    @Bean 
    public PeopleService peopleService() {
        return new PeopleService();
    }
}
